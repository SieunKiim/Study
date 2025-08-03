package org.example.question1;

public class ThreadQuestion1 {

    public static void main(String[] args) {
        CounterThread thread = new CounterThread();
        thread.start();
    }

}
