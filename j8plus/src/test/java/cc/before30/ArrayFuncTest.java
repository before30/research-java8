package cc.before30;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by before30 on 11/06/2017.
 */
public class ArrayFuncTest {

    @Test
    public void testIsEmptyArray() throws Exception {
        final String[] arrayNull = null;
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with null ", true, ArrayFunc.isEmpty(arrayNull));

        final String[] array0 = {};
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with an empty array ", true, ArrayFunc.isEmpty(array0));

        final String[] array1 = {"something"};
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with non array ", false, ArrayFunc.isEmpty(array1));

        final String[] array2 = {"somthing1", "something2"};
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with non array ", false, ArrayFunc.isEmpty(array2));

    }

    @Test
    public void testIsEmtpyStream() throws Exception {
        Stream stream = null;
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with an empty array ", true, ArrayFunc.isEmpty(stream));

        final String[] array0 = {};
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with an empty array ", true, ArrayFunc.isEmpty(Arrays.stream(array0)));

        final String[] array1 = {"something"};
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with non array ", false, ArrayFunc.isEmpty(Arrays.stream(array1)));

        final String[] array2 = {"somthing1", "something2"};
        Assert.assertEquals("Test ArrayFunc.isEmpty(T[]) with non array ", false, ArrayFunc.isEmpty(Arrays.stream(array2)));

    }

    @Test
    public void testIsNotEmpty() throws Exception {
        final String[] array = {};
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(T[]) with an empty array", ArrayFunc.isNotEmtpy(array), false);

        Assert.assertEquals("Test ArrayFunc.isNotEmpty(T[]) with an empty array", ArrayFunc.isNotEmtpy(Arrays.stream(array)), false);

        final String[] array1 = {"something"};
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(T[]) with an empty array", ArrayFunc.isNotEmtpy(array1), true);

        Assert.assertEquals("Test ArrayFunc.isNotEmpty(T[]) with an empty array", ArrayFunc.isNotEmtpy(Arrays.stream(array1)), true);

    }

    @Test
    public void testIsEmptyWithIntArray() throws Exception {
        final int[] arrayNull = null;
        final int[] array0 = {};
        final int[] array1 = {1};
        final int[] array2 = {1, 2};

        Assert.assertEquals("Test ArrayFunc.isEmpty(int[]) with null ", true, ArrayFunc.isEmpty(arrayNull));
        Assert.assertEquals("Test ArrayFunc.isEmpty(int[]) with an empty array ", true, ArrayFunc.isEmpty(array0));
        Assert.assertEquals("Test ArrayFunc.isEmpty(int[]) with non array ", false, ArrayFunc.isEmpty(array1));
        Assert.assertEquals("Test ArrayFunc.isEmpty(int[]) with non array ", false, ArrayFunc.isEmpty(array2));

    }


    @Test
    public void testIsNotEmptyWithIntArray() throws Exception {
        final int[] arrayNull = null;
        final int[] array0 = {};
        final int[] array1 = {1};
        final int[] array2 = {1, 2};

        Assert.assertEquals("Test ArrayFunc.isNotEmpty(int[]) with null ", false, ArrayFunc.isNotEmpty(arrayNull));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(int[]) with an empty array ", false, ArrayFunc.isNotEmpty(array0));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(int[]) with non array ", true, ArrayFunc.isNotEmpty(array1));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(int[]) with non array ", true, ArrayFunc.isNotEmpty(array2));

    }


    @Test
    public void testIsEmptyWithLongArray() throws Exception {
        final long[] arrayNull = null;
        final long[] array0 = {};
        final long[] array1 = {1};
        final long[] array2 = {1, 2};

        Assert.assertEquals("Test ArrayFunc.isEmpty(long[]) with null ", true, ArrayFunc.isEmpty(arrayNull));
        Assert.assertEquals("Test ArrayFunc.isEmpty(long[]) with an empty array ", true, ArrayFunc.isEmpty(array0));
        Assert.assertEquals("Test ArrayFunc.isEmpty(long[]) with non array ", false, ArrayFunc.isEmpty(array1));
        Assert.assertEquals("Test ArrayFunc.isEmpty(long[]) with non array ", false, ArrayFunc.isEmpty(array2));

    }


    @Test
    public void testIsNotEmptyWithLongArray() throws Exception {
        final long[] arrayNull = null;
        final long[] array0 = {};
        final long[] array1 = {1};
        final long[] array2 = {1, 2};

        Assert.assertEquals("Test ArrayFunc.isNotEmpty(long[]) with null ", false, ArrayFunc.isNotEmpty(arrayNull));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(long[]) with an empty array ", false, ArrayFunc.isNotEmpty(array0));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(long[]) with non array ", true, ArrayFunc.isNotEmpty(array1));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(long[]) with non array ", true, ArrayFunc.isNotEmpty(array2));

    }


    @Test
    public void testIsEmptyWithDoubleArray() throws Exception {
        final double[] arrayNull = null;
        final double[] array0 = {};
        final double[] array1 = {1};
        final double[] array2 = {1, 2};

        Assert.assertEquals("Test ArrayFunc.isEmpty(double[]) with null ", true, ArrayFunc.isEmpty(arrayNull));
        Assert.assertEquals("Test ArrayFunc.isEmpty(double[]) with an empty array ", true, ArrayFunc.isEmpty(array0));
        Assert.assertEquals("Test ArrayFunc.isEmpty(double[]) with non array ", false, ArrayFunc.isEmpty(array1));
        Assert.assertEquals("Test ArrayFunc.isEmpty(double[]) with non array ", false, ArrayFunc.isEmpty(array2));

    }


    @Test
    public void testIsNotEmptyWithDouleArray() throws Exception {
        final double[] arrayNull = null;
        final double[] array0 = {};
        final double[] array1 = {1};
        final double[] array2 = {1, 2};

        Assert.assertEquals("Test ArrayFunc.isNotEmpty(double[]) with null ", false, ArrayFunc.isNotEmpty(arrayNull));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(double[]) with an empty array ", false, ArrayFunc.isNotEmpty(array0));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(double[]) with non array ", true, ArrayFunc.isNotEmpty(array1));
        Assert.assertEquals("Test ArrayFunc.isNotEmpty(double[]) with non array ", true, ArrayFunc.isNotEmpty(array2));

    }
}