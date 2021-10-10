package ru.playtox.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.playtox.accountservice.Account;
import ru.playtox.accountservice.AccountService;
import ru.playtox.logging.LogConstants;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static final Logger mainLogger = LogManager.getLogger(Runner.class);

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            accounts.add(new Account());
        }
        logAccountsInfo(accounts);

        List<Thread> workingThreads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread newWorkingThread = new Thread(new AccountService(accounts));
            newWorkingThread.start();
            workingThreads.add(newWorkingThread);
        }

        for (Thread workingThread : workingThreads) {
            try {
                workingThread.join();
            } catch (InterruptedException e) {
                mainLogger.error(String.format(LogConstants.INTERRUPTED_ERROR.toString()), e);
            }
        }
        logAccountsInfo(accounts);
    }

    private static void logAccountsInfo(List<Account> accounts) {
        int sum = 0;
        for(Account acc : accounts) {
            sum += acc.getBalance();
            mainLogger.info(String.format(LogConstants.ACCOUNT_BALANCE_INFO.toString(), acc.getId(), acc.getBalance()));
        }
        mainLogger.info(String.format(LogConstants.ACCOUNT_SUM_INFO.toString(), accounts.size(), sum));
    }
}
