package com.dontbelievethebyte.skipshuffle.exceptions;

import java.io.File;

public class SubdirectoryException extends Exception{

    private File subdirectory;

    public SubdirectoryException(File subdirectory)
    {
        this.subdirectory = subdirectory;
    }

    public File getSubdirectory()
    {
        return subdirectory;
    }
}
