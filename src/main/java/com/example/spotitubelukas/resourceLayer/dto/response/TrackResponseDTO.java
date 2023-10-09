package com.example.spotitubelukas.resourceLayer.dto.response;

import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;

import java.util.ArrayList;

public class TrackResponseDTO {
    private ArrayList<TrackDTO> tracks;

    public TrackResponseDTO(){

    }

    public TrackResponseDTO(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
