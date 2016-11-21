package cc.before30.example.anonymous;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cc.before30.example.anonymous.AnonymousTypes.with;
import static java.util.Arrays.asList;

public class EnhancingListExample {

    public static void main(String... args) {
        List<String> stringList = asList("alpha", "bravo");

        with((ForwardingList<String>)() -> stringList, list -> {
            System.out.println("hello world" + list.stream().collect(Collectors.joining(",")));
        });


        with((ForwardingList<String> & Mappable<String>)() -> stringList, list -> {
            List<String> strings = list.map(String::toUpperCase);
            strings.forEach(System.out::println);
        });

    }

    interface Mappable<T> extends DelegatesTo<List<T>> {
        default List<T> map(Function<T,T> mapper) {
            return delegate().stream().map(mapper).collect(Collectors.toList());
        }
    }

}