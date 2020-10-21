package phonebook;

import java.util.ArrayList;

public class Search {

    public static int linearSearch(ArrayList<Person> array, String elem) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getName().toUpperCase().trim().compareTo(elem.toUpperCase().trim()) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static int binarySearch(ArrayList<Person> array, String elem) {
        int left = 0;
        int right = array.size();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array.get(mid).getName().toUpperCase().trim().compareTo(elem.toUpperCase().trim()) == 0) {
                return mid;
            } else if (array.get(mid).getName().toUpperCase().trim().compareTo(elem.toUpperCase().trim()) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
