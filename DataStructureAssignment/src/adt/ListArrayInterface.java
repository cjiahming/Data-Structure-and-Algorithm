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
public interface ListArrayInterface<T> {
    
    public boolean add(T newInput);
    
    public T getArray(int num);

    public boolean replace(int num, T newInput);

    public boolean empty();

    public int sizeOfList();
    
    public T remove(int num);

}
