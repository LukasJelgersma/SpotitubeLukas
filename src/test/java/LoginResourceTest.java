import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.LoginResource;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void executeIfLoginSuccesful(){
        var result = sut.login(mockedUserRequestDTO);

        assertEquals(200, result.getStatus());

        //UserRequestDTO userRequestDTO = new UserRequestDTO("lukas", "LukasGaming123");
        //UserResponseDTO userResponseDTO = mockedUserService.searchUser(userRequestDTO);

    }

}
