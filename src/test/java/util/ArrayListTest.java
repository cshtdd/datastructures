package util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {
    @Test
    void AddNullHandling(){
        var l = new ArrayList<String>();

        assertFalse(l.contains(null));

        assertTrue(l.add(null));

        assertTrue(l.contains(null));
    }

    @Test
    void AddEmptyCapacity(){
        var l = new ArrayList<String>(0);

        assertTrue(l.add("1"));
        assertTrue(l.contains("1"));
    }

    @Test
    void ToArrayWithBuffer(){
        var l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);

        var buffer = new Integer[6];
        var result = l.toArray(buffer);
        assertArrayEquals(new Integer[]{ 1, 2, 3, 4, null, null }, buffer);
        assertArrayEquals(new Integer[]{ 1, 2, 3, 4, null, null }, result);


        buffer = new Integer[2];
        result = l.toArray(buffer);
        assertArrayEquals(new Integer[]{ null, null }, buffer);
        assertArrayEquals(new Integer[]{ 1, 2, 3, 4 }, result);

        assertThrows(ArrayStoreException.class, () -> {
            l.toArray(new Long[6]);
        });
    }

    @Test
    void DoesNotThrowAfterIteratingAndModifyingACollectionOfOneElement(){
        var l = new com.tddapps.datastructures.ArrayList<>();
        l.add("1");

        l.forEach(i -> l.add("1"));
    }

    @Test
    void CanAttemptToRemoveWhileIteratingWhenElementsAreNotFound(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        l.forEach(i -> l.remove("aaa"));
        l.forEach(i -> l.removeAll(new ArrayList<>(){{ add("aaa"); }}));
    }

    @Test
    void CanAttemptToRetainAllWhileIteratingWhenAllElementsAreKept(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        l.forEach(i -> l.retainAll(new ArrayList<>(){{
            add("1");
            add("2");
        }}));
    }

    @Test
    void CannotAddAnEmptyListDuringAnIteration(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        assertThrows(ConcurrentModificationException.class, () -> {
            l.forEach(i-> l.addAll(new ArrayList<>()));
        });
    }

    @Test
    void CanRemoveAnEmptyListDuringAnIteration(){
        var l = new ArrayList<>();
        l.add("1");
        l.add("2");

        l.forEach(i-> l.removeAll(new ArrayList<>()));
    }

    @Test
    void ContainsAllReturnsTrueWhenParametersIsEmpty(){
        var l2 = new ArrayList<Integer>();

        var l = new ArrayList<Integer>();
        l.add(2);

        assertTrue(l.containsAll(l2));
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
}
