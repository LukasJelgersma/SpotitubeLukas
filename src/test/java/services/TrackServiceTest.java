package services;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.datasource.TrackDao;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.serviceLayer.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackServiceTest {
    private TrackService sut;
    private TrackDao mockedTrackDao;
    private PlaylistDao mockedPlaylistDao;
    private PlaylistDTO mockedPlaylistDTO;
    private TrackResponseDTO mockedTrackResponseDTO;

    @BeforeEach
    void setup() {
        this.sut = new TrackService();

        this.mockedTrackDao = mock(TrackDao.class);
        this.sut.setTrackDao(mockedTrackDao);

        this.mockedPlaylistDao = mock(PlaylistDao.class);
        this.sut.setPlaylistDao(mockedPlaylistDao);

        this.mockedPlaylistDTO = new PlaylistDTO();
        this.mockedTrackResponseDTO = mock(TrackResponseDTO.class);
    }

    @Test
    void succesfullyGetPlaylistTracks() {
        when(mockedPlaylistDao.getAllTracks(mockedPlaylistDTO.getId())).thenReturn(mockedPlaylistDTO.getTracks());
        TrackResponseDTO result = sut.getPlaylistTracks(mockedPlaylistDTO);
        assertEquals(mockedPlaylistDTO.getTracks(), result.getTracks());
    }

    @Test
    void succesfullyGetAllAvailableTracks() {
        when(mockedTrackDao.getAvailableTracks(mockedPlaylistDTO.getId())).thenReturn(mockedTrackResponseDTO);
        TrackResponseDTO result = sut.getAllAvailableTracks(mockedPlaylistDTO);
        assertEquals(mockedTrackResponseDTO, result);
    }
}
