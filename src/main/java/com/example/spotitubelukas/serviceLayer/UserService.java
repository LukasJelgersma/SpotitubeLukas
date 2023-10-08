package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasource.UserDao;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

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

    public UserDTO getUserByToken(String token){
        Optional<UserDTO> requestedUser = users.stream().filter(user -> user.getUsertoken().equals(token)).findFirst();
        if(requestedUser.isPresent()){
            return requestedUser.get();
        } else {
            throw new UserNotAvailableException();
        }
    }

    public UserResponseDTO searchUser(UserRequestDTO userRequestDTO) {
        UserDTO user = userDao.getUserCredentials(userRequestDTO.getUser());

        if(user.getPassword().equals(userRequestDTO.getPassword())){
            String token = UUID.randomUUID().toString();
            user.setUsertoken(token);
            userDao.setUserToken(user);
            users.add(user);
            return new UserResponseDTO(userRequestDTO.getUser(), token);
        } else {
            throw new UserNotAvailableException();
        }
    }
}
