package com.dontbelievethebyte.skipshuffle.activities.exception;

import java.io.File;

public class ParentDirectoryException extends Exception {

    private File parentDirectory;

    public ParentDirectoryException(File parentDirectory)
    {
        this.parentDirectory = parentDirectory;
    }

    public File getParentDirectory()
    {
        return parentDirectory;
    }
}