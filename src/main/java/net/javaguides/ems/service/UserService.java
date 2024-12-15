package net.javaguides.ems.service;

import net.javaguides.ems.models.User;
import net.javaguides.ems.models.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserService {
    User register(UserDto userDto);
}
