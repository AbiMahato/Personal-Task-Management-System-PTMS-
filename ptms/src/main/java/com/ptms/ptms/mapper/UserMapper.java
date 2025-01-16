package com.ptms.ptms.mapper;

import com.ptms.ptms.dto.AuthRequest;
import com.ptms.ptms.dto.UserDto;
import com.ptms.ptms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto convertUserEntityToDto(User user);
    User converDtoToUserEntity(UserDto userDto);
}
