package com.example.api;

import com.example.data.ConfigData;

import java.util.List;

public class APIQueryHandler {
    private List<ConfigData> configs;
    private List<String> outputs = null;
    private static Object LOCK = new Object();

    private volatile int requestCounter = 0;

    public synchronized void setLock(Object o) {
        LOCK = o;
    }

    public Object getLock() {
        return LOCK;
    }

    public List<ConfigData> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigData> configs) {
        this.configs = configs;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public APIQueryHandler(List<ConfigData> configs) {
        this.configs = configs;
    }

    /**
     * Shortcut for calling wait
     */
    private void waitForLock() {
        try {
            LOCK.wait();
        } catch (Throwable e) {}
    }

    /**
     * Performs network queries in parallel and retrieves the info.
     * @return The list of results.
     * @throws InterruptedException
     */
    public void getDataInParallel() throws InterruptedException {
        setLock(LOCK);

        Thread[] ts = new Thread[configs.size()];

        for (int i = 0; i < configs.size(); ++i) {
            int finalI = i;
            ts[i] = new Thread(
                    () -> {
                        ConfigData data = configs.get(finalI);
                        UrlRequest req = new UrlRequest(data.getUrl(), data.getParams());

                        String res = req.doRequest();
                        synchronized (LOCK) {
                            this.waitForLock(); // Wait for access to the list...
                            requestCounter++;
                            outputs.add(res);
                            LOCK.notify(); // Notify the next thread ...
                        }
                    }

            );
        }

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++i) {
                ts[i] = startThread(ts[i]);
            }
        }

        for (int i = 0; i < 10; ++i) {
            ts[i].join();
        }
    }

    Thread startThread(Runnable r) {
        Thread t = new Thread(r);
        t.run();
        return t;
    }
}
