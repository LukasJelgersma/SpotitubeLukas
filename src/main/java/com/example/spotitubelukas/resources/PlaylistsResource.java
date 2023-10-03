package com.example.spotitubelukas.resources;

import com.example.spotitubelukas.domain.PlaylistDTO;
import com.example.spotitubelukas.domain.UserDTO;
import com.example.spotitubelukas.services.PlaylistService;
import com.example.spotitubelukas.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/playlists")
@ApplicationScoped
public class PlaylistsResource {

    PlaylistService playlistService;
    UserService userService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setUserService(UserService userService){
        this.userService = userService;
    }

//    @GET
//    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
//    public Response addToken(String token){
//        return Response.created(
//               UriBuilder.fromPath("playlists/{token}").build(token)
//        ).build();
//    }
//
//    @GET
//    @Path("/{token}")
//    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getPlaylists(@PathParam("token") String token) {
//       UserDTO user = userService.getUserByToken(token);
//       return playlistService.getPlaylists(user);
//    }

    @GET
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(String token) {
        UserDTO user = userService.getUserByToken(token);
        return playlistService.getPlaylists(user);
    }


}
