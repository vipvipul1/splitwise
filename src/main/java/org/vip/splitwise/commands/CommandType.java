package org.vip.splitwise.commands;

import lombok.Getter;

@Getter
public enum CommandType {
    REGISTER("Register"),
    UPDATE_PROFILE("UpdateProfile"),
    ADD_GROUP("AddGroup"),
    ADD_MEMBER("AddMember"),
    GROUPS("Groups"),
    EXPENSE("Expense"),
    SETTLE_UP("SettleUp"),
    MY_TOTAL("MyTotal");

    private final String label;

    CommandType(String label) {
        this.label = label;
    }
}
