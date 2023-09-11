package org.vip.splitwise.contants;

import lombok.Getter;

@Getter
public enum PayType {
    I_PAY("iPay"),
    MULTI_PAY("multiPay");

    private String label;

    PayType(String label) {
        this.label = label;
    }

    public static PayType getByLabel(String label) {
        for (PayType payType: PayType.values()) {
            if (payType.getLabel().equals(label))
                return payType;
        }
        throw new IllegalArgumentException("No such PayType with label: " + label);
    }
}
