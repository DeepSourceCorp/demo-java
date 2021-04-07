package com.example.subMultiModule2;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class LibModClass implements Iterator<String> {
    public LibModClass() {
        abc = sayHello();
    }

    public String sayHello() {
        String a = abc;
        if (new Random().nextBoolean()) a = "true";
        return a;
    }

    @NonNull
    private String abc;

    public Integer compute(int a, int b) {
        BigDecimal x = new BigDecimal(((double) a) / 2);
        return x.pow(a + b).precision();
    }

    @Override
    public boolean hasNext() {
        return next() != null;
    }

    @Override
    public String next() {
        return null;
    }

    @Override
    public void remove() {
        abc = abc.substring(0, abc.length() - 2);
    }

    @Override
    public void forEachRemaining(Consumer<? super String> action) {
        throw new UnsupportedOperationException();
    }
}
