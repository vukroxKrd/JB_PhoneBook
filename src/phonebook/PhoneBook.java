package phonebook;

import javax.sound.sampled.Line;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//^([0-9]+)\s([a-zA-Z ]*$)\n

public class PhoneBook {

    private static Map<String, List<Long>> timestamps = new HashMap<>();
    private static final String LINEAR_SEARCH = "linear search";
    private static final String BUBBLE_SORT = "bubble sort";
    private static final String JUMP_SEARCH = "jump search";
    private static final String DICTIONARY_FILE_NAME = "directory.txt";
    private static final String LIMITED_DICTIONARY_FILE_NAME = "small_directory.txt";
    private static final String LIMITED_SEARCH_FILE_NAME = "small_find.txt";
    private static final String FULL_SEARCH_FILE_NAME = "find.txt";
    private static final String SORTED_DICTIONARY_FILE_NAME = "sorted_collection.txt";

    public static void main(String[] args) {
        try {
            System.out.println("Start searching (linear search)...");

            long beginning = System.currentTimeMillis();
            List<Addressat> sortedAddressats = new ArrayList<>();

            List<String> entries = readContentFromFileIntoList(DICTIONARY_FILE_NAME);
            List<String> search = readContentFromFileIntoList(FULL_SEARCH_FILE_NAME);

            //This will check whether 'sorted_collection.txt' is not empty.
            // If it is not, it will take data from there to avoid sorting operation later.
            String fileName = fileToReadFromProivider(LIMITED_DICTIONARY_FILE_NAME, SORTED_DICTIONARY_FILE_NAME);

            Map<String, Addressat> foundEntries = addressatsFoundByLinearSearch(entries, search);

            long linearEnd = System.currentTimeMillis();

            long minutes = (linearEnd - beginning) / 60000;
            long seconds = (linearEnd - beginning) / 1000;
            long ms = (linearEnd - beginning) % 1000;

            System.out.printf("Found %s/%s entries. Time taken: %d min. %d sec. %d ms. \n", search.size(),
                    foundEntries.size(),
                    minutes,
                    seconds,
                    ms);
            System.out.println("");
            System.out.printf("Start searching (%s + %s)...\n", BUBBLE_SORT, JUMP_SEARCH);

            long sortbeginning = System.currentTimeMillis();

            if (fileName.equals(SORTED_DICTIONARY_FILE_NAME)) {
                sortedAddressats = readSortedAddressatsFromFile(SORTED_DICTIONARY_FILE_NAME);
            } else {
                try {
                    sortedAddressats = bubbleSortToListOfAddressats(entries, sortbeginning);
                    saveSortedCollectionToFile(sortedAddressats);
                    long sortEnding = System.currentTimeMillis();

                    long minutesSort = (sortEnding - sortbeginning) / 60000;
                    long secondsSort = (sortEnding - sortbeginning) / 1000;
                    long msSort = (sortEnding - sortbeginning) % 1000;
                    timestamps.put(BUBBLE_SORT, List.of(minutesSort, secondsSort, msSort));
                } catch (RuntimeException e) {

                    long endOfBubbleSort = System.currentTimeMillis();

                    long minutesBs = (endOfBubbleSort - beginning) / 60000;
                    long secondsBs = (endOfBubbleSort - beginning) / 1000;
                    long msBs = (endOfBubbleSort - beginning) % 1000;
                    timestamps.put(BUBBLE_SORT, List.of(minutesBs, secondsBs, msBs));

                    long startLinear = System.currentTimeMillis();
                    Map<String, Addressat> foundByLinearSearch = addressatsFoundByLinearSearch(entries, search);
                    long endLinear = System.currentTimeMillis();

                    long minutesLinear = (endLinear - startLinear) / 60000;
                    long secondsLinear = (endLinear - startLinear) / 1000;
                    long msLinear = (endLinear - startLinear) % 1000;
                    timestamps.put(LINEAR_SEARCH, List.of(minutesLinear, secondsLinear, msLinear));

                    long minutesBubblePlusLinear = (timestamps.get(BUBBLE_SORT).get(0) + timestamps.get(LINEAR_SEARCH).get(0));
                    long secondsBubblePlusLinear = (timestamps.get(BUBBLE_SORT).get(1) + timestamps.get(LINEAR_SEARCH).get(1));
                    long msBubblePlusLinear = (timestamps.get(BUBBLE_SORT).get(2) + timestamps.get(LINEAR_SEARCH).get(2));

                    System.out.printf("Found %s/%s entries. Time taken: %d min. %d sec. %d ms. \n", search.size(),
                            foundByLinearSearch.size(),
                            minutesBubblePlusLinear,
                            secondsBubblePlusLinear,
                            msBubblePlusLinear);

                    System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search \n",
                            timestamps.get(BUBBLE_SORT).get(0),
                            timestamps.get(BUBBLE_SORT).get(1),
                            timestamps.get(BUBBLE_SORT).get(2));
                    System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                            timestamps.get(LINEAR_SEARCH).get(0),
                            timestamps.get(LINEAR_SEARCH).get(1),
                            timestamps.get(LINEAR_SEARCH).get(2));
                    System.exit(1);
                }
            }

            long jumpSearchBeginning = System.currentTimeMillis();
            List<Addressat> jumpSearchedAddressats = jumpSearchingNeededAddressats(sortedAddressats, foundEntries.values());
            long jumpSearchEnding = System.currentTimeMillis();

            long minutesJumpSearch = (jumpSearchEnding - jumpSearchBeginning) / 60000;
            long secondsJumpSearch = (jumpSearchEnding - jumpSearchBeginning) / 1000;
            long msJumpSearch = (jumpSearchEnding - jumpSearchBeginning) % 1000;
            timestamps.put(JUMP_SEARCH, List.of(minutesJumpSearch, secondsJumpSearch, msJumpSearch));

            System.out.printf("Found %s/%s entries. Time taken: %d min. %d sec. %d ms. \n", search.size(),
                    jumpSearchedAddressats.size(),
                    minutesJumpSearch + timestamps.get(BUBBLE_SORT).get(0),
                    secondsJumpSearch + timestamps.get(BUBBLE_SORT).get(1),
                    msJumpSearch + timestamps.get(BUBBLE_SORT).get(2));

            System.out.printf("Sorting time: %d min. %d sec. %d ms.\n",
                    timestamps.get(BUBBLE_SORT).get(0),
                    timestamps.get(BUBBLE_SORT).get(1),
                    timestamps.get(BUBBLE_SORT).get(2));
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n",
                    minutesJumpSearch,
                    secondsJumpSearch,
                    msJumpSearch);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Addressat> jumpSearchingNeededAddressats(List<Addressat> sortedAddressats, Collection<Addressat> addressatsToFind) {
        List<Addressat> result = new ArrayList<>();

        for (Addressat nextSearch : addressatsToFind) {
            result.add(jumpSearch(sortedAddressats, nextSearch));
        }
        return result;
    }

    private static Addressat jumpSearch(List<Addressat> sortedCollection, Addressat addressat) {
        if (sortedCollection.size() == 0) {
            return null;
        }

        int current = 0;
        int previous = 0;
        int last = sortedCollection.size();

        int step = (int) Math.floor(Math.sqrt(last));

        while (sortedCollection.get(current).compareTo(addressat) < 0) {
            if (sortedCollection.get(current).compareTo(sortedCollection.get(last - 1)) == 0) {
                return null;
            }
            previous = current;
            current = Math.min(current + step, last);

        }

        while (sortedCollection.get(current).compareTo(addressat) >= 0) {
            if (sortedCollection.get(current).compareTo(addressat) == 0) {
                return sortedCollection.get(current);
            }

            current = current - 1;
            if (sortedCollection.get(current).compareTo(sortedCollection.get(previous)) < 0) {
                return null;
            }
        }
        return null;
    }

    private static String fileToReadFromProivider(String dictionaryFileName, String sortedDictionaryFileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(sortedDictionaryFileName));
        if (br.readLine() != null) {
            return sortedDictionaryFileName;
        } else {
            return dictionaryFileName;
        }
    }

    public static List<Addressat> bubbleSortToListOfAddressats(List<String> entries, Long time) {

        Map<String, Addressat> map = listToMapOfAddressats(entries);
        List<Addressat> addressats = new ArrayList<>(map.values());
        int size = map.values().size();

        for (int i = 0; i < (size - 1); i++) {
            boolean swapped = false;
            for (int j = 0; j < (size - i - 1); j++) {
                if (addressats.get(j).compareTo(addressats.get(j + 1)) > 0) {
                    Addressat temp = addressats.get(j);
                    addressats.set(j, addressats.get(j + 1));
                    addressats.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
            long timePassed = System.currentTimeMillis();
//            if (timePassed - time > 1_212_251) {
            if (timePassed - time > 12_122) {
                throw new RuntimeException();
            }
        }
        return addressats;
    }

    public static List<Addressat> rawEntriesToAddressats(List<String> entries) {

        Map<String, Addressat> map = listToMapOfAddressats(entries);
        List<Addressat> addressats = new ArrayList<>(map.values());
        int size = map.values().size();

        for (int i = 0; i < (size - 1); i++) {
            boolean swapped = false;
            for (int j = 0; j < (size - i - 1); j++) {
                if (addressats.get(j).compareTo(addressats.get(j + 1)) > 0) {
                    Addressat temp = addressats.get(j);
                    addressats.set(j, addressats.get(j + 1));
                    addressats.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
        return addressats;
    }

    public static void saveSortedCollectionToFile(List<Addressat> sortedCollection) {
        File file = new File(SORTED_DICTIONARY_FILE_NAME);

        ObjectOutputStream ooStream = null;
        try {
            ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(sortedCollection);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (ooStream != null) {
                try {
                    ooStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Addressat> readSortedAddressatsFromFile(String fileName) {
        File file = new File(fileName);
        List<Addressat> result = new ArrayList<>();

        ObjectInputStream oiStream = null;
        try {
            oiStream = new ObjectInputStream(new FileInputStream(file));
            result = (List<Addressat>) oiStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oiStream != null) {
                try {
                    oiStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Map<String, Addressat> listToMapOfAddressats(List<String> registry) {
        String REGEX = "(\\d+)[^s](\\D+)";
        Pattern pattern = Pattern.compile(REGEX);

        Map<String, Addressat> result = new HashMap<>();

        for (String next : registry) {
            String[] arr = new String[3];
            Matcher matcher = pattern.matcher(next);
            while (matcher.find()) {
                for (int j = 0; j <= matcher.groupCount(); j++) {
                    String group = matcher.group(j);
                    arr[j] = group;
                }
                Addressat addressat = new Addressat();
                addressat.setNumber(arr[1]);
                addressat.setName(arr[2]);
                result.put(addressat.getNumber(), addressat);
            }
        }
        return result;
    }

    public static Map<String, Addressat> addressatsFoundByLinearSearch(List<String> entries, List<String> people) {
        String REGEX = "(\\d+)[^s](\\D+)";
        Pattern pattern = Pattern.compile(REGEX);

        List<String> filteredPeople = new ArrayList<>();

        Map<String, Addressat> result = new HashMap<>();
        for (String nextPerson : people) {
            for (String nextEntry : entries) {
                if (nextEntry.contains(nextPerson)) {
                    filteredPeople.add(nextEntry);
                    break;
                }
            }
        }

        int counter = 0;
        for (String next : filteredPeople) {
            String[] arr = new String[3];
            Matcher matcher = pattern.matcher(next);
            while (matcher.find()) {
                for (int j = 0; j <= matcher.groupCount(); j++) {
                    String group = matcher.group(j);
                    arr[j] = group;
                }
                Addressat addressat = new Addressat();
                addressat.setNumber(arr[1]);
                addressat.setName(arr[2]);
                result.put(String.valueOf(counter), addressat);
                counter++;
            }
        }

        return result;
    }

    public static <T> Set<T> findDuplicateBySetAdd(List<T> list) {
        Set<T> items = new HashSet<>();
        return list.stream()
                .filter(n -> !items.add(n))
                .peek(System.out::println)// Set.add() returns false if the element was already in the set.
                .collect(Collectors.toSet());
    }

    public static String readContentFromFileIntoString(String stringPathRepr) throws IOException {
        String returnVal = "";
        Path path = Paths.get(stringPathRepr);
        System.out.println(path.toAbsolutePath());

        try (Stream<String> allLines = Files.lines(path)) {
            returnVal = allLines.collect(Collectors.joining("\n"));
        }
        return returnVal;

    }

    public static List<String> readAndFilterNumbers(String stringPathRepr, List<String> people) throws
            IOException {

        List<String> result = new ArrayList<>();
        Path path = Paths.get(stringPathRepr);

        try (Stream<String> allLines = Files.lines(path)) {
            result = allLines.filter(s -> people
                            .stream()
                            .anyMatch(s::contains))
                    .map(s -> s.replaceAll("\\D", ""))
                    .collect(Collectors.toList());
        }
        return result;

    }

    public static List<String> readContentFromFileIntoList(String stringPathRepr) throws IOException {
        List<String> returnVal = new ArrayList<>();
        Path path = Paths.get(stringPathRepr);
        System.out.println(path.toAbsolutePath());

        try (Stream<String> allLines = Files.lines(path)) {
            returnVal = allLines
                    .collect(Collectors.toList());
        }
        return returnVal;
    }
}
