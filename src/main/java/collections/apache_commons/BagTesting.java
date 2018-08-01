package collections.apache_commons;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.SortedBag;
import org.apache.commons.collections4.bag.CollectionBag;
import org.apache.commons.collections4.bag.CollectionSortedBag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.TreeBag;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class BagTesting {

    public static void main(String[] args) {
        Bag<Integer> bag = new HashBag<>(
                Arrays.asList(1, 2, 3, 3, 3, 1, 4));

        assertThat(2, equalTo(bag.getCount(1)));

        bag = new HashBag<>();
        bag.add(1);

        assertThat(bag.add(1), equalTo(false));

        bag = CollectionBag.collectionBag(new HashBag<>());
        bag.add(1);

        assertThat(bag.add(1), is((true)));

        bag = new HashBag<>();

        bag.add(1, 5); // adding 1 five times

        assertThat(5, equalTo(bag.getCount(1)));

        bag = new HashBag<>(Arrays.asList(1, 2, 3, 3, 3, 1, 4));

        bag.remove(3, 1); // remove one element, two still remain
        assertThat(2, equalTo(bag.getCount(3)));

        bag.remove(1); // remove all
        assertThat(0, equalTo(bag.getCount(1)));

        TreeBag<Integer> treeBag = new TreeBag<>(Arrays.asList(7, 5,
                1, 7, 2, 3, 3, 3, 1, 4, 7));

        assertThat(treeBag.first(), equalTo(1));
        assertThat(treeBag.getCount(treeBag.first()), equalTo(2));
        assertThat(treeBag.last(), equalTo(7));
        assertThat(treeBag.getCount(treeBag.last()), equalTo(3));

        SortedBag<Integer> sortedBag = CollectionSortedBag.collectionSortedBag(new TreeBag<>());

        sortedBag.add(1);

        assertThat(sortedBag.add(1), is((true)));
    }
}
