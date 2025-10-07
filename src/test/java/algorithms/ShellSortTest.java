
package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {

    private PerformanceTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new PerformanceTracker();
    }

    private void testSort(ShellSort.GapSequenceType type) {
        int[] array = {12, 34, 54, 2, 3, 19, 1, 0, 99, -5};
        int[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);
        ShellSort.sort(array, tracker, type);
        assertArrayEquals(expected, array, "Failed with sequence: " + type);
    }

    @Test
    void testSortShellGap() {
        testSort(ShellSort.GapSequenceType.SHELL);
    }

    @Test
    void testSortKnuthGap() {
        testSort(ShellSort.GapSequenceType.KNUTH);
    }

    @Test
    void testSortSedgewickGap() {
        testSort(ShellSort.GapSequenceType.SEDGEWICK);
    }

    @Test
    void testEdgeCases() {
        // Empty
        int[] array1 = {};
        ShellSort.sort(array1, tracker, ShellSort.GapSequenceType.SEDGEWICK);
        assertArrayEquals(new int[]{}, array1);

        // Single Element
        int[] array2 = {42};
        ShellSort.sort(array2, tracker, ShellSort.GapSequenceType.SEDGEWICK);
        assertArrayEquals(new int[]{42}, array2);
    }

    @Test
    void testSortedAndReverse() {
        int[] sorted = {1, 2, 3, 4, 5};
        int[] reverse = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};

        ShellSort.sort(sorted, tracker, ShellSort.GapSequenceType.SEDGEWICK);
        assertArrayEquals(expected, sorted);

        ShellSort.sort(reverse, tracker, ShellSort.GapSequenceType.SEDGEWICK);
        assertArrayEquals(expected, reverse);
    }

    @Test
    void testArrayWithDuplicates() {
        int[] array = {5, 2, 5, 1, 2};
        int[] expected = {1, 2, 2, 5, 5};
        ShellSort.sort(array, tracker, ShellSort.GapSequenceType.SEDGEWICK);
        assertArrayEquals(expected, array);
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShellSort.sort(null, tracker, ShellSort.GapSequenceType.SEDGEWICK);
        });
    }

    @Test
    void testLargeRandomInput() {
        int size = 10000;
        int[] array = new int[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        int[] expected = Arrays.copyOf(array, size);
        Arrays.sort(expected);

        // Test all sequences on large input
        for (ShellSort.GapSequenceType type : ShellSort.GapSequenceType.values()) {
            int[] copy = Arrays.copyOf(array, size);
            ShellSort.sort(copy, tracker, type);
            assertArrayEquals(expected, copy, "Large input failed for: " + type);
        }
    }
}
