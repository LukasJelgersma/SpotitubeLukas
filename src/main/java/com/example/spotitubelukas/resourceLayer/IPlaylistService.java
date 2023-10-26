package com.example.spotitubelukas.resourceLayer;

import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;


public interface IPlaylistService {
    PlaylistResponseDTO getPlaylists(UserDTO user);

    PlaylistResponseDTO addPlaylist(UserDTO user, PlaylistDTO playlistDTO);

    PlaylistResponseDTO deletePlaylist(UserDTO user, int id);

    PlaylistResponseDTO editPlaylist(UserDTO user, int id, PlaylistDTO playlistDTO);

    TrackResponseDTO addTrackToPlaylist(int id, TrackDTO trackDTO);

    TrackResponseDTO removeTrackFromPlaylist(int trackId, int playlistId);

    PlaylistDTO getPlaylistById(int id, UserDTO userDTO);

}
