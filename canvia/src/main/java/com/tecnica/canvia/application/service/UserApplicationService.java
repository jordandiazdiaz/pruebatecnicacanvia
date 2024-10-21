package com.tecnica.canvia.application.service;

import com.tecnica.canvia.application.dto.UserDto;
import com.tecnica.canvia.application.mapper.UserMapper;
import com.tecnica.canvia.domain.model.User;
import com.tecnica.canvia.domain.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserApplicationService {

  private final UserService userService;
  private final UserMapper userMapper;

  public UserApplicationService(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @Transactional
  public UserDto createUser(UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User savedUser = userService.createUser(user, userDto.getRoleIds());
    return userMapper.toDto(savedUser);
  }

  @Transactional
  public UserDto updateUser(Long id, UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User updatedUser = userService.updateUser(id, user, userDto.getRoleIds());
    return userMapper.toDto(updatedUser);
  }

  public UserDto getUserById(Long id) {
    User user = userService.getUserById(id);
    return userMapper.toDto(user);
  }

  public List<UserDto> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return users.stream()
        .map(userMapper::toDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteUser(Long id) {
    userService.deleteUser(id);
  }
}