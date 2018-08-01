package collections.apache_commons;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.set.TransformedSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SetUtilsTesting {

    public static void main(String[] args) {

        Set<String> sourceSet = Set.of("List", "LinkedList", "ArrayList", "Queue", "Map", "Set");
        Set<String> sourceSet2 = Set.of("List", "LinkedList");

        try {
            SetUtils.predicatedSet(sourceSet, s -> s.startsWith("L"));
        } catch (IllegalArgumentException e) {
            System.out.println("It's normal behavior");
        }

        Set<String> validatingSet = SetUtils.predicatedSet(sourceSet2, s -> s.startsWith("L"));

        assertThat(validatingSet.size(), equalTo(2));
        assertThat(validatingSet, hasItems("List", "LinkedList"));

        Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 5));
        Set<Integer> b = new HashSet<>(Arrays.asList(1, 2));
        SetUtils.SetView<Integer> result = SetUtils.difference(a, b);

        assertTrue(result.size() == 1 && result.contains(5));

        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 5));
        SetUtils.SetView<Integer> union = SetUtils.union(a, b);

        assertTrue(SetUtils.isEqualSet(expected, union));

        expected = new HashSet<>(Arrays.asList(1, 2));
        SetUtils.SetView<Integer> intersect = SetUtils.intersection(a, b);

        assertTrue(SetUtils.isEqualSet(expected, intersect));

        a = SetUtils.transformedSet(new HashSet<>(), e -> e * 2  );
        a.add(2);

        assertEquals(a.toArray()[0], 4);

        Set<Integer> source = new HashSet<>(Arrays.asList(1));
        Set<Integer> newSet = TransformedSet.transformedSet(source, e -> e * 2);

        assertEquals(newSet.toArray()[0], 2);
        assertEquals(source.toArray()[0], 2);

        a = new HashSet<>(Arrays.asList(1, 2, 5));
        b = new HashSet<>(Arrays.asList(1, 2, 3));
        result = SetUtils.disjunction(a, b);

        assertTrue(result.toSet().contains(5) && result.toSet().contains(3));

    }
}
