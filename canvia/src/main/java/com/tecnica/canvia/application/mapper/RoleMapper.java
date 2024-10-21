package com.tecnica.canvia.application.mapper;

import com.tecnica.canvia.domain.model.Role;
import com.tecnica.canvia.application.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  RoleDto toDto(Role role);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  Role toEntity(RoleDto roleDto);
}
