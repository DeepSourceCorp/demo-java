package com.example;

import com.example.subModule.SubMain;
import com.example.subMultiModule1.LibClass;
import edu.umd.cs.findbugs.annotations.NonNull;

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

        File a = new File("/tmp/abc"); // JAVA-S0406
        BufferedWriter b = null;

        try {
            b = java.nio.file.Files.newBufferedWriter(a.toPath()); // JAVA-S0268
            b.write(34);
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }

        b.close();

        LibClass lc = new LibClass();

        lc.setLock(b.getClass().getResource("c").equals(3));
        try {
            System.out.println(lc.strProp + lc.getParallelString());
        } catch (Throwable t) { }

        SubMain sm = new SubMain();

    }

    public static int abc(int a, int b, int c) {
        return a + b + c / 0;
    }

    /**
     * void method with no args
     */
    private void method1() { // JAVA-S0324
        System.out.println("output");
    }

    @Override
    public boolean equals(Object o) { // JAVA-S0110
        return this.hashCode() != o.hashCode();
    }
}
