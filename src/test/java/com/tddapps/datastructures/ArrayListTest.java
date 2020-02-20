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

    @Test
    void SearchesAddedElements(){
        var l = new ArrayList<String>();

        assertFalse(l.contains("1"));
        assertTrue(l.add("1"));

        assertTrue(l.contains("1"));

        assertTrue(l.add("2"));
        assertTrue(l.contains("2"));
    }

    @Test
    void AddNulls(){
        var l = new ArrayList<String>();

        assertFalse(l.contains(null));
        assertTrue(l.add(null));
        assertFalse(l.isEmpty());

        assertTrue(l.contains(null));
        assertFalse(l.contains("1"));

        assertTrue(l.add("2"));
        assertTrue(l.contains("2"));
    }
}
