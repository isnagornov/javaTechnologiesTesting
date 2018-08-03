package multithreading;

import entities.CustomRecursiveAction;
import entities.CustomRecursiveTask;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoinTesting {

    private static final ForkJoinPool commonPool = ForkJoinPool.commonPool();

    @Test
    public void testRecursiveAction() throws ExecutionException, InterruptedException {
        CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction("qwertyuiopasdfghhjkklzxcvbnnm");
        commonPool.submit(customRecursiveAction).get();
    }

    @Test
    public void testRecursiveTask() {
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask(IntStream.range(10, 100).toArray());
        System.out.format("Computation result - %d", commonPool.invoke(customRecursiveTask));
        System.out.println();
    }
}

