package ru.playtox.accountservice;

import java.util.Random;

public class IdGenerator {
    public static String getIdForAcc() {
        Random random = new Random();

        return "acc-" + random.nextInt(10000) + "-id_acc-" + random.nextInt(100);
    }
}
