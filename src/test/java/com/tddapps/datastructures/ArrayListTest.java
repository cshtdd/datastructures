package com.tddapps.datastructures;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
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
    void ThrowsAfterIteratingAndModifyingACollectionOfOneElement(){
        var l = new ArrayList<>();
        l.add("1");

        assertThrows(ConcurrentModificationException.class, () -> l.forEach(i -> l.add("1")));
    }

    @Test
    void CannotRemoveWhileIterating(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.remove("1"));
        });
    }

    @Test
    void CanRemoveWhileIteratingWhenElementsAreNotFound(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        l.forEach(i -> l.remove("aaa"));
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
    void CannotRemoveAllWhileIterating(){
        var l2 = new ArrayList<>();
        l2.add("1");

        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.removeAll(l2));
        });
    }

    @Test
    void CanRemoveAllAnEmptyListWhileIterating(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        l.forEach(i -> l.removeAll(new ArrayList<>()));
    }

    @Test
    void CannotRetainAllWhileIterating(){
        var l2 = new ArrayList<>();
        l2.add("1");

        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i -> l.retainAll(l2));
        });
    }

    @Test
    void CanRetainAllElementsWhileIterating(){
        var l2 = new ArrayList<>();
        l2.add("1");
        l2.add("2");

        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        l.forEach(i -> l.retainAll(l2));
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

    @Test
    void RemoveReturnsFalseWhenElementNotFound(){
        var l = new ArrayList<String>();

        assertFalse(l.remove("1"));
        assertFalse(l.remove(null));

        l.add("2");

        assertFalse(l.remove("1"));
        assertFalse(l.remove(null));
    }

    @Test
    void RemoveDeletesTheFirstOccurrenceOfTheSpecifiedElement(){
        var l = new ArrayList<String>();
        l.add("1");
        l.add(null);
        l.add("2");
        l.add("3");
        l.add("2");
        l.add("1");

        assertEquals(6, l.size());
        assertArrayEquals(new String[]{ "1", null, "2", "3", "2", "1" }, l.toArray());


        assertTrue(l.remove("2"));
        assertArrayEquals(new String[]{ "1", null, "3", "2", "1" }, l.toArray());


        assertTrue(l.remove(null));
        assertArrayEquals(new String[]{ "1", "3", "2", "1" }, l.toArray());


        assertTrue(l.remove("1"));
        assertTrue(l.remove("1"));
        assertArrayEquals(new String[]{ "3", "2" }, l.toArray());
    }

    @Test
    void RemoveReducesCapacityInHalfWhenTheListIsLessThanHalfFull(){
        var l = new ArrayList<String>(4);
        l.add("1");
        l.add("2");
        l.add("3");

        assertEquals(3, l.size());
        assertEquals(4, l.capacity());

        assertTrue(l.remove("1"));
        assertEquals(2, l.size());
        assertEquals(4, l.capacity());

        assertTrue(l.remove("3"));
        assertEquals(1, l.size());
        assertEquals(2, l.capacity());
    }

    @Test
    void RemoveAllReturnsFalseWhenEmpty(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(2);

        var l = new ArrayList<Integer>();
        assertFalse(l.removeAll(l2));
    }

    @Test
    void RemoveAllReturnsFalseWhenNoElementsProvided(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);

        assertFalse(l.removeAll(new ArrayList<Integer>()));
        assertEquals(2, l.size());
    }

    @Test
    void RemoveAllReturnsFalseWhenNoElementsRemoved(){
        var l2 = new ArrayList<Integer>();
        l2.add(3);

        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);

        assertFalse(l.removeAll(l2));
        assertEquals(2, l.size());
    }

    @Test
    void RemoveAllDeletesAllOccurrencesOfEveryMatchingElement(){
        var l2 = new ArrayList<Integer>();
        l2.add(2);
        l2.add(null);

        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(2);
        l.add(null);
        l.add(3);
        l.add(null);

        assertTrue(l.removeAll(l2));

        assertArrayEquals(new Integer[] { 1, 3 }, l.toArray());
    }

    @Test
    void RemoveAllReducesCapacityOnlyOnce(){
        var l2 = new ArrayList<String>();
        l2.add("x");
        l2.add("z");
        l2.add("x");

        var l = new ArrayList<String>(8);
        l.add("y");
        l.add("x");
        l.add("x");
        l.add("x");
        l.add("x");

        assertTrue(l.removeAll(l2));

        assertArrayEquals(new String[] { "y" }, l.toArray());
        assertEquals(4, l.capacity());
    }

    @Test
    void RetainAllReturnsFalseWhenEmpty(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(2);

        var l = new ArrayList<Integer>();
        assertFalse(l.retainAll(l2));
    }

    @Test
    void RetainAllClearsTheListWhenNoElementsProvided(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);

        assertTrue(l.retainAll(new ArrayList<Integer>()));
        assertEquals(0, l.size());
    }

    @Test
    void RetainAllReturnsFalseWhenNoElementsRemoved(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(2);

        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(2);
        l.add(2);

        assertFalse(l.retainAll(l2));
        assertEquals(4, l.size());
    }

    @Test
    void RetainAllRemovesAllNonMatchingElements(){
        var l2 = new ArrayList<Integer>();
        l2.add(1);
        l2.add(1);
        l2.add(null);

        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(2);
        l.add(null);
        l.add(1);
        l.add(null);

        assertTrue(l.retainAll(l2));

        assertArrayEquals(new Integer[] { 1, null, 1, null }, l.toArray());
    }

    @Test
    void RetainAllReducesCapacityOnlyOnce(){
        var l2 = new ArrayList<String>();
        l2.add("y");

        var l = new ArrayList<String>(8);
        l.add("y");
        l.add("x");
        l.add("x");
        l.add("x");
        l.add("x");

        assertTrue(l.retainAll(l2));

        assertArrayEquals(new String[] { "y" }, l.toArray());
        assertEquals(4, l.capacity());
    }

    @Test
    void ListWithTheSameElementsInTheSameOrderAreEqualEvenWhenCapacitiesDiffer(){
        int x = 2;
        int y = 3;
        int z = 4;

        var l1 = new ArrayList<>(100);
        var l2 = new ArrayList<>(1000);
        var l3 = new ArrayList<>();
        assertEquals(l1, l2);
        assertEquals(l2, l3);
        assertEquals(l1.hashCode(), l2.hashCode());
        assertEquals(l2.hashCode(), l3.hashCode());

        l1.add(x);
        l1.add(y);
        l1.add(z);

        l2.add(x);
        l2.add(y);
        l2.add(z);

        l3.add(z);
        l3.add(y);
        l3.add(x);

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());

        assertNotEquals(l2, l3);
        assertNotEquals(l2.hashCode(), l3.hashCode());
    }

    @Test
    void IsNotThreadSafeByDefault() throws InterruptedException {
        var l1 = new ArrayList<Integer>();
        for (int i = 0; i < 100000; i++) {
            l1.add(i);
        }
        int threadCount = 3;
        int expectedSize = threadCount * l1.size();

        var l = new ArrayList<Integer>();
        com.tddapps.datastructures.ArrayListTest.addAllMultiThreaded(l1, l, threadCount);
        assertNotEquals(expectedSize, l.size());

        var threadSafeList = Collections.synchronizedCollection(new ArrayList<Integer>());
        com.tddapps.datastructures.ArrayListTest.addAllMultiThreaded(l1, threadSafeList, threadCount);
        assertEquals(expectedSize, threadSafeList.size());
    }

    public static <T> void addAllMultiThreaded(Collection<T> src, Collection<T> dest, int threadCount) throws InterruptedException {
        var threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> assertTrue(dest.addAll(src)));
        }

        for (var t : threads){
            t.start();
        }

        for (var t : threads){
            t.join(10000);
        }
    }

    @Test
    void GetReadsTheElementAtTheSpecifiedPosition(){
        var l = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }};

        assertEquals(1, l.get(0));
        assertEquals(2, l.get(1));
    }

    @Test
    void GetThrowsIndexOutOfBoundsExceptionWhenPositionIsInvalid(){
        var l = new ArrayList<Integer>();
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(0));

        l.add(1);
        l.add(2);
        l.add(3);

        assertThrows(IndexOutOfBoundsException.class, () -> l.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(3));
    }

    @Test
    void SetChangesTheElementAtTheSpecifiedPosition(){
        var l = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }};

        assertEquals(1, l.set(0, 10));
        assertEquals(10, l.get(0));
    }

    @Test
    void SetChangesTheElementAtTheSpecifiedPositionEvenWhenThePreviousValueWasNull(){
        var l = new ArrayList<>(){{
            add(1);
            add(null);
            add(3);
        }};

        assertNull(l.set(1, 20));
        assertEquals(20, l.get(1));
    }

    @Test
    void SetThrowsIndexOutOfBoundsExceptionWhenPositionIsInvalid(){
        var l = new ArrayList<Integer>();
        assertThrows(IndexOutOfBoundsException.class, () -> l.set(0, null));

        l.add(1);
        l.add(2);
        l.add(3);

        assertThrows(IndexOutOfBoundsException.class, () -> l.set(-1, null));
        assertThrows(IndexOutOfBoundsException.class, () -> l.set(3, null));
    }

    @Test
    void AddsElementAtAPosition(){
        var l = new ArrayList<String>();
        l.add(0, "a");
        l.add(0, "b");
        l.add(0, "c");

        assertArrayEquals(new String[]{"c", "b", "a"}, l.toArray());
    }

    @Test
    void AddThrowsIndexOutOfBoundsWhenPositionIsInvalid(){
        var l = new ArrayList<String>();

        assertThrows(IndexOutOfBoundsException.class, () -> l.add(-1, "4"));
        assertThrows(IndexOutOfBoundsException.class, () -> l.add(1, "4"));
    }
}
