package com.tddapps.datastructures;

import java.util.Collection;
import java.util.Iterator;

public class ArrayList<T> implements Collection<T> {
    static final int DEFAULT_CAPACITY = 100;

    private int size = 0;
    private int capacity = DEFAULT_CAPACITY;
    private Object[] data = new Object[capacity];

    public int capacity(){
        return capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T t) {
        data[size++] = t;

        if (size == capacity){
            doubleCapacity();
        }

        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            var e = data[i];
            if ((o == null && e == null) ||
                (e != null && e.equals(o))){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private void doubleCapacity() {
        var newCapacity = capacity << 1;
        var newData = new Object[newCapacity];
        for (int i = 0; i < capacity; i++) {
            newData[i] = data[i];
        }
        data = newData;
        capacity = newCapacity;
    }
}
