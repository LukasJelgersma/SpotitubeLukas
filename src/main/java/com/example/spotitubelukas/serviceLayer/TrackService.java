package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasourceLayer.PlaylistDao;
import com.example.spotitubelukas.datasourceLayer.TrackDao;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Default
@ApplicationScoped
public class TrackService {
    private TrackDao trackDao;
    private PlaylistDao playlistDao;

    public TrackService(){

    }

    public TrackResponseDTO getPlaylistTracks(PlaylistDTO playlistDTO){
        return new TrackResponseDTO(playlistDao.getAllTracks(playlistDTO.getId()));
    }

    public TrackResponseDTO getAllAvailableTracks(PlaylistDTO playlistDTO){

        return trackDao.getAvailableTracks(playlistDTO.getId());
    }

    @Inject
    public void setTrackDao(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

}
