
public interface Map<K,V>
{
    int size();

    boolean containsKey(K key);

    V get(K key);

    Object remove(K key);

    Object put(K key, V value);


}


