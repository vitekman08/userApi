package com.user.subscriptions.apiuser;

import com.user.subscriptions.apiuser.dto.UserDto;
import com.user.subscriptions.apiuser.exception.UserNotFoundException;
import com.user.subscriptions.apiuser.mappers.UserMapper;
import com.user.subscriptions.apiuser.model.User;
import com.user.subscriptions.apiuser.repository.UserRepository;
import com.user.subscriptions.apiuser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем тестового пользователя и DTO
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
    }

    @Test
    void testCreateUser() {
        // Подготовка
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Выполнение
        UserDto result = userService.createUser(userDto);

        // Проверка
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {
        // Подготовка
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        // Выполнение
        UserDto result = userService.getUserById(1L);

        // Проверка
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        // Подготовка
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Выполнение и проверка
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testUpdateUser() {
        // Подготовка
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // Создаем DTO для обновления
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(1L);
        updatedUserDto.setFirstName("UpdatedName");
        updatedUserDto.setLastName("Updated LastName");
        updatedUserDto.setEmail("updated.email@example.com");

        // Изменение полей в объекте пользователя через маппер
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName(updatedUserDto.getFirstName());
        updatedUser.setLastName(updatedUserDto.getLastName());
        updatedUser.setEmail(updatedUserDto.getEmail());

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toUserDto(updatedUser)).thenReturn(updatedUserDto);

        // Выполнение
        UserDto result = userService.updateUser(1L, updatedUserDto);

        // Проверка
        assertNotNull(result);
        assertEquals(updatedUserDto.getFirstName(), result.getFirstName());
        assertEquals(updatedUserDto.getLastName(), result.getLastName());
        assertEquals(updatedUserDto.getEmail(), result.getEmail());

        verify(userRepository, times(1)).save(any(User.class)); // Проверяем, что save был вызван
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Подготовка
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Выполнение и проверка
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, userDto));
    }

    @Test
    void testDeleteUser() {
        // Подготовка
        doNothing().when(userRepository).deleteById(1L);

        // Выполнение
        userService.deleteUser(1L);

        // Проверка
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Подготовка
        doThrow(new UserNotFoundException("User not found")).when(userRepository).deleteById(1L);

        // Выполнение и проверка
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
