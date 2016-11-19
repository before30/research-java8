package cc.before30.abstractdocumentpattern;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by before30 on 2016. 11. 19..
 */
@AllArgsConstructor
public class BaseDocument implements Document {
    private final Map<String, Object> entries;

    @Override
    public Object put(String key, Object value) {
        return entries.put(key, value);
    }

    @Override
    public Object get(String key) {
        return entries.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        final List<Map<String, Object>> children = (List<Map<String, Object>>)get(key);
        return children == null ? Stream.empty() : children.stream().map(constructor);
    }
}
