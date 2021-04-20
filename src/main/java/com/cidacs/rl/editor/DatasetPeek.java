package com.cidacs.rl.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DatasetPeek {

    public enum DatasetPeekResult {
        FILE_NOT_FOUND, UNSUPPORTED_FORMAT, IO_ERROR, UNSUPPORTED_CONTENTS,
        SUCCESS
    }

    private String fileName;
    Set<String> columnNames;

    public DatasetPeek(String fileName) {
        this.fileName = fileName;
    }

    public DatasetPeekResult peek() {
        File f = new File(fileName);
        if (!f.isFile())
            return DatasetPeekResult.FILE_NOT_FOUND;
        String nameLower = fileName.toLowerCase();
        if (nameLower.endsWith(".csv"))
            return peekCSV();
        if (nameLower.endsWith(".dbf"))
            return peekDBF();
        return null;
    }

    private enum CsvChar {
        QUOTE, COMMA, NEWLINE, OTHER
    }

    private enum CsvLineParseState {
        START, UNQUOTED_FIELD, QUOTED_FIELD, POST_QUOTED_FIELD, FINAL
    }

    private DatasetPeekResult peekCSV() {
        String header;
        char delimiter = ',';
        columnNames = new LinkedHashSet<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName)))) {
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
            }
            if (currentColumnName.length() > 0) {
                columnNames.add(currentColumnName.toString());
            }
        } catch (FileNotFoundException e) {
            return DatasetPeekResult.FILE_NOT_FOUND;
        } catch (IOException e) {
            return DatasetPeekResult.IO_ERROR;
        }
        return null;
    }

    private DatasetPeekResult peekDBF() {
        columnNames = new LinkedHashSet<>();
        try {
            FileInputStream is = new FileInputStream(fileName);
            int firstByte = is.read();
            if (firstByte == -1)
                return DatasetPeekResult.UNSUPPORTED_CONTENTS;
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
            if (is.read(bytes) != bytes.length)
                return DatasetPeekResult.UNSUPPORTED_CONTENTS;
            bytes = new byte[bytesInHeaderPerColumn];
            while (is.read(bytes) == bytes.length) {
                if (bytes[0] == 0x0D)
                    break;
                String colName = new String(bytes, 0, maxColNameLength)
                        .replaceAll("\\x00", "");
                columnNames.add(colName);
            }
        } catch (FileNotFoundException e) {
            return DatasetPeekResult.FILE_NOT_FOUND;
        } catch (IOException e) {
            return DatasetPeekResult.IO_ERROR;
        }
        return null;
    }

    public Set<String> getColumnNames() {
        return Collections.unmodifiableSet(columnNames);
    }

    public static void main(String[] args) {
        System.out.println(Charset.forName("utf8").name());
        DatasetPeek p = new DatasetPeek("/tmp/test.dbf");
        p.peek();
        for (String c : p.getColumnNames())
            System.out.println("«" + c + "»");
    }

}
