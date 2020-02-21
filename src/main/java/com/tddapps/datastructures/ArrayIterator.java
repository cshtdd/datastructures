package com.tddapps.datastructures;

import java.util.Iterator;

class ArrayIterator<T> implements Iterator<T> {
    private final int size;
    private final Object[] data;
    private int index = 0;

    public ArrayIterator(Object[] data, int size){
        this.data = data;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public T next() {
        return (T)data[index++];
    }
}
