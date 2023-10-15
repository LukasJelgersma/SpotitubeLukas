package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.exceptionmappers.PlaylistNotAvailableExceptionMapper;
import com.example.spotitubelukas.exceptions.PlaylistNotAvailableException;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

@Default
@ApplicationScoped
public class PlaylistService {

    private PlaylistDao playlistDao;

    private TrackService trackService;


    public PlaylistService() {
    }

    public PlaylistResponseDTO getPlaylists(UserDTO user) {


        String username = user.getUser();
        try{
            return playlistDao.getPlaylistResponse(username);
        } catch (Exception e){
            throw new PlaylistNotAvailableException();
        }
    }

    public PlaylistResponseDTO addPlaylist(UserDTO user, PlaylistDTO playlistDTO) {
        String username = user.getUser();

        playlistDao.addPlaylist(username, playlistDTO);

        return playlistDao.getPlaylistResponse(username);
    }

    public PlaylistResponseDTO deletePlaylist(UserDTO user, int id){
        String username = user.getUser();

        playlistDao.deletePlaylist(username, id);

        return playlistDao.getPlaylistResponse(username);
    }

    public PlaylistResponseDTO editPlaylist(UserDTO user, int id, PlaylistDTO playlistDTO){
        String username = user.getUser();

        playlistDao.editPlaylist(username, id, playlistDTO);

        return playlistDao.getPlaylistResponse(username);
    }

    public TrackResponseDTO addTrackToPlaylist(int id, TrackDTO trackDTO, UserDTO userDTO){

        playlistDao.addTrackToPlaylist(id, trackDTO);

        PlaylistDTO playlistDTO = playlistDao.getPlaylistById(id, userDTO.getUser());

        playlistDTO.getTracks().add(trackDTO);

        return new TrackResponseDTO(playlistDTO.getTracks());
    }

    public TrackResponseDTO removeTrackFromPlaylist(int trackId, int playlistId, UserDTO userDTO){

        playlistDao.removeTrackFromPlaylist(trackId, playlistId);

        return new TrackResponseDTO(playlistDao.getAllTracks(playlistId));
    }

    public PlaylistDTO getPlaylistById(int id, UserDTO userDTO){
        return (playlistDao.getPlaylistById(id, userDTO.getUser()));
    }

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao){
        this.playlistDao = playlistDao;
    }
    @Inject
    public void setTrackService(TrackService trackService){
        this.trackService = trackService;
    }
}
