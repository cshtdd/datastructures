package com.tddapps.datastructures;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class ArrayList<T> implements Collection<T> {
    static final int DEFAULT_CAPACITY = 10;

    private int size = 0;
    private Object[] data;

    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity){
        if (initialCapacity < 0){
            throw new IllegalArgumentException("initialCapacity cannot be negative");
        }

        data = new Object[initialCapacity];
    }

    public int capacity(){
        return data.length;
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
        if (size == capacity()){
            doubleCapacity();
        }

        data[size++] = t;

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
        return new ArrayIterator<>(data, size);
    }

    @Override
    public Object[] toArray() {
        var result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        var result = a;

        if (a.length < size){
            result = (T1[])Array.newInstance(a.getClass().getComponentType(), size);
        }

        System.arraycopy(data, 0, result, 0, size);
        return result;
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
        int currentCapacity = capacity();
        int updateCapacity = Math.max(2, currentCapacity << 1);
        var newData = new Object[updateCapacity];
        System.arraycopy(data, 0, newData, 0, currentCapacity);
        data = newData;
    }
}
