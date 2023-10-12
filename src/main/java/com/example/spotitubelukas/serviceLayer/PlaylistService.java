package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Default
@ApplicationScoped
public class PlaylistService {
    @Inject
    private PlaylistDao playlistDao;

    @Inject
    private TrackService trackService;


    public PlaylistService() {
    }

    public PlaylistResponseDTO getPlaylists(UserDTO user) {


        String username = user.getUser();
        PlaylistResponseDTO playlistResponseDTO = playlistDao.getPlaylistResponse(username);

        //playlists.addAll(playlistResponseDTO.getPlaylists());

        // Convert the JSON object to a string and return it
        return playlistDao.getPlaylistResponse(username);
    }

    public PlaylistResponseDTO addPlaylist(UserDTO user, PlaylistDTO playlistDTO) {
        String username = user.getUser();

        playlistDao.addPlaylist(username, playlistDTO);

        return playlistDao.getPlaylistResponse(username);
    }

    public PlaylistResponseDTO deletePlaylist(UserDTO user, int id){
        String username = user.getUser();

        playlistDao.deletePlaylist(username, id);

        return playlistDao.getPlaylistResponse(username);
    }

    public PlaylistResponseDTO editPlaylist(UserDTO user, int id, PlaylistDTO playlistDTO){
        String username = user.getUser();

        playlistDao.editPlaylist(username, id, playlistDTO);

        return playlistDao.getPlaylistResponse(username);
    }

    public TrackResponseDTO addTrackToPlaylist(int id, TrackDTO trackDTO, UserDTO userDTO){

        playlistDao.addTrackToPlaylist(id, trackDTO);

        PlaylistDTO playlistDTO = playlistDao.getPlaylistById(id, userDTO.getUser());

        playlistDTO.getTracks().add(trackDTO);

        return new TrackResponseDTO(playlistDTO.getTracks());
    }

    public TrackResponseDTO removeTrackFromPlaylist(int trackId, int playlistId, UserDTO userDTO){

        playlistDao.removeTrackFromPlaylist(trackId, playlistId);

        return new TrackResponseDTO(playlistDao.getAllTracks(playlistId));
    }

    public PlaylistDTO getPlaylistById(int id, UserDTO userDTO){
        return (playlistDao.getPlaylistById(id, userDTO.getUser()));
    }
}
