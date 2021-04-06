package com.example.subMultiModule2;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class LibModClass {
    public LibModClass() {
        abc = null;
    }

    @NonNull
    public String sayHello() {
        String a = null;
        if (new Random().nextBoolean()) a = "true";
        return a;
    }

    @NonNull
    public String abc;

    public int compute(int a, int b) {
        return a + b;
    }
}
