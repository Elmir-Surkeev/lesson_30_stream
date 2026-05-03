package kg.attractor.java.lesson.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListTraverse {

    // этот код показывает разницу между выполнением
    // одной и той же операции, разными способами, над двумя типами
    // коллекции List.
    // Важно тут то, что с виду похожие решения
    // приводят к тому, что есть большая разница в производительности
    // и очень важен тип коллекции

    public static void main(String[] args) {
        var bound = 100_000;

        var arrayList = makeList(bound, ArrayList::new);
        var linkedList = makeList(bound, LinkedList::new);

        System.out.println();
        System.out.println("------- started --------");
        System.out.println();

        System.out.println("Count Elements Using parallel stream");
        measure(() -> streamParallelCount(arrayList));
        measure(() -> streamParallelCount(linkedList));
        System.out.println();

        System.out.println("Count Elements Using stream");
        measure(() -> streamCount(arrayList));
        measure(() -> streamCount(linkedList));
        System.out.println();

        System.out.println("Count Elements Using for each");
        measure(() -> forEachCount(arrayList));
        measure(() -> forEachCount(linkedList));
        System.out.println();

        System.out.println("Count Elements Using Numeric For");
        measure(() -> numericForCount(arrayList));
        measure(() -> numericForCount(linkedList));
        System.out.println();

        System.out.println("------- finished --------");
    }

    private static List<String> makeList(int bound, Supplier<List<String>> supplier) {
        System.out.println("Generating " + bound + " random numbers as string in ArrayList");
        var start = System.nanoTime();
        var arrayList = Stream.generate(Math::random).map(String::valueOf).limit(bound).collect(Collectors.toCollection(supplier));
        var delta = System.nanoTime() - start;
        System.out.println("Spent: " + delta / 1000 / 1000 + " ms");
        return arrayList;
    }

    private static void measure(IntSupplier consumer) {
        var start = System.nanoTime();

        int count = consumer.getAsInt();

        System.out.println("Counted: " + count);
        var delta = System.nanoTime() - start;
        System.out.println("Spent: " + delta / 1000 / 1000 + " ms");
    }

    private static int numericForCount(List<String> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            var value = list.get(i);
            if (value.contains("42")) {
                ++count;
            }
        }
        return count;
    }

    private static int forEachCount(List<String> list) {
        int count = 0;
        for (var value : list) {
            if (value.contains("42")) {
                ++count;
            }
        }
        return count;
    }

    private static int streamCount(List<String> list) {
        return (int) list.stream()
                .filter(a -> a.contains("42"))
                .count();
    }

    private static int streamParallelCount(List<String> list) {
        return (int) list.parallelStream()
                .filter(a -> a.contains("42"))
                .count();
    }
}
