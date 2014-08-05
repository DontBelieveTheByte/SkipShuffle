package com.dontbelievethebyte.skipshuffle;

import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private Long playlistId;

    private DbHandler dbHandler;

    private long _cursor = 0;

    private List<Long> _tracksIds = new ArrayList<Long>();

    public RandomPlaylist(DbHandler dbHandler){
        this.dbHandler = dbHandler;
    }

    public RandomPlaylist(Long playlistId, DbHandler dbHandler){
        this.dbHandler = dbHandler;
        this.playlistId = playlistId;
        try {
            _tracksIds = dbHandler.loadPlaylist(playlistId);
        } catch (JSONException e) {
            Log.d("TAGGG", e.toString());
        }
        Log.d("PLAYLIST INFO", _tracksIds.toString());
    }

    @Override
    public List<Long> getList(){
        return _tracksIds;
    };

    @Override
    public void addTrack(Track track) {
        if(!_tracksIds.contains(track.getId())){
            _tracksIds.add(track.getId());
        }
    }

    @Override
    public void removeTrack(Track track) {
        if(_tracksIds.contains(track.getId())){
            _tracksIds.remove(track.getId());
        }
    }

    @Override
    public Track getFirst() {
        return dbHandler.getTrack(_tracksIds.get(0));
    }

    @Override
    public Track getNext() {
        if(_cursor >= _tracksIds.size()){
            _cursor = 0;
        } else {
            _cursor++;
        }
        Track track;
        track = dbHandler.getTrack(_cursor);
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
        track = dbHandler.getTrack(_cursor);
        return track;
    }

    @Override
    public long getCursorPosition() {
        return _cursor;
    }

    @Override
    public void setCursorPosition(long position) {
        if(position > (long) _tracksIds.size()){
            _cursor = _tracksIds.size();
        } else if(position < 0){
            _cursor = 0;
        } else {
            _cursor = position;
        }
    }

    @Override
    public void shuffle(){
        Collections.shuffle(_tracksIds);
        _cursor = 0;
    }

    @Override
    public void save(){
        if(playlistId != null) {
            dbHandler.savePlaylist(playlistId, _tracksIds);
        } else {
            dbHandler.savePlaylist(null, _tracksIds);
        }
    }

    @Override
    public int getSize(){
        return _tracksIds.size();
    }

    public List<Track> getAllTracks(){
        return dbHandler.getAllPlaylistTracks(_tracksIds);
    }
}
