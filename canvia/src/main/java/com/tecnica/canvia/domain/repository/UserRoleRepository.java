package com.tecnica.canvia.domain.repository;

import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.model.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {
  UserRole save(UserRole userRole);
  List<UserRole> findByUserId(Long userId);
  void deleteByUserIdAndRoleId(Long userId, Long roleId);
  boolean existsByUserIdAndRoleId(Long userId, Long roleId);
}
