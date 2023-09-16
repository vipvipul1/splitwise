package org.vip.splitwise.models;

public enum ExpenseType {
    // REAL         : Used when user adds an expense to a group on individual users.
    // SETTLEMENT   : Used when user clicks on Settle Up and a reverse transaction is added in db by system.
    REAL, SETTLEMENT
}
