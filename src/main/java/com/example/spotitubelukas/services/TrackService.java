package com.example.spotitubelukas.services;

import com.example.spotitubelukas.datasource.TrackDao;
import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Default
@ApplicationScoped
public class TrackService {

    @Inject
    private TrackDao trackDao;

    public TrackService(){

    }

    public TrackResponseDTO getTracks(int id, PlaylistDTO playlistDTO){


    }
}
