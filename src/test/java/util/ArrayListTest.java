package util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
}
