
package metrics;

public class PerformanceTracker {
    private long comparisons;
    private long swapsOrMoves;
    private long arrayAccesses;
    private long startTime;
    private long endTime;

    public PerformanceTracker() {
        reset();
    }

    public void reset() {
        comparisons = 0;
        swapsOrMoves = 0;
        arrayAccesses = 0;
        startTime = 0;
        endTime = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementSwapsOrMoves() {
        swapsOrMoves++;
    }

    public void incrementArrayAccesses(int count) {
        arrayAccesses += count;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwapsOrMoves() {
        return swapsOrMoves;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public double getExecutionTimeMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }

    @Override
    public String toString() {
        return String.format("Time: %.4f ms, Comparisons: %d, Swaps/Moves: %d, Accesses: %d",
                getExecutionTimeMillis(), comparisons, swapsOrMoves, arrayAccesses);
    }
}
