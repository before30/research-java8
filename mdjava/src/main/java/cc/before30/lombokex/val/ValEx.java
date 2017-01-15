package cc.before30.lombokex.val;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by before30 on 15/01/2017.
 */

// https://projectlombok.org/features/val.html
// lombok.val.flagUsage = [warning | error] (default: not set)
// Lombok will flag any usage of val as a warning or error if configured.
@Slf4j
public class ValEx {

    public String example() {
        val example = new ArrayList<String>();
        example.add("Hello, World!");
        val foo = example.get(0);
        return foo.toLowerCase();
    }

    public void example2() {
        val map = new HashMap<Integer, String>();
        map.put(0, "zero");
        map.put(5, "five");
        for (val entry : map.entrySet()) {
            log.info("{}: {}", entry.getKey(), entry.getValue());
        }
    }

    public void example3() {
        val test = "1234";
        val i = 10;
//        test = "4567"; // error
//        i = 11; // error

    }

    public static void main(String[] args) {
        ValEx valEx = new ValEx();
        System.out.println(valEx.example());
        valEx.example2();
    }
}
