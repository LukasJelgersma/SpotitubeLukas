import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.exceptions.PlaylistNotAvailableException;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.PlaylistsResource;
import com.example.spotitubelukas.serviceLayer.PlaylistService;
import com.example.spotitubelukas.serviceLayer.TrackService;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PlaylistResourceTest {
    private PlaylistsResource sut;
    private PlaylistService mockedPlaylistService;
    private UserService mockedUserService;
    private PlaylistDTO mockedPlaylistDTO;
    private TrackDTO mockedTrackDTO;
    private TrackResponseDTO mockedTrackResponseDTO;
    private UserDTO mockedUserDTO;

    @BeforeEach
    void setup(){
        this.sut = new PlaylistsResource();

        this.mockedPlaylistService = mock(PlaylistService.class);
        this.sut.setPlaylistService(mockedPlaylistService);
        this.mockedUserService = mock(UserService.class);
        this.sut.setUserService(mockedUserService);

        this.mockedTrackDTO = new TrackDTO(1, "test", "test",
                145, "test", 12,
                LocalDate.parse("2018-05-05"), "test", true);
        ArrayList<TrackDTO> mockedTracks = new ArrayList<>();
        mockedTracks.add(mockedTrackDTO);
        this.mockedTrackResponseDTO = new TrackResponseDTO(mockedTracks);
        this.mockedPlaylistDTO = new PlaylistDTO(1, "Hardstyle", true);
        mockedPlaylistDTO.setTracks(mockedTracks);
        this.mockedUserDTO = new UserDTO("lukas", "LukasGaming123", "Lukas Jelgersma", "870df322-1800-4a1e-9f54-e78908fc4667");

        // Gebruik Mockito om een instantie te maken
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPlaylistsSuccesful(){
        // Arrange
        ArrayList<PlaylistDTO> mockedPlaylistResponseDTO = new ArrayList<>();
        mockedPlaylistResponseDTO.add(mockedPlaylistDTO);
        var returnWaardeFixture = new PlaylistResponseDTO(mockedPlaylistResponseDTO, 1);

        // Mock service
        Mockito.when(mockedPlaylistService.getPlaylists(mockedUserDTO)).thenReturn(returnWaardeFixture);

        // Act
        Response response = sut.getPlaylists("870df322-1800-4a1e-9f54-e78908fc4667");

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getPlaylistsUnsuccesful(){
        // Arrange
        var returnWaardeFixture = PlaylistNotAvailableException.class;

        // Mock service
        Mockito.when(mockedPlaylistService.getPlaylists(mockedUserDTO)).thenThrow(returnWaardeFixture);

        // Throw en Assert
        Assertions.assertThrows(PlaylistNotAvailableException.class, () -> {
            sut.getPlaylists("870df322-1800-4a1e-9f54-e78908fc4667");
        });
    }

    @Test
    void addPlaylistSuccesful(){
        // Arrange
        ArrayList<PlaylistDTO> mockedPlaylistResponseDTO = new ArrayList<>();
        mockedPlaylistResponseDTO.add(mockedPlaylistDTO);
        var returnWaardeFixture = new PlaylistResponseDTO(mockedPlaylistResponseDTO, 1);

        // Mock service
        Mockito.when(mockedPlaylistService.addPlaylist(mockedUserDTO, mockedPlaylistDTO)).thenReturn(returnWaardeFixture);

        // Act
        Response response = sut.addPlaylist("870df322-1800-4a1e-9f54-e78908fc4667", mockedPlaylistDTO);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void addPlaylistUnsuccesful(){
        // Arrange
        var returnWaardeFixture = PlaylistNotAvailableException.class;

        // Mock service
        Mockito.when(mockedPlaylistService.addPlaylist(mockedUserDTO, mockedPlaylistDTO)).thenThrow(returnWaardeFixture);

        // Throw en Assert
        Assertions.assertThrows(PlaylistNotAvailableException.class, () -> {
            sut.addPlaylist("870df322-1800-4a1e-9f54-e78908fc4667", mockedPlaylistDTO);
        });
    }

    @Test
    void deletePlaylistSuccesful(){
        // Arrange
        ArrayList<PlaylistDTO> mockedPlaylistResponseDTO = new ArrayList<>();
        mockedPlaylistResponseDTO.add(mockedPlaylistDTO);
        var returnWaardeFixture = new PlaylistResponseDTO(mockedPlaylistResponseDTO, 1);

        // Mock service
        Mockito.when(mockedPlaylistService.deletePlaylist(mockedUserDTO, 1)).thenReturn(returnWaardeFixture);

        // Act
        Response response = sut.deletePlaylist("870df322-1800-4a1e-9f54-e78908fc4667", 1);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void deletePlaylistUnsuccesful(){
        // Arrange
        var returnWaardeFixture = PlaylistNotAvailableException.class;

        // Mock service
        Mockito.when(mockedPlaylistService.deletePlaylist(mockedUserDTO, 1)).thenThrow(returnWaardeFixture);

        // Throw en Assert
        Assertions.assertThrows(PlaylistNotAvailableException.class, () -> {
            sut.deletePlaylist("870df322-1800-4a1e-9f54-e78908fc4667", 1);
        });
    }
}
