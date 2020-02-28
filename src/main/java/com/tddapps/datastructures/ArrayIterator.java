package com.tddapps.datastructures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Supplier;

class ArrayIterator<T> implements Iterator<T> {
    private final int size;
    private final Object[] data;
    private final long initialState;
    private final Supplier<Long> stateReader;
    private int index = 0;

    public ArrayIterator(Object[] data, int size){
        this(data, size, null);
    }

    public ArrayIterator(Object[] data, int size, Supplier<Long> stateReader){
        this.data = data;
        this.size = size;
        this.stateReader = stateReader;
        initialState = readState();
    }

    @Override
    public boolean hasNext() {
        validateState();
        return index < size;
    }

    @Override
    public T next() {
        validateState();
        return (T)data[index++];
    }

    private void validateState(){
        long currentState = readState();
        if (initialState != currentState){
            throw new ConcurrentModificationException();
        }
    }

    private long readState(){
        if (stateReader == null){
            return 0;
        }

        return stateReader.get();
    }
}
