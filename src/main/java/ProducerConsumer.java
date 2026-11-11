
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Demonstrates communication between producer and consumer threads
public class ProducerConsumer {

    private static final int QUEUE_CAPACITY = 5;
    private static final int STOP_SIGNAL = -1;

    private final BlockingQueue<Integer> sharedQueue;

    public ProducerConsumer() {
        // Creates a thread-safe queue with limited capacity
        sharedQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    }

    // Producer generates items and places them in the shared queue
    public void produce(int itemCount) {
        try {
            for (int item = 1; item <= itemCount; item++) {
                sharedQueue.put(item);
                System.out.println("Produced: " + item);
            }

            // Sends a stop signal after producing all items
            sharedQueue.put(STOP_SIGNAL);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Consumer retrieves and processes items from the shared queue
    public void consume() {
        try {
            while (true) {
                int item = sharedQueue.take();

                // Stops consuming when the stop signal is received
                if (item == STOP_SIGNAL) {
                    break;
                }

                System.out.println("Consumed: " + item);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Starts producer and consumer threads
    public void start(int itemCount) {

        Thread producerThread = new Thread(() -> produce(itemCount));
        Thread consumerThread = new Thread(this::consume);

        producerThread.start();
        consumerThread.start();

        try {
            // Waits for both threads to complete
            producerThread.join();
            consumerThread.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Process interrupted.");
        }
    }

    public static void main(String[] args) {
        ProducerConsumer application = new ProducerConsumer();

        // Produces and consumes 10 items
        application.start(10);
    }
}
