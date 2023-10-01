package com.example.spotitubelukas.exceptionmappers;

import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotAvailableExceptionMapper implements ExceptionMapper<UserNotAvailableException> {
    @Override
    public Response toResponse(UserNotAvailableException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
