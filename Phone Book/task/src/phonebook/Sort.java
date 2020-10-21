package phonebook;

import java.util.ArrayList;

public class Sort {

    public static <T> void swap(ArrayList <T> arr, int from, int to) {
        T temp = arr.get(from);
        arr.set(from, arr.get(to));
        arr.set(to, temp);
    }

    public static void bubbleSort(ArrayList<Person> array) {
        Person a;
        Person b;
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = 0; j < array.size() - i - 1; j++) {
                a = array.get(j);
                b = array.get(j + 1);
                if (a.compareTo(b) > 0) {
                    array.set(j + 1, a);
                    array.set(j, b);
                }
            }
        }
    }

    public static void quickSort(ArrayList<Person> array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);
            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }
    }

    private static int partition(ArrayList<Person> array, int left, int right) {
        Person pivot = array.get(right);
        int partitionIndex = left;
        for (int i = left; i < right; i++) {
            if (array.get(i).compareTo(pivot) < 0) {
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }
        swap(array, partitionIndex, right); //
        return partitionIndex;
    }
}
