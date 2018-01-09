import com.sun.javafx.scene.EnteredExitedHandler;

import java.util.*;

/**
 *
 * @param <K> key
 * @param <V> value
 *
 * @author vikash Singh
 * @date   09 Jan 2018
 * @version 1.0
 *
 *  In this HashMap generics implementation does not handle the collision resolution
 *  and HashMap is also mutable
 */

class HashMap<K extends Comparable<K>,V extends Comparable<V>>
{

  private ArrayList< Entry<K,V> > arrayList;
  private int capacity = 100;


  public HashMap()
  {
      arrayList = new ArrayList<>(capacity);
      //initialization
      for(int i =0 ;i< capacity;i++)
          arrayList.add(null);
  }
  /*
    public  boolean isEmpty()
    {
        return (arrayList.size() == 0);
    }
  */

    public  Entry<K,V> treeSearch(Entry<K,V> root ,Entry<K,V> data)
    {
        if(root == null || (root.compareTo(data) == 0))
            return root ;

        if(data.compareTo(root) < 0)
            return  treeSearch(root.getLeft(),data);
        else
            return treeSearch(root.getRight(),data);
    }


    public boolean isContain(K key , V value)
    {
        int hash = Math.abs(key.hashCode() % capacity);

        if(arrayList.get(hash) == null)
            return false;
        else
        {
            // Entry<K,V> isPresent = treeSearch(arrayList.get(hash) , new Entry<K,V>(key,value),new EntryComparator<>());
            //refactor here for the arguments
            Entry<K,V> isKeyPresent = treeSearch(arrayList.get(hash) , new Entry<>(key,value));

            if(isKeyPresent == null)
                return false;
            else
                return true;
        }

    }

    public  Entry<K ,V > treeInsertion(Entry<K,V> root, Entry<K,V> data)
    {
        if(root == null)
        {
            return data;
        }

        if(data.compareTo(root) < 0)
        {
            root.setLeft(treeInsertion(root.getLeft(),data));
        }
        else if(data.compareTo(root) > 0)
        {
            root.setRight(treeInsertion(root.getRight(),data));
        }

        return root;
    }

   public  void add(K key,V value)throws Exception
   {
       int hash = Math.abs(key.hashCode() % capacity);
       //handle the duplicate exception here
       if(!isContain(key,value)) {
           Entry<K,V> root = treeInsertion(arrayList.get(hash),new Entry<>(key, value));
           arrayList.add(hash, root);
       }
       else {
           throw new Exception("Duplicate key");
       }

   }



   //searching the object in the Binary tree
  /*
   public  Entry<K,V> treeSearch(Entry<K,V> root ,Entry<K,V> data, Comparator<Entry<K,V>>comparator)
   {
       if(root == null || (comparator.compare(root,data) == 0))
           return root ;

       if(comparator.compare(data,root) < 0)
          return  treeSearch(root.getLeft(),data,comparator);
       else
          return treeSearch(root.getRight(),data,comparator);
   }
*/

  /*

   public  void remove(K key) throws Exception
   {
     if(isContain(key))
           arrayList.remove(key.hashCode()); //remove with help of index
       else
           throw  new IllegalAccessException("Key not present");

   }
   */


}

//this should be hash map inner  public class (refactor)

class  Entry<K extends Comparable<K>,V extends Comparable<V>> implements Comparable< Entry<K,V> >
{

    private K key;
    private V value;
    private Entry<K,V> left;
    private Entry<K,V> right;

    public Entry(K key, V value)
    {
        this.key    = key;
        this.value  = value;
        this.left   = null;
        this.right  = null;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public Entry<K,V> getLeft() {
        return this.left;
    }

    public Entry<K, V> getRight() {
        return right;
    }

    public void setLeft(Entry<K, V> left) {
        this.left = left;
    }

    public void setRight(Entry<K, V> right) {
        this.right = right;
    }

    @Override

    public int compareTo(Entry<K,V> obj){
        return obj.getValue().compareTo(this.getValue());
    }

}


/*
class EntryComparator<K extends Comparable<K>,V extends Comparable<V> > implements Comparator<Entry<K,V>>
{
    @Override
    public int compare(Entry<K,V> obj1 , Entry<K,V> obj2)
    {
        return  obj1.getKey().compareTo(obj2.getKey());
    }
}
*/

public class Generics {

    public static void main(String args[])throws Exception
    {
        HashMap<String,String> mis = new HashMap<>();
        mis.add("vikash" , "Singh");
        mis.add("vikash" , "kumar");
        mis.add("Ironman" , "2");


    }
}
