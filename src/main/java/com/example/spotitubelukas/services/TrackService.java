package com.example.spotitubelukas.services;

import com.example.spotitubelukas.datasource.TrackDao;
import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.response.PlaylistResponseDTO;
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

    public TrackResponseDTO getPlaylistTracks(PlaylistDTO playlistDTO){
        return new TrackResponseDTO(playlistDTO.getTracks());
    }

    public TrackResponseDTO getAllAvailableTracks(PlaylistDTO playlistDTO){

        return trackDao.getAvailableTracks(playlistDTO.getId());
    }

}
