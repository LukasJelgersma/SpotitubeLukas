package resources;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistResourceTest {
    private PlaylistsResource sut;
    private PlaylistService mockedPlaylistService;
    private UserService mockedUserService;
    private TrackService mockedTrackService;
    private PlaylistDTO mockedPlaylistDTO;
    private TrackDTO mockedTrackDTO;
    private TrackResponseDTO mockedTrackResponseDTO;
    private PlaylistResponseDTO mockedPlaylistResponseDTO;
    private UserDTO mockedUserDTO;
    private String testtoken = "testtoken1";
    private int testPlaylistId = 1;

    @BeforeEach
    public void setup() {
        this.sut = new PlaylistsResource();

        this.mockedPlaylistService = mock(PlaylistService.class);
        this.sut.setPlaylistService(mockedPlaylistService);

        this.mockedUserService = mock(UserService.class);
        this.sut.setUserService(mockedUserService);

        this.mockedTrackService = mock(TrackService.class);
        this.sut.setTrackService(mockedTrackService);

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
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.getPlaylists(mockedUserDTO)).thenReturn(mockedPlaylistResponseDTO);

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
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.addPlaylist(mockedUserDTO, mockedPlaylistDTO)).thenReturn(mockedPlaylistResponseDTO);

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
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.deletePlaylist(mockedUserDTO, testPlaylistId)).thenReturn(mockedPlaylistResponseDTO);


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
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.editPlaylist(mockedUserDTO, testPlaylistId, mockedPlaylistDTO)).thenReturn(mockedPlaylistResponseDTO);

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
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.getPlaylistById(testPlaylistId, mockedUserDTO)).thenReturn(mockedPlaylistDTO);
        when(mockedPlaylistService.addTrackToPlaylist(testPlaylistId, mockedTrackDTO)).thenReturn(mockedTrackResponseDTO);

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
        when(mockedPlaylistService.removeTrackFromPlaylist(mockedTrackDTO.getId(), testPlaylistId)).thenReturn(mockedTrackResponseDTO);

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
        when(mockedUserService.getUserByToken(testtoken)).thenReturn(mockedUserDTO);
        when(mockedPlaylistService.getPlaylistById(testPlaylistId, mockedUserDTO)).thenReturn(mockedPlaylistDTO);
        when(mockedTrackService.getPlaylistTracks(mockedPlaylistDTO)).thenReturn(mockedTrackResponseDTO);

        // Act
        Response result = sut.getTracks(testtoken, testPlaylistId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(mockedTrackResponseDTO, result.getEntity());
    }



}
