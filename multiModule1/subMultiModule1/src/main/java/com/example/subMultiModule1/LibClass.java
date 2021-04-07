package com.example.subMultiModule1;

import com.example.subMultiModule2.LibModClass;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LibClass {
    public static String strProp = "libClassProperty";
    private static Object LOCK = new Object();
    private volatile int counter = 0;

    public synchronized void setLock(Object o) {
        LOCK = o;
    }

    public Object getLock() {
        return LOCK;
    }

    /**
     * Shortcut for calling wait
     */
    private void waitForLock() {
        try {
            LOCK.wait();
        } catch (Throwable e) {}
    }

    public String getParallelString() throws InterruptedException {
        final StringBuilder sb = new StringBuilder();
        setLock(LOCK);
        LibModClass lmc = new LibModClass();

        Runnable r = () -> {
            synchronized (LOCK) {
                waitForLock(); // Wait for the previous append to finish...
                counter++;
                String toAppend = lmc.sayHello();
                sb.append(toAppend);
                LOCK.notify(); // Notify the next thread ...
            }
        };

        Thread[] ts = new Thread[10];

        for (int i = 0; i < 10; ++i) {
            ts[i] = new Thread(r);
        }

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++i) {
                ts[i] = startThread(ts[i]);
            }
        }

        for (int i = 0; i < 10; ++i) {
            ts[i].join();
        }

        return sb.toString();
    }

    Thread startThread(Runnable r) {
        Thread t = new Thread(r);
        t.start();
        return t;
    }

}
