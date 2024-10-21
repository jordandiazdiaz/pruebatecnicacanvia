package com.tecnica.canvia.application.service;

import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.repository.RoleRepository;
import com.tecnica.canvia.domain.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

  @Mock
  private RoleRepository roleRepository;

  @InjectMocks
  private RoleService roleService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRole_Success() {
    Role role = new Role();
    role.setName("ROLE_TEST");

    when(roleRepository.save(any(Role.class))).thenReturn(role);

    Role createdRole = roleService.createRole(role);

    assertNotNull(createdRole);
    assertEquals("ROLE_TEST", createdRole.getName());
    verify(roleRepository).save(any(Role.class));
  }

  @Test
  void getRoleById_Success() {
    Role role = new Role();
    role.setId(1L);
    role.setName("ROLE_TEST");

    when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

    Role foundRole = roleService.getRoleById(1L);

    assertNotNull(foundRole);
    assertEquals(1L, foundRole.getId());
    assertEquals("ROLE_TEST", foundRole.getName());
  }

  @Test
  void getRoleById_NotFound() {
    when(roleRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> roleService.getRoleById(1L));
  }


}
