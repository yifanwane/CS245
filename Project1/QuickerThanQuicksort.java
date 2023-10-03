import java.util.Random;

public class QuickerThanQuicksort {

    public static void main(String[] args) {
        int[] arrSizes = {50000, 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000};

        for (int size : arrSizes) {
            double[] arr = generateRandomArray(size);

            long hybridSortTime = hybridSort(arr.clone());
            System.out.println("Array size: " + size);
            System.out.println("Hybrid Sort Time: " + hybridSortTime + " milliseconds");
            System.out.println();
        }
    }

    public static long hybridSort(double[] arr) {
        long startTime = System.currentTimeMillis();

        hybridSortRecursive(arr, 0, arr.length - 1);

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void hybridSortRecursive(double[] arr, int left, int right) {
        // Implement hybrid sorting algorithm here
        int threshold = 10; // Example threshold value, adjust as needed
        if (right - left + 1 <= threshold) {
            quadraticsort(arr, left, right);
        } else {
            int pivotIndex = quicksort(arr, left, right);
            hybridSortRecursive(arr, left, pivotIndex - 1);
            hybridSortRecursive(arr, pivotIndex + 1, right);
        }
    }

    public static int quicksort(double[] arr, int left, int right) {
        // Implement quicksort here and return the pivot index
        if (left < right) {
            double pivot = arr[right];
            int i = left - 1;
            for (int j = left; j < right; j++) {
                if (arr[j] <= pivot) {
                    i++;
                    double temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            double temp = arr[i + 1];
            arr[i + 1] = arr[right];
            arr[right] = temp;
            return i + 1;
        }
        return left;
    }

    public static void quadraticsort(double[] arr, int left, int right) {
        // Implement quadratic sorting algorithm here, e.g., insertion sort
        int n = right - left + 1;
        for (int i = 1; i < n; i++) {
            double key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static double[] generateRandomArray(int size) {
        Random random = new Random();
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextDouble();
        }
        return arr;
    }
}
