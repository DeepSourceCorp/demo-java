package com.example.subMultiModule1;

import com.example.subMultiModule2.LibModClass;

public class LibClass {
    public String strProp = "libClassProperty";
    static Object LOCKf = new Object();
    private volatile int counter = 0;

    public void setLock(Object o) {
        LOCKf = o;
    }

    public String getParallelString() throws InterruptedException {
        final StringBuilder sb = new StringBuilder();
        setLock(LOCKf);
        LibModClass lmc = new LibModClass();
        lmc.abc = null;
        Runnable r = () -> {
            synchronized (LOCKf) {
                counter++;
            }
            String toAppend = lmc.sayHello();
            sb.append(toAppend);
        };

        Thread[] ts = new Thread[10];

        for (int i = 0; i < 10; ++i) {
            ts[i] = new Thread(r);
        }

        for (int i = 0; i < 10; ++i) {
            ts[i].run();
        }

        for (int i = 0; i < 10; ++i) {
            ts[i].join();
        }

        return sb.toString();
    }

}
