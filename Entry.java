
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

    public Entry()
    {
        this.key    =   null;
        this.value  =   null;
        this.left   =   null;
        this.right  =   null;
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
        return this.getKey().compareTo(obj.getKey());
    }

}
