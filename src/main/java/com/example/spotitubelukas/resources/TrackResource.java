package com.example.spotitubelukas.resources;

import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.UserDTO;
import com.example.spotitubelukas.services.PlaylistService;
import com.example.spotitubelukas.services.TrackService;
import com.example.spotitubelukas.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.sound.midi.Track;

@Path("/playlists")
public class TrackResource {

    @Inject
    private PlaylistService playlistService;
    @Inject
    private UserService userService;

    @Inject
    private TrackService trackService;

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("token") String token, @PathParam("id") int id) {
        UserDTO user = userService.getUserByToken(token);
        PlaylistDTO playlistDTO = playlistService.getPlaylists(user);

        return Response
                .status(200)
                .entity(trackService.getTracks(id, ))
                .build();
    }

}
