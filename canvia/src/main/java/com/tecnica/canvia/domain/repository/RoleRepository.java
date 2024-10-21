package com.tecnica.canvia.domain.repository;

import com.tecnica.canvia.domain.model.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role save(Role role);
  Optional<Role> findById(Long id);
  List<Role> findAll();
  void deleteById(Long id);
  Optional<Role> findByName(String name);
}
