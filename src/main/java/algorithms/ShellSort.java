
package algorithms;

import metrics.PerformanceTracker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShellSort {

    public enum GapSequenceType {
        SHELL,
        KNUTH,
        SEDGEWICK
    }

    // Optimization: Pre-computed Sedgewick's sequence (1986 variant) for optimized performance.
    // Avoids Math.pow and dynamic allocation during runtime for this sequence.
    // 1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609...
    private static final int[] SEDGEWICK_GAPS = {
        1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609, 587521, 1045505
    };

    public static void sort(int[] array, PerformanceTracker tracker, GapSequenceType sequenceType) {
        if (array == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        tracker.reset();
        tracker.startTimer();

        int n = array.length;
        if (n <= 1) {
            tracker.stopTimer();
            return;
        }

        // Handle sequences optimized for O(1) space where possible
        if (sequenceType == GapSequenceType.SHELL) {
            // Shell's sequence: N/2, N/4, ... 1
            for (int gap = n / 2; gap > 0; gap /= 2) {
                gappedInsertionSort(array, n, gap, tracker);
            }
        } else if (sequenceType == GapSequenceType.KNUTH) {
            // Knuth's sequence: Start at largest h < N/3, where h = 3*h_prev + 1. Then h = (h-1)/3.
            int gap = 1;
            while (gap < n / 3) {
                gap = 3 * gap + 1;
            }
            while (gap >= 1) {
                 gappedInsertionSort(array, n, gap, tracker);
                 gap = (gap - 1) / 3;
            }
        }
        else if (sequenceType == GapSequenceType.SEDGEWICK) {
            // Use the pre-computed sequence. Iterate backwards.
            
            // Find the starting index (the largest gap smaller than N)
            int startIndex = 0;
            while (startIndex < SEDGEWICK_GAPS.length && SEDGEWICK_GAPS[startIndex] < n) {
                startIndex++;
            }

            // Iterate backwards from the largest applicable gap down to 1
            for (int i = startIndex - 1; i >= 0; i--) {
                gappedInsertionSort(array, n, SEDGEWICK_GAPS[i], tracker);
            }
        }

        tracker.stopTimer();
    }

    private static void gappedInsertionSort(int[] array, int n, int gap, PerformanceTracker tracker) {
        for (int i = gap; i < n; i++) {
            tracker.incrementArrayAccesses(1); // Read array[i] for temp
            int temp = array[i];
            int j;
            for (j = i; j >= gap; j -= gap) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses(1); // Read array[j-gap] for comparison
                if (array[j - gap] > temp) {
                    tracker.incrementArrayAccesses(2); // Read array[j-gap], Write array[j]
                    tracker.incrementSwapsOrMoves(); // Counting the shift as a move
                    array[j] = array[j - gap];
                } else {
                    break;
                }
            }
            // Place temp in its final position (only if it moved)
            if (j != i) {
               tracker.incrementArrayAccesses(1); // Write temp to array[j]
               array[j] = temp;
            }
        }
    }
}
