package recovida.idas.rl.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

/**
 * This class provides methods to "peek" into dataset files and read their
 * column names.
 */
public class DatasetPeek {

    /**
     * Represents a result of the "peek".
     */
    public enum DatasetPeekResult {

        /**
         * File name is blank.
         */
        BLANK_NAME,

        /**
         * File does not exist.
         */
        FILE_NOT_FOUND,

        /**
         * Format is not supported.
         */
        UNSUPPORTED_FORMAT,

        /**
         * Error when reading.
         */
        IO_ERROR,

        /**
         * Contents are not supported. Possibly the specified encoding is
         * incorrect, or the file contents don't correspond to its extension.
         */
        UNSUPPORTED_CONTENTS,

        /**
         * Column names read successfully.
         */
        SUCCESS
    }

    private final Path directory;

    private final String fileName;

    private final String encoding;

    Set<String> columnNames;

    DatasetPeekResult result = null;

    /**
     * Creates an instance.
     *
     * @param directory directory where the file is saved
     * @param fileName  name of the file (without the path)
     * @param encoding  the encoding of the file
     */
    public DatasetPeek(Path directory, String fileName, String encoding) {
        this.fileName = fileName;
        this.directory = directory;
        if (encoding != null && encoding.toUpperCase()
                .replaceAll("[^A-Z0-9]", "").equals("ANSI"))
            encoding = "Cp1252";
        this.encoding = encoding;
        columnNames = null;
    }

    /**
     * Tries to read column names from the dataset.
     *
     * @return the result of the operation
     */
    public DatasetPeekResult peek() {
        result = peek0();
        return result;
    }

    protected synchronized DatasetPeekResult peek0() {
        if (fileName == null || fileName.isEmpty())
            return DatasetPeekResult.BLANK_NAME;
        File f;
        if (directory != null) {
            f = directory.resolve(fileName).toFile();
        } else if (!Paths.get(fileName).isAbsolute()) {
            // without a directory, we must have an absolute path
            return DatasetPeekResult.FILE_NOT_FOUND;
        } else
            f = new File(fileName);
        if (!f.isFile())
            return DatasetPeekResult.FILE_NOT_FOUND;
        String nameLower = fileName.toLowerCase();
        if (nameLower.endsWith(".csv"))
            return peekCSV();
        if (nameLower.endsWith(".dbf"))
            return peekDBF();
        return DatasetPeekResult.UNSUPPORTED_FORMAT;
    }

    public static String[] getSupportedExtensions() {
        return new String[] { "csv", "dbf" };
    }

    /**
     * Character categories in a CSV file.
     */
    private enum CsvChar {
        QUOTE, COMMA, NEWLINE, OTHER
    }

    /**
     * Parsing state.
     */
    private enum CsvLineParseState {
        START, UNQUOTED_FIELD, QUOTED_FIELD, POST_QUOTED_FIELD, FINAL
    }

    private DatasetPeekResult peekCSV() {
        char delimiter = ',';

        // try to guess delimiter
        try (FileInputStream is = new FileInputStream(fileName)) {
            byte[] bytes = new byte[1024];
            int n = is.read(bytes);
            if (n == -1)
                return DatasetPeekResult.UNSUPPORTED_CONTENTS;
            Map<Character, Integer> delimiterFreq = new HashMap<>();
            delimiterFreq.put(',', 0);
            delimiterFreq.put(';', 0);
            delimiterFreq.put('|', 0);
            delimiterFreq.put('\t', 0);
            for (int i = 0; i < n; i++) {
                char c = (char) bytes[i];
                if (delimiterFreq.containsKey(c))
                    delimiterFreq.put(c, delimiterFreq.get(c) + 1);
            }
            delimiter = Collections.max(delimiterFreq.keySet(),
                    (Character c1, Character c2) -> delimiterFreq.get(c1)
                            - delimiterFreq.get(c2));
        } catch (FileNotFoundException ex) {
            return DatasetPeekResult.FILE_NOT_FOUND;
        } catch (IOException ex) {
            return DatasetPeekResult.IO_ERROR;
        }

        columnNames = new LinkedHashSet<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new BOMInputStream(new FileInputStream(fileName),
                                ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE,
                                ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_32LE,
                                ByteOrderMark.UTF_32BE),
                        Charset.forName(encoding)))) {
            int c;
            CsvLineParseState state = CsvLineParseState.START;
            StringBuilder currentColumnName = new StringBuilder();
            while (-1 != (c = reader.read())
                    && state != CsvLineParseState.FINAL) {
                char ch = (char) c;
                CsvChar type = null;
                if (ch == '\r' || ch == '\n')
                    type = CsvChar.NEWLINE;
                else if (ch == delimiter)
                    type = CsvChar.COMMA;
                else if (ch == '"')
                    type = CsvChar.QUOTE;
                else
                    type = CsvChar.OTHER;
                switch (state) {
                case START:
                    if (type == CsvChar.COMMA)
                        columnNames.add(""); // empty field
                    else if (type == CsvChar.NEWLINE)
                        state = CsvLineParseState.FINAL;
                    else if (type == CsvChar.QUOTE)
                        state = CsvLineParseState.QUOTED_FIELD;
                    else if (type == CsvChar.OTHER) {
                        currentColumnName.append(ch);
                        state = CsvLineParseState.UNQUOTED_FIELD;
                    }
                    break;
                case UNQUOTED_FIELD:
                    if (type == CsvChar.COMMA) {
                        columnNames.add(currentColumnName.toString());
                        currentColumnName.setLength(0);
                        state = CsvLineParseState.START;
                    } else if (type == CsvChar.NEWLINE)
                        state = CsvLineParseState.FINAL;
                    else
                        currentColumnName.append(ch);
                    break;
                case QUOTED_FIELD:
                    if (type == CsvChar.QUOTE)
                        state = CsvLineParseState.POST_QUOTED_FIELD;
                    else
                        currentColumnName.append(ch);
                    break;
                case POST_QUOTED_FIELD:
                    if (type == CsvChar.COMMA) {
                        columnNames.add(currentColumnName.toString());
                        currentColumnName.setLength(0);
                        state = CsvLineParseState.START;
                    } else if (type == CsvChar.NEWLINE)
                        state = CsvLineParseState.FINAL;
                    else if (type == CsvChar.QUOTE) {
                        currentColumnName.append(ch);
                        state = CsvLineParseState.QUOTED_FIELD;
                    } else if (type == CsvChar.OTHER) {
                        // should not happen
                        state = CsvLineParseState.UNQUOTED_FIELD;
                    }
                    break;
                case FINAL:
                    break;
                }
                if (currentColumnName.length() > 10000) {
                    columnNames = null;
                    return DatasetPeekResult.UNSUPPORTED_CONTENTS;
                }
            }
            if (currentColumnName.length() > 0) {
                columnNames.add(currentColumnName.toString());
            }
        } catch (FileNotFoundException e) {
            columnNames = null;
            return DatasetPeekResult.FILE_NOT_FOUND;
        } catch (IOException e) {
            columnNames = null;
            return DatasetPeekResult.IO_ERROR;
        } catch (UnsupportedCharsetException e) {
            columnNames = null;
            return DatasetPeekResult.UNSUPPORTED_CONTENTS;
        }
        return DatasetPeekResult.SUCCESS;
    }

    private DatasetPeekResult peekDBF() {
        columnNames = new LinkedHashSet<>();
        try (FileInputStream is = new FileInputStream(fileName)) {
            int firstByte = is.read();
            if (firstByte == -1) {
                columnNames = null;
                return DatasetPeekResult.UNSUPPORTED_CONTENTS;
            }
            int bytesInHeaderBeforeColNames, bytesInHeaderPerColumn,
                    maxColNameLength;
            if ((firstByte & 0x07) == 0x04) {
                bytesInHeaderBeforeColNames = 68;
                bytesInHeaderPerColumn = 48;
                maxColNameLength = 32;
            } else {
                bytesInHeaderBeforeColNames = 32;
                bytesInHeaderPerColumn = 32;
                maxColNameLength = 11;
            }
            byte[] bytes = new byte[bytesInHeaderBeforeColNames - 1];
            if (is.read(bytes) != bytes.length) {
                columnNames = null;
                return DatasetPeekResult.UNSUPPORTED_CONTENTS;
            }
            bytes = new byte[bytesInHeaderPerColumn];
            while (is.read(bytes) == bytes.length) {
                if (bytes[0] == 0x0D)
                    break;
                String colName = new String(bytes, 0, maxColNameLength)
                        .replaceAll("\\x00", "");
                columnNames.add(colName);
            }
        } catch (FileNotFoundException e) {
            columnNames = null;
            return DatasetPeekResult.FILE_NOT_FOUND;
        } catch (IOException e) {
            columnNames = null;
            return DatasetPeekResult.IO_ERROR;
        }
        return DatasetPeekResult.SUCCESS;
    }

    public Set<String> getColumnNames() {
        return columnNames == null ? null
                : Collections.unmodifiableSet(columnNames);
    }

    public DatasetPeekResult getResult() {
        return result;
    }
}
