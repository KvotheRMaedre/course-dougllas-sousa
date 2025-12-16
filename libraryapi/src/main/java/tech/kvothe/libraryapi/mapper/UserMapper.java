package tech.kvothe.libraryapi.mapper;

import org.mapstruct.Mapper;
import tech.kvothe.libraryapi.dto.UserDTO;
import tech.kvothe.libraryapi.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);
}
