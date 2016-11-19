package cc.before30.abstractdocumentpattern;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by before30 on 2016. 11. 19..
 */
public interface Document {
    Object put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
