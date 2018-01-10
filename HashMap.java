import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @param <K> key
 * @param <V> value
 *
 * @author vikash Singh
 * @date   09 Jan 2018
 * @version 1.4
 *
 *
 *  HashMap use the combination of ArrayList and Binary tree data structure
 *  that improve the time complexity as compared to ArrayList and Linked list implementation.
 *
 */

final class HashMap<K extends Comparable<K>,V extends Comparable<V>> implements Map<K,V>
{
    final private int capacity = (1 << 2); //aka 256,Default size

    final private  Entry<K,V> [] table = new Entry[capacity]; //Array  storing the Tree object

    final private  Entry<K,V> [] entriesArray;


    public HashMap(Entry<K,V> ... entry)
    {
        //entriesArray will store key-value in  sequential manner
        entriesArray = new Entry[entry.length];

        int currentEntryIndex = 0;

        for (Entry<K,V> keyValue : entry)
        {
            K key   =   keyValue.getKey();
            V value =   keyValue.getValue();

            int hash = Math.abs(key.hashCode() % capacity);

                Entry<K, V> rootNode = table[hash];
                Entry<K, V> data = new Entry<>(key, value);


                rootNode = treeInsertion(rootNode, data);

                //adding the updated root after inserting the key-value pair into the Binary tree

                table[hash]  =  rootNode;
                entriesArray[currentEntryIndex++] = keyValue;


        }
    }

    @Override
    public int size()
    {
        return entriesArray.length;
    }



    @Override
    public boolean containsKey(K key)
    {
        int hash = Math.abs(key.hashCode() % capacity);

        //starting root of the BinaryTree corresponding to hash value
        Entry<K,V> rootNode = table[hash];

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



    @Override
    public  V get(K key)
    {
        int hash = Math.abs(key.hashCode() % capacity);

        Entry<K,V> rootNode = table[hash];

        Entry<K,V> keyNode = treeSearch( rootNode, key);

        if ( keyNode == null)
            throw new NoSuchElementException();
        else
            return keyNode.getValue();

    }



    @Override
    public  HashMap<K,V> put(K key,V value)
    {
        List<Entry<K,V>> entries = new ArrayList<>();

        //adding the current key- value pair
        entries.add(new Entry<>(key,value));


        for(Entry<K,V> rootNode :table)
        {
            if(rootNode != null) {
                // traversePreRecursive() method return list of key-value pair except
                // duplicate key , while traversing the Binary tree .
                entries.addAll(traversePreRecursive(rootNode, key));
            }
        }

        Entry<K,V> entriesArr[]  = new Entry[entries.size()];
        entriesArr = entries.toArray(entriesArr);


        return new HashMap<>(entriesArr);
    }



    public Entry<K, V> treeSearch(Entry<K, V> root, K key) {
        if (root == null || (root.getKey().compareTo(key) == 0))
            return root;

        if (key.compareTo(root.getKey()) < 0)
            return treeSearch(root.getLeft(), key);
        else
            return treeSearch(root.getRight(), key);
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

    //Return the list of all key-value in entriesArray
    public List traversePreRecursive(Entry<K,V> node, K key) {
        if (node == null) return new ArrayList();

        List nodeValues = new ArrayList();

        if(node.getKey().compareTo(key) !=0 )
            nodeValues.add(new Entry<>(node));

        nodeValues.addAll(traversePreRecursive(node.getLeft(),key));
        nodeValues.addAll(traversePreRecursive(node.getRight(),key));

        return nodeValues;
    }



    public MapIterator iterator()
    {
        return new MapIterator();
    }


    // The proper way to use an Iterator from outside of the HashMap
    // class is something like this:

    // HashMap<String,Integer> hashmap= new HashMap<>();
    // Iterator itr = hashmap.iterator();
    //
    // Entry<String,Integer> temp;
    //
    // while(itr.hasNext())
    // {
    //   temp = new Entry<>((Entry)itr.next());
    //   System.out.println("key : " + temp.getKey() + " value : " temp.getValue());
    // }

    public class MapIterator implements Iterator<Entry<K,V>>
    {

        int current;


        // The constructor initializes a new iterator that has not yet
        // returned any of the elements of the list.
        public MapIterator()
        {
            // our key-value is stored forwards in the array, so the first item
            // is really at the beginning of the array
            current = 0;
        }


        // hasNext() returns true if there are more elements in the list
        // that have not been returned, and false if there are no more
        // elements.
        public boolean hasNext()
        {
            return ( current < entriesArray.length );
        }


        // next() returns the next element in the HashMap.  The first time next()
        // is called on an iterator, the first element of the HashMap is returned;
        // the second time next() is called, the second element is returned;
        // and so on.
        //
        // If there are no more elements in the HashMap, a NoSuchElementException
        // should be thrown.

        public Entry<K,V> next()
        {
            if ( current >= entriesArray.length )
                throw new NoSuchElementException();

            // advance current and return the item we just passed.
            current++;
            //new instance instead of reference
            return new Entry<>(entriesArray[current - 1]);
        }
    }


}
