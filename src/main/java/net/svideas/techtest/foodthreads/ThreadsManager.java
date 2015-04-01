package net.svideas.techtest.foodthreads;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class ThreadsManager {

    private volatile Map<Runnable, Thread> threads = new HashMap<Runnable, Thread>();

    public synchronized void stopAllThreads() {
        for (Thread thread : threads.values()) {
            thread.interrupt();
        }
    }

    public synchronized void fireThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        threads.put(runnable, thread);
    }

    public synchronized void killThread(Runnable runnable) {
        threads.get(runnable).interrupt();
        threads.remove(runnable);
    }

}
