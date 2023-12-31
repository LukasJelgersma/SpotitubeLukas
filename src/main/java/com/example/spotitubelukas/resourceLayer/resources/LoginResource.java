package com.example.spotitubelukas.resourceLayer.resources;

import com.example.spotitubelukas.resourceLayer.IUserService;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/login")
@ApplicationScoped
public class LoginResource {

    private IUserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserRequestDTO userRequestDTO) {

        return Response
                .status(200)
                .entity(userService.authUser(userRequestDTO))
                .build();
    }

    @Inject
    public void setUserService(IUserService userService){
        this.userService = userService;
    }
}