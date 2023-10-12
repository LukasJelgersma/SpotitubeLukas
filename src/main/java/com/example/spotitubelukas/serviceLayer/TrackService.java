package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.datasource.TrackDao;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.Connection;

@Default
@ApplicationScoped
public class TrackService {

    @Inject
    private TrackDao trackDao;

    @Inject
    private PlaylistDao playlistDao;

    public TrackService(){

    }

    public TrackResponseDTO getPlaylistTracks(PlaylistDTO playlistDTO){
        return new TrackResponseDTO(playlistDao.getAllTracks(playlistDTO.getId()));
    }

    public TrackResponseDTO getAllAvailableTracks(PlaylistDTO playlistDTO){

        return trackDao.getAvailableTracks(playlistDTO.getId());
    }

}
