package com.tddapps.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {
    @Test
    void EmptyByDefault() {
        var l = new ArrayList<>();

        assertTrue(l.isEmpty());
        assertEquals(0, l.size());
    }

    @Test
    void AddingElementsChangesSize(){
        var l = new ArrayList<String>();

        assertTrue(l.add("1"));
        assertFalse(l.isEmpty());
        assertEquals(1, l.size());

        assertTrue(l.add("1"));
        assertFalse(l.isEmpty());
        assertEquals(2, l.size());
    }
}
