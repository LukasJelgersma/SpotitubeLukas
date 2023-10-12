package com.example.spotitubelukas.resourceLayer.resources;

import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.serviceLayer.PlaylistService;
import com.example.spotitubelukas.serviceLayer.TrackService;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource {

    @Inject
    private PlaylistService playlistService;
    @Inject
    private UserService userService;

    @Inject
    private TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int id) {
        UserDTO user = userService.getUserByToken(token);
        PlaylistDTO playlistDTO = playlistService.getPlaylistById(id, user);

        return Response
                .status(200)
                .entity(trackService.getAllAvailableTracks(playlistDTO))
                .build();
    }

}
