package cc.before30.example.tobytv004;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 20/11/2016.
 */
public class AnotherIntersectionType2 {
    interface Pair<T> {
        T getFirst();
        T getSecond();
        void setFirst(T first);
        void setSecond(T second);
    }

    static class Name implements Pair<String> {
        String firstName;
        String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
        @Override
        public String getFirst() {
            return this.firstName;
        }

        @Override
        public String getSecond() {
            return this.lastName;
        }

        @Override
        public void setFirst(String first) {
            this.firstName = first;

        }

        @Override
        public void setSecond(String second) {
            this.lastName = second;
        }
    }

    interface DelegateTo<T> {
        T delegate();
    }

    interface ForwardingPair<T> extends DelegateTo<Pair<T>>, Pair<T> {
        default T getFirst() { return delegate().getFirst(); }
        default T getSecond() { return delegate().getSecond(); }
        default void setFirst(T first) { delegate().setFirst(first); }
        default void setSecond(T second) { delegate().setSecond(second);}

    }

    interface Converable<T> extends DelegateTo<Pair<T>> {
        default void convert(Function<T, T> mapper) {
            Pair<T> pair = delegate();
            pair.setFirst(mapper.apply(pair.getFirst()));
            pair.setSecond(mapper.apply(pair.getSecond()));

        }
    }
    private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    static <T> void print(Pair<T> pair) {
        System.out.println(pair.getFirst() + " " + pair.getSecond());
    }

    // default method, static method는 Function 구현과는 상관없다!!!!!
    public static void main(String[] args) {
        Pair<String> name = new Name("Toby", "Lee");
        run((ForwardingPair<String>)() -> name, o -> {
            System.out.println(o.getFirst());
            System.out.println(o.getSecond());
        });

        run((ForwardingPair<String> & Converable<String>)() -> name, o -> {
            print(o);
            o.convert(s->s.toUpperCase());
            print(o);
        });
    }
}
