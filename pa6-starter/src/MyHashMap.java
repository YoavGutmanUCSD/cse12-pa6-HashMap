import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
import java.lang.Math;


// ADD HEADER COMMENTS :(
public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	private List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	

	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
            // TODO Finish initializing instance fields
            if(initialCapacity < 0 || loadFactor <= 0) {
                String message = "initialCapacity and loadFactor have to be greater than 0.";
                throw new IllegalArgumentException(message);
            }
            this.capacity = initialCapacity;
            this.size = 0;
            this.loadFactor = loadFactor;
            // if you use Separate Chaining
            buckets = (List<HashMapEntry<K, V>>[]) new LinkedList[capacity];

            // // if you use Linear Probing (i won't)
            // entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}

	@Override
        /* This method adds a value to the map with the (key, value) pair.
         * K key: key to refer to the value with, and to hash
         * V value: value to refer to with the key.
         */
	public boolean put(K key, V value) throws IllegalArgumentException {
            // hashing information
            // can also use key.hashCode() assuming key is not null
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null.");
            }
            int keyHash = Math.abs(Objects.hashCode(key)); 
            int index = keyHash % capacity;
            // should probably remove the != null ... I HAVE REMOVED IT
            if (get(key) == null){
                // value to insert stored in HashMapEntry
                HashMapEntry valToInsert = new HashMapEntry(key, value);
                buckets[index] = new LinkedList<HashMapEntry<K,V>>();
                buckets[index].add(valToInsert);
                size++;
                return true;
            }
            return false;
	}

        // Replace original value at key with a new value.
        // Return true if successful, false otherwise.
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null.");
            }
            // remove(key);
            // put(key, newValue);
            if(get(key) == null) {
                return false;
            }
            int keyHash = Math.abs(Objects.hashCode(key)); 
            int index = keyHash % capacity;
            // this should run only once for the majority of cases. Average O(1), worst case O(n).
            for(HashMapEntry e: buckets[index]){
                if (e.getKey().equals(key) || e == key) {
                    e.setValue(newValue);
                    return true;
                }
            }
            return false;
	}

	@Override
        // Remove the (key, value) pair associated with the key given as input.
        // Return true if successful, false otherwise.
	public boolean remove(K key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null.");
            }
            int keyHash = Math.abs(Objects.hashCode(key)); 
            int index = keyHash % capacity;

            try {
            for(int i = 0; i < buckets[index].size(); i++){
            // for(HashMapEntry e: buckets[index]){
                HashMapEntry entry = buckets[index].get(i);
                if(entry.getKey().equals(key) || entry == key){
                    buckets[index].remove(i);
                    size--;
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
            return false;
	}

	@Override
        // Set a key to a value. Add new one if not already there, otherwise replace the original value.
	public void set(K key, V value) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null.");
            }
            if(get(key) == null) {
                put(key, value);
            }
            else {
                replace(key, value);
            }
		
	}

	@Override
        // Return the value associated with the key given as input. Return null if key is not in hash table.
	public V get(K key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null.");
            }
            int keyHash = Math.abs(Objects.hashCode(key)); 
            System.out.format("%s", keyHash);
            int index = keyHash % capacity;
            System.out.format("%s", index);
            // loop runs only once on average
            if(buckets[index] != null) {
                for(int i = 0; i < buckets[index].size(); i++){
                    HashMapEntry currEntry = buckets[index].get(i);
                    K keyValue = (K) currEntry.getKey();
                    if (keyValue.equals(key)) {
                        return (V) currEntry.getValue();
                    }
                }
            }
            return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
        // Return true if the key is in the hash map, false otherwise
	public boolean containsKey(K key) throws IllegalArgumentException {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null.");
            }
            // TODO Auto-generated method stub
            // int keyHash = Objects.hashCode(key);
            return get(key) != null;
	}

	@Override
        // return a list with every key in the hash map. Takes no input.
	public List<K> keys() {
            List<K> allKeys = new LinkedList<K>();
            for(List<HashMapEntry<K,V>> e: buckets){
                if(e != null){
                    for(HashMapEntry entry: e){
                        allKeys.add((K) entry.getKey());
                    }
                }
            }
            return allKeys;
	}
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
