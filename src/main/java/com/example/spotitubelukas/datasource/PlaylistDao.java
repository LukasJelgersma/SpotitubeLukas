package com.example.spotitubelukas.datasource;

import com.example.spotitubelukas.datasource.util.DatabaseProperties;
import com.example.spotitubelukas.resourceLayer.dto.PlaylistDTO;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.PlaylistResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;
@Default
@ApplicationScoped
public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    DatabaseProperties databaseProperties = new DatabaseProperties();

    public PlaylistDao() {
    }

    public PlaylistResponseDTO getPlaylistResponse(String username){
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        int totalDuration = 0;
        int totalTrackDuration = 0;

        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_PLAYLIST_ALL);

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                ArrayList<TrackDTO> tracks;
                tracks = getAllTracks(resultSet.getInt("id"));

                totalTrackDuration = tracks.stream()
                        .mapToInt(TrackDTO::getDuration)
                        .sum();

                PlaylistDTO playlistDTO = new PlaylistDTO(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        Objects.equals(username, resultSet.getString("owner")));
                playlistDTO.setTracks(tracks);
                playlists.add(playlistDTO);
                totalDuration += totalTrackDuration;
            }

            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }

        return new PlaylistResponseDTO(playlists, totalDuration);
    }

    public ArrayList<TrackDTO> getAllTracks(int playlistId){
        Connection connection;
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
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
        }
        return tracks;
    }

    public PlaylistDTO getPlaylistById(int id, String username){
        Connection connection;
        PlaylistDTO playlistDTO = null;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectPlaylistStatement = connection.prepareStatement(SQL_SELECT_PLAYLIST_BY_ID);

            selectPlaylistStatement.setInt(1, id);

            ResultSet resultSet = selectPlaylistStatement.executeQuery();
            while (resultSet.next()) {
                ArrayList<TrackDTO> tracks;
                tracks = getAllTracks(resultSet.getInt("id"));

                playlistDTO = new PlaylistDTO(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        Objects.equals(username, resultSet.getString("owner")));
                playlistDTO.setTracks(tracks);
            }

            selectPlaylistStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }
        return playlistDTO;
    }

    public void addPlaylist(String username, PlaylistDTO playlistDTO){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement insertStatement = connection.prepareStatement(SQL_INSERT_PLAYLIST);

            insertStatement.setString(1, playlistDTO.getName());
            insertStatement.setString(2, username);

            insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }

    }

    public void deletePlaylist(String username, int id){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_PLAYLIST);

            deleteStatement.setString(1, username);
            deleteStatement.setInt(2, id);

            deleteStatement.executeUpdate();

            deleteStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }

    }

    public void editPlaylist(String username, int id, PlaylistDTO playlistDTO){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement deleteStatement = connection.prepareStatement(SQL_EDIT_PLAYLIST);

            deleteStatement.setString(1, playlistDTO.getName());
            deleteStatement.setString(2, username);
            deleteStatement.setInt(3, id);

            deleteStatement.executeUpdate();

            deleteStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }
    }

    public void addTrackToPlaylist(int id, TrackDTO trackDTO){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement insertStatement = connection.prepareStatement(SQL_ADD_TRACK_PLAYLIST);

            insertStatement.setInt(1, trackDTO.getId());
            insertStatement.setInt(2, id);

            insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }
    }

    public void removeTrackFromPlaylist(int trackId, int playlistId){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement insertStatement = connection.prepareStatement(SQL_REMOVE_TRACK_PLAYLIST);

            insertStatement.setInt(1, trackId);
            insertStatement.setInt(2, playlistId);

            insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }
    }

    private static final String SQL_SELECT_PLAYLIST_ALL = "SELECT * FROM playlists";
    private static final String SQL_SELECT_PLAYLIST_BY_ID = "SELECT * FROM playlists where id = ?";
    private static final String SQL_SELECT_TRACKS_ALL = "SELECT * FROM tracksinplaylists tp JOIN tracks t ON tp.trackid = t.id WHERE tp.playlistid = ?";
    private static final String SQL_INSERT_PLAYLIST = "INSERT INTO playlists (name, owner) VALUES (?, ?)";
    private static final String SQL_DELETE_PLAYLIST = "DELETE FROM playlists WHERE (owner = ?) AND (id = ?)";
    private static final String SQL_EDIT_PLAYLIST = "UPDATE playlists SET name = ? WHERE (owner = ?) AND (id = ?)";
    private static final String SQL_ADD_TRACK_PLAYLIST = "INSERT INTO tracksinplaylists (trackid, playlistid) VALUES (?, ?)";
    private static final String SQL_REMOVE_TRACK_PLAYLIST = "DELETE FROM tracksinplaylists WHERE (trackid = ?) AND (playlistid = ?)";



}
