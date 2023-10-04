import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.dto.UserDTO;
import com.example.spotitubelukas.resources.PlaylistsResource;
import com.example.spotitubelukas.services.PlaylistService;
import com.example.spotitubelukas.services.UserService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class PlaylistResourceTest {
    private PlaylistsResource sut;
    @InjectMocks
    private PlaylistService mockedPlaylistService;

    @Mock
    private PlaylistDao mockedPlaylistDao;

    @InjectMocks
    private UserService mockedUserService;



    @BeforeEach
    void setup() {
        this.sut = new PlaylistsResource();

        // Gebruik Mockito om een instantie te maken
        MockitoAnnotations.openMocks(this);

        // Gebruik de setter om de mockedItemService te zetten
    }


    @Test
    void getJsonPlaylistsCallsGetPlaylists() {
        // Arrange
        String token = "e7826f66-3791-44ad-a2b0-ed87132a6273";


        // Act
        sut.getPlaylists(token);
        UserDTO userDTO = mockedUserService.getUserByToken(token);


        // Assert
        verify(mockedPlaylistService).getPlaylists(userDTO);
    }

    @Test
    void getPlaylistReturnsObjectAsJson() {
        // Arrange
        String token = "e7826f66-3791-44ad-a2b0-ed87132a6273";
        UserDTO user = new UserDTO("meron", "MySuperSecretPassword12341", "Meron Brouwer", token);
        MockitoAnnotations.openMocks(this);
        // Act
        //Response response = mockedPlaylistService.getPlaylists(user);

        // Assert
        //CHECK IF THE OUTPUT OF GET PLAYLISTS EQUALS A JSON
        //assertEquals(MediaType.APPLICATION_JSON, response.getMediaType());
    }
}
