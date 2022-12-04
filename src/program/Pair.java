package program;

import java.util.Objects;

public class Pair<K, V>{
    K key;
    V value;
    private final int hashCode;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
        this.hashCode = Objects.hash(this.key, this.value);
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair{" + "key=" + key + ", value=" + value + '}';
    }

    @Override
    public int hashCode()
    {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        Pair<K,V> that = (Pair<K,V>) o;
        String isequal = String.valueOf(this.key == that.getKey() && this.value == that.getValue());
        //System.out.println(this.toString() + " " + that.toString() + " " + isequal);
        //return this.key == that.getKey() && this.value == that.getValue();
        return this.hashCode() == that.hashCode(); // should be done with values not hashcode check again
    }

    public K getFirst() {
        return key;
    }

    public V getSecond() {
        return value;
    }

}