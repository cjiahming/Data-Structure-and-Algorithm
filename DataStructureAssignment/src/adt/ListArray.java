/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;


/**
 *
 * @author User
 */
public class ListArray<T> implements ListArrayInterface<T> {
    
    private T[] array;
    private int count; //size of array
    private static final int DEFAULT_CAPACITY = 100;

    double total = 0;

    public ListArray() {
        this(DEFAULT_CAPACITY);
    }

    public ListArray(int initialCapacity) {
        count = 0;
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(T newInput) {

        array[count] = newInput;
        count++;
        return true;
    }
    
    @Override
    public boolean replace(int num, T newInput) {
        boolean successful = true;

        if ((num >= 1) && (num <= count)) {
            array[num - 1] = newInput;
        } else {
            successful = false;
        }

        return successful;
    }

    @Override
    public T getArray(int num) {
        T output = null;
        for (int index = 0; (index < count); index++) {
            if ((num > 0) && (num <= count)) {
                output = array[num - 1];
            }
            if (num == count) {
                output = array[num - 1];
            }
        }
        return output;
    }

    @Override
    public int sizeOfList() {
        int size = 0;
        size = count;
        return size;
    }

    @Override
    public boolean empty() {
        boolean found = false;
        if (count == 0) {
            found = true;
        }

        return found;
    }
    
    @Override
    public T remove(int num) {
        T result = null;

        if ((num >= 1) && (num <= count)) {
            result = array[num - 1];

            if (num < count) {
                //removeGap(givenNum);
                for (int i = num - 1; i < count - 1; i++) {
                    array[i] = array[i + 1];
                }
            }
            count--;
        }
        return result;
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < count; ++i) {
            output += array[i] + "\n";
        }

        return output;
    }
}
