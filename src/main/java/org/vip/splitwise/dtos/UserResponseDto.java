package org.vip.splitwise.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto extends BaseResponse {
    private Long userId;
    private String username;
    private Long phone;
}
