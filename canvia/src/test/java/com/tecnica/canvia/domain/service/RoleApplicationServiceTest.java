package com.tecnica.canvia.domain.service;

import com.tecnica.canvia.application.dto.RoleDto;
import com.tecnica.canvia.application.mapper.RoleMapper;
import com.tecnica.canvia.application.service.RoleApplicationService;
import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleApplicationServiceTest {

  @Mock
  private RoleService roleService;

  @Mock
  private RoleMapper roleMapper;

  @InjectMocks
  private RoleApplicationService roleApplicationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRole_Success() {
    RoleDto roleDto = new RoleDto();
    roleDto.setName("ROLE_TEST");

    Role role = new Role();
    role.setName("ROLE_TEST");

    Role savedRole = new Role();
    savedRole.setId(1L);
    savedRole.setName("ROLE_TEST");

    when(roleMapper.toEntity(roleDto)).thenReturn(role);
    when(roleService.createRole(role)).thenReturn(savedRole);
    when(roleMapper.toDto(savedRole)).thenReturn(new RoleDto(1L, "ROLE_TEST"));

    RoleDto result = roleApplicationService.createRole(roleDto);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("ROLE_TEST", result.getName());

    verify(roleMapper).toEntity(roleDto);
    verify(roleService).createRole(role);
    verify(roleMapper).toDto(savedRole);
  }

  @Test
  void getRoleById_Success() {
    Role role = new Role();
    role.setId(1L);
    role.setName("ROLE_TEST");

    RoleDto roleDto = new RoleDto(1L, "ROLE_TEST");

    when(roleService.getRoleById(1L)).thenReturn(role);
    when(roleMapper.toDto(role)).thenReturn(roleDto);

    RoleDto result = roleApplicationService.getRoleById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("ROLE_TEST", result.getName());

    verify(roleService).getRoleById(1L);
    verify(roleMapper).toDto(role);
  }

  @Test
  void getAllRoles_Success() {
    List<Role> roles = Arrays.asList(
        new Role(1L, "ROLE_ADMIN"),
        new Role(2L, "ROLE_USER")
    );

    List<RoleDto> roleDtos = Arrays.asList(
        new RoleDto(1L, "ROLE_ADMIN"),
        new RoleDto(2L, "ROLE_USER")
    );

    when(roleService.getAllRoles()).thenReturn(roles);
    when(roleMapper.toDto(any(Role.class))).thenReturn(roleDtos.get(0), roleDtos.get(1));

    List<RoleDto> result = roleApplicationService.getAllRoles();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("ROLE_ADMIN", result.get(0).getName());
    assertEquals("ROLE_USER", result.get(1).getName());

    verify(roleService).getAllRoles();
    verify(roleMapper, times(2)).toDto(any(Role.class));
  }

}
