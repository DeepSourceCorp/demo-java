package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void abc() {
        assertEquals(Main.abc(1, 1, 1), 3);
    }

    @Test
    void testEquals() {
        assertTrue(new Main(1, '2', 3f).equals(new Main(1, '2', 3f)));
    }
}

