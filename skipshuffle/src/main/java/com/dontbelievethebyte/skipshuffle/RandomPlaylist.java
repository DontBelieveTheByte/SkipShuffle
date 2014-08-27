package com.dontbelievethebyte.skipshuffle;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private Long playlistId;

    private DbHandler dbHandler;

    private int playlistPosition = 0;

    private List<Long> tracksIds = new ArrayList<Long>();

    public RandomPlaylist(DbHandler dbHandler){
        this.dbHandler = dbHandler;
    }

    public RandomPlaylist(Long playlistId, DbHandler dbHandler) throws JSONException {
        this.dbHandler = dbHandler;
        this.playlistId = playlistId;
        tracksIds = dbHandler.loadPlaylist((long)playlistId);
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public List<Long> getList(){
        return tracksIds;
    };

    @Override
    public void addTrack(Track track) {
        if(!tracksIds.contains(track.getId())){
            tracksIds.add(track.getId());
        }
    }

    @Override
    public void removeTrack(Track track) {
        if(tracksIds.contains(track.getId())){
            tracksIds.remove(track.getId());
        }
    }

    @Override
    public Track getFirst() {
        return dbHandler.getTrack(tracksIds.get(0));
    }

    @Override
    public Track getCurrent() {
        return dbHandler.getTrack(tracksIds.get(playlistPosition + 1));
    }

    @Override
    public Track getAtPosition(int position) {
        if(position > tracksIds.size()){
            position = tracksIds.size();
        }
        return dbHandler.getTrack(tracksIds.get(position));
    }

    @Override
    public Track getNext() {
        if(playlistPosition >= tracksIds.size()){
            playlistPosition = 0;
        } else {
            playlistPosition++;
        }
        return dbHandler.getTrack((long)playlistPosition);
    }

    @Override
    public Track getPrev() {
        if(playlistPosition <= 0){
            playlistPosition = 0;
        } else {
            playlistPosition--;
        }
        return dbHandler.getTrack((long)playlistPosition);
    }

    @Override
    public long getId() {
        return playlistId;
    }

    @Override
    public int getPosition() {
        return playlistPosition;
    }

    @Override
    public void setPosition(int position) {
        if(position > tracksIds.size()){
            playlistPosition = tracksIds.size();
        } else if(position < 0){
            playlistPosition = 0;
        } else {
            playlistPosition = position;
        }
    }

    @Override
    public void shuffle(){
        Collections.shuffle(tracksIds);
        playlistPosition = 0;
    }

    @Override
    public void save(){
        if(playlistId != null) {
            dbHandler.savePlaylist((long)playlistId, tracksIds);
        } else {
            dbHandler.savePlaylist(null, tracksIds);
        }
    }

    @Override
    public int getSize(){
        return tracksIds.size();
    }

    public List<Track> getAllTracks(){
        return dbHandler.getAllPlaylistTracks(tracksIds);
    }
}
