package DAOs;

import com.example.spotitubelukas.datasource.UserDao;
import com.example.spotitubelukas.datasource.util.DatabaseProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;

public class UserDaoTest {

    private UserDao sut;

    @BeforeEach
    void setup() {
        this.sut = new UserDao();
    }

    @Test
    void sucessfullySetUserToken() {

    }

}
