package com.example.dp.delegate;

/**
 * Created by before30 on 21/11/2016.
 */
public class DelegatePattern {
    static class RealPrinter {
        void print() {
            System.out.println("Something");
        }
    }

    static class Printer {
        RealPrinter delegate = new RealPrinter(); // create the delegate
        void print() {
            delegate.print();
        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();
        printer.print();
    }
}
