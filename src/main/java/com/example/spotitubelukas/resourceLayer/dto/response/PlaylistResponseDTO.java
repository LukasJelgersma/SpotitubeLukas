package com.example.spotitubelukas.resourceLayer.dto.response;

import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;

import java.util.ArrayList;

public class PlaylistResponseDTO {

    private ArrayList<PlaylistDTO> playlists;
    private int length;

    public PlaylistResponseDTO(){

    }

    public PlaylistResponseDTO(ArrayList<PlaylistDTO> playlists, int length){
        this.playlists = playlists;
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
