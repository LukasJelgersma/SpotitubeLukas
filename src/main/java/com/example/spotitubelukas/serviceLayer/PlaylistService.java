package com.example.spotitubelukas.serviceLayer;

import com.example.spotitubelukas.datasourceLayer.PlaylistDao;
import com.example.spotitubelukas.resourceLayer.IPlaylistService;
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
public class PlaylistService implements IPlaylistService {

    private PlaylistDao playlistDao;


    public PlaylistService() {
    }

    /**
     * Get all playlists from user
     * @param user
     * @return PlaylistResponseDTO
     */
    @Override
    public PlaylistResponseDTO getPlaylists(UserDTO user) {


        String username = user.getUser();

        return playlistDao.getPlaylistResponse(username);
    }

    /**
     * Add playlist to user
     * @param user
     * @param playlistDTO
     * @return PlaylistResponseDTO
     */
    @Override
    public PlaylistResponseDTO addPlaylist(UserDTO user, PlaylistDTO playlistDTO) {
        String username = user.getUser();

        playlistDao.addPlaylist(username, playlistDTO);

        return playlistDao.getPlaylistResponse(username);
    }

    /**
     * Delete playlist from user
     * @param user
     * @param id
     * @return PlaylistResponseDTO
     */
    @Override
    public PlaylistResponseDTO deletePlaylist(UserDTO user, int id) {
        String username = user.getUser();

        playlistDao.deletePlaylist(username, id);

        return playlistDao.getPlaylistResponse(username);
    }

    /**
     * Edit playlist from user
     * @param user
     * @param id
     * @param playlistDTO
     * @return PlaylistResponseDTO
     */
    @Override
    public PlaylistResponseDTO editPlaylist(UserDTO user, int id, PlaylistDTO playlistDTO) {
        String username = user.getUser();

        playlistDao.editPlaylist(username, id, playlistDTO);

        return playlistDao.getPlaylistResponse(username);
    }

    /**
     * add track to playlist
     * @param id
     * @param trackDTO
     * @return TrackResponseDTO
     */
    @Override
    public TrackResponseDTO addTrackToPlaylist(int id, TrackDTO trackDTO) {

        playlistDao.addTrackToPlaylist(id, trackDTO);

        return new TrackResponseDTO(playlistDao.getAllTracks(id));
    }

    /**
     * Delete track from playlist
     * @param trackId
     * @param playlistId
     * @return TrackResponseDTO
     */
    @Override
    public TrackResponseDTO removeTrackFromPlaylist(int trackId, int playlistId) {

        playlistDao.removeTrackFromPlaylist(trackId, playlistId);

        return new TrackResponseDTO(playlistDao.getAllTracks(playlistId));
    }

    /**
     * Get playlist by id
     * @param id
     * @param userDTO
     * @return PlaylistDTO
     */
    @Override
    public PlaylistDTO getPlaylistById(int id, UserDTO userDTO) {
        return (playlistDao.getPlaylistById(id, userDTO.getUser()));
    }

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

}
