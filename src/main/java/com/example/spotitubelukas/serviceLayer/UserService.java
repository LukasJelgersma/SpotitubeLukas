package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasource.UserDao;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Default
@ApplicationScoped
public class UserService {
    private UserDao userDao;

    public UserService() {

    }

    public UserDTO getUserByToken(String token){
        return userDao.getUserByToken(token);
    }

    public UserResponseDTO authUser(UserRequestDTO userRequestDTO) {
        UserDTO user = userDao.getUserCredentials(userRequestDTO.getUser());
        if(user.getPassword().equals(DigestUtils.sha256Hex(userRequestDTO.getPassword()))){
            String token = UUID.randomUUID().toString();
            user.setUsertoken(token);
            userDao.setUserToken(user);
            return new UserResponseDTO(userRequestDTO.getUser(), token);
        } else {
            throw new UserNotAvailableException();
        }
    }

    @Inject
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
}
