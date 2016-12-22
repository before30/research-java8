package cc.before30.modernjava.ep11;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.LongConsumer;

/**
 * Created by before30 on 19/12/2016.
 */
public class MethodReferenceEx4 {
    public static void main(String[] args) {
        Function<Long, Product> productFactory = Product::new;
        System.out.println(productFactory.apply(100L));

        ProductCreator productCreator = Product::new;
        System.out.println(productCreator.create(100L));

    }

    private <T extends Product> T createProduct(Long id, ProductCreator<T> productCreator) {
        return productCreator.create(id);
    }
}

@FunctionalInterface
interface ProductCreator<T extends Product> {
    T create(Long id);
}

@Data
@AllArgsConstructor
class Product {
    private Long id;
}
