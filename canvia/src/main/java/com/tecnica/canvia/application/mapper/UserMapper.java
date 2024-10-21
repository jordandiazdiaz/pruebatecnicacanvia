package com.tecnica.canvia.application.mapper;

import com.tecnica.canvia.domain.model.User;
import com.tecnica.canvia.application.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "password", ignore = true)
  UserDto toDto(User user);

  @Mapping(target = "id", ignore = true)
  User toEntity(UserDto userDto);
}