package cc.before30.modernjava.ep09;

/**
 * Created by before30 on 12/12/2016.
 */
public class ClosureEx {
    /*
    First class citizen Function
    Function
    First Class Citizen
    1. Parameter로 사용가능함
    2. Function에서 Return으로 사용가능함
    3. Variable에 저장할 수 있다 / Data Structure에 저장할 수 있다.
     */
    public static void main(String[] args) {
        System.out.println("hello world");

        final int number = 100;
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        };
        runnable.run();

        Runnable runnable2 = () -> System.out.println(number);
        runnable2.run();

        testClouser("test", new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        });
        
    }

    private static void testClouser(final String name, Runnable runnable) {
        System.out.println("==============================");
        System.out.println("Start : " + name + ": ");
        runnable.run();
        System.out.println("==============================");
    }
}
