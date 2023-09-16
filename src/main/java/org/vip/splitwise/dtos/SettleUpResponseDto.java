package org.vip.splitwise.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.User;
import org.vip.splitwise.models.UserExpense;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettleUpResponseDto extends BaseResponse {
    private Group group;
    private List<SettleUpTrxn> settleUpTrxns;
}
