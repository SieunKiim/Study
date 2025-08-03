package org.example.question1;

import static org.example.MyLogger.log;

import java.awt.print.Pageable;

public class ThreadQuestion2  {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
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
        }, "counter");
        thread.start();
    }

   

}
