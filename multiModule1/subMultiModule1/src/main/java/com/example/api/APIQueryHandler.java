package com.example.api;

import com.example.data.ConfigData;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Performs a network call in parallel using the provided config options. */
public class APIQueryHandler {
  private ConfigData[] configs;
  private List<String> outputs;
  private static Lock LOCK = new ReentrantLock(true);

  private volatile int requestCounter = 0;

  public Lock getLock() {
    return LOCK;
  }

  public ConfigData[] getConfigs() {
    return configs;
  }

  public synchronized void setConfigs(ConfigData[] configs) {
    this.configs = configs;
  }

  public List<String> getOutputs() {
    return outputs;
  }

  Condition c = LOCK.newCondition();

  Condition getC() {
    return c;
  }

  public APIQueryHandler(ConfigData[] configs) {
    this.configs = configs;
  }

  /** Shortcut for calling wait */
  private void waitForLock(Condition c) {
    try {
      c.wait();
    } catch (Throwable e) {
    }
  }

  /**
   * Performs network queries in parallel and retrieves the info.
   *
   * @return The list of results.
   * @throws InterruptedException
   */
  public void getDataInParallel() throws InterruptedException {

    Thread[] ts = new Thread[configs.length];

    // Locks make use of condition variables for synchronization.
    Condition prevDone = LOCK.newCondition();
    for (int i = 0; i < configs.length; ++i) {
      int finalI = i;
      Lock l = new ReentrantLock();
      ts[i] =
          new Thread(
              () -> {
                ConfigData data = configs[finalI];
                UrlRequest req = new UrlRequest(data.getUrl(), data.getParams());
                String res = req.doRequest();
                synchronized (LOCK) {
                  try {
                    getC().wait();
                  } catch (InterruptedException | IllegalMonitorStateException e) {
                    e.printStackTrace();
                  }
                  waitForLock(prevDone); // Wait for access to the list...

                  requestCounter++;
                  outputs.add(res);
                  prevDone.signal(); // Notify the next thread ...
                  c.signal();
                }
              });
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
