/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;
import java.util.Iterator;
/**
 *
 * @author Gab
 */
public interface DynamicQueueInterface<T> {
    public boolean enqueue(T newEntry);
    public T dequeue();
    public T getFront();
    public int size();
    public T get(int index);
    public boolean replace(int Position,T newEntry);
    public Iterator<T> getIterator();
     public T removeAt(int position);
     public void clear();
    public boolean isEmpty(); 
}
