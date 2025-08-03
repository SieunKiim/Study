package org.example.question1;

import static org.example.MyLogger.log;

import org.example.MyLogger;

public class CounterThread extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            log("value: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
