import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

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

		// if you use Separate Chaining
                this.capacity = initialCapacity;
                this.size = 0;
                this.loadFactor = loadFactor;
		buckets = (List<HashMapEntry<K, V>>[]) new LinkedList[capacity];

		// // if you use Linear Probing (i won't)
		// entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}

	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
            // hashing information
            // can also use key.hashCode() assuming key is not null
            int keyHash = Objects.hashCode(key); 
            int index = keyHash % capacity;
            if (get(key) == null){
                // value to insert stored in HashMapEntry
                HashMapEntry valToInsert = new HashMapEntry(key, value);

                buckets[index].add(valToInsert);
                return true;
            }
            return false;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
            if(get(key) == null) {
                return false;
            }
            int keyHash = Objects.hashCode(key); 
            int index = keyHash % capacity;
            // this should run only once for the majority of cases. Average O(1), worst case O(n).
            for(HashMapEntry e: buckets[index]){
                if (e.getKey().equals(key)) {
                    e.setValue(newValue);
                    return true;
                }

            }
            return false;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
            if(get(key) == null) {
                put(key, value);
            }
            else {
                replace(key, value);
            }
		
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
            int keyHash = Objects.hashCode(key); 
            int index = keyHash % capacity;
            // loop runs only once on average
            if(buckets[index] != null) {
                for(HashMapEntry e: buckets[index]){
                    if (e.getKey().equals(key)) {
                        return (V) e.getValue();
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
	public boolean containsKey(K key) throws IllegalArgumentException {
            // TODO Auto-generated method stub
            // int keyHash = Objects.hashCode(key);
            return get(key) != null;
	}

	@Override
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
