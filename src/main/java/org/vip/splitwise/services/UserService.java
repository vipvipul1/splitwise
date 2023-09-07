package org.vip.splitwise.services;

import org.springframework.stereotype.Service;
import org.vip.splitwise.dtos.UserRequestDto;
import org.vip.splitwise.dtos.UserResponseDto;
import org.vip.splitwise.models.User;

public interface UserService {
    User registerUser(UserRequestDto requestDto);
}
