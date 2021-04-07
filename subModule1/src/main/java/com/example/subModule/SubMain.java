package com.example.subModule;

import com.example.subMultiModule2.LibModClass;

import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class SubMain {


    Lock l = new ReentrantLock(true);
    Condition c = l.newCondition();

    public String doTHing() throws InterruptedException {
        LibModClass a;
        a = new LibModClass();

        Thread[] threads = new Thread[123];
        Runnable r = () -> {
            l.lock();
            try { c.wait(); } catch (InterruptedException ignored) {}

            doOp(a);

            c.signal();
            l.unlock();
        };


        for (int i = 0; i  < 123; ++i) {
            threads[i] = new Thread(r);
        }

        for (int i = 0; i < 123; ++i) {
            // forgot to use threads anywhere...
        }

        return a.sayHello();
    }

    void doOp(LibModClass a) {
        String test = a.sayHello();

        Pattern p = Pattern.compile("true|false");
        if (null != test && p.matcher(test).find()) {
            System.out.println(test);
        } else {
            System.exit(22);
        }
    }

    public int hashCode() {
        return 3;
    }
}
