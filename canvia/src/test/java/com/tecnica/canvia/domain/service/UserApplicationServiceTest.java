package com.tecnica.canvia.domain.service;

import com.tecnica.canvia.application.dto.UserDto;
import com.tecnica.canvia.application.mapper.UserMapper;
import com.tecnica.canvia.application.service.UserApplicationService;
import com.tecnica.canvia.domain.model.User;
import com.tecnica.canvia.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserApplicationServiceTest {

  @Mock
  private UserService userService;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserApplicationService userApplicationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUser_Success() {
    UserDto userDto = new UserDto();
    userDto.setUsername("testuser");
    userDto.setEmail("test@example.com");
    userDto.setPassword("password");
    Set<Long> roleIds = new HashSet<>(Arrays.asList(1L, 2L));
    userDto.setRoleIds(roleIds);

    User user = new User();
    user.setUsername("testuser");
    user.setEmail("test@example.com");
    user.setPassword("password");

    User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setUsername("testuser");
    savedUser.setEmail("test@example.com");

    when(userMapper.toEntity(userDto)).thenReturn(user);
    when(userService.createUser(user, roleIds)).thenReturn(savedUser);
    when(userMapper.toDto(savedUser)).thenReturn(new UserDto(1L, "testuser", "test@example.com", null, roleIds));

    UserDto result = userApplicationService.createUser(userDto);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("testuser", result.getUsername());
    assertEquals("test@example.com", result.getEmail());
    assertEquals(roleIds, result.getRoleIds());

    verify(userMapper).toEntity(userDto);
    verify(userService).createUser(user, roleIds);
    verify(userMapper).toDto(savedUser);
  }

  @Test
  void getUserById_Success() {
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setEmail("test@example.com");

    UserDto userDto = new UserDto(1L, "testuser", "test@example.com", null, new HashSet<>());

    when(userService.getUserById(1L)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(userDto);

    UserDto result = userApplicationService.getUserById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("testuser", result.getUsername());
    assertEquals("test@example.com", result.getEmail());

    verify(userService).getUserById(1L);
    verify(userMapper).toDto(user);
  }

  @Test
  void getAllUsers_Success() {
    List<User> users = Arrays.asList(
        new User(1L, "user1", "user1@example.com", "password"),
        new User(2L, "user2", "user2@example.com", "password")
    );

    List<UserDto> userDtos = Arrays.asList(
        new UserDto(1L, "user1", "user1@example.com", null, new HashSet<>()),
        new UserDto(2L, "user2", "user2@example.com", null, new HashSet<>())
    );

    when(userService.getAllUsers()).thenReturn(users);
    when(userMapper.toDto(any(User.class))).thenReturn(userDtos.get(0), userDtos.get(1));

    List<UserDto> result = userApplicationService.getAllUsers();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("user1", result.get(0).getUsername());
    assertEquals("user2", result.get(1).getUsername());

    verify(userService).getAllUsers();
    verify(userMapper, times(2)).toDto(any(User.class));
  }

}