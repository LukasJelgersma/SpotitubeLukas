package com.example.spotitubelukas.dto.response;

import com.example.spotitubelukas.dto.TrackDTO;

import java.util.ArrayList;

public class TrackResponseDTO {
    private ArrayList<TrackDTO> tracks;

    public TrackResponseDTO(){

    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
