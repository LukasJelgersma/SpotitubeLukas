import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.TrackResource;
import com.example.spotitubelukas.serviceLayer.PlaylistService;
import com.example.spotitubelukas.serviceLayer.TrackService;
import com.example.spotitubelukas.serviceLayer.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackResourceTest {

    private TrackResource sut;
    private TrackService mockedTrackService;
    private UserService mockedUserService;
    private PlaylistService mockedPlaylistService;
    private TrackDTO mockedTrackDTO;
    private TrackResponseDTO mockedTrackResponseDTO;
    private UserDTO mockedUserDTO;
    private PlaylistDTO mockedPlaylistDTO;
    private PlaylistResponseDTO mockedPlaylistResponseDTO;
    private String testtoken = "testtoken1";
    private int testPlaylistId = 1;

    @BeforeEach
    public void setUp() {
        this.sut = new TrackResource();

        this.mockedPlaylistService = mock(PlaylistService.class);
        this.sut.setPlaylistService(mockedPlaylistService);

        this.mockedUserService = mock(UserService.class);
        this.sut.setUserService(mockedUserService);

        this.mockedTrackService = mock(TrackService.class);
        this.sut.setTrackService(mockedTrackService);

        this.mockedTrackDTO = mock(TrackDTO.class);
        this.mockedTrackResponseDTO = mock(TrackResponseDTO.class);
        this.mockedUserDTO = mock(UserDTO.class);
        this.mockedPlaylistDTO = mock(PlaylistDTO.class);
        this.mockedPlaylistResponseDTO = mock(PlaylistResponseDTO.class);

        mockedTrackDTO.setId(1);
        mockedPlaylistDTO.setId(1);
        mockedUserDTO.setUsertoken(testtoken);

        mockedTrackResponseDTO.getTracks().add(mockedTrackDTO);

        ArrayList<PlaylistDTO> playlistDTOArrayList = new ArrayList<>();
        playlistDTOArrayList.add(mockedPlaylistDTO);

        mockedPlaylistResponseDTO.setPlaylists(playlistDTOArrayList);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTracksSuccesful(){
        // Arrange
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.getPlaylistById(testPlaylistId, mockedUserDTO)).thenReturn(mockedPlaylistDTO);
        when(mockedTrackService.getAllAvailableTracks(mockedPlaylistDTO)).thenReturn(mockedTrackResponseDTO);

        // Act
        Response result = sut.getTracks(testtoken, testPlaylistId);

        // Assert
        assertEquals(200, result.getStatus());
        assertEquals(mockedTrackResponseDTO, result.getEntity());
    }
}
