package com.example.spotitubelukas.domain;

import java.util.ArrayList;

public class PlaylistDTO {

    private int id;
    private String name;
    private String owner;
    private ArrayList<TrackDTO> tracks;

    public PlaylistDTO(int id, String name, String owner, ArrayList<TrackDTO> tracks){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "PlaylistDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
