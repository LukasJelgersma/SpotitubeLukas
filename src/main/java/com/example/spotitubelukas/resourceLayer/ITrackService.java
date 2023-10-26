package com.example.spotitubelukas.resourceLayer;

import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;

public interface ITrackService {
    TrackResponseDTO getPlaylistTracks(PlaylistDTO playlistDTO);

    TrackResponseDTO getAllAvailableTracks(PlaylistDTO playlistDTO);
}
