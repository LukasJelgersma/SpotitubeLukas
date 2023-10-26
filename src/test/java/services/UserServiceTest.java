package services;

import com.example.spotitubelukas.datasourceLayer.UserDao;
import com.example.spotitubelukas.exceptions.InvalidCredentialsException;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.serviceLayer.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService sut;
    private UserDao mockedUserDao;
    private final String testtoken = "testtoken1";
    private UserRequestDTO mockedUserRequestDTO;
    private UserDTO mockedUserDTO;

    @BeforeEach
    void setup() {
        this.sut = new UserService();

        this.mockedUserDao = mock(UserDao.class);
        this.sut.setUserDao(mockedUserDao);

        this.mockedUserRequestDTO = new UserRequestDTO("testuser", "testpassword");
        this.mockedUserDTO = new UserDTO("testuser", DigestUtils.sha256Hex("testpassword"), "testname", testtoken);


        // Gebruik Mockito om een instantie te maken
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void succesfullyAuthenticateUserWithValidCredentials() {
        when(mockedUserDao.getUserCredentials(mockedUserRequestDTO.getUser())).thenReturn(mockedUserDTO);

        doNothing().when(mockedUserDao).setUserToken(mockedUserDTO);

        UserResponseDTO result = sut.authUser(mockedUserRequestDTO);

        assertEquals(mockedUserRequestDTO.getUser(), result.getUser());
    }

    @Test
    void unsuccesfullyAuthenticateUserWhenUserDoesNotExist() {
        when(mockedUserDao.getUserCredentials(mockedUserRequestDTO.getUser())).thenReturn(null);

        assertThrows(UserNotAvailableException.class, () -> sut.authUser(mockedUserRequestDTO));
    }

    @Test
    void unsuccesfullyAuthenticateUserWhenPasswordIsIncorrect() {
        mockedUserDTO.setPassword(DigestUtils.sha256Hex("wrongpassword"));
        when(mockedUserDao.getUserCredentials(mockedUserRequestDTO.getUser())).thenReturn(mockedUserDTO);

        assertThrows(InvalidCredentialsException.class, () -> sut.authUser(mockedUserRequestDTO));
    }

    @Test
    void getUserByToken() {
        when(mockedUserDao.getUserByToken(testtoken)).thenReturn(mockedUserDTO);

        UserDTO result = sut.getUserByToken(testtoken);

        assertEquals(mockedUserDTO, result);
    }
}
