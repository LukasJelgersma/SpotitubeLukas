package com.example.spotitubelukas.services;

import com.example.spotitubelukas.datasource.UserDao;
import com.example.spotitubelukas.domain.UserDTO;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import com.example.spotitubelukas.exceptions.UsernameIsAlreadyInUseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Default
@ApplicationScoped
public class UserService {

    private List<UserDTO> users = new ArrayList<>();
    private UserDao userDao = new UserDao();

    public UserService() {

    }

    public List<UserDTO> getAll() {
        return users;
    }

    public UserDTO getUser(String name){
        UserDTO user = userDao.getUserCredentials(name);
        return user;
    }

    public UserDTO getUserByToken(String token){
        Optional<UserDTO> requestedUser = users.stream().filter(user -> user.getUsertoken().equals(token)).findFirst();
        if(requestedUser.isPresent()){
            return requestedUser.get();
        } else {
            throw new UserNotAvailableException();
        }
    }

    public void addUser(UserDTO user) {
        if (users.stream().anyMatch(otherUser -> user.getUsername() == user.getUsername())) {
            throw new UsernameIsAlreadyInUseException();
        }

        users.add(user);
    }

    public Response searchUser(String userInfo) {

        JsonReader jsonReader = Json.createReader(new StringReader(userInfo));
        JsonObject jsonObject = jsonReader.readObject();

        // Extract the "user" and "password" values
        String username = jsonObject.getString("user");
        String password = jsonObject.getString("password");

        UserDTO user = userDao.getUserCredentials(username);

        String token = UUID.randomUUID().toString();

        if(user.getPassword().equals(password)){
            user.setUsertoken(token);
            userDao.setUserToken(user);

            JsonObject json = Json.createObjectBuilder()
                    .add("token", user.getUsertoken())
                    .add("user", user.getName()).build();

            users.add(user);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Login unsuccesful").build();
        }
    }
}
