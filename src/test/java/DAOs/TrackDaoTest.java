package DAOs;

import com.example.spotitubelukas.datasource.TrackDao;
import com.example.spotitubelukas.datasource.util.ConnectionManager;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;

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
    private ArrayList<TrackDTO> mockedTracks;
    private ArrayList<PlaylistDTO> mockedPlaylists;
    private LocalDate testDate = LocalDate.of(2021, 1, 1);
    private String testDateString = "2021-1-1";

    @BeforeEach
    void setup(){
        this.sut = new TrackDao();
        this.mockedConnectionManager = mock(ConnectionManager.class);

        this.mockedConnection = mock(Connection.class);
        this.mockedPreparedStatement = mock(PreparedStatement.class);
        this.mockedResultSet = mock(ResultSet.class);

        this.mockedUserDTO = new UserDTO("testuser", "testpassword", "testusername", "testtoken");
        this.mockedPlaylistDTO = new PlaylistDTO(1, "testplaylist", true);
        this.mockedTrackDTO = new TrackDTO(1, "testtitle", "testperformer", 1, "testalbum", 1, LocalDate.of(2021, 1, 1), "testdescription", true);
        this.mockedTrackResponseDTO = new TrackResponseDTO();
        this.mockedTracks = new ArrayList<>();
        this.mockedPlaylists = new ArrayList<>();

    }
}
