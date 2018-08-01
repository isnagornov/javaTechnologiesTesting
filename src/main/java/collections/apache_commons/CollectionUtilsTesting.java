package collections.apache_commons;

import collections.apache_commons.entity.Address;
import collections.apache_commons.entity.Customer;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollectionUtilsTesting {

    public static void main(String[] args) {

        Customer customer1 = new Customer(1, "Daniel", "locality1", "city1");
        Customer customer2 = new Customer(2, "Fredrik", "locality2", "city2");
        Customer customer3 = new Customer(3, "Kyle", "locality3", "city3");
        Customer customer4 = new Customer(4, "Bob", "locality4", "city4");
        Customer customer5 = new Customer(5, "Cat", "locality5", "city5");
        Customer customer6 = new Customer(6, "John", "locality6", "city6");

        List<Customer> list1 = Arrays.asList(customer1, customer2, customer3);
        List<Customer> list2 = Arrays.asList(customer4, customer5, customer6);
        List<Customer> list3 = Arrays.asList(customer1, customer2);

        List<Customer> sortedList = CollectionUtils.collate(list1, list2);

        assertEquals(6, sortedList.size());
        assertTrue(sortedList.get(0).getName().equals("Bob"));
        assertTrue(sortedList.get(2).getName().equals("Daniel"));

        Collection<Address> addressCol = CollectionUtils.collect(list1, Customer::getAddress);

        List<Address> addressList = new ArrayList<>(addressCol);
        assertTrue(addressList.size() == 3);
        assertTrue(addressList.get(0).getLocality().equals("locality1"));

        List<Customer> emptyList = new ArrayList<>();
        List<Customer> nullList = null;

        assertTrue(CollectionUtils.isEmpty(nullList));
        assertTrue(CollectionUtils.isEmpty(emptyList));

        assertTrue(CollectionUtils.isSubCollection(list3, list1));

        Collection<Customer> intersection = CollectionUtils.intersection(list1, list3);
        assertTrue(intersection.size() == 2);

        Collection<Customer> result = CollectionUtils.subtract(list1, list3);
        assertFalse(result.contains(customer1));

        Collection<Customer> union = CollectionUtils.union(list1, list2);

        assertTrue(union.contains(customer1));
        assertTrue(union.contains(customer4));
    }
}
