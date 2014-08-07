package com.dontbelievethebyte.skipshuffle;

import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private Long playlistId;

    private DbHandler dbHandler;

    private int playlistPosition = 0;

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

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
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
    public Track getCurrent() {
        return dbHandler.getTrack(_tracksIds.get((int) playlistPosition));
    }

    @Override
    public Track getAtPosition(int position) {
        if(position > _tracksIds.size()){
            position = _tracksIds.size();
        }
        return dbHandler.getTrack(_tracksIds.get(position));
    }

    @Override
    public Track getNext() {
        if(playlistPosition >= _tracksIds.size()){
            playlistPosition = 0;
        } else {
            playlistPosition++;
        }
        Track track;
        track = dbHandler.getTrack((long)playlistPosition);
        return track;
    }

    @Override
    public Track getPrev() {
        if(playlistPosition <= 0){
            playlistPosition = 0;
        } else {
            playlistPosition--;
        }
        Track track;
        track = dbHandler.getTrack((long)playlistPosition);
        return track;
    }

    @Override
    public int getPosition() {
        return playlistPosition;
    }

    @Override
    public void setPosition(int position) {
        if(position > _tracksIds.size()){
            playlistPosition = _tracksIds.size();
        } else if(position < 0){
            playlistPosition = 0;
        } else {
            playlistPosition = position;
        }
    }

    @Override
    public void shuffle(){
        Collections.shuffle(_tracksIds);
        playlistPosition = 0;
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
