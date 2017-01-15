package cc.before30.lombokex.fluent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by before30 on 15/01/2017.
 */
@Accessors(fluent = true, chain = true)
@ToString(includeFieldNames = true)
public class FluentGetterSetterEx {

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String message;

    public static void main(String[] args) {
        FluentGetterSetterEx ex = new FluentGetterSetterEx();
        ex.age(100).age(100).name("test").message("message");

        System.out.println(ex.toString());
    }
}
