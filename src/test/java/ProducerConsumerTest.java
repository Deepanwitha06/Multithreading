
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ProducerConsumerTest {

    // Verifies that the producer generates all requested items
    @Test
    void testProducerGeneratesAllItems() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            ProducerConsumer application = new ProducerConsumer();
            application.start(5);

        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        for (int i = 1; i <= 5; i++) {
            assertTrue(result.contains("Produced: " + i));
        }
    }

    // Verifies that the consumer processes all produced items
    @Test
    void testConsumerConsumesAllItems() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            ProducerConsumer application = new ProducerConsumer();
            application.start(5);

        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        for (int i = 1; i <= 5; i++) {
            assertTrue(result.contains("Consumed: " + i));
        }
    }

    // Verifies that the application handles zero items
    @Test
    void testZeroItems() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            ProducerConsumer application = new ProducerConsumer();
            application.start(0);

        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertFalse(result.contains("Produced:"));
        assertFalse(result.contains("Consumed:"));
    }
}
