package org.vip.splitwise.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDto {
    private Group group;
    private User addedUser;
    private User addedByUser;
}
