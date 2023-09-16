package org.vip.splitwise.services;

import org.vip.splitwise.dtos.SettleUpResponseDto;
import org.vip.splitwise.exceptions.GroupNotFoundException;

public interface SettleUpService {
    SettleUpResponseDto getUserSettleUpTransactionsAll(String userId);

    SettleUpResponseDto getGroupSettleUpTransactionsAll(String groupId) throws GroupNotFoundException;

    Double getUserTotalOutstanding(String userId);
}
