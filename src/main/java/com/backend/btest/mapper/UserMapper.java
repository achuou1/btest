package com.backend.btest.mapper;

import com.backend.btest.dto.UserDTO;
import com.backend.btest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    List<UserDTO> toDto(List<User> device);

    List<User> toEntity(List<UserDTO> userDTO);
}
