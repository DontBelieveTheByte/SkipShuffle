package com.dontbelievethebyte.skipshuffle.services;

public class MediaScanStatus {

    public String currentDirectory;
    public String currentFile;
    public boolean isLastFile;

    public MediaScanStatus()
    {

    }

    public String getCurrentDirectory()
    {
        return currentDirectory;
    }

    public void setCurrentDirectory(String currentDirectory)
    {
        this.currentDirectory = currentDirectory;
    }

    public String getCurrentFile()
    {
        return currentFile;
    }

    public void setCurrentFile(String currentFile)
    {
        this.currentFile = currentFile;
    }

    public boolean isLastFile() {
        return isLastFile;
    }

    public void setLastFile(boolean isLastFile)
    {
        this.isLastFile = isLastFile;
    }
}
