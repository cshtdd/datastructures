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
    void DefaultCapacity() {
        var l = new ArrayList<>();

        assertEquals(10, l.capacity());
    }

    @Test
    void CapacityCanBeDefined() {
        var l = new ArrayList<>(1024);

        assertEquals(1024, l.capacity());
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

    @Test
    void CanAddAPracticallyInfiniteNumberOfElements(){
        var l = new ArrayList<Integer>();

        int start = 10;
        int end = 10000;

        for (int i = start; i <= end; i++) {
            assertTrue(l.add(i));

            var expectedSize = 1 + i - start;
            assertEquals(expectedSize, l.size());
        }

        for (int i = start; i <= end; i++) {
            assertTrue(l.contains(i));
        }
    }

    @Test
    void CapacityIsDoubled() {
        var l = new ArrayList<Integer>(0);

        assertEquals(0, l.size());
        assertEquals(0, l.capacity());

        assertTrue(l.add(1));
        assertEquals(1, l.size());
        assertEquals(2, l.capacity());

        assertTrue(l.add(1));
        assertEquals(2, l.size());
        assertEquals(2, l.capacity());

        assertTrue(l.add(1));
        assertEquals(3, l.size());
        assertEquals(4, l.capacity());

        assertTrue(l.add(1));
        assertTrue(l.add(1));
        assertEquals(5, l.size());
        assertEquals(8, l.capacity());
    }
}
