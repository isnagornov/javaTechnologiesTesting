package entities;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable, RunnableWorker {
    private List<String> outputScraper;
    private CountDownLatch countDownLatch;

    public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        try {
            doSomeWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        outputScraper.add("Counted down");
        countDownLatch.countDown();
    }
}
