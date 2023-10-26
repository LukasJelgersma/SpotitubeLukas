package com.example.spotitubelukas.datasourceLayer;

import com.example.spotitubelukas.datasourceLayer.util.ConnectionManager;
import com.example.spotitubelukas.resourceLayer.dto.TrackDTO;
import com.example.spotitubelukas.resourceLayer.dto.response.TrackResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

@Default
@ApplicationScoped
public class TrackDao {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private ConnectionManager connectionManager;

    public TrackResponseDTO getAvailableTracks(int playlistId){
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try {
            Connection connection = connectionManager.ConnectionStart();
            PreparedStatement selectStatementTrack = connection.prepareStatement(SQL_GET_AVAILABLE_TRACK);

            selectStatementTrack.setInt(1, playlistId);

            ResultSet resultSet = selectStatementTrack.executeQuery();
            while (resultSet.next()){
                TrackDTO track = new TrackDTO(resultSet.getInt("id"),
                        resultSet.getString("title"), resultSet.getString("performer"),
                        resultSet.getInt("duration"), resultSet.getString("album"),
                        resultSet.getInt("playcount"), resultSet.getDate("publicationDate").toLocalDate(),
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
    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private static final String SQL_GET_AVAILABLE_TRACK = "SELECT t.* FROM spotitube.tracks t LEFT JOIN" +
            " spotitube.tracksinplaylists tp ON t.id = tp.trackid AND" +
            " tp.playlistid = ? WHERE tp.trackid IS NULL";
}
