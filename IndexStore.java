package csc435.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class IndexStore {
  // TO-DO use a search structure to keep track of the index
  private final HashMap<String, ArrayList<Pair<String, Integer>>> index;

  /*
   * In index store create a private index hash table
   * in the index store constructor where we would intilaize the hash table
   * we would have a variable index the index store
   * we would need to intialize the variable inside the constructor
   * variable type-- hash table
   */

  public IndexStore() {
    // TO-DO implement constructor
    this.index = new HashMap<>();
  }

  public void updateIndex(HashMap<String, Integer> temporaryHashMap, String datasetPath) throws IOException {
    /*
     * TO-DO implement index insert
     * pass a arg file path and a temporary hash table
     * we would we iterate the elements of THH and add
     * them to the index hash table
     */

    for (String word : temporaryHashMap.keySet()) {
      int wordFrequency = temporaryHashMap.get(word);

      // Check if the word already exists in the index
      if (index.containsKey(word)) {
        // Get the existing list of locations for the word
        ArrayList<Pair<String, Integer>> locations = index.get(word);

        // Add the current document path and frequency to the locations list
        locations.add(new Pair<>(datasetPath, wordFrequency));
      } else {
        // Create a new ArrayList with the current document path and frequency
        ArrayList<Pair<String, Integer>> locations = new ArrayList<>();
        locations.add(new Pair<>(datasetPath, wordFrequency));
        // Add the new word and its locations to the index
        index.put(word, locations);
      }
    }
    // System.out.println(index.size());
  }

  public ArrayList<Pair<String, Integer>> lookupIndex(String word) {
    /*
     * TO-DO implement index lookup
     * pass as an arg a word, and i would return a list
     * of documents and the freq of that word
     * Extract that list from Index Hashtable and then
     * when we retrun the list create a pairs.
     * retrun list of pairs, Pair Data structure
    */

    ArrayList<Pair<String, Integer>> wordData = index.get(word); // Get word data from the index

    if (wordData != null) {
      return wordData;
    } else {
      // Word not found, return an empty list
      return new ArrayList<>();
    }
  }

  /*
   * This is the Pair Datastructure created
  */
  public class Pair<K, V> {

    private final K key;
    private final V value;

    public Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public V getValue() {
      return value;
    }
  }

}
