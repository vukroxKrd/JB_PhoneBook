package phonebook;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstantSearch {

    public static Map<String, Addressat> dictionaryToNamePhoneMap(List<String> dictionary) {
        String REGEX = "(\\d+)[^s](\\D+)";
        Pattern pattern = Pattern.compile(REGEX);

        Map<String, Addressat> result = new HashMap<>();

        for (String next : dictionary) {
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
                result.put(addressat.getName(), addressat);
            }
        }
        return result;
    }

    public static List<Addressat> instantHashSearch(List<String> search, Map<String,Addressat> dictionary) {
        List<Addressat> result = new ArrayList<>();
        for (String next : search) {
            Addressat addressat = dictionary.get(next);
            if (addressat != null){
                result.add(addressat);
            }
        }
        return result;
    }
}
