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
        mainLogger.info(String.format(LogConstants.START_INFO.toString()));

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            accounts.add(new Account());
        }

        List<Thread> workingThreads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            workingThreads.add(new Thread(new AccountService(accounts)));
            workingThreads.get(i).start();
        }

        for (Thread workingThread : workingThreads) {
            try {
                workingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int accountSum = 0;
        for (Account account : accounts) {
            accountSum += account.getBalance();
        }
        mainLogger.info(String.format(LogConstants.END_INFO.toString(), accounts.size(), accountSum));
    }
}
