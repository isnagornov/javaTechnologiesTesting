package collections.apache_commons;

import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderedMapTesting {

    public static void main(String[] args) {
        final String[] names = {"Emily", "Mathew", "Rose", "John", "Anna"};
        final Integer[] ages = {37, 28, 40, 36, 21};
        final int RUNNERS_COUNT = names.length;

        LinkedMap<String, Integer> runnersLinkedMap = new LinkedMap<>();

        for (int i = 0; i < names.length; i++) {
            runnersLinkedMap.put(names[i], ages[i]);
        }

        String name = runnersLinkedMap.firstKey();
        int i = 0;
        while (name != null) {
            assertEquals(name, names[i]);
            name = runnersLinkedMap.nextKey(name);
            i++;
        }

        name = runnersLinkedMap.lastKey();
        i = RUNNERS_COUNT - 1;
        while (name != null) {
            assertEquals(name, names[i]);
            name = runnersLinkedMap.previousKey(name);
            i--;
        }

        OrderedMapIterator<String, Integer> runnersIterator = runnersLinkedMap.mapIterator();

        i = 0;
        while (runnersIterator.hasNext()) {
            runnersIterator.next();

            assertEquals(runnersIterator.getKey(), names[i]);
            assertEquals(runnersIterator.getValue(), ages[i]);
            i++;
        }

        LinkedMap<String, Integer> lmap = new LinkedMap<>(runnersLinkedMap);

        Integer johnAge = lmap.remove("John");

        assertEquals(johnAge, Integer.valueOf(36));
        assertEquals(lmap.size(), RUNNERS_COUNT - 1);

        Integer emilyAge = lmap.remove(0);

        assertEquals(emilyAge, Integer.valueOf(37));
        assertEquals(lmap.size(), RUNNERS_COUNT - 2);

        lmap = new LinkedMap<>(runnersLinkedMap);

        List<String> listKeys = new ArrayList<>(runnersLinkedMap.keySet());
        List<String> linkedMap = lmap.asList();

        assertEquals(listKeys, linkedMap);

        for (i = 0; i < RUNNERS_COUNT; i++) {
            name = lmap.get(i);

            assertEquals(name, names[i]);
            assertEquals(lmap.indexOf(names[i]), i);
        }
    }
}
