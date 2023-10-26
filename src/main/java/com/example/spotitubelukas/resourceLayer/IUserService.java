package com.example.spotitubelukas.resourceLayer;

import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;

public interface IUserService {
    UserDTO getUserByToken(String token);

    UserResponseDTO authUser(UserRequestDTO userRequestDTO);
}
