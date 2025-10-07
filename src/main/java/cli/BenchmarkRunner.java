
package cli;

import algorithms.HeapSort;
import algorithms.ShellSort;
import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public class BenchmarkRunner {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java cli.BenchmarkRunner <Algorithm> <InputSize> [Options]");
            System.out.println("Algorithms: HeapSort, ShellSort");
            System.out.println("Options for ShellSort: [GapSequence] (SHELL, KNUTH, SEDGEWICK)");
            System.out.println("Options for all: [InputType] (RANDOM, SORTED, REVERSE)");
            System.out.println("Example 1: HeapSort 10000 RANDOM");
            System.out.println("Example 2: ShellSort 5000 KNUTH REVERSE");
            return;
        }

        String algorithm = args[0];
        int size = 0;
        try {
            size = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: InputSize must be an integer.");
            return;
        }

        // Parsing Options (Simple positional parsing based on Algorithm type)
        String gapSequenceArg = "SEDGEWICK"; // Default for ShellSort
        String inputType = "RANDOM"; // Default for all

        if (algorithm.equalsIgnoreCase("ShellSort")) {
            if (args.length > 2) {
                // Check if arg[2] is an InputType or a GapSequence
                if (isInputType(args[2])) {
                    inputType = args[2];
                } else {
                    gapSequenceArg = args[2];
                    if (args.length > 3 && isInputType(args[3])) {
                        inputType = args[3];
                    }
                }
            }
        } else {
             if (args.length > 2 && isInputType(args[2])) {
                inputType = args[2];
            }
        }


        int[] array = generateArray(size, inputType.toUpperCase());
        PerformanceTracker tracker = new PerformanceTracker();
        BiConsumer<int[], PerformanceTracker> sortFunction = null;

        System.out.println("--- Starting Benchmark ---");
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Input Size: " + size);
        System.out.println("Input Type: " + inputType.toUpperCase());

        switch (algorithm.toLowerCase()) {
            case "heapsort":
                // Ensure HeapSort class exists (for Student B project)
                try {
                    Class.forName("algorithms.HeapSort");
                    sortFunction = HeapSort::sort;
                } catch (ClassNotFoundException e) {
                    System.err.println("HeapSort implementation not found in this project.");
                    return;
                }
                break;
            case "shellsort":
                 // Ensure ShellSort class exists (for Student A project)
                 try {
                    Class.forName("algorithms.ShellSort");
                    ShellSort.GapSequenceType gapSequence;
                    try {
                        gapSequence = ShellSort.GapSequenceType.valueOf(gapSequenceArg.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error: Invalid GapSequence ("+ gapSequenceArg +"). Using SEDGEWICK.");
                        gapSequence = ShellSort.GapSequenceType.SEDGEWICK;
                    }
                    System.out.println("Gap Sequence: " + gapSequence);
                    sortFunction = (arr, t) -> ShellSort.sort(arr, t, gapSequence);
                } catch (ClassNotFoundException e) {
                    System.err.println("ShellSort implementation not found in this project.");
                    return;
                }
                break;
            default:
                System.err.println("Error: Unknown algorithm.");
                return;
        }

        if (sortFunction != null) {
            try {
                sortFunction.accept(array, tracker);
                System.out.println("--- Benchmark Complete ---");
                System.out.println("Results: " + tracker);
                if (!isSorted(array)) {
                    System.err.println("ERROR: Array is not sorted correctly!");
                }
            } catch (Exception e) {
                System.err.println("An error occurred during sorting: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static boolean isInputType(String arg) {
        String upper = arg.toUpperCase();
        return upper.equals("RANDOM") || upper.equals("SORTED") || upper.equals("REVERSE");
    }

    private static int[] generateArray(int size, String type) {
        if (size < 0) return new int[0];
        Random random = new Random();
         switch (type) {
            case "SORTED":
                return IntStream.range(0, size).toArray();
            case "REVERSE":
                return IntStream.range(0, size).map(i -> size - i - 1).toArray();
            case "RANDOM":
            default:
                 // Use a fixed seed for reproducibility if desired, but random for benchmark is fine.
                 return IntStream.generate(() -> random.nextInt(size * 10)).limit(size).toArray();
        }
    }

    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i+1]) {
                return false;
            }
        }
        return true;
    }
}
