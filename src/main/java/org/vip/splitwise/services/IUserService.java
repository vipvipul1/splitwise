package org.vip.splitwise.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vip.splitwise.dtos.UserRequestDto;
import org.vip.splitwise.dtos.UserResponseDto;
import org.vip.splitwise.models.User;
import org.vip.splitwise.repositories.UserRepository;

import java.util.Optional;

@Service
public class IUserService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IUserService.class);

    private UserRepository userRepository;

    @Autowired
    public IUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User registerUser(UserRequestDto requestDto) {
        User savedUser;
        try {
            User user = new User();
            user.setUsername(requestDto.getUsername());
            user.setPhone(requestDto.getPhone());
            user.setPassword(requestDto.getPassword());
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error in IUserService -> registerUser() : " + e.getMessage());
            throw e;
        }
        return savedUser;
    }
}
