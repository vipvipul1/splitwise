package org.vip.splitwise.commands;

import lombok.Getter;

@Getter
public enum CommandType {
    REGISTER("Register"),
    UPDATE_PROFILE("UpdateProfile"),
    SETTLE_UP("SettleUp");

    private final String label;

    CommandType(String label) {
        this.label = label;
    }
}
