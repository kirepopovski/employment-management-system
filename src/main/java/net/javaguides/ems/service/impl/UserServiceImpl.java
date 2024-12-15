package net.javaguides.ems.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.javaguides.ems.models.User;
import net.javaguides.ems.models.dto.UserDto;
import net.javaguides.ems.models.exceptions.InvalidUsernameOrPasswordException;
import net.javaguides.ems.models.exceptions.UsernameAlreadyExistsException;
import net.javaguides.ems.repository.UserRepository;
import net.javaguides.ems.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null
                || userDto.getUsername().isEmpty() || userDto.getPassword().isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }


        if(this.userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(userDto.getUsername());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

}
