package com.dontbelievethebyte.skipshuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private Integer playlistId;

    private DbHandler _DbHandler;

    private int _cursor = 0;

    private List<Integer> _tracksIds = new ArrayList<Integer>();

    public RandomPlaylist(DbHandler dbHandler){
        _DbHandler = dbHandler;
    }
    public RandomPlaylist(int Id, DbHandler dbHandler){
        _DbHandler = dbHandler;
        playlistId = Id;
    }

    @Override
    public List<Integer> getList(){
        return _tracksIds;
    };

    @Override
    public int addTrack(Track track) {
        if(!_tracksIds.contains(track.getId())){
            _tracksIds.add(track.getId());
        }
        return 0;
    }

    @Override
    public void removeTrack(Track track) {
        if(_tracksIds.contains(track.getId())){
            _tracksIds.remove(track.getId());
        }
    }

    @Override
    public Track getFirst() {
        return _DbHandler.getTrack(0);
    }

    @Override
    public Track getNext() {
        if(_cursor >= _tracksIds.size()){
            _cursor = 0;
        } else {
            _cursor++;
        }
        Track track;
        track = _DbHandler.getTrack(_cursor);
        return track;
    }

    @Override
    public Track getPrev() {
        if(_cursor <= 0){
            _cursor = 0;
        } else {
            _cursor--;
        }
        Track track;
        track = _DbHandler.getTrack(_cursor);
        return track;
    }

    @Override
    public int getCursorPosition() {
        return _cursor;
    }

    @Override
    public int setCursorPosition(int position) {
        if(position > _tracksIds.size()){
            _cursor = _tracksIds.size();
        } else if(position < 0){
            _cursor = 0;
        } else {
            _cursor = position;
        }
        return _cursor;
    }

    @Override
    public void shuffle(){
        Collections.shuffle(_tracksIds);
        _cursor = 0;
    }

    @Override
    public void save(){
        if(playlistId != null) {
            _DbHandler.savePlaylist(playlistId, _tracksIds);
        } else {
            _DbHandler.savePlaylist(null, _tracksIds);
        }
    }

    public List<Track> getAllTracks(){
        return _DbHandler.getAllPlaylistTracks(_tracksIds);
    }
}
