package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasourceLayer.UserDao;
import com.example.spotitubelukas.exceptions.InvalidCredentialsException;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

@Default
@ApplicationScoped
public class UserService implements com.example.spotitubelukas.resourceLayer.IUserService {
    private UserDao userDao;

    public UserService() {

    }

    @Override
    public UserDTO getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }

    @Override
    public UserResponseDTO authUser(UserRequestDTO userRequestDTO) {
        UserDTO user = userDao.getUserCredentials(userRequestDTO.getUser());
        if (user == null) {
            throw new UserNotAvailableException();
        } else if (user.getPassword().equals(DigestUtils.sha256Hex(userRequestDTO.getPassword()))) {
            String token = UUID.randomUUID().toString();
            user.setUsertoken(token);
            userDao.setUserToken(user);
            return new UserResponseDTO(userRequestDTO.getUser(), token);
        } else {
            throw new InvalidCredentialsException();
        }
    }

    @Inject
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
