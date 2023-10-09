import com.example.spotitubelukas.resourceLayer.resources.LoginResource;
import com.example.spotitubelukas.serviceLayer.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("Login succesful", sut);
    }

}
