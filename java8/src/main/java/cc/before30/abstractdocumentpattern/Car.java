package cc.before30.abstractdocumentpattern;

import java.util.Map;

/**
 * Created by before30 on 2016. 11. 19..
 */
public class Car extends BaseDocument implements HasPrice {
    public Car(Map<String, Object> entries) {
        super(entries);
    }
}
