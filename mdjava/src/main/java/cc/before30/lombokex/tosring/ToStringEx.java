package cc.before30.lombokex.tosring;

/**
 * Created by before30 on 15/01/2017.
 */

// https://projectlombok.org/features/ToString.html

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * includeFieldNames = true
 * non-static fields will be printed
 * exclude =
 * callSuper
 *
 * lombok.toString.includeFieldNames = [true | false] (default: true)
 * * lombok.toString.doNotUseGetters = [true | false] (default: false)
 * * lombok.toString.flagUsage = [warning | error] (default: not set)
 *
*/
public class ToStringEx {
    @ToString(exclude = "aName")
    static class AClass extends RootClass {
        private static final int STATIC_VALUE_ACLASS = 100;
        private String aName;

        public AClass(int width, int height, String aName) {
            super(width, height);
            this.aName = aName;
        }
    }

    @ToString(includeFieldNames = false)
    static class BClass extends RootClass {
        private String bName;

        public BClass(int width, int height, String bName) {
            super(width, height);
            this.bName = bName;
        }
    }

    @ToString(callSuper = true)
    static class CClass extends RootClass {

        private String cName;
        private ChildClass childClass;

        public CClass(int width, int height, String cName, ChildClass childClass) {
            super(width, height);
            this.cName = cName;
            this.childClass = childClass;
        }
    }

    @ToString(of = {"id"}, callSuper = true)
    static class DClass extends RootClass {
        private String dName;
        private int id;
        private ChildClass childClass;

        public DClass(int width, int height, String dName, int id, ChildClass childClass) {
            super(width, height);
            this.dName = dName;
            this.id = id;
            this.childClass = childClass;
        }
    }

    static class ChildClass extends RootClass {
        private String childName;

        public ChildClass(int width, int height, String childName) {
            super(width, height);
            this.childName = childName;
        }
    }

    @AllArgsConstructor
    static class RootClass {
        public static final int STATIC_VALUE_ROOTCLASS = 99999;
        private final int width;
        private final int height;

    }

    public static void main(String[] args) {

        AClass a = new AClass(1, 2, "aaaa");
        BClass b = new BClass(11, 22, "bbbb");
        CClass c = new CClass(111, 222, "cccc", new ChildClass(0, 0, "child"));
        DClass d = new DClass(1111, 2222, "dddd", 9999, new ChildClass(0, 0, "child"));

        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(c.toString());
        System.out.println(d.toString());

    }
}
