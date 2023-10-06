package com.example.spotitubelukas.datasource;

import com.example.spotitubelukas.datasource.util.DatabaseProperties;
import com.example.spotitubelukas.dto.PlaylistDTO;
import com.example.spotitubelukas.dto.TrackDTO;
import com.example.spotitubelukas.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

@Default
@ApplicationScoped
public class TrackDao {
    private Logger logger = Logger.getLogger(getClass().getName());

    DatabaseProperties databaseProperties = new DatabaseProperties();

    public TrackResponseDTO getAvailableTracks(int playlistId){
        Connection connection;

        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatementTrack = connection.prepareStatement(SQL_GET_AVAILABLE_TRACK);

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
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database: " + e);
        }
        return new TrackResponseDTO(tracks);
    }

    private static final String SQL_GET_AVAILABLE_TRACK = "SELECT t.* FROM spotitube.tracks t LEFT JOIN" +
            " spotitube.tracksinplaylists tp ON t.id = tp.trackid AND" +
            " tp.playlistid = ? WHERE tp.trackid IS NULL";
}
