package com.tddapps.datastructures;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ArrayList<T> implements List<T> {
    static final int DEFAULT_CAPACITY = 10;
    private static final int NOT_FOUND = -1;

    private int size = 0;
    private long changeOperationsCount = 0;
    private Object[] data;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity cannot be negative");
        }

        data = new Object[initialCapacity];
    }

    int capacity() {
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
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public boolean add(T t) {
        trackModification();

        if (isFull()) {
            doubleCapacity();
        }

        data[size++] = t;

        return true;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        trackModification();
        int additionsCount = c.size();

        if (additionsCount == 0) {
            return false;
        }

        if (isFull(additionsCount)) {
            growCapacityToFit(additionsCount);
        }

        for (T i : c) {
            data[size++] = i;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != NOT_FOUND;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (containsObjectAt(o, i)) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (isEmpty()) {
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
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
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

        if (a.length < size) {
            result = (T1[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        var index = indexOf(o);

        if (index == NOT_FOUND) {
            return false;
        }

        trackModification();

        shiftLeftAt(index);
        size--;

        if (isLessThanHalfFull()) {
            halveCapacity();
        }

        return true;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var indexesToRemove = new ArrayList<Integer>(size);

        for (Object o : c) {
            for (int i = 0; i < size; i++) {
                if (containsObjectAt(o, i)) {
                    indexesToRemove.add(i);
                }
            }
        }

        if (indexesToRemove.isEmpty()) {
            return false;
        }

        trackModification();

        var indexes = indexesToRemove
                .stream()
                .distinct()
                .sorted()
                .toArray(Integer[]::new);

        for (int i = indexes.length - 1; i >= 0; i--) {
            var index = indexes[i];
            shiftLeftAt(index);
            size--;
        }

        if (isLessThanHalfFull()) {
            halveCapacity();
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        var indexesToPreserve = new ArrayList<Integer>(size);

        for (Object o : c) {
            for (int i = 0; i < size; i++) {
                if (containsObjectAt(o, i)) {
                    indexesToPreserve.add(i);
                }
            }
        }

        var indexes = indexesToPreserve
                .stream()
                .distinct()
                .sorted()
                .toArray(Integer[]::new);

        if (indexes.length == size) {
            return false;
        }

        trackModification();

        for (int i = 0; i < indexes.length - 1; i++) {
            var currIdx = indexes[i];
            var nextIdx = indexes[i + 1];
            var diff = nextIdx - currIdx;
            if (diff > 1) {
                var shiftStartIndex = currIdx + 1;
                var shiftSize = diff - 1;
                shiftLeftAt(shiftStartIndex, shiftSize);
                decrementBy(indexes, shiftSize);
            }
        }

        size = indexes.length;

        if (isLessThanHalfFull()) {
            halveCapacity();
        }

        return true;
    }

    @Override
    public void clear() {
        trackModification();
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var that = (ArrayList<T>)o;

        if (this.size != that.size) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            var thisElem = this.data[i];
            var thatElem = that.data[i];

            if (thisElem == null) {
                if (thatElem != null) {
                    return false;
                }
            } else {
                if (!thisElem.equals(thatElem)){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 7 * Objects.hash(size);

        for (int i = 0; i < size; i++) {
            int elementHash = 0;
            if (data[i] != null) {
                elementHash = data[i].hashCode();
            }

            result = 31 * result + elementHash;
        }

        return result;
    }

    private boolean isFull() {
        return isFull(1);
    }

    private boolean isFull(int additionalCount) {
        return available() < additionalCount;
    }

    private boolean isLessThanHalfFull() {
        return available() > size;
    }

    private void growCapacityToFit(int additionalCount) {
        changeCapacity(additionalCount - available());
    }

    private void doubleCapacity() {
        changeCapacity(Math.max(1, capacity()));
    }

    private void halveCapacity() {
        int half = capacity() >> 1;
        changeCapacity(-half);
    }

    private void changeCapacity(int delta) {
        var newData = new Object[capacity() + delta];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private boolean containsObjectAt(Object o, int index) {
        var e = data[index];
        return (o == null && e == null) ||
                (e != null && e.equals(o));
    }

    private void decrementBy(Integer[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= value;
        }
    }

    private void shiftLeftAt(int index) {
        shiftLeftAt(index, 1);
    }

    private void shiftLeftAt(int index, int count) {
        for (int i = index + count; i < size; i++) {
            data[i - count] = data[i];
        }
    }

    private void trackModification() {
        changeOperationsCount++;
    }
}
