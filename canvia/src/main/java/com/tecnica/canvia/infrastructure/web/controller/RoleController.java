package com.tecnica.canvia.infrastructure.web.controller;

import com.tecnica.canvia.application.dto.RoleDto;
import com.tecnica.canvia.application.service.RoleApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleApplicationService roleApplicationService;

  public RoleController(RoleApplicationService roleApplicationService) {
    this.roleApplicationService = roleApplicationService;
  }

  @PostMapping
  public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
    return ResponseEntity.ok(roleApplicationService.createRole(roleDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
    return ResponseEntity.ok(roleApplicationService.updateRole(id, roleDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoleDto> getRole(@PathVariable Long id) {
    return ResponseEntity.ok(roleApplicationService.getRoleById(id));
  }

  @GetMapping
  public ResponseEntity<List<RoleDto>> getAllRoles() {
    return ResponseEntity.ok(roleApplicationService.getAllRoles());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
    roleApplicationService.deleteRole(id);
    return ResponseEntity.ok().build();
  }
}