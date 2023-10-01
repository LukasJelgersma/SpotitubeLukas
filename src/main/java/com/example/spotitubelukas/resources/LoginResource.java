package com.example.spotitubelukas.resources;

import com.example.spotitubelukas.services.UserService;
import com.mysql.cj.log.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/login")
@ApplicationScoped
public class LoginResource {

    UserService userService;

    @Inject
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String userInfo) {
        //userService.addUser(user);

        return userService.searchUser(userInfo);
    }

}