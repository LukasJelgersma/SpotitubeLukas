package com.example.spotitubelukas.datasource;

import com.example.spotitubelukas.datasource.util.DatabaseProperties;
import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.TrackDTO;
import com.example.spotitubelukas.dto.response.PlaylistResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Default;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;
@Default
@ApplicationScoped
public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();
    private ArrayList<TrackDTO> tracks = new ArrayList<>();
    DatabaseProperties databaseProperties = new DatabaseProperties();

    public PlaylistDao() {
    }

    public PlaylistResponseDTO getPlaylistInformation(String username){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_PLAYLIST_ALL);

            selectStatement.setString(1, username);

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                tracks = getAllTracks(resultSet.getInt("id"), connection);

                PlaylistDTO playlistDTO = new PlaylistDTO(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        Objects.equals(username, resultSet.getString("owner")));
                playlists.add(playlistDTO);
            }

            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }

        return new PlaylistResponseDTO(playlists, 1412);
    }

    public ArrayList<TrackDTO> getAllTracks(int playlistId, Connection connection){
        tracks = new ArrayList<>();
        try {
            PreparedStatement selectStatementTrack = connection.prepareStatement(SQL_SELECT_TRACKS_ALL);

            selectStatementTrack.setInt(1, playlistId);

            ResultSet resultSet = selectStatementTrack.executeQuery();
            while (resultSet.next()){
                TrackDTO track = new TrackDTO(resultSet.getInt("id"),
                        resultSet.getString("title"), resultSet.getString("performer"),
                        resultSet.getInt("duration"), resultSet.getString("album"),
                        resultSet.getInt("playcount"), resultSet.getDate("publicationDate"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("offlineAvailable"));
                tracks.add(track);
            }

            selectStatementTrack.close();
            //connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database: " + e);
            System.out.println("SOMETHIN WENT WRONG " + e);
        }
        return tracks;
    }


    private static final String SQL_SELECT_PLAYLIST_ALL = "SELECT * FROM spotitube.playlists p JOIN spotitube.users u ON p.owner = u.user WHERE u.user = ?";
    private static final String SQL_SELECT_TRACKS_ALL = "SELECT * FROM tracksinplaylists tp JOIN tracks t ON tp.trackid = t.id WHERE tp.playlistid = ?";

}
