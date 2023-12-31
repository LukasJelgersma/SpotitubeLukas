package resources;

import com.example.spotitubelukas.resourceLayer.IPlaylistService;
import com.example.spotitubelukas.resourceLayer.ITrackService;
import com.example.spotitubelukas.resourceLayer.IUserService;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.resourceLayer.resources.PlaylistsResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistResourceTest {
    private PlaylistsResource sut;
    private IPlaylistService mockedIPlaylistService;
    private IUserService mockedIUserService;
    private ITrackService mockedITrackService;
    private PlaylistDTO mockedPlaylistDTO;
    private TrackDTO mockedTrackDTO;
    private TrackResponseDTO mockedTrackResponseDTO;
    private PlaylistResponseDTO mockedPlaylistResponseDTO;
    private UserDTO mockedUserDTO;
    private final String testtoken = "testtoken1";
    private final int testPlaylistId = 1;

    @BeforeEach
    public void setup() {
        this.sut = new PlaylistsResource();

        this.mockedIPlaylistService = mock(IPlaylistService.class);
        this.sut.setPlaylistService(mockedIPlaylistService);

        this.mockedIUserService = mock(IUserService.class);
        this.sut.setUserService(mockedIUserService);

        this.mockedITrackService = mock(ITrackService.class);
        this.sut.setTrackService(mockedITrackService);

        this.mockedTrackDTO = mock(TrackDTO.class);
        this.mockedPlaylistDTO = mock(PlaylistDTO.class);

        this.mockedPlaylistResponseDTO = mock(PlaylistResponseDTO.class);
        this.mockedTrackResponseDTO = mock(TrackResponseDTO.class);
        this.mockedUserDTO = mock(UserDTO.class);

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
    void getPlaylistsSuccesful() {
        // Arrange
        // Mock service
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.getPlaylists(mockedUserDTO)).thenReturn(mockedPlaylistResponseDTO);

        // Act
        Response result = sut.getPlaylists(testtoken);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(mockedPlaylistResponseDTO, result.getEntity());

    }

    @Test
    void addPlaylistSuccesful() {
        // Arrange

        // Mock service
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.addPlaylist(mockedUserDTO, mockedPlaylistDTO)).thenReturn(mockedPlaylistResponseDTO);

        // Act
        Response result = sut.addPlaylist(testtoken, mockedPlaylistDTO);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(mockedPlaylistResponseDTO, result.getEntity());
    }

    @Test
    void deletePlaylistSuccesful() {
        // Arrange

        // Mock service
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.deletePlaylist(mockedUserDTO, testPlaylistId)).thenReturn(mockedPlaylistResponseDTO);


        // Act
        Response result = sut.deletePlaylist(testtoken, testPlaylistId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(mockedPlaylistResponseDTO, result.getEntity());
    }

    @Test
    void editPlaylistSuccesful() {
        // Arrange

        // Mock service
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.editPlaylist(mockedUserDTO, testPlaylistId, mockedPlaylistDTO)).thenReturn(mockedPlaylistResponseDTO);

        // Act
        Response result = sut.editPlaylist(testtoken, testPlaylistId, mockedPlaylistDTO);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(mockedPlaylistResponseDTO, result.getEntity());
    }

    @Test
    void addTrackToPlaylistSuccesful() {
        // Arrange

        // Mock service
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.getPlaylistById(testPlaylistId, mockedUserDTO)).thenReturn(mockedPlaylistDTO);
        when(mockedIPlaylistService.addTrackToPlaylist(testPlaylistId, mockedTrackDTO)).thenReturn(mockedTrackResponseDTO);

        // Act
        Response result = sut.addTrack(testtoken, testPlaylistId, mockedTrackDTO);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(mockedTrackResponseDTO, result.getEntity());
    }

    @Test
    void removeTrackFromPlaylistSuccesful() {
        // Arrange

        // Mock service
        when(mockedIPlaylistService.removeTrackFromPlaylist(mockedTrackDTO.getId(), testPlaylistId)).thenReturn(mockedTrackResponseDTO);

        // Act
        Response result = sut.deleteTrack(mockedTrackDTO.getId(), testPlaylistId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(mockedTrackResponseDTO, result.getEntity());
    }

    @Test
    void getTracksFromPlaylistSuccesful(){
        // Arrange

        // Mock service
        when(mockedIUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedIPlaylistService.getPlaylistById(testPlaylistId, mockedUserDTO)).thenReturn(mockedPlaylistDTO);
        when(mockedITrackService.getPlaylistTracks(mockedPlaylistDTO)).thenReturn(mockedTrackResponseDTO);

        // Act
        Response result = sut.getTracks(testtoken, testPlaylistId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(mockedTrackResponseDTO, result.getEntity());
    }



}
