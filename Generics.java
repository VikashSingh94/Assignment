
import java.util.*;

/**
 *
 * @param <K> key
 * @param <V> value
 *
 * @author vikash Singh
 * @date   09 Jan 2018
 * @version 1.1
 *
 *  In this HashMap generics implementation handle the collision resolution
 *  and HashMap is also mutable
 *
 *  HashMap use the combination of ArrayList and Binary tree data structure
 *  that improve the time complexity as compared to ArrayList and Linked list implementation.
 */

class HashMap<K extends Comparable<K>,V extends Comparable<V>>
{

    private ArrayList< Entry<K,V> > arrayList; //ArrayList storing the Tree object
    private int capacity = (1 << 4); //aka 16 ,Default size

    public HashMap()
    {
        arrayList = new ArrayList<>(capacity);
        //initialization
        for(int i =0 ;i< capacity;i++)
          arrayList.add(null);
    }


    public  Entry<K,V> treeSearch(Entry<K,V> root ,K key)
    {
        if(root == null || (root.getKey().compareTo(key) == 0))
            return root ;

        if(key.compareTo(root.getKey()) < 0)
            return  treeSearch(root.getLeft(),key);
        else
            return treeSearch(root.getRight(),key);
    }


    public boolean isContain(K key)
    {
        int hash = Math.abs(key.hashCode() % capacity);

        //starting root of the BinaryTree corresponding to hash value
        Entry<K,V> rootNode = arrayList.get(hash);

        if(rootNode == null)
            return false;
        else
        {
            //if collision occur ,then we  have to search in BinaryTree
            Entry<K,V> isKeyPresent = treeSearch(rootNode , key);

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

       if (!isContain(key))
       {
           Entry<K, V> rootNode = arrayList.get(hash);
           Entry<K,V>  data = new Entry<>(key, value);

           //Inserting  the key-value pair into the  Binary tree
           rootNode = treeInsertion(rootNode , data);

           //adding the updated root after inserting the key-value pair into the Binary tree
           arrayList.add(hash, rootNode);

       }
       else {
           throw new Exception("Duplicate key");
       }

   }

   public  V get(K key)throws  Exception
   {
       int hash = Math.abs(key.hashCode() % capacity);

       Entry<K,V> rootNode = arrayList.get(hash);

       Entry<K,V> keyNode = treeSearch( rootNode, key);

       if ( keyNode == null)
           throw new Exception("key not present");
       else
           return keyNode.getValue();

   }


    public class  Entry<K extends Comparable<K>,V extends Comparable<V>> implements Comparable< Entry<K,V> >
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
            return this.getValue().compareTo(obj.getValue());
        }

        // search(key) = getleft.search(key) || getright.search(key)

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
        mis.add("Vickey" , "Singh");

        System.out.println(mis.get("Vickey"));


    }
}
