package resources;

import com.example.spotitubelukas.resourceLayer.IPlaylistService;
import com.example.spotitubelukas.resourceLayer.ITrackService;
import com.example.spotitubelukas.resourceLayer.IUserService;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.TrackResource;
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
    private ITrackService mockedITrackService;
    private IUserService mockedIUserService;
    private IPlaylistService mockedIPlaylistService;
    private TrackDTO mockedTrackDTO;
    private TrackResponseDTO mockedTrackResponseDTO;
    private UserDTO mockedUserDTO;
    private PlaylistDTO mockedPlaylistDTO;
    private PlaylistResponseDTO mockedPlaylistResponseDTO;
    private final String testtoken = "testtoken1";
    private final int testPlaylistId = 1;

    @BeforeEach
    public void setUp() {
        this.sut = new TrackResource();

        this.mockedIPlaylistService = mock(IPlaylistService.class);
        this.sut.setPlaylistService(mockedIPlaylistService);

        this.mockedIUserService = mock(IUserService.class);
        this.sut.setUserService(mockedIUserService);

        this.mockedITrackService = mock(ITrackService.class);
        this.sut.setTrackService(mockedITrackService);

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
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.getPlaylistById(testPlaylistId, mockedUserDTO)).thenReturn(mockedPlaylistDTO);
        when(mockedITrackService.getAllAvailableTracks(mockedPlaylistDTO)).thenReturn(mockedTrackResponseDTO);

        // Act
        Response result = sut.getTracks(testtoken, testPlaylistId);

        // Assert
        assertEquals(200, result.getStatus());
        assertEquals(mockedTrackResponseDTO, result.getEntity());
    }
}
