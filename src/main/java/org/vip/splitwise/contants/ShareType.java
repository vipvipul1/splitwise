package org.vip.splitwise.contants;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public enum ShareType {
    EQUAL("Equal"),
    PERCENT("Percent"),
    RATIO("Ratio"),
    EXACT("Exact");

    private String label;

    ShareType(String label) {
        this.label = label;
    }

    public static ShareType getByLabel(String label) {
        for (ShareType shareType: ShareType.values()) {
            if (shareType.getLabel().equals(label))
                return shareType;
        }
        throw new IllegalArgumentException("No such ShareType with label: " + label);
    }
}
