
package adt;


import java.util.Iterator;


/**
 *
 * @author Gab
 */
public class DynamicQueue<T> implements DynamicQueueInterface <T> {
    
    private T[] array;
    private int numberOfElements;
    private int frontIndex;
    private int backIndex;
    private static final int DEFAULT_CAPACITY = 10;

    public DynamicQueue(){
        this(DEFAULT_CAPACITY);
//        frontIndex = 0;
    }
    
    public DynamicQueue(int initialCapacity){
        array = (T[]) new Object[initialCapacity];
        backIndex = -1;
        frontIndex = 0;
    }

   
    
     @Override
    public boolean enqueue(T newEntry) {
        for(int i=0; i<numberOfElements;i++){
            if(array[i].equals(newEntry)){
                return false;
            }
        }
        if(isArrayFull()){
            if(((backIndex+1)-frontIndex)== array.length){
                extendQueue();
            }else{
                shiftQueue();
            }
            backIndex++;
            numberOfElements++;
            array[backIndex] = newEntry;
            
        }
        else{
            backIndex = backIndex + 1;
            numberOfElements++;
            array[backIndex] = newEntry;
            return true;
        }
        return false;
    }


    @Override
    public T dequeue() {
        if(isEmpty()){
            return null;
        }else{
            T result = array[frontIndex];
            frontIndex++;
            numberOfElements--;
            return result;
        }
    }

    @Override
    public T getFront() {
        T front = null;
        if(!isEmpty()){
            front = array[frontIndex];
        }
        return front;
    }
    
    @Override
    public int size()
    {
       return numberOfElements;
    }
    
    @Override
    public T get(int index){
        for(int i=0; i<numberOfElements; i++){
            if(i == index){
                T element = (T) array[i];
                return element;
            }
        }
        return null;
    }


    @Override
    public boolean replace(int index,T newEntry) {
           
      if(index<numberOfElements && array[index]!=null){
          array[index]=newEntry;
          return true;
        
       }
        return false;
        
        
   }
    


    @Override
    public T removeAt(int position) {
       
        
        if(position>numberOfElements+1 ||position<=0){
            return null;
        }else{
            int index = position-1;
            T entry = array[index];
            
            for(int i = index;i<backIndex;i++){
                array[i] = array[i+1];
            }
            array[backIndex] = null;
            backIndex -= 1;
            numberOfElements -- ;
            return entry;
        }
    }

    @Override
    public void clear() {
        array = (T[]) new Object[array.length];
        backIndex = -1;
        frontIndex = 0;
    }

    @Override
    public boolean isEmpty() {
        return backIndex < frontIndex;
    }



    @Override
    public Iterator<T> getIterator() {
        return new DynamicFrontIterator();
    }

    
     private class DynamicFrontIterator implements Iterator<T> {
        private int nextIndex;
        
        private DynamicFrontIterator(){
            nextIndex = 0 ;
        }

        @Override
        public boolean hasNext() {
            return nextIndex <= backIndex;
        }

        @Override
        public T next() {
            if(hasNext()){
                T nextEntry = array[nextIndex++];
                return nextEntry;
            }
            else{
                return null;
            }
        }

    }

    private boolean isArrayFull() {
       return backIndex == (array.length);
    }

    private void extendQueue() {
        int newCapacity = array.length * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        int index;
        for(index = frontIndex;index<=backIndex;index++){
            newArray[index-frontIndex] = array[index];
        }
        array = newArray;
    }

    private void shiftQueue() {
       int index;
       T[] tempQueue = (T[]) new Object[array.length];
       for (index = frontIndex;index<=backIndex;index++){
           tempQueue[index-frontIndex] = array[index];
        }
       array = tempQueue;
       frontIndex = 0;
       backIndex = index;
    }
    
}
