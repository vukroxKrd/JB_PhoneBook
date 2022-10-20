package phonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BubbleSort {

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
}
