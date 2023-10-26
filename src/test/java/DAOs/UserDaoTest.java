package DAOs;

import com.example.spotitubelukas.datasourceLayer.UserDao;
import com.example.spotitubelukas.datasourceLayer.util.ConnectionManager;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDaoTest {

    private UserDao sut;
    private UserDTO mockedUserDTO;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;
    private Connection mockedConnection;
    private ConnectionManager mockedConnectionManager;

    @BeforeEach
    void setup() {
        this.sut = new UserDao();
        this.mockedConnectionManager = mock(ConnectionManager.class);

        this.mockedConnection = mock(Connection.class);
        this.mockedPreparedStatement = mock(PreparedStatement.class);
        this.mockedResultSet = mock(ResultSet.class);

        String mockedUserToken = "testtoken";
        this.mockedUserDTO = new UserDTO("testuser", "testpassword", "testusername", mockedUserToken);

        sut.setConnectionManager(mockedConnectionManager);
    }

    @Test
    void sucessfullySetUserToken() throws SQLException {
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeUpdate()).thenReturn(1);

        sut.setUserToken(mockedUserDTO);

        // Verify that the correct methods are called
        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(1)).setString(1, mockedUserDTO.getUsertoken());
        verify(mockedPreparedStatement, times(1)).setString(2, mockedUserDTO.getUser());
        verify(mockedPreparedStatement, times(1)).executeUpdate();
        verify(mockedConnection, times(1)).close();
    }

    @Test
    void succesfullyGetUserCredentials() throws SQLException {
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true, false);
        when(mockedResultSet.getString("user")).thenReturn(mockedUserDTO.getUser());
        when(mockedResultSet.getString("password")).thenReturn(mockedUserDTO.getPassword());
        when(mockedResultSet.getString("name")).thenReturn(mockedUserDTO.getName());
        when(mockedResultSet.getString("usertoken")).thenReturn(mockedUserDTO.getUsertoken());

        UserDTO result = sut.getUserCredentials("testuser");

        // Verify that the correct methods are called
        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(1)).setString(1, mockedUserDTO.getUser());
        verify(mockedPreparedStatement, times(1)).executeQuery();
        verify(mockedPreparedStatement, times(1)).close();
        verify(mockedConnection, times(1)).close();
        assertEquals(mockedUserDTO.getUser(), result.getUser());
    }

    @Test
    void succesfullyGetUserByToken() throws SQLException{
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true, false);
        when(mockedResultSet.getString("user")).thenReturn(mockedUserDTO.getUser());
        when(mockedResultSet.getString("password")).thenReturn(mockedUserDTO.getPassword());
        when(mockedResultSet.getString("name")).thenReturn(mockedUserDTO.getName());
        when(mockedResultSet.getString("usertoken")).thenReturn(mockedUserDTO.getUsertoken());

        UserDTO result = sut.getUserByToken("testtoken");

        // Verify that the correct methods are called
        verify(mockedConnectionManager, times(1)).ConnectionStart();
        verify(mockedConnection, times(1)).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(1)).setString(1, mockedUserDTO.getUsertoken());
        verify(mockedPreparedStatement, times(1)).executeQuery();
        verify(mockedPreparedStatement, times(1)).close();
        verify(mockedConnection, times(1)).close();
        assertEquals(mockedUserDTO.getUser(), result.getUser());
    }

    @Test
    void unsuccesfullyGetUserByToken() throws SQLException{
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);

        UserDTO result = sut.getUserByToken("testtoken");

        // Verify that the correct methods are called
        assertNull(result);
    }

    @Test
    void unsuccesfullyGetUserByTokenGetException() throws SQLException{
        when(mockedConnectionManager.ConnectionStart()).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);

        UserDTO result = sut.getUserByToken("testtoken");

        // Verify that the correct methods are called
        assertNull(result);
    }
}
