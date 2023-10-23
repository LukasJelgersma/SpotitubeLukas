package DAOs;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.datasource.util.ConnectionManager;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlaylistDaoTest {

    private PlaylistDao sut;
    private PlaylistResponseDTO mockedPlaylistResponseDTO;
    private TrackDTO mockedTrackDTO;
    private PlaylistDTO mockedPlaylistDTO;
    private UserDTO mockedUserDTO;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;
    private Connection mockedConnection;
    private ConnectionManager mockedConnectionManager;
    private String mockedUserToken;
    private ArrayList<TrackDTO> mockedTracks;
    private ArrayList<PlaylistDTO> mockedPlaylists;
    private LocalDate testDate = LocalDate.of(2021, 1, 1);
    private String testDateString = "2021-1-1";

    @BeforeEach
    void setup() {
        this.sut = new PlaylistDao();
        this.mockedConnectionManager = mock(ConnectionManager.class);

        this.mockedConnection = mock(Connection.class);
        this.mockedPreparedStatement = mock(PreparedStatement.class);
        this.mockedResultSet = mock(ResultSet.class);

        this.mockedUserToken = "testtoken";
        this.mockedUserDTO = new UserDTO("testuser", "testpassword", "testusername", mockedUserToken);
        this.mockedPlaylistDTO = new PlaylistDTO(1, "testplaylist", true);
        this.mockedTrackDTO = new TrackDTO(1, "testtitle", "testperformer", 1, "testalbum", 1, testDate, "testdescription", true);
        this.mockedPlaylistResponseDTO = new PlaylistResponseDTO();
        this.mockedTracks = new ArrayList<>();
        this.mockedPlaylists = new ArrayList<>();

        mockedTracks.add(mockedTrackDTO);
        mockedPlaylistDTO.setTracks(mockedTracks);
        mockedPlaylists.add(mockedPlaylistDTO);
        mockedPlaylistResponseDTO.setPlaylists(mockedPlaylists);

        sut.setConnectionManager(mockedConnectionManager);
    }

    @Test
    void sucessfullyGetPlaylistResponse() throws SQLException {
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);

        PlaylistResponseDTO result = sut.getPlaylistResponse(mockedUserDTO.getUser());

        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());

        assertEquals(mockedPlaylistResponseDTO.getClass(), result.getClass());
    }

    @Test
    void sucessfullyGetAllTracks() throws SQLException {
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);


        ArrayList<TrackDTO> result = sut.getAllTracks(mockedPlaylistDTO.getId());

        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());

        assertEquals(mockedTracks.getClass(), result.getClass());
    }

    @Test
    void succesfullyGetPlaylistById() throws SQLException {
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true, false);
        when(sut.getAllTracks(mockedPlaylistDTO.getId())mockedResultSet.next()).thenReturn(mockedTracks);


        PlaylistDTO result = sut.getPlaylistById(mockedPlaylistDTO.getId(), mockedUserDTO.getUser());

        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());

        assertEquals(mockedPlaylistDTO.getClass(), result.getClass());
    }

}
