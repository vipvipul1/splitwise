package org.vip.splitwise.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vip.splitwise.models.Group;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDto extends BaseResponse {
    private Group group;
}
