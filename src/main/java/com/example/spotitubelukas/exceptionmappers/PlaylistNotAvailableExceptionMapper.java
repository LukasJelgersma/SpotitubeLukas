package com.example.spotitubelukas.exceptionmappers;

import com.example.spotitubelukas.exceptions.PlaylistNotAvailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PlaylistNotAvailableExceptionMapper implements ExceptionMapper<PlaylistNotAvailableException> {

    @Override
    public Response toResponse(PlaylistNotAvailableException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
