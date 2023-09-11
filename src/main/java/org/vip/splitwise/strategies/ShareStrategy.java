package org.vip.splitwise.strategies;

import java.util.List;
import java.util.Map;

public interface ShareStrategy {
    Map<String, Double> getAllUsersShare(List<String> allUserIds, List<Double> paidAmounts, List<Integer> shareUnits);
}
