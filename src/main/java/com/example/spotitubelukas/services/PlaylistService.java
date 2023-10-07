package com.example.spotitubelukas.services;

import com.example.spotitubelukas.datasource.PlaylistDao;
import com.example.spotitubelukas.datasource.TrackDao;
import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.TrackDTO;
import com.example.spotitubelukas.dto.UserDTO;
import com.example.spotitubelukas.dto.response.PlaylistResponseDTO;
import com.example.spotitubelukas.dto.response.TrackResponseDTO;
import com.example.spotitubelukas.exceptions.UserNotAvailableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Default
@ApplicationScoped
public class PlaylistService {
    private List<PlaylistDTO> playlists = new ArrayList<>();

    @Inject
    private PlaylistDao playlistDao;

    @Inject
    private TrackService trackService;


    public PlaylistService() {
    }

    public PlaylistResponseDTO getPlaylists(UserDTO user) {


        String username = user.getUser();
        PlaylistResponseDTO playlistResponseDTO = playlistDao.getPlaylistResponse(username);
        playlists.addAll(playlistResponseDTO.getPlaylists());
        // Convert the JSON object to a string and return it
        return playlistDao.getPlaylistResponse(username);
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

    public TrackResponseDTO addTrackToPlaylist(int id, TrackDTO trackDTO){

        playlistDao.addTrackToPlaylist(id, trackDTO);

        PlaylistDTO playlistDTO = getPlaylistById(id);

        playlistDTO.getTracks().add(trackDTO);

        return new TrackResponseDTO(playlistDTO.getTracks());
    }

    public TrackResponseDTO removeTrackFromPlaylist(int trackId, int playlistId){

        playlistDao.removeTrackFromPlaylist(trackId, playlistId);

        PlaylistDTO playlistDTO = getPlaylistById(playlistId);

        List<TrackDTO> tracks = playlistDTO.getTracks();
        for (TrackDTO track : tracks) {
            if (track.getId() == trackId) {
                tracks.remove(track);
                break; // Exit the loop after removing the track
            }
        }

        return new TrackResponseDTO(playlistDTO.getTracks());
    }

    public PlaylistDTO getPlaylistById(int id){
        Optional<PlaylistDTO> requestedPlaylist = playlists.stream()
                .filter(playlistDto -> playlistDto.getId() == id)
                .findFirst();
        if(requestedPlaylist.isPresent()){
            return requestedPlaylist.get();
        } else {
            throw new UserNotAvailableException();
        }
    }
}
