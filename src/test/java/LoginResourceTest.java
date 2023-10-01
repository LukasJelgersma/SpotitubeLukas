import com.example.spotitubelukas.resources.LoginResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginResourceTest {

    private LoginResource sut;

    @BeforeEach
    void setup(){
        sut = new LoginResource();
    }

    @Test
    void executeIfLoginSuccesful(){
        assertEquals("Login succesful", sut);
    }

}
