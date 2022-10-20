package phonebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BinarySearch {

    public static List<Addressat> binarySearchingNeededAddressats(List<Addressat> sortedAddressats, Collection<Addressat> addressatsToLookFor) {
        List<Addressat> result = new ArrayList<>();
        for (Addressat next : addressatsToLookFor) {
            result.add(binarySearch(sortedAddressats, next));
        }
        return result;
    }

    public static Addressat binarySearch(List<Addressat> collection, Addressat searchTerm) {
        int leftIndex = 0;
        int rightIndex = collection.size() - 1;
        int middle = 0;

        while (leftIndex <= rightIndex) {

            middle = ((leftIndex + rightIndex) / 2);

            if (collection.get(middle).compareTo(searchTerm) == 0) {
                return collection.get(middle);
            } else if (collection.get(middle).compareTo(searchTerm) > 0) {
                rightIndex = middle - 1;
            } else {
                leftIndex = middle + 1;
            }
        }
        return null;
    }

    public static List<List<Integer>> readArrayFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<List<Integer>> input = new ArrayList<>();
        int i = 0;
        String st = "";
        while ((st = br.readLine()) != null) {
            if (!st.equals("")) {
                List<Integer> list = Arrays.stream(st.trim().split("\\r|\\n|\\s"))
                        .map(Integer::parseInt)
                        .toList();
                input.add(list);
            }
        }
        return input;
    }

    public static int searchMethod(List<Integer> whatToSearch, int searchedElement) {
        int leftIndex = 0;
        int rightIndex = whatToSearch.size() - 1;
        int middle = 0;

        int leftDiff = 0;
        int rightDiff = 0;

        while (leftIndex <= rightIndex) {
            middle = ((leftIndex + rightIndex) / 2);

            if (whatToSearch.get(middle) == searchedElement) {
                return whatToSearch.get(middle);
            } else if (whatToSearch.get(middle) > searchedElement) {
                rightIndex = middle - 1;
            } else {
                leftIndex = middle + 1;
            }
        }
        if (leftIndex >= 0 && leftIndex < whatToSearch.size()) {
            leftDiff = Math.abs(whatToSearch.get(leftIndex) - searchedElement);
        } else {
            leftIndex = rightIndex;
        }

        if (rightIndex >= 0 && rightIndex < whatToSearch.size()) {
            rightDiff = Math.abs(whatToSearch.get(rightIndex) - searchedElement);
        } else {
            rightIndex = leftIndex;
        }

        if (leftDiff < rightDiff) {
            return whatToSearch.get(leftIndex);
        } else if (leftDiff > rightDiff) {
            return whatToSearch.get(rightIndex);
        } else {
            return whatToSearch.get(Math.min(leftIndex, rightIndex));
        }
    }


    public static int binarySearch(List<Integer> whatToSearch, int searchedElement) {
        int leftIndex = 0;
        int rightIndex = whatToSearch.size() - 1;
        int middle = 0;

        while (leftIndex <= rightIndex) {

            middle = ((leftIndex + rightIndex) / 2);

            if (whatToSearch.get(middle) == searchedElement) {
                return whatToSearch.get(middle);
            } else if (whatToSearch.get(middle) > searchedElement) {
                rightIndex = middle - 1;
            } else {
                leftIndex = middle + 1;
            }
        }
        return -1;
    }

    public static void mainCopy(String[] args) {
        try {
            List<List<Integer>> arr = readArrayFromFile("C:\\Projects\\JB\\Phone Book\\Phone Book\\task\\src\\hyperskill-dataset-70220264.txt");
            System.out.println(arr);
            List<Integer> elementsToLookFor = arr.get(1);
            for (Integer next : elementsToLookFor
            ) {
                System.out.print(searchMethod(arr.get(0), next) + " ");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
