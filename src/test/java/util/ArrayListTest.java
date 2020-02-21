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
}
