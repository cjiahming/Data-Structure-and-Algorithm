/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

/**
 *
 * @author chany
 */
public interface ArrayListInterface<T> {
    
    public void add(T data);
    
    public boolean add(int enterPosition, T data);

public boolean replace(int givenPosition, T newEntry);

 public T remove(int enterPosition);
 
 public int getLength();
 
 public void clear();
 
 public boolean isEmpty();
 
 public boolean isFull();
 
 public boolean cArrayFull();
 
 public void moveElement(int enterPosition);
 
 public void removeElement(int enterPosition);
 
 public String toString();
 
 public T getEntry();
 
  public T getEntry(int givenPosition);
}
