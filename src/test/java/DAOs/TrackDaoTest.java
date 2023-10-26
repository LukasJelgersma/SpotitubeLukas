package DAOs;

import com.example.spotitubelukas.datasourceLayer.TrackDao;
import com.example.spotitubelukas.datasourceLayer.util.ConnectionManager;
import com.example.spotitubelukas.datasourceLayer.util.DatabaseProperties;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackDaoTest {
    private TrackDao sut;
    private TrackResponseDTO mockedTrackResponseDTO;
    private PlaylistDTO mockedPlaylistDTO;
    private UserDTO mockedUserDTO;
    private TrackDTO mockedTrackDTO;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;
    private Connection mockedConnection;
    private ConnectionManager mockedConnectionManager;
    private DatabaseProperties mockedDatabaseProperties;
    private ArrayList<TrackDTO> mockedTracks;
    private ArrayList<PlaylistDTO> mockedPlaylists;
    private final LocalDate testDate = LocalDate.of(2021, 1, 1);
    private final String testDateString = "2021-1-1";

    @BeforeEach
    void setup(){
        this.sut = new TrackDao();
        this.mockedConnectionManager = mock(ConnectionManager.class);
        this.mockedDatabaseProperties = mock(DatabaseProperties.class);
        mockedConnectionManager.setDatabaseProperties(mockedDatabaseProperties);

        this.mockedConnection = mock(Connection.class);
        this.mockedPreparedStatement = mock(PreparedStatement.class);
        this.mockedResultSet = mock(ResultSet.class);

        this.mockedUserDTO = new UserDTO("testuser", "testpassword", "testusername", "testtoken");
        this.mockedPlaylistDTO = new PlaylistDTO(1, "testplaylist", true);
        this.mockedTrackDTO = new TrackDTO(1, "testtitle", "testperformer", 1, "testalbum", 1, testDate, "testdescription", true);
        this.mockedTrackResponseDTO = new TrackResponseDTO();
        this.mockedTracks = new ArrayList<>();
        this.mockedPlaylists = new ArrayList<>();

        mockedTracks.add(mockedTrackDTO);
        mockedPlaylistDTO.setTracks(mockedTracks);
        mockedPlaylists.add(mockedPlaylistDTO);
        mockedTrackResponseDTO.setTracks(mockedTracks);

        sut.setConnectionManager(mockedConnectionManager);

    }

    @Test
    void succesfullyGetAvailableTracks() throws SQLException {
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true, false);
        when(mockedResultSet.getDate("publicationDate")).thenReturn(java.sql.Date.valueOf(testDateString));

        TrackResponseDTO result = sut.getAvailableTracks(mockedPlaylistDTO.getId());

        assertEquals(mockedTrackResponseDTO.getClass(), result.getClass());

        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(1)).setInt(1, mockedPlaylistDTO.getId());
        verify(mockedPreparedStatement, times(1)).executeQuery();
        verify(mockedConnection, times(1)).close();

    }
}
