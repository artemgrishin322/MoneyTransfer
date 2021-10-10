package ru.playtox.accountservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.playtox.logging.LogConstants;

public class Account {
    private static final int INITIAL_MONEY_VAL = 10_000;

    private final Logger accLogger = LogManager.getLogger(Account.class);
    private final String id;
    private volatile int balance;

    public Account() {
        id = IdGenerator.getIdForAcc();
        balance = INITIAL_MONEY_VAL;
    }

    /*
    Constructor added in case you want to set the initial balance manually
     */
    public Account(int balance) {
        id = IdGenerator.getIdForAcc();
        this.balance = balance;
    }

    public synchronized void withdraw(int sum) throws IllegalArgumentException {
        if (sum > balance) {
            throw new IllegalArgumentException("Not enough money on account");
        }

        int balanceBeforeWithdrawal = balance;
        balance -= sum;
        accLogger.info(String.format(LogConstants.WITHDRAW_INFO.toString(), id, balanceBeforeWithdrawal, sum, balance));
    }

    public synchronized void deposit(int sum) {
        int balanceBeforeDeposit = balance;
        balance += sum;
        accLogger.info(String.format(LogConstants.DEPOSIT_INFO.toString(), id, balanceBeforeDeposit, sum, balance));
    }

    public int getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }
}
