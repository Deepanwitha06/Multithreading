import java.util.Scanner;

// Handles counting from 1 to a specified limit using multiple threads
public class ThreadedCounter {

    private final int limit;
    private int currentCount = 1;

    public ThreadedCounter(int limit) {
        this.limit = limit;
    }

    // Synchronized method ensures threads print numbers in correct order
    public synchronized void countNumbers(int threadId) {
        while (currentCount <= limit) {
            // Each thread waits for its turn based on the number sequence
            while (currentCount <= limit && (currentCount - 1) % 3 != threadId) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            if (currentCount <= limit) {
                System.out.println("Thread " + (threadId + 1) + " : " + currentCount);
                currentCount++;

                // Notify other waiting threads
                notifyAll();
            }
        }

        // Wake up other threads when counting is complete
        notifyAll();
    }

    // Starts three threads to count numbers in synchronized order
    public void startCounting() {

        Thread thread1 = new Thread(() -> countNumbers(0));
        Thread thread2 = new Thread(() -> countNumbers(1));
        Thread thread3 = new Thread(() -> countNumbers(2));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            // Wait for all threads to finish
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Counting interrupted.");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the counting limit: ");
            int limit = scanner.nextInt();

            if (limit <= 0) {
                System.out.println("Please enter a positive integer.");
                return;
            }

            ThreadedCounter counter = new ThreadedCounter(limit);
            counter.startCounting();

        } catch (Exception e) {
            System.out.println("Invalid input! Please enter an integer.");
        } finally {
            scanner.close();
        }
    }
}
