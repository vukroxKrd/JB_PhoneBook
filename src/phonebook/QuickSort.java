package phonebook;

import java.util.*;

public class QuickSort {

    public static List<Addressat> quickSortToListOfAddressats(Map<String, Addressat> map, Long time) {

        List<Addressat> addressats = new ArrayList<>(map.values());
        quickSort(addressats);
        return addressats;
    }

    public static void quickSort(List<Addressat> addressats) {
        quickSort(addressats, 0, addressats.size() - 1);
    }

    public static void quickSort(List<Addressat> collection, int begin, int end) {
        if (begin > end) {
            return;
        }
        int pivotIndex = new Random().nextInt((end - begin) + 1) + begin;
        Addressat pivot = collection.get(pivotIndex);
        swap(collection, pivotIndex, end);

        int leftPointer = partition(collection, begin, end, pivot);

        quickSort(collection, begin, leftPointer - 1);
        quickSort(collection, leftPointer + 1, end);
    }

    private static int partition(List<Addressat> collection, int begin, int end, Addressat pivot) {
        int leftPointer = begin;
        int rightPointer = end;

        while (leftPointer < rightPointer) {
            while (collection.get(leftPointer).compareTo(pivot) <= 0  && leftPointer < rightPointer) {
                leftPointer++;
            }
            while (collection.get(rightPointer).compareTo(pivot) >= 0 && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(collection, leftPointer, rightPointer);
        }
        swap(collection, leftPointer, end);
        return leftPointer;
    }

    private static void swap(List<Addressat> collection, int index1, int index2) {
        Addressat tempElement = collection.get(index1);
        collection.set(index1, collection.get(index2));
        collection.set(index2, tempElement);
    }

}
