package com.dontbelievethebyte.skipshuffle;

public class Track {
    private int _id;
    private String _checksum;
    private String _path;

    public Track(){

    }

    public Track(int id, String checksum, String path){
        _id = id;
        _checksum = checksum;
        _path = path;
    }

    public String getChecksum() {
        return _checksum;
    }

    public void setChecksum(String _checksum) {
        this._checksum = _checksum;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String _path) {
        this._path = _path;
    }

}
