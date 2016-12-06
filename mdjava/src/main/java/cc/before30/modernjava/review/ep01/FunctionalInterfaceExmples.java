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
		System.out.println("hello world");

		// 1
		Function<String, String> f = (arg) -> "hello :" + arg;
		System.out.println(f.apply("joel"));

		// 2
		MyFunction<Integer, String> f2 = new MyFunction<Integer, String>() {

			@Override public String test(Integer arg) {
				return "Hello " + arg;
			}
		};
		System.out.println(f2.test(1));

		// 3
		MyFunction<Integer, String> f3 = (arg) -> {
			return "hi therer " + arg;
		};
		System.out.println(f3.test(1111));

		// anonymous class, lambda expression 차이는 무엇일까?
	}


	@FunctionalInterface
	interface MyFunction<T, R> {
		R test(T arg);
	}


}
