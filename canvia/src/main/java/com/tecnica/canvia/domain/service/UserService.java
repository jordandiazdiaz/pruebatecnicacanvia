package com.tecnica.canvia.domain.service;

import com.tecnica.canvia.domain.model.User;
import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.domain.model.UserRole;
import com.tecnica.canvia.domain.repository.UserRepository;
import com.tecnica.canvia.domain.repository.RoleRepository;
import com.tecnica.canvia.domain.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository,
      UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User createUser(User user, Set<Long> roleIds) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new RuntimeException("Username already exists");
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new RuntimeException("Email already exists");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);

    assignRolesToUser(savedUser, roleIds);

    return savedUser;
  }

  @Transactional
  public User updateUser(Long id, User userDetails, Set<Long> roleIds) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    user.setUsername(userDetails.getUsername());
    user.setEmail(userDetails.getEmail());
    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
    }

    User updatedUser = userRepository.save(user);

    // Remove existing roles
    userRoleRepository.findByUserId(id).forEach(userRole ->
        userRoleRepository.deleteByUserIdAndRoleId(id, userRole.getRole().getId()));

    // Assign new roles
    assignRolesToUser(updatedUser, roleIds);

    return updatedUser;
  }

  private void assignRolesToUser(User user, Set<Long> roleIds) {
    for (Long roleId : roleIds) {
      Role role = roleRepository.findById(roleId)
          .orElseThrow(() -> new RuntimeException("Role not found"));
      if (!userRoleRepository.existsByUserIdAndRoleId(user.getId(), role.getId())) {
        userRoleRepository.save(new UserRole(user, role));
      }
    }
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found");
    }
    userRepository.deleteById(id);
  }
}
