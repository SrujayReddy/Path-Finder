// --== CS400 File Header Information ==--
// Name: SRUJAY REDDY JAKKIDI
// Email: jakkidi@wisc.edu
// Group: E15
// Group TA: Lakshika
// Lecturer: GARY DAHL
// Notes to Grader: NONE

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class implements a hashtable-based map to store and retrieve key-value pairs. The map does
 * not allow duplicate keys and uses chaining to resolve collisions.
 *
 * @param <KeyType>   the type of keys maintained by this map
 * @param <ValueType> the type of mapped values
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private static final int DEFAULT_CAPACITY = 32;
  protected LinkedList<Pair>[] table; // Array of linked lists for chaining
  private int size; // Number of key-value pairs in the hashtable


  /**
   * Inner class to hold key-value pairs.
   */
  protected class Pair {
    KeyType key;
    ValueType value;

    Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }
  }

  /**
   * Constructs a new HashtableMap with the specified capacity.
   *
   * @param capacity the initial capacity of the hashtable
   */
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    table = new LinkedList[capacity];
    size = 0;
  }

  /**
   * Constructs a new HashtableMap with the default capacity.
   */
  public HashtableMap() {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Adds a new key,value pair/mapping to this collection.
   *
   * @param key   the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException if key already maps to a value
   * @throws NullPointerException     if key is null
   */
  @Override
  public void put(KeyType key, ValueType value)
      throws IllegalArgumentException, NullPointerException {
    if (key == null) {
      throw new NullPointerException("Key cannot be null");
    }

    int index = Math.abs(key.hashCode()) % table.length;
    if (table[index] == null) {
      table[index] = new LinkedList<>();
    } else {
      for (Pair pair : table[index]) {
        if (pair.key.equals(key)) {
          throw new IllegalArgumentException("Key already exists");
        }
      }
    }

    table[index].add(new Pair(key, value));
    size++;
    if ((double) size / table.length >= 0.75) {
      resize();
    }
  }

  /**
   * Checks whether a key maps to a value in this collection.
   *
   * @param key the key to check
   * @return true if the key maps to a value, and false is the key doesn't map to a value
   */
  @Override
  public boolean containsKey(KeyType key) {
    int index = Math.abs(key.hashCode()) % table.length;
    LinkedList<Pair> bucket = table[index];
    if (bucket != null) {
      for (Pair pair : bucket) {
        if (pair.key.equals(key)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Retrieves the specific value that a key maps to.
   *
   * @param key the key to look up
   * @return the value that key maps to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode()) % table.length;
    LinkedList<Pair> bucket = table[index];
    if (bucket != null) {
      for (Pair pair : bucket) {
        if (pair.key.equals(key)) {
          return pair.value;
        }
      }
    }
    throw new NoSuchElementException("Key not found");
  }

  /**
   * Remove the mapping for a key from this collection.
   *
   * @param key the key whose mapping to remove
   * @return the value that the removed key mapped to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode()) % table.length;
    LinkedList<Pair> bucket = table[index];
    if (bucket != null) {
      for (Pair pair : bucket) {
        if (pair.key.equals(key)) {
          bucket.remove(pair);
          size--;
          return pair.value;
        }
      }
    }
    throw new NoSuchElementException("Key not found");
  }

  /**
   * Removes all key,value pairs from this collection.
   */
  @Override
  public void clear() {
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        table[i].clear();
      }
    }
    size = 0;
  }

  /**
   * Retrieves the number of keys stored in this collection.
   *
   * @return the number of keys stored in this collection
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Retrieves this collection's capacity.
   *
   * @return the size of te underlying array for this collection
   */
  @Override
  public int getCapacity() {
    return table.length;
  }

  /**
   * Resizes the hashtable to twice its current size and rehashes all the elements.
   */
  @SuppressWarnings("unchecked")
  private void resize() {
    LinkedList<Pair>[] oldTable = table;
    table = new LinkedList[oldTable.length * 2];
    size = 0;

    for (LinkedList<Pair> bucket : oldTable) {
      if (bucket != null) {
        for (Pair pair : bucket) {
          put(pair.key, pair.value); // Rehash
        }
      }
    }
  }

  // JUnit Tests

  /**
   * Tests the put method for adding key-value pairs and getSize method for verifying the size of
   * the map. Checks if the size is updated correctly after adding elements.
   */
  @Test
  public void testPutAndGetSize() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    map.put("key2", 2);
    Assertions.assertEquals(2, map.getSize(), "Size should be 2 after adding two different keys");
  }

  /**
   * Tests the get method for both existing and non-existing keys. Checks if the correct value is
   * retrieved for an existing key and if a NoSuchElementException is thrown for a non-existing
   * key.
   */
  @Test
  public void testGetForExistingAndNonExistingKey() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    Assertions.assertEquals(1, map.get("key1"), "Retrieving value for 'key1' should return 1");
    Assertions.assertThrows(NoSuchElementException.class, () -> map.get("key3"),
        "Accessing a non-existing key should throw NoSuchElementException");
  }

  /**
   * Tests the remove method and the containsKey method. Verifies if a key is correctly removed from
   * the map and subsequently not found by containsKey.
   */
  @Test
  public void testRemoveAndContainsKey() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    map.remove("key1");
    Assertions.assertFalse(map.containsKey("key1"),
        "After removal, containsKey should return false for 'key1'");
  }

  /**
   * Tests the hashtable's collision handling. It checks if the map can store and retrieve multiple
   * keys that hash to the same index correctly.
   */
  @Test
  public void testCollisionHandling1() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    map.put("key2", 1); // Assuming 'key1' and 'key2' hash to the same index
    Assertions.assertTrue(map.containsKey("key1") && map.containsKey("key2"),
        "Hashtable should handle collisions and store both keys");
  }

  /**
   * Tests the auto-resizing feature of the hashtable. It adds elements to the map and checks if the
   * map's capacity is increased automatically once a certain threshold is reached.
   */
  @Test
  public void testAutoResizing() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    for (int i = 0; i < 50; i++) {
      map.put("key" + i, i);
    }
    Assertions.assertEquals(50, map.getSize(), "Size should be 50 after adding 50 elements");
    Assertions.assertTrue(map.getCapacity() > 32,
        "Capacity should have increased from the default");
  }

  /**
   * Tests the behavior of the put method when a null key is used. Expects a NullPointerException to
   * be thrown.
   */
  @Test
  public void testPutWithNullKey() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    Assertions.assertThrows(NullPointerException.class, () -> {
      map.put(null, 1);
    });
  }

  /**
   * Tests the put method with duplicate keys. Expects an IllegalArgumentException to be thrown when
   * a key is re-inserted.
   */
  @Test
  public void testPutWithDuplicateKeys() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      map.put("key1", 2);
    });
  }

  /**
   * Tests the automatic resizing functionality of the hashtable. Adds enough elements to trigger a
   * resize and checks if the capacity is doubled and size remains consistent.
   */
  @Test
  public void testResizeFunctionality() {
    HashtableMap<Integer, Integer> map = new HashtableMap<>(4); // Smaller initial capacity for test
    for (int i = 0; i < 4; i++) { // Adding elements to trigger resize
      map.put(i, i);
    }
    Assertions.assertEquals(8, map.getCapacity()); // Check if capacity is doubled
    Assertions.assertEquals(4, map.getSize()); // Check if all elements are still there
  }

  /**
   * Tests retrieving values for both existing and non-existing keys. Verifies that the correct
   * value is returned for existing keys and a NoSuchElementException is thrown for non-existing
   * keys.
   */
  @Test
  public void testGetForExistingAndNonExistingKeys() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    Assertions.assertEquals(1, map.get("key1"));
    Assertions.assertThrows(NoSuchElementException.class, () -> {
      map.get("keyX");
    });
  }

  /**
   * Tests the remove functionality of the map. Verifies that a key is correctly removed and not
   * found after removal, and that removing a non-existing key throws a NoSuchElementException.
   */
  @Test
  public void testRemoveFunctionality() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    map.remove("key1");
    Assertions.assertFalse(map.containsKey("key1"));
    Assertions.assertThrows(NoSuchElementException.class, () -> {
      map.remove("keyX");
    });
  }

  /**
   * Tests the clear method of the map. Verifies that the map size is zero after clearing and
   * contains no elements.
   */
  @Test
  public void testClearMethod() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    map.put("key2", 2);
    map.clear();
    Assertions.assertEquals(0, map.getSize());
  }

  /**
   * Tests the containsKey method for both existing and non-existing keys. Verifies that the method
   * returns true for added keys and false for keys that were never added or have been removed.
   */
  @Test
  public void testContainsKey() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    Assertions.assertTrue(map.containsKey("key1"));
    Assertions.assertFalse(map.containsKey("key2"));
  }

  /**
   * Tests the map's behavior with edge case values (such as Integer.MAX_VALUE and
   * Integer.MIN_VALUE). Verifies that these edge cases are handled correctly.
   */
  @Test
  public void testEdgeCases() {
    HashtableMap<Integer, Integer> map = new HashtableMap<>();
    map.put(Integer.MAX_VALUE, 1);
    map.put(Integer.MIN_VALUE, 2);
    Assertions.assertTrue(map.containsKey(Integer.MAX_VALUE) && map.containsKey(Integer.MIN_VALUE));
  }

  /**
   * Tests the consistency of the map after a series of operations including put and remove.
   * Verifies that the map maintains correct size and content.
   */
  @Test
  public void testConsistencyAfterOperations() {
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("key1", 1);
    map.remove("key1");
    map.put("key2", 2);
    Assertions.assertEquals(1, map.getSize());
    Assertions.assertTrue(map.containsKey("key2"));
  }

  /**
   * Stress test for the HashtableMap with a large number of keys. Verifies that the map can handle
   * a large number of insertions and removals without errors.
   */
  @Test
  public void stressTestWithManyKeys() {
    HashtableMap<Integer, Integer> map = new HashtableMap<>();
    int largeNumber = 100000; // A large number of elements to be inserted
    for (int i = 0; i < largeNumber; i++) {
      map.put(i, i);
    }
    Assertions.assertEquals(largeNumber, map.getSize(),
        "Map should contain " + largeNumber + " elements");

    // Verify that all inserted keys are present and correctly mapped
    for (int i = 0; i < largeNumber; i++) {
      Assertions.assertTrue(map.containsKey(i), "Map should contain the key: " + i);
      Assertions.assertEquals(i, map.get(i), "Key " + i + " should map to value " + i);
    }

    // Test removal of all elements
    for (int i = 0; i < largeNumber; i++) {
      map.remove(i);
    }
    Assertions.assertEquals(0, map.getSize(), "Map should be empty after removing all elements");
  }
}

