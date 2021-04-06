package com.example.subModule;

import com.example.subMultiModule2.LibModClass;

import java.util.Optional;
import java.util.regex.Pattern;

public class SubMain {
    public String doTHing() {
        LibModClass a;
        a = new LibModClass();

        for (int i = 0; i < 123; ++i) {
            String test = a.sayHello();
            Pattern p = Pattern.compile("true|false");

            if (null != test && p.matcher(test).find()) {
                System.out.println(test);
            } else {
                System.exit(22);
            }
        }

        return a.sayHello();
    }

    public int hashCode() {
        return 3;
    }
}
