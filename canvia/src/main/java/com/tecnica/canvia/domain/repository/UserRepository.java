package com.tecnica.canvia.domain.repository;

import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
  User save(User user);
  Optional<User> findById(Long id);
  List<User> findAll();
  void deleteById(Long id);
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}