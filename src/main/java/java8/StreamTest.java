package java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {

        Stream<Integer> stream = Stream.<Integer>builder().build();

        int[] arr = {6, 7, 8, 9};

        Stream<Integer> resultStream = stream;
        for (int value : arr) {
            resultStream = Stream.concat(resultStream, Stream.of(value));
        }

        System.out.println(resultStream.collect(Collectors.toList()));

        List<Foo> foos = new ArrayList<>();

        IntStream
                .range(1, 4)
                .forEach(i -> foos.add(new Foo("Foo" + i)));

        foos.forEach(f ->
                IntStream
                        .range(1, 4)
                        .forEach(i -> {
                            for (int idx = 0; idx < i; idx++) {
                                f.bars.add(new Bar("Bar" + idx + " <- " + f.name));
                            }
                        }));

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .filter(b -> b.name.contains("oo1"))
                .peek(bar -> System.out.println(bar))
                .forEach(b -> System.out.println(b.name));

        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
                        .forEach(f.bars::add))
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(foo -> System.out.println(foo.name))
                .flatMap(foo -> foo.bars.stream())
                .map(bar -> bar.name)
                .forEach(barName -> System.out.println(barName));

        System.out.println(IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(foo -> System.out.println(foo.name))
                .anyMatch(foo -> foo.name.contains("1")));

        BiConsumer<String, String> consumer = (s1, s2) -> System.out.println(String.join(" ", s1, s2));
        BiConsumer<String, String> consumer2 = consumer.andThen((s1, s2) -> System.out.println(String.join("-", s1, s2)));

        String[][] strings = {{"a", "b"}, {"c", "d"}};

        Arrays.stream(strings).forEach(array -> consumer2.accept(array[0], array[1]));

        Function<Integer, Integer> multiply2 = (arg) -> 2 * arg;
        Function<Integer, Integer> result = multiply2.compose((arg) -> arg + 1);

        System.out.println(result.apply(2));

        Predicate<Integer> moreThan100Predicate = (arg) -> arg.compareTo(100) > 0;
        Predicate<Integer> predicate = moreThan100Predicate.and((arg) -> arg.compareTo(150) < 0);

        System.out.println(predicate.test(10));
        System.out.println(predicate.test(140));
        System.out.println(predicate.test(170));

        Comparator<Foo> comparator = Comparator.comparingInt(foo -> foo.bars.size());
        Predicate<Foo> predicate1 = (foo) -> foo.name.contains("1");
        Predicate<Foo> predicate2 = (foo) -> foo.name.contains("2");

        foos.stream().filter(predicate1.or(predicate2)).peek(foo -> System.out.println(foo.name)).sorted(comparator.reversed())
                .forEach(foo -> System.out.println(foo.bars.size()));

    }

    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }

    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.join(" ", "Bar", name);
        }
    }
}
