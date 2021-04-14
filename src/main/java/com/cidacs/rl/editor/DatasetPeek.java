package com.cidacs.rl.editor;
import java.io.File;

public class DatasetPeek {

    public enum DatasetPeekResult {
        FILE_NOT_FOUND,
        UNSUPPORTED_FORMAT,
        IO_ERROR,
        UNSUPPORTED_CONTENTS,
        SUCCESS
    }

    private String fileName;

    public DatasetPeek(String fileName) {
        this.fileName = fileName;
    }

    public DatasetPeekResult peek() {
        File f = new File(fileName);
        if (!f.isFile())
            return DatasetPeekResult.FILE_NOT_FOUND;
        return null;
    }

    private DatasetPeekResult peekCsv() {
        return null;
    }

}
