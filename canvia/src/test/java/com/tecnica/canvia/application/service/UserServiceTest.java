package com.tecnica.canvia.application.service;

import com.tecnica.canvia.domain.model.User;
import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.repository.UserRepository;
import com.tecnica.canvia.domain.repository.RoleRepository;
import com.tecnica.canvia.domain.repository.UserRoleRepository;
import com.tecnica.canvia.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private UserRoleRepository userRoleRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUser_Success() {
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("test@example.com");
    user.setPassword("password");

    Role role = new Role();
    role.setId(1L);
    role.setName("ROLE_USER");

    Set<Long> roleIds = new HashSet<>();
    roleIds.add(1L);

    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

    User createdUser = userService.createUser(user, roleIds);

    assertNotNull(createdUser);
    assertEquals("testuser", createdUser.getUsername());
    assertEquals("test@example.com", createdUser.getEmail());
    assertEquals("encodedPassword", createdUser.getPassword());

    verify(userRepository).save(any(User.class));
    verify(userRoleRepository).save(any());
  }

  @Test
  void createUser_UsernameExists() {
    User user = new User();
    user.setUsername("existinguser");

    when(userRepository.existsByUsername("existinguser")).thenReturn(true);

    assertThrows(RuntimeException.class, () -> userService.createUser(user, new HashSet<>()));
  }


}
