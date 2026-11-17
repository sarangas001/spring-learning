import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 4, 6, 8, 2,3);

        nums.stream()
                .filter(n -> n%2 != 0)
                .map(n -> n*2)
                .sorted()
                .forEach(System.out::println);
        System.out.print(nums);


//        Stream<Integer> newStream = nums.stream();
//
//        Stream<Integer> mappedStream = newStream.map(n -> n * 2);
//
//        mappedStream.forEach(System.out::println);

//        Stream<Integer> filteredStream = newStream.filter(n -> n % 2 == 0);
//        filteredStream.forEach(System.out::println);


//        Stream<Integer> sortedStream = newStream.sorted();
//
//        sortedStream.forEach(System.out::println);

//        long count = newStream.count();
//
//        newStream.forEach(num -> System.out.println(num));

    }
}
