import java.util.ArrayList;
import java.util.List;

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

final class HashMap<K extends Comparable<K>,V extends Comparable<V>>
{
    final private int capacity = (1 << 4); //aka 16 ,Default size
    final private  Entry<K,V> [] table = new Entry[capacity]; //ArrayList storing the Tree object
    private int size = 0;


    public HashMap(Entry<K,V> ... entry)throws Exception
    {

        for (Entry<K,V> tuple : entry)
        {
            K key   =   tuple.getKey();
            V value =   tuple.getValue();

            int hash = Math.abs(key.hashCode() % capacity);

            if (!containsKey(key))
            {
                Entry<K, V> rootNode = table[hash];
                Entry<K, V> data = new Entry<>(key, value);


                rootNode = treeInsertion(rootNode, data);

                //adding the updated root after inserting the key-value pair into the Binary tree

                table[hash] = rootNode;
                //represent no of the key-value pair in hash map
                size++;
            }
            else
            {
                throw new Exception("Duplicate key");

            }
        }
    }



    public int size()
    {
        return size;
    }



    public Entry<K, V> treeSearch(Entry<K, V> root, K key) {
        if (root == null || (root.getKey().compareTo(key) == 0))
            return root;

        if (key.compareTo(root.getKey()) < 0)
            return treeSearch(root.getLeft(), key);
        else
            return treeSearch(root.getRight(), key);
    }


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


    public List traversePreRecursive(Entry<K,V> node) {
        if (node == null) return new ArrayList();

        List nodeValues = new ArrayList();
        nodeValues.add(new Entry<>(node));
        nodeValues.addAll(traversePreRecursive(node.getLeft()));
        nodeValues.addAll(traversePreRecursive(node.getRight()));

        return nodeValues;
    }


    public  HashMap<K,V> put(K key,V value)throws Exception
    {
        List<Entry<K,V>> entries = new ArrayList<>();

        //adding the current key- value pair
        entries.add(new Entry<>(key,value));


        for(Entry<K,V> rootNode :table)
        {
            if(rootNode != null)
                entries.addAll(traversePreRecursive(rootNode));
        }

        Entry<K,V> entriesArr[]  = new Entry[entries.size()];
        entriesArr = entries.toArray(entriesArr);


        return new HashMap<>(entriesArr);
    }


    public  V get(K key)throws  Exception
    {
        int hash = Math.abs(key.hashCode() % capacity);

        Entry<K,V> rootNode = table[hash];

        Entry<K,V> keyNode = treeSearch( rootNode, key);

        if ( keyNode == null)
            throw new Exception("key not present");
        else
            return keyNode.getValue();

    }

/*
    public Iterator iterator()
    {
        return new Iterator();
    }


    public class Iterator
    {

        int current;


        // The constructor initializes a new iterator that has not yet
        // returned any of the elements of the list.
        public Iterator()
        {
            // our list is stored backwards in the array, so the first item
            // is really at the end of the array
            current = list.size()-1;
        }


        // hasNext() returns true if there are more elements in the list
        // that have not been returned, and false if there are no more
        // elements.
        public boolean hasNext()
        {
            return ( current >= 0 );
        }


        // next() returns the next element in the list.  The first time next()
        // is called on an iterator, the first element of the list is returned;
        // the second time next() is called, the second element is returned;
        // and so on.
        //
        // If there are no more elements in the list, a NoSuchElementException
        // should be thrown.  You generally won't want to catch this exception,
        // because it's an indication of a bug in your program; best to let
        // the program crash (with useful information about where the crash
        // occurred) so you can find and fix the problem.
        public E next()
        {
            if ( current < 0 )
                throw new NoSuchElementException();

            // advance current and return the item we just passed.
            current--;
            return list.get( current + 1 );
        }
    }
*/




         class Entry<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Entry<K, V>> {

            private K key;
            private V value;
            private Entry<K, V> left;
            private Entry<K, V> right;

            public Entry(Entry<K,V> node)
            {
                this.key    =   node.getKey();
                this.value  =   node.getValue();
                this.left   =   node.getLeft();
                this.right  =   node.getRight();
            }

            public Entry(K key, V value) {
                this.key = key;
                this.value = value;
                this.left = null;
                this.right = null;
            }

            public V getValue() {
                return value;
            }

            public K getKey() {
                return key;
            }

            public Entry<K, V> getLeft() {
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

            public int compareTo(Entry<K, V> obj) {
                return this.getValue().compareTo(obj.getValue());
            }

        }




}
