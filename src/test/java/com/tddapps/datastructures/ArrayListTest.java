package com.tddapps.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArrayListTest {
    @Test
    void CanBeConstructed() {
        assertNotNull(new ArrayList<String>());
    }
}
