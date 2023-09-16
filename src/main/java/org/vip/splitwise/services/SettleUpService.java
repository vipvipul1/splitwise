package org.vip.splitwise.services;

import org.vip.splitwise.dtos.SettleUpResponseDto;
import org.vip.splitwise.exceptions.GroupNotFoundException;

public interface SettleUpService {
    SettleUpResponseDto getAllUserSettleUpTransactions(String userId);

    SettleUpResponseDto getAllGroupSettleUpTransactions(String groupId) throws GroupNotFoundException;
}
