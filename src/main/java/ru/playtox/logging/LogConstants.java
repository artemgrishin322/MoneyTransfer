package ru.playtox.logging;

public enum LogConstants {
    ACCOUNT_CREATED_INFO("%s is created%n"),
    BALANCE_ERROR("Requested sum = %d is bigger than account {%s} balance = %d%n"),
    WITHDRAW_INFO("Withdraw procedure from account {%s}: balance before withdrawal = %d, withdrawal sum = %d, account balance = %d%n"),
    DEPOSIT_INFO("Deposit procedure to account {%s}, balance before deposit = %d, deposit sum = %d, balance after deposit = %d%n"),
    ACCOUNT_SERVICE_CREATED_INFO("Account service {%s} is created%n"),
    OPERATION_INFO("Operation #%d%n"),
    INTERRUPTED_ERROR("Thread was interrupted%n"),
    ACCOUNT_BALANCE_NEGATIVE_WARN("Not enough money on the account {%s}%n"),
    TRANSFER_INFO("Sum to be transferred = %d from {%s} to {%s}%n");

    private final String msgPattern;

    LogConstants(String msgPattern) {
        this.msgPattern = msgPattern;
    }

    @Override
    public String toString() {
        return msgPattern;
    }
}
