package cc.before30.lombokex.util;

import lombok.experimental.UtilityClass;

/**
 * Created by before30 on 16/01/2017.
 */
public class UtilityClassEx {
    public static void main(String[] args) {
        System.out.println(UtilityClassTest.addSometing(10));
//        UtilityClassTest t = new UtilityClassTest();
    }
}

@UtilityClass
class UtilityClassTest {
    private final int CONTANT = 5;

    public int addSometing(int in) {
        return in + CONTANT;
    }
}
