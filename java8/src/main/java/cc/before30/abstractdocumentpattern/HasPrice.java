package cc.before30.abstractdocumentpattern;

import java.util.OptionalInt;

/**
 * Created by before30 on 2016. 11. 19..
 */
public interface HasPrice extends Document {
    String PRICE = "price";
    default OptionalInt getPrice() {
        final Number num = (Number) get(PRICE);
        return num == null
                ? OptionalInt.empty()
                : OptionalInt.of(num.intValue());
    }

    default void setPrice(int price) {
        put(PRICE, price);
    }
}
