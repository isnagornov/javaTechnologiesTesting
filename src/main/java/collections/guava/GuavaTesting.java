package collections.guava;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class GuavaTesting {

    public static void main(String[] args) {
        String found;

        Iterable<String> theCollection = Lists.newArrayList("a", "bc", "def");
        boolean contains = Iterables.any(theCollection, input -> input.length() == 1);
        assertTrue(contains);

        theCollection = Sets.newHashSet("a", "bc", "def");
        found = Iterables.find(theCollection, input -> input.length() == 1);
        System.out.println(found);
        contains = found != null;
        assertTrue(contains);

        List<String> values = Lists.newArrayList("a", null, "b", "c", null, null);
        Iterable<String> withoutNulls = Iterables.filter(values, Predicates.notNull());
        System.out.println(withoutNulls);

        ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");
        System.out.println(immutableList);
        ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
        System.out.println(immutableSet);
        ImmutableMap<String, String> imuttableMap = ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3");
        System.out.println(imuttableMap);

        List<String> muttableList = Lists.newArrayList("1", "2", "3");
        immutableList = ImmutableList.<String>builder().addAll(muttableList).build();
        System.out.println(immutableList);

        Set<String> muttableSet = Sets.newHashSet("A", "B", "C");
        immutableSet = ImmutableSet.<String>builder().addAll(muttableSet).build();
        System.out.println(immutableSet);

        Map<String, String> muttableMap = Maps.newHashMap(imuttableMap);
        imuttableMap = ImmutableMap.<String, String>builder().putAll(muttableMap).build();
        System.out.println(imuttableMap);

        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        String result = Joiner.on(",").join(names);
        System.out.println(result);

        assertEquals(result, "John,Jane,Adam,Tom");

        Map<String, Integer> salary = Maps.newHashMap();
        salary.put("John", 1000);
        salary.put("Jane", 1500);
        result = Joiner.on(" , ").withKeyValueSeparator(" = ")
                .join(salary);
        System.out.println(result);

        assertThat(result, containsString("John = 1000"));
        assertThat(result, containsString("Jane = 1500"));

        List<ArrayList<String>> nested = Lists.newArrayList(
                Lists.newArrayList("apple", "banana", "orange"),
                Lists.newArrayList("cat", "dog", "bird"),
                Lists.newArrayList("John", "Jane", "Adam"));
        result = Joiner.on(";").join(Iterables.transform(nested,
                (Function<List<String>, String>) input -> Joiner.on("-").join(input)));

        System.out.println(result);

        assertThat(result, containsString("apple-banana-orange"));
        assertThat(result, containsString("cat-dog-bird"));
        assertThat(result, containsString("apple-banana-orange"));

        names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        result = Joiner.on(",").skipNulls().join(names);

        System.out.println(result);
        assertEquals(result, "John,Jane,Adam,Tom");

        names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        result = Joiner.on(",").useForNull("nameless").join(names);
        System.out.println(result);

        assertEquals(result, "John,nameless,Jane,Adam,Tom");

        String input = "apple - banana - orange";
        List<String> resultList = Splitter.on("-").trimResults()
                .splitToList(input);
        System.out.println(resultList);

        assertThat(resultList, hasItems("apple", "banana", "orange"));

        input = "John=first,Adam=second";
        Map<String, String> resultMap = Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(input);
        System.out.println(resultMap);

        assertEquals("first", resultMap.get("John"));
        assertEquals("second", resultMap.get("Adam"));

        input = "apple.banana,,orange,,.";
        resultList = Splitter.onPattern("[.,]")
                .omitEmptyStrings()
                .splitToList(input);
        System.out.println(resultList);

        assertThat(resultList, hasItems("apple", "banana", "orange"));

    }
}
