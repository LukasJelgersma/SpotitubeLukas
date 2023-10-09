import com.example.spotitubelukas.resourceLayer.dto.request.UserRequestDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.UserResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.LoginResource;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class LoginResourceTest {

    private LoginResource sut;

    @InjectMocks
    private UserService mockedUserService;


    @BeforeEach
    void setup(){
        this.sut = new LoginResource();

        // Gebruik Mockito om een instantie te maken
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeIfLoginSuccesful(){
        UserRequestDTO userRequestDTO = new UserRequestDTO("lukas", "LukasGaming123");
        UserResponseDTO userResponseDTO = mockedUserService.searchUser(userRequestDTO);

    }

}
