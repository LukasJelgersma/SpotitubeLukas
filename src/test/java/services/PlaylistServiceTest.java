package services;

import com.example.spotitubelukas.datasourceLayer.PlaylistDao;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.serviceLayer.PlaylistService;
import com.example.spotitubelukas.serviceLayer.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {

    private ArrayList<PlaylistDTO> mockedPlaylists = new ArrayList<>();
    private ArrayList<TrackDTO> mockedTracks = new ArrayList<>();
    LocalDate testDate = LocalDate.of(2021, 1, 1);
    private PlaylistService sut;
    private TrackService mockedTrackService;
    private PlaylistDao mockedPlaylistDao;
    private PlaylistResponseDTO mockedPlaylistResponseDTO;
    private TrackResponseDTO mockedTrackResponseDTO;
    private UserDTO mockedUserDTO;
    private PlaylistDTO mockedPlaylistDTO;
    private TrackDTO mockedTrackDTO;


    @BeforeEach
    void setup() {
        this.sut = new PlaylistService();

        this.mockedTrackService = mock(TrackService.class);
        this.sut.setTrackService(mockedTrackService);

        this.mockedPlaylistDao = mock(PlaylistDao.class);
        this.sut.setPlaylistDao(mockedPlaylistDao);

        this.mockedPlaylistResponseDTO = new PlaylistResponseDTO();
        this.mockedTrackResponseDTO = mock(TrackResponseDTO.class);
        this.mockedUserDTO = new UserDTO();
        this.mockedPlaylistDTO = mock(PlaylistDTO.class);
        this.mockedTrackDTO = mock(TrackDTO.class);

        mockedTrackDTO.setId(1);
        mockedTrackDTO.setTitle("testtitle");
        mockedTrackDTO.setPerformer("testperformer");
        mockedTrackDTO.setDuration(1);
        mockedTrackDTO.setAlbum("testalbum");
        mockedTrackDTO.setPlaycount(1);
        mockedTrackDTO.setPublicationDate(testDate);
        mockedTrackDTO.setDescription("testdescription");
        mockedTrackDTO.setOfflineAvailable(true);
        mockedTracks.add(mockedTrackDTO);

        mockedTrackResponseDTO.setTracks(mockedTracks);

        mockedPlaylistDTO.setId(1);
        mockedPlaylistDTO.setName("testname");
        mockedPlaylistDTO.setOwner(true);
        mockedPlaylistDTO.setTracks(mockedTracks);
        mockedPlaylists.add(mockedPlaylistDTO);

        mockedUserDTO.setUser("testuser");
        mockedUserDTO.setPassword("testpassword");
        mockedUserDTO.setName("testname");
        mockedUserDTO.setUsertoken("testtoken");

        mockedPlaylistResponseDTO.setPlaylists(mockedPlaylists);
        mockedPlaylistResponseDTO.setLength(1);
    }

    @Test
    void succesfullyGetPlaylists() {
        when(mockedPlaylistDao.getPlaylistResponse(mockedUserDTO.getUser())).thenReturn(mockedPlaylistResponseDTO);

        PlaylistResponseDTO result = sut.getPlaylists(mockedUserDTO);

        assertEquals(mockedPlaylistResponseDTO, result);
        // Check length
        assertEquals(mockedPlaylistResponseDTO.getLength(), result.getLength());
        // Check playlists
        assertEquals(mockedPlaylistResponseDTO.getPlaylists(), result.getPlaylists());
    }

    @Test
    void succesfullyAddPlaylist() {
        sut.addPlaylist(mockedUserDTO, mockedPlaylistDTO);

        verify(mockedPlaylistDao, times(1)).addPlaylist(mockedUserDTO.getUser(), mockedPlaylistDTO);
    }

    @Test
    void succesfullyDeletePlaylist() {
        sut.deletePlaylist(mockedUserDTO, mockedPlaylistDTO.getId());

        verify(mockedPlaylistDao, times(1)).deletePlaylist(mockedUserDTO.getUser(), mockedPlaylistDTO.getId());
    }

    @Test
    void succesfullyEditPlaylist() {
        sut.editPlaylist(mockedUserDTO, mockedPlaylistDTO.getId(), mockedPlaylistDTO);

        verify(mockedPlaylistDao, times(1)).editPlaylist(mockedUserDTO.getUser(), mockedPlaylistDTO.getId(), mockedPlaylistDTO);
    }

    @Test
    void succesfullyAddTrackToPlaylist() {
        when(mockedPlaylistDao.getPlaylistById(mockedPlaylistDTO.getId(), mockedUserDTO.getUser())).thenReturn(mockedPlaylistDTO);

        TrackResponseDTO result = sut.addTrackToPlaylist(mockedPlaylistDTO.getId(), mockedTrackDTO);

        assertEquals(mockedTrackResponseDTO.getClass(), result.getClass());
        verify(mockedPlaylistDao, times(1)).addTrackToPlaylist(mockedPlaylistDTO.getId(), mockedTrackDTO);
    }

    @Test
    void succesfullyRemoveTrackFromPlaylist(){
        sut.removeTrackFromPlaylist(mockedTrackDTO.getId(), mockedPlaylistDTO.getId());

        verify(mockedPlaylistDao, times(1)).removeTrackFromPlaylist(mockedTrackDTO.getId(), mockedPlaylistDTO.getId());
    }


}
