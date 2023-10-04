package com.example.spotitubelukas.resources;

import com.example.spotitubelukas.dto.UserDTO;
import com.example.spotitubelukas.dto.request.UserRequestDTO;
import com.example.spotitubelukas.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/login")
@ApplicationScoped
public class LoginResource {

    private  UserService userService;

    @Inject
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserRequestDTO userRequestDTO) {
        //userService.addUser(user);

        return Response.ok().entity(userService.searchUser(userRequestDTO)).build();
    }

}