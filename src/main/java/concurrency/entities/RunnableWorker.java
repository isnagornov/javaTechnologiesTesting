package concurrency.entities;

public interface RunnableWorker {

    default void doSomeWork() throws InterruptedException {
        System.out.println("-----------------------------------");
        System.out.println("Do some work...");

        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
        }

        System.out.println("Thread " + Thread.currentThread().getId() + " completed");
    }
}
