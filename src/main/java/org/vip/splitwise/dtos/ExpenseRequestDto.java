package org.vip.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;
import org.vip.splitwise.contants.PayType;
import org.vip.splitwise.contants.ShareType;

import java.util.List;

@Getter
@Setter
public class ExpenseRequestDto {
    private List<String> userIds;
    private PayType payType;
    private List<Double> paidAmounts;
    private ShareType shareType;
    private List<Integer> shareUnits;
    private String description;
}
