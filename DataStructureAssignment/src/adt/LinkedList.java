package adt;

import Entity.Medicine;
import Entity.Symptom;

/**
 * LinkedList.java A class that implements the ADT list by using a chain of nodes,
 with the node implemented as an inner class. test
 */
public class LinkedList<T> implements ListInterface<T> {

  private Node firstNode; // reference to first node
  private Node lastNode; 
  private int numberOfElements;  	// number of entries in list

  public LinkedList() {
    clear();
  }

  @Override
  public int clear() {
        firstNode = null;
        int result = numberOfElements;
        numberOfElements = 0;
        return result;
    }
  

  @Override
  public boolean add(T newElement) {
    Node newNode = new Node(newElement);	// create the new node

        if(numberOfElements == 0){
            firstNode = newNode;
            lastNode = newNode;
        }else{
            lastNode.next = newNode;
            lastNode = newNode;
        }
        numberOfElements++;
        return true;
  }

  @Override
  public boolean add(int position, T newElement) { // OutOfMemoryError possible
    Node newNode = new Node(newElement);
        if(position <= 1){
            if(numberOfElements == 0){
                firstNode = newNode;
                lastNode = newNode;
            }else{
                newNode.next = firstNode;
                firstNode = newNode;
            }
            numberOfElements++;
            return true;
        }else if(position > 1 && position <= numberOfElements){
            Node previousNode = firstNode;
            for (int i = 1; i < position - 1; i++) {
                previousNode = previousNode.next;
            }
            newNode.next = previousNode.next;
            previousNode.next = newNode;
            numberOfElements++;
            return true;
        }else{
            add(newElement);
            return true;
        }
  }

  @Override
  public T remove(int position) {
     if(position == 1){
            if(numberOfElements >= 1){
                T result = firstNode.data;
                firstNode = firstNode.next;
                numberOfElements--;
                return result;
            }else{
                return null;
            }
        }else if (position > 1 && position <= numberOfElements){
            Node previousNode = firstNode;
            for (int i = 1; i < position - 1; i++) {
                previousNode = previousNode.next;
            }
            T result = previousNode.next.data;
            previousNode.next = previousNode.next.next;
            if(position == numberOfElements){
                lastNode = previousNode;
            }
            numberOfElements--;
            return result;
        }else{
            return null;
        }
  }
  
  

  @Override
  public boolean replace(int givenPosition, T newEntry) {
    boolean isSuccessful = true;

    if ((givenPosition >= 1) && (givenPosition <= numberOfElements)) {
      Node currentNode = firstNode;
      for (int i = 0; i < givenPosition - 1; ++i) {
        currentNode = currentNode.next;		// advance currentNode to next node
      }
      currentNode.data = newEntry;	// currentNode is pointing to the node at givenPosition
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }

  @Override
  public T get(int position) {
        T element;
        if(position < 1 || position > numberOfElements){
            element = null;
        }else if(position == 1){
            element = firstNode.data;
        }else if(position == numberOfElements){
            element = lastNode.data;
        }else{
            Node currentNode = firstNode;
            for (int i = 1; i < position; i++) {
                currentNode = currentNode.next;
            }
            element = currentNode.data;
        }
        return element;
    }

  @Override
  public boolean contains(T element) {
    boolean found = false;
    Node currentNode = firstNode;

    while (!found && (currentNode != null)) {
      if (element.equals(currentNode.data)) {
        found = true;
      } else {
        currentNode = currentNode.next;
      }
    }
    return found;
  }

  @Override
  public int size() {
        return numberOfElements;
    }

  @Override
  public boolean isEmpty() {
   return numberOfElements == 0;


  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public String toString() {
    String result = "";
        Node currentNode = firstNode;
        for (int i = 1; i <= numberOfElements; i++) {
            result += currentNode.data + "\n";
            currentNode = currentNode.next;
        }
        return result;
  }

 

  private class Node {

    private T data;
    private Node next;

    private Node(T data) {
      this.data = data;
      this.next = null;
    }

    private Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

}
