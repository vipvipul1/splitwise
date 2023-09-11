package org.vip.splitwise.strategies;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ExactShareStrategy")
public class ExactShareStrategy implements ShareStrategy {
    @Override
    public Map<String, Double> getAllUsersShare(List<String> allUserIds, List<Double> paidAmounts, List<Integer> shareUnits) {
        Map<String, Double> usersShare = new HashMap<>();
        List<String> orderedUserIds = allUserIds.stream().sorted().toList();
        for (int i = 0; i < orderedUserIds.size(); i++) {
            usersShare.put(orderedUserIds.get(i), Double.valueOf(shareUnits.get(i)));
        }
        return usersShare;
    }
}
