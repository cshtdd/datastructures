package com.tddapps.datastructures;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;

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

    @Test()
    void CapacityCannotBeNegative(){
        assertThrows(IllegalArgumentException.class, ()->{
            new ArrayList<>(-1);
        });
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
        assertEquals(1, l.capacity());

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

    @Test
    void CannotIterateThroughAnEmptyList(){
        final int[] count = {0};

        new ArrayList<String>().forEach(s -> count[0] += 1);

        assertEquals(0, count[0]);
    }

    @Test
    void CanIterate(){
        final int[] count = {0};
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");
        l.add("3");
        l.add("4");

        var elements = new String[4];
        l.forEach(s -> {
            elements[count[0]] = (String)s;
            count[0] += 1;
        });

        assertEquals("1", elements[0]);
        assertEquals("2", elements[1]);
        assertEquals("3", elements[2]);
        assertEquals("4", elements[3]);
    }

    @Test
    void CannotAddWhileIterating(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.add("1"));
        });
    }

    @Test
    void CannotAddAllWhileIterating(){
        var l2 = new ArrayList<>();
        l2.add("1");

        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.addAll(l2));
        });
    }

    @Test
    void CannotAddAllAnEmptyListWhileIterating(){
        var l2 = new ArrayList<>();

        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.addAll(l2));
        });
    }

    @Test
    void CannotClearWhileIterating(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.clear());
        });
    }

    @Test
    void toArrayReturnsAnArray(){
        var l = new ArrayList<Integer>(1000);
        var expected = new Object[5];
        for (int i = 0; i < 5; i++) {
            l.add(i);
            expected[i] = i;
        }

        assertArrayEquals(expected, l.toArray());
    }

    @Test
    void toArrayReturnsAnEmptyArrayWhenEmpty(){
        var l = new ArrayList<Integer>();

        assertArrayEquals(new Integer[0], l.toArray());
    }

    @Test
    void toArrayCopiesElementsToTheSuppliedArrayWhenThereIsEnoughCapacity(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);

        var buffer = new Integer[6];
        assertArrayEquals(new Integer[]{ null, null, null, null, null, null }, buffer);


        var result = l.toArray(buffer);


        assertArrayEquals(new Integer[]{ 1, 2, 3, 4, null, null }, buffer);
        assertArrayEquals(new Integer[]{ 1, 2, 3, 4, null, null }, result);
    }

    @Test
    void toArrayDoesNotCopyWhenEmpty(){
        var l = new ArrayList<Integer>();


        var buffer = new Integer[6];
        assertArrayEquals(new Integer[]{ null, null, null, null, null, null }, buffer);


        var result = l.toArray(buffer);


        assertArrayEquals(new Integer[]{ null, null, null, null, null, null }, buffer);
        assertArrayEquals(new Integer[]{ null, null, null, null, null, null }, result);
    }

    @Test
    void toArrayCreatesANewArrayWhenThereIsNotEnoughCapacity(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);

        var buffer = new Integer[2];
        assertArrayEquals(new Integer[]{ null, null }, buffer);


        var result = l.toArray(buffer);


        assertArrayEquals(new Integer[]{ null, null }, buffer);
        assertArrayEquals(new Integer[]{ 1, 2, 3, 4 }, result);
    }

    @Test
    void toArrayFailsWhenTypesDoNotMatch(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);

        assertThrows(ArrayStoreException.class, () -> {
            l.toArray(new Long[20]);
        });
    }

    @Test
    void ClearsTheList(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);

        l.clear();

        assertEquals(0, l.size());
        assertTrue(l.isEmpty());
    }

    @Test
    void AddsAllTheElements(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(null);
        l2.add(2);
        l2.add(3);
        assertArrayEquals(new Integer[]{1, null, 2, 3}, l2.toArray());

        var l = new ArrayList<Integer>();
        assertTrue(l.addAll(l2));
        assertArrayEquals(new Integer[]{1, null, 2, 3}, l.toArray());
    }

    @Test
    void AddAllAllocatesCapacityOnce(){
        var l2 = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            l2.add(i);
        }

        var l = new ArrayList<Integer>(5);
        l.addAll(l2);

        assertEquals(100, l.capacity());
    }

    @Test
    void AddAllReturnsFalseWhenNewListIsEmpty(){
        var l = new ArrayList<Integer>();

        assertFalse(l.addAll(new ArrayList<>()));
    }

    @Test
    void ContainsAllReturnsFalseWhenEmpty(){
        var l2 = new ArrayList<Integer>();
        l2.add(2);

        var l = new ArrayList<Integer>();
        assertFalse(l.containsAll(l2));
    }

    @Test
    void ContainsAllReturnsTrueWhenParametersIsEmpty(){
        var l2 = new ArrayList<Integer>();

        var l = new ArrayList<Integer>();
        l.add(2);

        assertTrue(l.containsAll(l2));
    }

    @Test
    void ContainsAllReturnsTrueWhenAllElementsAreFound(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(2);
        l2.add(null);

        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        l.add(null);

        assertTrue(l.containsAll(l2));
    }

    @Test
    void ContainsAllReturnsFalseWhenAtLeastOneIsNotFound(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(2);
        l2.add(null);

        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);

        assertFalse(l.containsAll(l2));
    }
}
