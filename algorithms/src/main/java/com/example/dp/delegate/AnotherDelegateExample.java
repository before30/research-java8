package com.example.dp.delegate;

/**
 * Created by before30 on 21/11/2016.
 */
public class AnotherDelegateExample {
    interface I {
        void doF();
        void doG();
    }

    static class A implements I {

        @Override
        public void doF() {
            System.out.println("A: doing f");
        }

        @Override
        public void doG() {
            System.out.println("A: doing g");
        }
    }

    static class B implements I {

        @Override
        public void doF() {
            System.out.println("B: doing f");
        }

        @Override
        public void doG() {
            System.out.println("B: doing g");
        }
    }
    static class C implements I {
        I i = new A();

        @Override
        public void doF() {
            i.doF();
        }

        @Override
        public void doG() {
            i.doG();
        }

        public void toA() { i = new A(); }
        public void toB() { i = new B(); }

    }

    public static void main(String[] args) {
        C c = new C();
        c.doF();
        c.doG();
        c.toB();
        c.doF();
        c.doG();
    }
}
