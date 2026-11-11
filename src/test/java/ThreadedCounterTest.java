import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThreadedCounterTest {

    // Verifies that all numbers are printed in the correct order
    @Test
    void testCountingProducesNumbersInOrder() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            ThreadedCounter counter = new ThreadedCounter(10);
            counter.startCounting();

        } finally {
            System.setOut(originalOut);
        }

        String[] lines = output.toString().trim().split("\\R");
        List<Integer> numbers = new ArrayList<>();

        for (String line : lines) {
            numbers.add(Integer.parseInt(line.substring(line.lastIndexOf(":") + 1).trim()));
        }

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), numbers);
    }

    // Verifies that all three threads participate in counting
    @Test
    void testAllThreeThreadsParticipate() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            ThreadedCounter counter = new ThreadedCounter(9);
            counter.startCounting();

        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("Thread 1"));
        assertTrue(result.contains("Thread 2"));
        assertTrue(result.contains("Thread 3"));
    }

    // Verifies that the counter handles a limit of one
    @Test
    void testSingleNumber() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            ThreadedCounter counter = new ThreadedCounter(1);
            counter.startCounting();

        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("Thread 1 : 1"));
    }
}