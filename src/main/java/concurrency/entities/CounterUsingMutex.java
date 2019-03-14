package concurrency.entities;

import java.util.concurrent.Semaphore;

public class CounterUsingMutex {

    private final Semaphore semaphore;
    private int count;

    public CounterUsingMutex() {
        semaphore = new Semaphore(1);
        count = 0;
    }

    public void increase() throws InterruptedException {
        semaphore.acquire();
        this.count = this.count + 1;
        Thread.sleep(1000);
        semaphore.release();

    }

    public int getCount() {
        return this.count;
    }

    public boolean hasQueuedThreads() {
        return semaphore.hasQueuedThreads();
    }

}