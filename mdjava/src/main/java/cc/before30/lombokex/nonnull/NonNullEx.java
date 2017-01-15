package cc.before30.lombokex.nonnull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Created by before30 on 15/01/2017.
 */

// https://projectlombok.org/features/NonNull.html
@Slf4j
public class NonNullEx {
    static class NonNullExWithOutLombok {

        private String name;

        public NonNullExWithOutLombok(String name) {
            this.name = Objects.requireNonNull(name);
        }
    }

    static class NonNullExWithOutLombok2 {

        private String name;

        public NonNullExWithOutLombok2(Person person) {
            Objects.nonNull(person);
            this.name = person.getName();
        }
    }

    static class NonNullExWithLombok {

        private String name;

        public NonNullExWithLombok(@NonNull Person person) {
            this.name = person.getName();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Person {
        private String name;
    }

    public static void main(String[] args) {

        try {
            NonNullExWithOutLombok test1 = new NonNullExWithOutLombok(null);
        } catch (NullPointerException ex) {
            log.info("test1 : good");
        }
        NonNullExWithOutLombok test2 = new NonNullExWithOutLombok("test");
        try {
            NonNullExWithOutLombok2 test3 = new NonNullExWithOutLombok2(null);
        } catch (NullPointerException ex) {
            log.info("test3 : good");
        }
        NonNullExWithOutLombok2 test4 = new NonNullExWithOutLombok2(new Person("hello"));

        try {
            NonNullExWithLombok test5 = new NonNullExWithLombok(null);
        } catch (NullPointerException ex) {
            log.info("test5 : good");
        }
    }
}
