package multithreading;

import concurrency.entities.BrokenWorker;
import concurrency.entities.WaitingWorker;
import concurrency.entities.Worker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CountDownLatchTesting {

    @Test
    public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion()
            throws InterruptedException {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);
        countDownLatch.await();
        outputScraper.add("Latch released");

        System.out.format("outputScraper: %s", outputScraper);
        assertThat(outputScraper).containsExactly(
                "Counted down",
                "Counted down",
                "Counted down",
                "Counted down",
                "Counted down",
                "Latch released"
        );
    }

    @Test
    public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime()
            throws InterruptedException {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new WaitingWorker(
                        outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);
        readyThreadCounter.await();
        outputScraper.add("Workers ready");
        callingThreadBlocker.countDown();
        completedThreadCounter.await();
        outputScraper.add("Workers complete");

        System.out.format("outputScraper: %s", outputScraper);
        assertThat(outputScraper)
                .containsExactly(
                        "Workers ready",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Workers complete"
                );
    }

    @Test
    public void whenFailingToParallelProcess_thenMainThreadShouldGetNotGetStuck()
            throws InterruptedException {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new BrokenWorker(outputScraper, countDownLatch)))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);

        boolean completed = countDownLatch.await(10L, TimeUnit.SECONDS);
        assertThat(completed).isFalse();
    }
}
