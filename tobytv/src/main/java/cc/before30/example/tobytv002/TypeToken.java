package cc.before30.example.tobytv002;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by before30 on 2016. 11. 5..
 */
public class TypeToken {
    static class TypesafeMap {
//        Map<Class<?>, Object> map = new HashMap<>();
        Map<TypeReference<?>, Object> map = new HashMap<>();

        <T> void put(TypeReference<T> tr, T value) {
            map.put(tr, value);
        }

        <T> T get(TypeReference<T> tr) {
            if (tr.type instanceof Class<?>)
                return ((Class<T>)tr.type).cast(map.get(tr));
            else
                return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr));

        }

//        <T> void put(Class<T> clazz, T value) {
//            map.put(clazz, value);
//        }
//
//        <T> T get(Class<T> clazz) {
//            return clazz.cast(map.get(clazz));
//        }
    }

    static class TypeReference<T> {
        Type type;

        public TypeReference() {
            Type sType = getClass().getGenericSuperclass();
            if (sType instanceof ParameterizedType) {
                this.type = ((ParameterizedType)sType).getActualTypeArguments()[0];
            } else {
                throw new RuntimeException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;

            TypeReference<?> that = (TypeReference<?>) o;

            return type.equals(that.type);

        }

        @Override
        public int hashCode() {
            return type.hashCode();
        }
    }


    // SUPER TYPE TOKEN

    public static void main(String[] args) {
        TypeReference t = new TypeReference<String>() {};
        System.out.println(t.type);

        TypesafeMap m = new TypesafeMap();
        m.put(new TypeReference<Integer>() {}, 1);
        m.put(new TypeReference<String>() {}, "string");
        m.put(new TypeReference<List>() {}, Arrays.asList(1,2,3,4));
        m.put(new TypeReference<List<Integer>>() {}, Arrays.asList(1, 2, 3));
        m.put(new TypeReference<List<String>>() {}, Arrays.asList("1", "2", "3"));

        System.out.println(m.get(new TypeReference<Integer>() {}));
        System.out.println(m.get(new TypeReference<String>() {}));
        System.out.println(m.get(new TypeReference<List>() {}));
        System.out.println(m.get(new TypeReference<List<Integer>>() {}));
        System.out.println(m.get(new TypeReference<List<String>>() {}));


//        TypesafeMap m = new TypesafeMap();
//        m.put(Integer.class, 1);
//        m.put(String.class,  "Value");
//        m.put(List.class, Arrays.asList(1, 2, 3));
//
//        // Parameterized Type
//        // Class Type
//        // Class Type에는 generic 정보가 빠져있음
//        List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
//        System.out.println(list.getClass());
//        System.out.println(list.getClass().getTypeName());
//        System.out.println(list.getClass().getTypeParameters());
//
//        System.out.println(m.get(Integer.class));
//        System.out.println(m.get(String.class));
//        System.out.println(m.get(List.class));

    }


//    static class Generic<T> {
//        T value;
//
//        void set(T t) {
//        }
//
//        T get() { return null; }
//    }
//
//    public static void main(String[] args) {
//        Generic<String> s = new Generic<String>();
//        s.value = "string";
//
//        System.out.println(s.value.getClass());
//        Generic<Integer> i = new Generic<Integer>();
//        i.value = 1;
//        i.set(10);
//
//
//    }
//    static Object create() {
//        return new Object();
//    }
//
//    static <T> T create(Class<T> clazz) throws Exception {
//        return clazz.newInstance();
//    }
//
//    public static void main(String[] args) throws Exception {
//        Object o = create();
//        System.out.println(o.getClass());
//
//        String so = create(String.class);
//        System.out.println(so.getClass());
//
//        Integer io = create(Integer.class);
//        System.out.println(io.getClass());
//    }
}
