package com.example.spotitubelukas.services;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.UserDTO;
import com.example.spotitubelukas.dto.response.PlaylistResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Default
@ApplicationScoped
public class PlaylistService {

    @Inject
    private PlaylistDao playlistDao;


    public PlaylistService() {
    }

    public PlaylistResponseDTO getPlaylists(UserDTO user) {


        String username = user.getUser();
        // Convert the JSON object to a string and return it
        return playlistDao.getPlaylistInformation(username);
    }

    public PlaylistResponseDTO addPlaylist(UserDTO user, PlaylistDTO playlistDTO) {
        String username = user.getUser();

        playlistDao.addPlaylist(username, playlistDTO);

        return playlistDao.getPlaylistInformation(username);
    }

    public PlaylistResponseDTO deletePlaylist(UserDTO user, int id){
        String username = user.getUser();

        playlistDao.deletePlaylist(username, id);

        return playlistDao.getPlaylistInformation(username);
    }

    public PlaylistResponseDTO editPlaylist(UserDTO user, int id, PlaylistDTO playlistDTO){
        String username = user.getUser();

        playlistDao.editPlaylist(username, id, playlistDTO);

        return playlistDao.getPlaylistInformation(username);
    }
}
