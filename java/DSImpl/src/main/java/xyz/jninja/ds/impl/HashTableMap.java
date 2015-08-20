package xyz.jninja.ds.impl;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 
 */

/**
 * Map ADT with HashTable impl. (with Vector) with Integer/String keys.
 * 
 * @author viswanath
 *
 */
public class HashTableMap<K extends Comparable<? super K>, V extends Comparable<? super V>> {
  private List<Entry<K, V>>[] hashTable;
  private int size;

  private static double loadFactor = 2.0; // default

  @SuppressWarnings("unchecked")
  public HashTableMap(int size) {
    this.hashTable = (List<Entry<K, V>>[]) Array.newInstance(Vector.class, size);
    this.size = size;
  }

  public V put(K k, V v) {
    Entry<K, V> entry = new Entry<K, V>(k, v);
    V eV = this.get(k);
    if (eV == null) {
      V rV = this.add(this.hashTable, entry);
      return rV;
    } else {
      return this.update(entry);
    }
  }

  public V get(K k) {
    List<Entry<K, V>> list = getListFromHashTable(this.hashTable, k);
    if (list != null) {
      for (Entry<K, V> entry : list) {
        if (entry.getKey().equals(k)) {
          return entry.getValue();
        }
      }
    }
    return null;
  }

  public boolean containsKey(K k) {
    List<Entry<K, V>> list = getListFromHashTable(this.hashTable, k);
    if (list != null) {
      for (Entry<K, V> entry : list) {
        if (entry.getKey().equals(k)) {
          return true;
        }
      }
    }
    return false;
  }

  public Set<K> keySet() {
    Set<K> keySet = new HashSet<K>();
    for (List<Entry<K, V>> list : this.hashTable) {
      if (list != null) {
        if (list.size() == 0) {
          continue;
        }

        for (Entry<K, V> entry : list) {
          keySet.add(entry.getKey());
        }
      }
    }
    return keySet;
  }

  private V update(Entry<K, V> entry) {
    List<Entry<K, V>> list = getListFromHashTable(this.hashTable, entry);
    for (Entry<K, V> e : list) {
      if (list != null) {
        if (e.getKey().equals(entry.getKey())) {
          V v = entry.getValue();
          e.setValue(v);
          return v;
        }
      }
    }
    return null;
  }

  private List<Entry<K, V>> getListFromHashTable(List<Entry<K, V>>[] hashTable, Entry<K, V> entry) {
    return getListFromHashTable(hashTable, entry.getKey());
  }

  private List<Entry<K, V>> getListFromHashTable(List<Entry<K, V>>[] hashTable, K k) {
    int sIndex = this.findNominalIndex(this.generateHashCode(k));
    return hashTable[sIndex];
  }

  private V add(List<Entry<K, V>>[] hashTable, Entry<K, V> entry) {
    List<Entry<K, V>> list = getListFromHashTable(hashTable, entry);

    if (list == null) {
      list = new Vector<Entry<K, V>>();
      int sIndex = this.findNominalIndex(this.generateHashCode(entry.getKey()));
      hashTable[sIndex] = list;
    }

    if (list.size() > loadFactor) {
      resize();
      this.add(this.hashTable, entry); // add entry which lead to resize
      // to new hash table.
    }

    if (list.add(entry)) {
      return entry.getValue();
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private void resize() {
    this.size *= 2;
    List<Entry<K, V>>[] hashTable =
        (List<Entry<K, V>>[]) Array.newInstance(Vector.class, this.size);

    for (List<Entry<K, V>> list : this.hashTable) {
      if (list != null) {
        if (list.size() == 0) {
          continue;
        }

        for (Entry<K, V> entry : list) {
          this.add(hashTable, entry);
        }
      }
    }
    this.hashTable = hashTable;
  }

  /**
   * Integer/String keys
   * 
   * @param k
   * @return
   */
  private int generateHashCode(K k) {
    if (k instanceof Integer) {
      return ((Integer) k).intValue();
    } else if (k instanceof String) {
      return this.shiftedSum((String) k);
    }
    return -1;
  }

  private int shiftedSum(final String string) {
    final int multiplier = 10;
    int accumulator = 0;
    char[] cArray = string.toCharArray();
    for (char c : cArray) {
      int val = c;
      accumulator += val;
      accumulator *= multiplier;
    }
    return accumulator;
  }

  private int findNominalIndex(int hashCode) {
    if (hashCode != -1) {
      return Math.abs(hashCode % this.size);
    } else {
      return -1;
    }
  }

  static class Entry<K extends Comparable<? super K>, V extends Comparable<? super V>> {
    private K key;
    private V value;

    public Entry() {
      this.key = null;
      this.value = null;
    }

    public Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    /**
     * @return the key
     */
    public K getKey() {
      return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(K key) {
      this.key = key;
    }

    /**
     * @return the value
     */
    public V getValue() {
      return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(V value) {
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "Entry [key=" + key + ", value=" + value + "]";
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    HashTableMap<Integer, String> map = new HashTableMap<Integer, String>(5);

    map.put(10, "Hello");
    map.put(20, "hai");
    map.put(5, "hai");
    map.put(20, "Hello");
    map.put(25, "how");

    for (Integer i : map.keySet()) {
      System.out.println(i + " : " + map.get(i));
    }

    System.out.println();

    HashTableMap<String, Integer> map1 = new HashTableMap<String, Integer>(10);
    String s = "Hello! how are you? how are you? how";
    StringTokenizer st = new StringTokenizer(s, " .,?!", false);
    for (; st.hasMoreTokens();) {
      String nextToken = st.nextToken();
      if (map1.containsKey(nextToken)) {
        int tmp = (int) map1.get(nextToken);
        map1.put(nextToken, ++tmp);
      } else {
        map1.put(nextToken, 1);
      }
    }

    for (String str : map1.keySet()) {
      System.out.println(str + " : " + map1.get(str));
    }

  }

}
