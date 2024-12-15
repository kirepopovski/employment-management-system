package net.javaguides.ems.service;

import net.javaguides.ems.models.User;
import net.javaguides.ems.models.dto.LoginRequest;

import java.util.List;

public interface AuthService {
    User login(String username, String password);
    List<User> findAll();
}
