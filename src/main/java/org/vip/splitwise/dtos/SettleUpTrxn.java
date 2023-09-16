package org.vip.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettleUpTrxn {
    private String fromUserId;
    private String toUserId;
    private Double amount;
}