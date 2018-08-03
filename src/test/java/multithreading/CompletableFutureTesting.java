package multithreading;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CompletableFutureTesting {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World");

        final String result = future.get();
        assertEquals("Hello World", result);
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture
                .thenApply(s -> s + " World");

        assertEquals("Hello World", future.get());

        completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future2 = completableFuture
                .thenAccept(s -> System.out.println("Computation returned: " + s));

        future2.get();

        completableFuture = CompletableFuture.supplyAsync(() -> "Hello");

        future2 = completableFuture.thenRun(() -> System.out.println("Computation finished."));

        future2.get();

        completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

        assertEquals("Hello World", completableFuture.get());
    }

    @Test
    public void test3() {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        assertEquals("Hello Beautiful World", combined);
    }

    @Test
    public void test4() throws ExecutionException, InterruptedException {
        String name1 = null;
        String name2 = "Igor";

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> TestExecutable.execute(name1))
                .handle((s, t) -> s != null ? s : "Hello, Stranger");
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> TestExecutable.execute(name2))
                .handle((s, t) -> s != null ? s : "Hello, Stranger");

        assertEquals("Hello, Stranger", completableFuture1.get());
        assertEquals("Hello, Igor", completableFuture2.get());
    }

    private interface TestExecutable {
        static String execute(String param) {
            if (param == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + param;
        }
    }
}
