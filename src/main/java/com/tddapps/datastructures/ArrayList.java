package com.tddapps.datastructures;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class ArrayList<T> implements Collection<T> {
    static final int DEFAULT_CAPACITY = 10;
    private static final int NOT_FOUND = -1;

    private int size = 0;
    private long changeOperationsCount = 0;
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

    private int available() {
        return capacity() - size;
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
        trackModification();

        if (isFull()){
            doubleCapacity();
        }

        data[size++] = t;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        trackModification();
        int additionsCount = c.size();

        if (additionsCount == 0){
            return false;
        }

        if (isFull(additionsCount)){
            growCapacityToFit(additionsCount);
        }

        for (T i : c) {
            data[size++] = i;
        }

        return true;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != NOT_FOUND;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (isEmpty()){
            return false;
        }

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(data, size, () -> changeOperationsCount);
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
        var index = indexOf(o);

        if (index == NOT_FOUND){
            return false;
        }

        trackModification();

        for(int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;

        if (isLessThanHalfFull()){
            halveCapacity();
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var indexesToRemove = new ArrayList<Integer>(size);

        for (Object o : c){
            for (int i = 0; i < size; i++) {
                if (containsObjectAt(o, i)){
                    indexesToRemove.add(i);
                }
            }
        }

        var indexes = indexesToRemove
                .stream()
                .distinct()
                .sorted()
                .toArray(Integer[]::new);

        var newData = new Object[size - indexes.length];
        int bufferIndex = 0;
        for (int i = 0; i < size; i++) {
            if (!binarySearch(indexes, i)){
                newData[bufferIndex] = data[i];
                bufferIndex++;
            }
        }

        data = newData;
        size = newData.length;

        return indexes.length > 0;
    }

    private boolean binarySearch(Integer[] arr, Integer e){
//        TODO: faster implementation
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == e){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        trackModification();
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private boolean isFull() {
        return isFull(1);
    }

    private boolean isFull(int additionalCount){
        return available() < additionalCount;
    }

    private boolean isLessThanHalfFull() {
        return available() > size;
    }

    private void growCapacityToFit(int additionalCount){
        changeCapacity(additionalCount - available());
    }

    private void doubleCapacity() {
        changeCapacity(Math.max(1, capacity()));
    }

    private void halveCapacity(){
        int half = capacity() >> 1;
        changeCapacity(-half);
    }

    private void changeCapacity(int delta){
        var newData = new Object[capacity() + delta];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private int indexOf(Object o){
        for (int i = 0; i < size; i++) {
            if (containsObjectAt(o, i)){
                return i;
            }
        }

        return NOT_FOUND;
    }

    private boolean containsObjectAt(Object o, int index){
        var e = data[index];
        return (o == null && e == null) ||
                (e != null && e.equals(o));
    }

    private void trackModification() {
        changeOperationsCount++;
    }
}
