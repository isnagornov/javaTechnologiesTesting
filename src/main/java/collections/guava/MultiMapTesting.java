package collections.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import static org.junit.Assert.assertEquals;

public class MultiMapTesting {

    public static void main(String[] args) {
        String key = "a-key";
        Multimap<String, String> map = ArrayListMultimap.create();

        System.out.println(map.get("absentKey"));

        map.put(key, "firstValue");
        map.put(key, "secondValue");

        assertEquals(2, map.size());

        System.out.println(map.get(key));
    }
}
