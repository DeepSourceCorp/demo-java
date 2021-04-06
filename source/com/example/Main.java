package com.example;

import com.example.subModule.SubMain;
import com.example.subMultiModule1.LibClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * lorem ipsum sit dor tass
 */
public class Main {

    /**
     * documented field.
     */
    public Object memberObj = new Object();

    public Integer memberInt = 4;

    /**
     * public constructor with 3 args
     *
     * @param a int parameter
     * @param b char parameter
     * @param c boxed float parameter
     */
    public Main(int a, char b, Float c) {
        System.out.printf("%d %c %f", a, b, c);
    }

    /**
     * Documented function
     *
     * @param args adb, ddhdhfwdfe
     */
    public static void main(String[] args) throws IOException {
        System.out.println("test");

        File a = new File("/tmp/abc");
        BufferedWriter b = null;

        try {
            b = java.nio.file.Files.newBufferedWriter(a.toPath());
            b.write(34);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }

        b.write(4);

        LibClass lc = new LibClass();

        lc.setLock(a);
        try {
            System.out.println(lc.strProp + lc.getParallelString());
        } catch (Throwable t) {}

        SubMain sm = new SubMain();
    }

    public static int abc(int a, int b, int c) {
        return a + b + c;
    }

    /**
     * void method with no args
     */
    private void method1() {
        System.out.println("output");
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() != o.hashCode();
    }
}
