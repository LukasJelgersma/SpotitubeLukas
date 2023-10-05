package com.example.spotitubelukas.resources;

import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.UserDTO;
import com.example.spotitubelukas.services.PlaylistService;
import com.example.spotitubelukas.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistsResource {

    @Inject
    private PlaylistService playlistService;

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        UserDTO user = userService.getUserByToken(token);
        return Response
                .status(200)
                .entity(playlistService.getPlaylists(user))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO){
        UserDTO user = userService.getUserByToken(token);
        return Response
                .status(201)
                .entity(playlistService.addPlaylist(user, playlistDTO))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id, PlaylistDTO playlistDTO){
        UserDTO user = userService.getUserByToken(token);
        return Response
                .status(200)
                .entity(playlistService.editPlaylist(user, id, playlistDTO))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id){
        UserDTO user = userService.getUserByToken(token);
        return Response
                .status(200)
                .entity(playlistService.deletePlaylist(user, id))
                .build();
    }
}
