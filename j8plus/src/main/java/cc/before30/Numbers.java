package cc.before30;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by before30 on 12/06/2017.
 */
public final class Numbers {
    public static final Predicate<Integer> ODD_INTEGER  = Numbers::isOdd;
    public static final Predicate<Integer> EVEN_INTEGER = Numbers::isEven;

    public static final Predicate<Long> ODD_LONG  = Numbers::isOdd;
    public static final Predicate<Long> EVEN_LONG = Numbers::isEven;

    public static final Predicate<BigInteger> ODD_BIG_INTEGER  = Numbers::isOdd;
    public static final Predicate<BigInteger> EVEN_BIG_INTEGER = Numbers::isEven;

    public static final Predicate<Integer> NEGATIVE_INTEGER = Numbers::isNegative;
    public static final Predicate<Integer> ZERO_INTEGER     = Numbers::isZero;
    public static final Predicate<Integer> POSITIVE_INTEGER = Numbers::isPositive;

    public static final Predicate<Long> NEGATIVE_LONG = Numbers::isNegative;
    public static final Predicate<Long> ZERO_LONG     = Numbers::isZero;
    public static final Predicate<Long> POSITIVE_LONG = Numbers::isPositive;

    public static final Predicate<BigInteger> NEGATIVE_BIG_INTEGER = Numbers::isNegative;
    public static final Predicate<BigInteger> ZERO_BIG_INTEGER     = Numbers::isZero;
    public static final Predicate<BigInteger> POSITIVE_BIG_INTEGER = Numbers::isPositive;

    public static final Predicate<BigDecimal> NEGATIVE_BIG_DECIMAL = Numbers::isNegative;
    public static final Predicate<BigDecimal> ZERO_BIG_DECIMAL     = Numbers::isZero;
    public static final Predicate<BigDecimal> POSITIVE_BIG_DECIMAL = Numbers::isPositive;

    public static boolean isOdd(final int number) {
        return (number & 1) != 0;
    }

    public static boolean isEven(final int number) {
        return !isOdd(number);
    }

    public static boolean isOdd(final long number) {
        return (number & 1) != 0;
    }

    public static boolean isEven(final long number) {
        return !isOdd(number);
    }

    public static boolean isOdd(final BigInteger number) {
        return number.and(BigInteger.ONE)
                .compareTo(BigInteger.ZERO) != 0;
    }

    public static boolean isEven(final BigInteger number) {
        return !isOdd(number);
    }

    public static boolean isNegative(final int number) {
        return number < 0;
    }

    public static boolean isZero(final int number) {
        return number == 0;
    }

    public static boolean isPositive(final int number) {
        return number > 0;
    }

    public static boolean isNegative(final long number) {
        return number < 0;
    }

    public static boolean isZero(final long number) {
        return number == 0;
    }

    public static boolean isPositive(final long number) {
        return number > 0;
    }

    public static boolean isNegative(final BigInteger number) {
        return number.compareTo(BigInteger.ZERO) < 0;
    }

    public static boolean isZero(final BigInteger number) {
        return number.compareTo(BigInteger.ZERO) == 0;
    }

    public static boolean isPositive(final BigInteger number) {
        return number.compareTo(BigInteger.ZERO) > 0;
    }

    public static boolean isNegative(final BigDecimal number) {
        return number.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isZero(final BigDecimal number) {
        return number.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isPositive(final BigDecimal number) {
        return number.compareTo(BigDecimal.ZERO) > 0;
    }

    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    public static class BigIntegerNumber {

        private final BigInteger number;

        public boolean lt(final BigInteger anotherNumber) {
            return number.compareTo(anotherNumber) < 0;
        }

        public boolean lt(final String anotherNumber) {
            return lt(new BigInteger(anotherNumber));
        }

        public boolean le(final BigInteger anotherNumber) {
            return number.compareTo(anotherNumber) <= 0;
        }

        public boolean le(final String anotherNumber) {
            return le(new BigInteger(anotherNumber));
        }

        public boolean eq(final BigInteger anotherNumber) {
            return number.compareTo(anotherNumber) == 0;
        }

        public boolean eq(final String anotherNumber) {
            return eq(new BigInteger(anotherNumber));
        }

        public boolean ge(final BigInteger anotherNumber) {
            return number.compareTo(anotherNumber) >= 0;
        }

        public boolean ge(final String anotherNumber) {
            return ge(new BigInteger(anotherNumber));
        }

        public boolean gt(final BigInteger anotherNumber) {
            return number.compareTo(anotherNumber) > 0;
        }

        public boolean gt(final String anotherNumber) {
            return gt(new BigInteger(anotherNumber));
        }

        public boolean isOdd() {
            return Numbers.isOdd(number);
        }

        public boolean isEven() {
            return !isOdd();
        }
    }

    public static BigIntegerNumber bigInt(final BigInteger number) {
        return new BigIntegerNumber(number);
    }

    public static BigIntegerNumber bigInt(final String number) {
        return bigInt(new BigInteger(number));
    }

    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    public static class BigDecimalNumber {
        private final BigDecimal number;

        public boolean lt(final BigDecimal anotherNumber) {
            return number.compareTo(anotherNumber) < 0;
        }

        public boolean lt(final String anotherNumber) {
            return lt(new BigDecimal(anotherNumber));
        }

        public boolean le(final BigDecimal anotherNumber) {
            return number.compareTo(anotherNumber) <= 0;
        }

        public boolean le(final String anotherNumber) {
            return le(new BigDecimal(anotherNumber));
        }

        public boolean eq(final BigDecimal anotherNumber) {
            return number.compareTo(anotherNumber) == 0;
        }

        public boolean eq(final String anotherNumber) {
            return eq(new BigDecimal(anotherNumber));
        }

        public boolean ge(final BigDecimal anotherNumber) {
            return number.compareTo(anotherNumber) >= 0;
        }

        public boolean ge(final String anotherNumber) {
            return ge(new BigDecimal(anotherNumber));
        }

        public boolean gt(final BigDecimal anotherNumber) {
            return number.compareTo(anotherNumber) > 0;
        }

        public boolean gt(final String anotherNumber) {
            return gt(new BigDecimal(anotherNumber));
        }
    }

    public static BigDecimalNumber decimal(final BigDecimal number) {
        return new BigDecimalNumber(number);
    }

    public static BigDecimalNumber decimal(final String number) {
        return decimal(new BigDecimal(number));
    }

    public enum BigIntegers {

        INSTANCE;

        public static Predicate<BigInteger> lt(final BigInteger number) {
            return anotherNumber -> anotherNumber.compareTo(number) < 0;
        }

        public static Predicate<BigInteger> lt(final String number) {
            return lt(new BigInteger(number));
        }

        public static Predicate<BigInteger> le(final BigInteger number) {
            return anotherNumber -> anotherNumber.compareTo(number) <= 0;
        }

        public static Predicate<BigInteger> le(final String number) {
            return le(new BigInteger(number));
        }

        public static Predicate<BigInteger> eq(final BigInteger number) {
            return anotherNumber -> anotherNumber.compareTo(number) == 0;
        }

        public static Predicate<BigInteger> eq(final String number) {
            return eq(new BigInteger(number));
        }

        public static Predicate<BigInteger> gt(final BigInteger number) {
            return anotherNumber -> anotherNumber.compareTo(number) > 0;
        }

        public static Predicate<BigInteger> gt(final String number) {
            return gt(new BigInteger(number));
        }

        public static Predicate<BigInteger> ge(final BigInteger number) {
            return anotherNumber -> anotherNumber.compareTo(number) >= 0;
        }

        public static Predicate<BigInteger> ge(final String number) {
            return ge(new BigInteger(number));
        }

        public static BigInteger total(final Collection<BigInteger> bigIntegers) {
            return bigIntegers.stream()
                    .reduce(BigInteger.ZERO, BigInteger::add);
        }

        public static <T> BigInteger total(final Collection<T> list, final Function<T, BigInteger> toBigIntegerMapper) {
            return list.stream()
                    .map(toBigIntegerMapper)
                    .reduce(BigInteger.ZERO, BigInteger::add);
        }

        public static BigInteger parallelTotal(final Collection<BigInteger> bigIntegers) {
            return bigIntegers.parallelStream()
                    .reduce(BigInteger.ZERO, BigInteger::add);
        }

        public static <T> BigInteger parallelTotal(final Collection<T> list, final Function<T, BigInteger> toBigIntegerMapper) {
            return list.parallelStream()
                    .map(toBigIntegerMapper)
                    .reduce(BigInteger.ZERO, BigInteger::add);
        }
    }

    public static final BigIntegers bigInt = BigIntegers.INSTANCE;

    public static Predicate<BigDecimal> lt(final BigDecimal number) {
        return anotherNumber -> anotherNumber.compareTo(number) < 0;
    }

    public static Predicate<BigDecimal> lt(final String number) {
        return lt(new BigDecimal(number));
    }

    public static Predicate<BigDecimal> le(final BigDecimal number) {
        return anotherNumber -> anotherNumber.compareTo(number) <= 0;
    }

    public static Predicate<BigDecimal> le(final String number) {
        return le(new BigDecimal(number));
    }

    public static Predicate<BigDecimal> eq(final BigDecimal number) {
        return anotherNumber -> anotherNumber.compareTo(number) == 0;
    }

    public static Predicate<BigDecimal> eq(final String number) {
        return eq(new BigDecimal(number));
    }

    public static Predicate<BigDecimal> gt(final BigDecimal number) {
        return anotherNumber -> anotherNumber.compareTo(number) > 0;
    }

    public static Predicate<BigDecimal> gt(final String number) {
        return gt(new BigDecimal(number));
    }

    public static Predicate<BigDecimal> ge(final BigDecimal number) {
        return anotherNumber -> anotherNumber.compareTo(number) >= 0;
    }

    public static Predicate<BigDecimal> ge(final String number) {
        return ge(new BigDecimal(number));
    }

    public static BigDecimal total(final Collection<BigDecimal> bigDecimals) {
        return bigDecimals.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> BigDecimal total(final Collection<T> list, final Function<T, BigDecimal> toBigDecimalMapper) {
        return list.stream()
                .map(toBigDecimalMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal parallelTotal(final Collection<BigDecimal> bigDecimals) {
        return bigDecimals.parallelStream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> BigDecimal parallelTotal(final Collection<T> list, final Function<T, BigDecimal> toBigDecimalMapper) {
        return list.parallelStream()
                .map(toBigDecimalMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
