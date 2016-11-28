package cc.before30.modernjava.ep07;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by before30 on 28/11/2016.
 */
public class FunctionalInterfaceExamples {

    public static void main(String[] args) {
        final List<Product> products = Arrays.asList(
                new Product(1L, "A", new BigDecimal("10.00")),
                new Product(2L, "B", new BigDecimal("55.50")),
                new Product(3L, "C", new BigDecimal("17.45")),
                new Product(4L, "D", new BigDecimal("23.00")),
                new Product(5L, "#", new BigDecimal("110.99"))
        );

        List<Product> result = filter(products, p -> p.getPrice().compareTo(new BigDecimal("20")) >= 0);
        System.out.println(result);

        System.out.println(products.stream().filter(p -> p.getPrice().compareTo(new BigDecimal("20")) >= 0).collect(Collectors.toList()));

        System.out.println(map(result, p -> new DiscountedProduct(p.getId(), p.getName(), p.getPrice().multiply(new BigDecimal("0.5")))));

        final Predicate<Product> lessThanOrEqualTo30 = product -> product.getPrice().compareTo(new BigDecimal("30")) <= 0;

        List<DiscountedProduct> discountedProducts = map(result, p -> new DiscountedProduct(p.getId(), p.getName(), p.getPrice().multiply(new BigDecimal("0.5"))));
        filter(discountedProducts, lessThanOrEqualTo30);

    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<T>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<R>();
        for (T t: list) {
            result.add(function.apply(t));
        }

        return result;
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;
        for (T t: list) {
            total = total.add(mapper.apply(t));
        }

        return total;
    }
}

@Data
@AllArgsConstructor
class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

@ToString(callSuper = true)
class DiscountedProduct extends Product {
    public DiscountedProduct(final Long id, final String name, final BigDecimal price) {
        super(id, name, price);
    }
}

@AllArgsConstructor
@Data
class OrderItem {
    private Long id;
    private Product product;
    private int quantity;
}

@AllArgsConstructor
@Data
class Order {
    private Long id;
    private String orderNumber;
    private List<OrderItem> orderItems;

}