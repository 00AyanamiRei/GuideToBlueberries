package com.ayanami.dao.tests;

import com.ayanami.dao.UserDAO;
import com.ayanami.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    @Mock
    private UserDAO userDAO;

    @Before
    public void setUp() {
        // Ініціалізація mock-об'єкту та анотацій Mockito
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = User.builder()
                .id(1)
                .userName("john")
                .password("password123")
                .mail("john@example.com")
                .build();

        // Act
        doNothing().when(userDAO).save(user);
        userDAO.save(user);

        // Assert
        verify(userDAO, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        User user = User.builder()
                .id(1)
                .userName("john")
                .password("password123")
                .mail("john@example.com")
                .build();

        // Act
        doNothing().when(userDAO).update(user);
        userDAO.update(user);

        // Assert
        verify(userDAO, times(1)).update(user);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User user = User.builder()
                .id(1)
                .userName("john")
                .password("password123")
                .mail("john@example.com")
                .build();

        // Act
        doNothing().when(userDAO).delete(user);
        userDAO.delete(user);

        // Assert
        verify(userDAO, times(1)).delete(user);
    }

    @Test
    public void testFindById() {
        // Arrange
        User user = User.builder()
                .id(1)
                .userName("john")
                .password("password123")
                .mail("john@example.com")
                .build();

        // Act
        when(userDAO.findById(1)).thenReturn(user);
        User result = userDAO.findById(1);

        // Assert
        assertEquals(user, result);
        verify(userDAO, times(1)).findById(1);
    }

    @Test
    public void testFindAllUsers() {
        // Arrange
        List<User> userList = Arrays.asList(
                User.builder().id(1).userName("john").password("password123").mail("john@example.com").build(),
                User.builder().id(2).userName("jane").password("password123").mail("jane@example.com").build(),
                User.builder().id(3).userName("james").password("password123").mail("james@example.com").build()
        );

        // Act
        when(userDAO.findAll()).thenReturn(userList);
        List<User> result = userDAO.findAll();

        // Assert
        assertEquals(userList, result);
        verify(userDAO, times(1)).findAll();
    }
}

