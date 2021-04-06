package com.example.subModule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubMainTest {

    @Test
    void doTHing() {
        SubMain sm = new SubMain();

        assertNotEquals("", sm.doTHing());
    }

}
