package services;

import com.example.spotitubelukas.datasource.UserDao;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.serviceLayer.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private UserService sut;
    private UserDao mockedUserDao;
    private String testtoken = "testtoken1";
    private UserRequestDTO mockedUserRequestDTO;
    private UserDTO mockedUserDTO;

    @BeforeEach
    void setup(){
        this.sut = new UserService();

        this.mockedUserDao = mock(UserDao.class);
        this.sut.setUserDao(mockedUserDao);

        this.mockedUserRequestDTO = mock(UserRequestDTO.class);
        this.mockedUserDTO = mock(UserDTO.class);



        // Gebruik Mockito om een instantie te maken
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void succesfullyAuthenticateUser(){


    }



}
