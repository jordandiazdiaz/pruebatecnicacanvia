package com.tecnica.canvia.application.service;

import com.tecnica.canvia.application.dto.RoleDto;
import com.tecnica.canvia.application.mapper.RoleMapper;
import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleApplicationService {

  private final RoleService roleService;
  private final RoleMapper roleMapper;

  public RoleApplicationService(RoleService roleService, RoleMapper roleMapper) {
    this.roleService = roleService;
    this.roleMapper = roleMapper;
  }

  @Transactional
  public RoleDto createRole(RoleDto roleDto) {
    Role role = roleMapper.toEntity(roleDto);
    Role savedRole = roleService.createRole(role);
    return roleMapper.toDto(savedRole);
  }

  @Transactional
  public RoleDto updateRole(Long id, RoleDto roleDto) {
    Role role = roleMapper.toEntity(roleDto);
    Role updatedRole = roleService.updateRole(id, role);
    return roleMapper.toDto(updatedRole);
  }

  public RoleDto getRoleById(Long id) {
    Role role = roleService.getRoleById(id);
    return roleMapper.toDto(role);
  }

  public List<RoleDto> getAllRoles() {
    List<Role> roles = roleService.getAllRoles();
    return roles.stream()
        .map(roleMapper::toDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteRole(Long id) {
    roleService.deleteRole(id);
  }
}
