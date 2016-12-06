package cc.before30.modernjava.review.ep01;

import jdk.management.resource.ResourceId;

import java.util.function.Function;

/**
 * User: before30 
 * Date: 2016. 12. 6.
 * Time: 오후 8:31
 */
public class FunctionalInterfaceExmples {

	public static void main(String[] args) {
		// 0
		Function<String, Integer> toInt1 = new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return Integer.valueOf(s);
			}
		};

		System.out.println(toInt1.apply("100"));

		// 1
		Function<String, Integer> toInt2 = (String s) -> {
				return Integer.valueOf(s);
		};

		System.out.println(toInt2.apply("101"));

		// 2
		Function<String, Integer> toInt3 = s -> Integer.valueOf(s);

		System.out.println(toInt3.apply("102"));


		/////////////////////
		// identity function
		//
		System.out.println(Function.identity().apply("identity"));
		System.out.println(Function.identity().apply(100));

		final Function<String, String> identityFunction = t -> t;
		System.out.println(identityFunction.apply("string"));

		///////
		// Custom Function
		//
		MyFunction<Integer, String> f3 = (arg) -> {
			return "hi therer " + arg;
		};
		System.out.println(f3.test(1111));

	}


	@FunctionalInterface
	interface MyFunction<T, R> {
		R test(T arg);
	}


}
