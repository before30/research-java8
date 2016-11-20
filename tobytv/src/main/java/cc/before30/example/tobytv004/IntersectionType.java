package cc.before30.example.tobytv004;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by before30 on 20/11/2016.
 */
public class IntersectionType {

    public static void main(String[] args) {
        // 해당 람다식을 내부적으로 정의할때 안에 있는 것들을 구현하는 방식으로 만든다
        // Marker Interface가 필요한경우 이렇게 넣어서 사용하면 된다!!
        // upper bound를 정의해서 사용하는 케이스에 많이 사용된다.
        hello((Function & Serializable) s -> s);

        // Benji ????
        // interface를 조합해서 여러가지 기능의 코드를 만들어낼 수 있다.


        // 안에 구현될 정의가 하나라는 것이지 default method, static method까지 이야기하는 것이 아니다
        hello3((Function & Hello & Hi) s -> s);

        run((Function & Hello & Hi)s -> s, o -> {
            o.hello();
            o.hi();
        });

        run((Function & Hello & Hi & Printer) s -> s, o -> {
            o.hello();
            o.hi();
            o.print("print me");
        });

        // 실제로 어떻게 쓸까?
        // Delegate 방식을 사용해서 공통적으로 사용하게 하는 방식이 있다
        // 3개를 합쳤을때, 1개 짜리면 된다 (뭐가?)
        //
        run((Function & Hello2 & Hi2)s->s, o -> {
            o.hi();
            o.hello();
        });
    }

    // field 정의 불가
    interface Hello {
        default void hello() {
            System.out.println("Hello");
        }
    }

    interface Hi {
        default void hi() {
            System.out.println("Hi");
        }
    }

    interface Hello2 extends Function {
        default void hello() {
            System.out.println("Hello");
        }
    }

    interface Hi2 extends Function {
        default void hi() {
            System.out.println("Hi");
        }
    }

    interface Printer {
        default void print(String str) {
            System.out.println(str);
        }
    }
    private static void hello(Function o) {

    }

    private static <T extends Function & Serializable & Cloneable> void hello2(T o) {

    }

    private static <T extends Function & Hello & Hi> void hello3(T t) {
        t.hello();
        t.hi();
    }

    // Consumer, Supplier 확인 필요
    // Function, BiFunction
    private static <T extends Function> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
