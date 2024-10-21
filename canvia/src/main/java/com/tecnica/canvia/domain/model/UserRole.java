package com.tecnica.canvia.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Lombok generará un constructor sin argumentos
@Entity
@Table(name = "user_roles")
public class UserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  // Constructor que acepta User y Role
  public UserRole(User user, Role role) {
    this.user = user;
    this.role = role;
  }
}