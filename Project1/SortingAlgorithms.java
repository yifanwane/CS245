import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {

    public static void main(String[] args) {
        int[] arrSizes = {50000, 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000};

        for (int size : arrSizes) {
            float[] arr = generateRandomArray(size);

            long selectionSortTime = sort("selection", arr.clone());
            long bubbleSortTime = sort("bubble", arr.clone());
            long insertionSortTime = sort("insertion", arr.clone());
            long mergeSortTime = sort("merge", arr.clone());
            long quickSortTime = sort("quick", arr.clone());

            System.out.println("Array size: " + size);
            System.out.println("Selection Sort Time: " + selectionSortTime + " milliseconds");
            System.out.println("Bubble Sort Time: " + bubbleSortTime + " milliseconds");
            System.out.println("Insertion Sort Time: " + insertionSortTime + " milliseconds");
            System.out.println("Merge Sort Time: " + mergeSortTime + " milliseconds");
            System.out.println("Quick Sort Time: " + quickSortTime + " milliseconds");
            System.out.println();
        }
    }

    public static long sort(String algorithm, float[] arr) {
        long startTime = System.currentTimeMillis();

        switch (algorithm) {
            case "selection":
                selectionSort(arr);
                break;
            case "bubble":
                bubbleSort(arr);
                break;
            case "insertion":
                insertionSort(arr);
                break;
            case "merge":
                mergeSort(arr);
                break;
            case "quick":
                quickSort(arr, 0, arr.length - 1);
                break;
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void selectionSort(float[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            float temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void bubbleSort(float[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    float temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void insertionSort(float[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            float key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void mergeSort(float[] arr) {
        int n = arr.length;
        if (n > 1) {
            int mid = n / 2;
            float[] left = Arrays.copyOfRange(arr, 0, mid);
            float[] right = Arrays.copyOfRange(arr, mid, n);

            mergeSort(left);
            mergeSort(right);

            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                }
                k++;
            }

            while (i < left.length) {
                arr[k] = left[i];
                i++;
                k++;
            }

            while (j < right.length) {
                arr[k] = right[j];
                j++;
                k++;
            }
        }
    }

    public static void quickSort(float[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSort(arr, left, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, right);
        }
    }

    public static int partition(float[] arr, int left, int right) {
        float pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                float temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        float temp = arr[i + 1];
        arr[i + 1] = arr[right];
        arr[right] = temp;
        return i + 1;
    }

    public static float[] generateRandomArray(int size) {
        Random random = new Random();
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextFloat();
        }
        return arr;
    }
}
