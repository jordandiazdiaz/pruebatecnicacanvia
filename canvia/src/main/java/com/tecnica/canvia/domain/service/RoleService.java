package com.tecnica.canvia.domain.service;

import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Transactional
  public Role createRole(Role role) {
    return roleRepository.save(role);
  }

  @Transactional
  public Role updateRole(Long id, Role roleDetails) {
    Role role = roleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Role not found"));

    role.setName(roleDetails.getName());
    return roleRepository.save(role);
  }

  public Role getRoleById(Long id) {
    return roleRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Role not found"));
  }

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

  @Transactional
  public void deleteRole(Long id) {
    roleRepository.deleteById(id);
  }
}