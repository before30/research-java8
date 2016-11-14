package cc.before30.example.tobytv002;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by before30 on 2016. 11. 5..
 */
public class SuperTypeToken {
    static class Sup<T> {
        T value;
    }

    static void show(Sup s) throws NoSuchFieldException {
        System.out.println(s.getClass().getDeclaredField("value").getType());

    }

    static class Sub extends Sup<String> {

    }

    public static void main(String[] args) throws NoSuchFieldException {
        Sup<String> s = new Sup<>();
        show(s);

        Sub b = new Sub();
        Type t = b.getClass().getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType)t;
        System.out.println(pType.getActualTypeArguments()[0]);
    }
}
