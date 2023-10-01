package com.example.spotitubelukas.datasource;

import com.example.spotitubelukas.datasource.util.DatabaseProperties;
import com.example.spotitubelukas.domain.PlaylistDTO;
import com.example.spotitubelukas.domain.TrackDTO;
import com.example.spotitubelukas.domain.UserDTO;
import com.example.spotitubelukas.services.PlaylistService;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());

    DatabaseProperties databaseProperties = new DatabaseProperties();

    public ArrayList<PlaylistDTO> getPlaylistInformation(String username){
        Connection connection;
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_PLAYLIST_ALL);

            selectStatement.setString(1, username);

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                ArrayList<TrackDTO> tracks = getAllTracks(resultSet.getInt("id"));
                PlaylistDTO playlistDTO = new PlaylistDTO(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"), tracks);
                playlists.add(playlistDTO);
            }

            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
            System.out.println("SOMETHIN WENT WRONG " + e);
        }

        return playlists;
    }

    public ArrayList<TrackDTO> getAllTracks(int playlistId){
        Connection connection;
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_TRACKS_ALL);

            selectStatement.setInt(1, playlistId);

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()){
                TrackDTO track = new TrackDTO(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("performer"),
                        resultSet.getInt("duration"), resultSet.getString("album"), resultSet.getInt("playcount"),
                        resultSet.getDate("publicationDate"), resultSet.getString("description"), resultSet.getBoolean("offlineAvailable"));
                tracks.add(track);
            }

            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database: " + e);
            System.out.println("SOMETHIN WENT WRONG " + e);
        }
        return tracks;
    }


    private static final String SQL_SELECT_PLAYLIST_ALL = "SELECT * FROM playlists p JOIN users u ON p.owner = u.user WHERE u.user = ?";
    private static final String SQL_SELECT_TRACKS_ALL = "SELECT * FROM tracksinplaylists tp JOIN tracks t ON tp.trackid = t.id WHERE tp.playlistid = ?";

}
