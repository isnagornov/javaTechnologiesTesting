package collections;

import java.util.ArrayList;
import java.util.List;

public class SpeedTest {

    public static void main(String[] args) {

        int size = 10000000;

        List<Integer> list1 = new ArrayList<>(size);

        long start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            list1.add(i);
        }

        System.out.println("Operation ADD completed, - size - " + list1.size());
        System.out.println("Operation time - " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            int var = list1.get(i);

            if (i == size / 2) {
                System.out.println(var);
            }
        }

        System.out.println("Operation FORI completed, - size - " + list1.size());
        System.out.println("Operation time - " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        for (Integer item : list1) {
            if (item == size / 2) {
                System.out.println(item);
            }
        }

        System.out.println("Operation FOR completed, - size - " + list1.size());
        System.out.println("Operation time - " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        list1.forEach((item) -> {
            if (item == size / 2) {
                System.out.println(item);
            }
        });

        System.out.println("Operation FOREACH completed, - size - " + list1.size());
        System.out.println("Operation time - " + (System.currentTimeMillis() - start));

    }
}
