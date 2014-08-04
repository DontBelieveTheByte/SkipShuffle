package com.dontbelievethebyte.skipshuffle;

public class Track {
    private long _id;
    private String _path;

    public Track(){}

    public long getId() {
        return _id;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String _path) {
        this._path = _path;
    }
    public void setId(long id) {
        this._id = id;
    }

}
