package ru.playtox.accountservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.playtox.logging.LogConstants;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountService implements Runnable {
    private static final int MAX_TRANSFER = 30;
    private static final AtomicInteger operation_number = new AtomicInteger();
    private final List<Account> availableAccounts;

    private final Logger accServLogger = LogManager.getLogger(AccountService.class);
    private final Random randomizer;

    public AccountService(List<Account> availableAccounts) {
        randomizer = new Random();
        this.availableAccounts = availableAccounts;
    }

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        try {
            while (operation_number.get() < MAX_TRANSFER) {
                accServLogger.info(String.format(LogConstants.OPERATION_INFO.toString(), operation_number.incrementAndGet()));
                /*
                To avoid transfers to the same account, in case if acc2 == acc1 the second account should be chosen again
                 */
                Account acc1 = chooseAccount();
                Account acc2;
                do {
                    acc2 = chooseAccount();
                } while (acc1 == acc2);

                transfer(acc1, acc2);
                Thread.sleep((int) (Math.random() * 1000) + 1000);
            }
        } catch (InterruptedException e) {
            accServLogger.error(LogConstants.INTERRUPTED_ERROR.toString(), e);
        }
    }

    public void transfer(Account from, Account to) {
        if (from.getBalance() <= 0) {
            accServLogger.warn(LogConstants.ACCOUNT_BALANCE_NEGATIVE_WARN.toString(), from.getId());
            return;
        }

        int sum = randomizer.nextInt(from.getBalance());
        accServLogger.info(String.format(LogConstants.TRANSFER_INFO.toString(),sum, from.getId(), to.getId() ));
        try {
            from.withdraw(sum);
            to.deposit(sum);
        } catch(IllegalArgumentException e) {
            accServLogger.error(String.format(LogConstants.BALANCE_ERROR.toString(), sum, from.getId(), from.getBalance()), e);
        }
    }

    private Account chooseAccount() {
        return availableAccounts.get((int) (Math.random() * availableAccounts.size()));
    }
}
