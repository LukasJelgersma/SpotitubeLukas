package com.example.spotitubelukas.services;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.domain.PlaylistDTO;
import com.example.spotitubelukas.domain.TrackDTO;
import com.example.spotitubelukas.domain.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Default
@ApplicationScoped
public class PlaylistService {

    private PlaylistDao playlistDao = new PlaylistDao();


    public PlaylistService() {
    }

    public Response getPlaylists(UserDTO user) {

        String username = user.getUsername();

        ArrayList<PlaylistDTO> playlists = playlistDao.getPlaylistInformation(username);

        // Create an empty JSON array for "playlists"
        JsonArrayBuilder playlistsArrayBuilder = Json.createArrayBuilder();

        for (PlaylistDTO playlist : playlists) {
            // Create a JSON object for each playlist
            JsonObject playlistObject = Json.createObjectBuilder()
                    .add("id", playlist.getId())
                    .add("name", playlist.getName())
                    .add("owner", playlist.getOwner())
                    .add("tracks", Json.createArrayBuilder().build()) // Assuming tracks are also in a JSON array
                    .build();

            playlistsArrayBuilder.add(playlistObject);
        }

        // Build the "playlists" array
        JsonArray playlistsArray = playlistsArrayBuilder.build();

        // Create the main JSON object
        JsonObject json = Json.createObjectBuilder()
                .add("playlists", playlistsArray)
                .add("length", 123445)
                .build();

        // Convert the JSON object to a string and return it
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
