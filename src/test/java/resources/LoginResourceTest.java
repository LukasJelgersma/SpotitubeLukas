package resources;

import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.LoginResource;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoginResourceTest {

    private LoginResource sut;
    private UserService mockedUserService;
    private UserRequestDTO mockedUserRequestDTO;

    @BeforeEach
    void setup(){
        this.sut = new LoginResource();

        this.mockedUserService = mock(UserService.class);
        this.sut.setUserService(mockedUserService);

        this.mockedUserRequestDTO = new UserRequestDTO("lukas", "LukasGaming123");

        // Gebruik Mockito om een instantie te maken
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void LoginSuccesful(){
        // Arrange
        var returnWaardeFixture = new UserResponseDTO("Lukas Jelgersma", "870df322-1800-4a1e-9f54-e78908fc4667");

        // Mock service
        when(mockedUserService.authUser(mockedUserRequestDTO)).thenReturn(returnWaardeFixture);

        // Act
        Response response = sut.login(mockedUserRequestDTO);

        // Assert
        UserResponseDTO userResponse = (UserResponseDTO) response.getEntity();
        assertEquals(returnWaardeFixture, userResponse);
    }

    @Test
    void LoginUnsuccesful(){
        // Arrange
        var returnWaardeFixture = UserNotAvailableException.class;

        // Mock service
        when(mockedUserService.authUser(mockedUserRequestDTO)).thenThrow(returnWaardeFixture);

        // Throw en Assert
        assertThrows(UserNotAvailableException.class, () -> {
            sut.login(mockedUserRequestDTO);
        });
    }

}
