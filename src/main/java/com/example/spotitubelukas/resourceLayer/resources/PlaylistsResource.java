package com.example.spotitubelukas.resourceLayer.resources;

import com.example.spotitubelukas.resourceLayer.IPlaylistService;
import com.example.spotitubelukas.resourceLayer.ITrackService;
import com.example.spotitubelukas.resourceLayer.IUserService;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.serviceLayer.PlaylistService;
import com.example.spotitubelukas.serviceLayer.TrackService;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistsResource {

    private IPlaylistService playlistService;

    private IUserService userService;

    private ITrackService trackService;

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
                .status(201)
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

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("token") String token, @PathParam("id") int id) {
        UserDTO user = userService.getUserByToken(token);
        PlaylistDTO playlistDTO = playlistService.getPlaylistById(id, user);

        return Response
                .status(200)
                .entity(trackService.getPlaylistTracks(playlistDTO))
                .build();
    }

    @POST
    @Path("{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrack(@QueryParam("token") String token, @PathParam("id") int id, TrackDTO trackDTO){
        UserDTO user = userService.getUserByToken(token);
        return Response
                .status(201)
                .entity(playlistService.addTrackToPlaylist(id, trackDTO))
                .build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrack(@PathParam("trackId") int trackId, @PathParam("playlistId") int playlistId){
        return Response
                .status(200)
                .entity(playlistService.removeTrackFromPlaylist(trackId, playlistId))
                .build();
    }

    @Inject
    public void setUserService(IUserService userService){
        this.userService = userService;
    }

    @Inject
    public void setPlaylistService(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(ITrackService trackService){
        this.trackService = trackService;
    }
}
