package com.example.tp_unit_test.service;

import com.example.tp_unit_test.exception.DataClownException;
import com.example.tp_unit_test.model.User;
import com.example.tp_unit_test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Test get all users")
    @Test
    public void testGetAllUsers(){
        User user = new User(1L, "Mathieu", "mathieu@gmail.com", "1234");
        User user1 = new User(2L, "Jimmy", "jimmy@gmail.com", "1234");

        List<User> listUser = Arrays.asList(user1, user);

        when(userRepository.findAll()).thenReturn(listUser);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @DisplayName("Test find by id")
    @Test
    public void testFindUserById() {
        User user = new User(1L, "Mathis", "mathis@outlook.fr", "1234");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> user1 = userService.getUserById(1L);

        assertTrue(user1.isPresent());
        assertEquals("Mathis", user1.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @DisplayName("Test save")
    @Test
    public void testSaveUser() {
        User user = new User(1L, "Mathieu", "mathieu@gmail.com", "1234");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User user1 = userService.saveUser(user);

        assertEquals("Mathieu", user1.getName());
        assertEquals("1234", user1.getPassword());

        verify(userRepository, times(1)).save(user);

    }

    @DisplayName("Test update")
    @Test
    public void testUpdateUser(){
        User existingUser = new User(1L,"Mathieu","mathieu@gmail.com", "1234");
        User updatedUser = new User(1L, "Mathieu", "mathis@gmail.com", "1234");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Optional<User> user1 = userService.getUserById(1L);
        assertTrue(user1.isPresent());

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        User userUpdated = userService.updateUser(1L, updatedUser);

        assertEquals("mathis@gmail.com", userUpdated.getEmail());
        verify(userRepository,times(2)).findById(1L);
        verify(userRepository,times(1)).save(updatedUser);

    }

    @DisplayName("Test delete")
    @Test
    public void testDeleteUser() {
        User user = new User(1L, "Mathieu", "mathieu.muse@outlook.fr", "1234");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> user1 = userService.getUserById(1L);
        assertTrue(user1.isPresent());

        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUserById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @DisplayName("Test duplicated email")
    @Test
    public void testDuplicatedEmail() {
        User user = new User();
        User user1 = new User();
        user.setEmail("mathieu.muse@outlook.fr");
        user1.setEmail("mathieu.muse@outlook.fr");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        try {
            userService.saveUser(user1);
            fail("Expected an DataClownException to be thrown");
        } catch (DataClownException e) {
            assertThat(e.getMessage(), is("Email already exists"));
        }
    }

}
