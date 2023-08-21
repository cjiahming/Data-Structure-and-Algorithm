package adt;

public class ArrayList<T> implements ArrayListInterface<T> {
    
    private T[] arryList;
    private int countLength;
    private static final int ARRYLIST_SIZE = 15;

    public ArrayList() {
        this(ARRYLIST_SIZE);
    }

    public ArrayList(int initialCapacity) {
    countLength = 0;
    arryList = (T[]) new Object[initialCapacity];
  }
    
    //Add into list
    public void add(T data)
    {
        
        arryList[countLength] = data;
        countLength++;
        
    }
    
    public boolean add(int enterPosition, T data)
    {
        if((enterPosition>=1)&&(enterPosition <= countLength +1))
        {
            if(!cArrayFull())
            {
                moveElement(enterPosition);
                arryList[enterPosition-1] = data;
                countLength++;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    
    public boolean replace(int givenPosition, T newEntry) {
    boolean isSuccessful = true;

    if ((givenPosition >= 1) && (givenPosition <= countLength)) {
      arryList[givenPosition - 1] = newEntry;
    } else {
      isSuccessful = false;
    }
    return isSuccessful;
  }
    //remove
    public T remove(int enterPosition)    
    {
        T result = null;
        
        if((enterPosition >= 1) && (enterPosition <= countLength))
        {
            result = arryList[enterPosition-1];
            
            if (enterPosition<countLength)
            {
                removeElement(enterPosition);
            }
            
            countLength--;
        }
        return result;
    }
    
 

    
    
    //count the array Length
    public int getLength()
    {
        return countLength;
    }
    //clear all array 
    public void clear()
    {
        countLength = 0;
    }
    
    
    //check array is empty or not 
    public boolean isEmpty()
    {
        return countLength == 0;
    }
    
    public boolean isFull() {
    return false;
    }
     
    //check array is full or not
    public boolean cArrayFull()
    {
        return countLength == arryList.length;
    }
    
    //move all element to back
    public void moveElement(int enterPosition){
        int newIndex = enterPosition - 1;
        int lastIndex = countLength - 1;
        
        //
        for(int i = lastIndex; i >= newIndex; i--)
        {
            //copy from b to a 
            //example a position is 5 b position is 4
            //copy from 4 to 5
            //    a               b
            arryList[i + 1] = arryList[i];
        }
    }
    
    //remove the empty element
    public void removeElement(int enterPosition)
    {
        int removeIndex = enterPosition - 1;
        int lastIndex = countLength - 1;
        
        for(int i = removeIndex; i < lastIndex; i++)
        {
            //copy from b to a 
            //example a position is 4 b position is 3
            //copy from 3 to 4
            //    a               b
            arryList[i] = arryList[i + 1];
        }
    }
    
    public String toString() {
    String outputStr = "";
    for (int index = 0; index < countLength; ++index) {
      outputStr += arryList[index] + "\n";
    }

    return outputStr;
  }

    public T getEntry()
    {
        T result = null;
        
        for(int i = 0; i < countLength; i++)
        {
        result = arryList[i];
        }
        return result;
    }
    public T getEntry(int givenPosition) {
    T result = null;

    if ((givenPosition >= 1) && (givenPosition <= countLength)) {
      result = arryList[givenPosition - 1];
    }

    return result;
   }
    

    


}
