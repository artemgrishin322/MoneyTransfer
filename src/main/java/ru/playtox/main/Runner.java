package ru.playtox.main;

import ru.playtox.accountservice.Account;
import ru.playtox.accountservice.AccountService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<Account> accounts = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            accounts.add(new Account());
        }

        for (int i = 0; i < 2; i++) {
            new Thread(new AccountService(accounts)).start();
        }
    }
}
