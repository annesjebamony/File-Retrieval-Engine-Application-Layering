package csc435.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import csc435.app.IndexStore.Pair;

public class ProcessingEngine {
    // TO-DO keep track of the index store
    private IndexStore store;

    public ProcessingEngine(IndexStore store) {
        this.store = store;
        // TO-DO implement constructor
    }

    /*
     * index files code and helper methods start here
     */
    public ArrayList<String> indexFiles(String datasetPath) {
        // TO-DO implement index files
        // path to data set as arg
        // we can return total bytes parsed, total exectuion time.
        ArrayList<String> indexResultList = new ArrayList<>();

        try {
            indexResultList = processDocuments(datasetPath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return indexResultList;
    }

    private ArrayList<String> processDocuments(String datasetPath) throws FileNotFoundException {
        long startTime = System.currentTimeMillis(); // Capture start time
        long totalBytesProcessed = 0; // To store total bytes and processing time
        ArrayList<String> resultList = new ArrayList<>();
        // temporary hash map
        HashMap<String, Integer> wordCountHashMap = new HashMap<>();

        ArrayList<String> documentsList = getDatasetList(datasetPath);

        for (String item : documentsList) {
            File file = new File(item);
            ArrayList<String> wordList = extractWordsFromFile(file);

            for (String word : wordList) {
                // Check if the word already exists in the HashMap
                if (wordCountHashMap.containsKey(word)) {
                    // If it exists, increment the count for that word
                    int currentCount = wordCountHashMap.get(word);
                    wordCountHashMap.put(word, currentCount + 1);
                } else {
                    // If it's a new word, add it with a count of 1
                    wordCountHashMap.put(word, 1);
                }
                // Update total bytes processed (assuming each word is 1 byte)
                totalBytesProcessed += word.length();
            }
            updateIndexHashMap(wordCountHashMap, item);
            wordCountHashMap.clear();
        }
        long endTime = System.currentTimeMillis();
        double processingTime = (endTime - startTime) / 1000.0; // Convert milliseconds to seconds

        // Add results to the list
        resultList.add("Completed indexing " + totalBytesProcessed + " bytes of data from " + datasetPath);
        resultList.add("Completed indexing in " + processingTime + " seconds");

        return resultList;
    }

    private void updateIndexHashMap(HashMap<String, Integer> map, String datasetPath) {
        try {
            store.updateIndex(map, datasetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getDatasetList(String datasetPath) {
        ArrayList<String> documentsFilePath = new ArrayList<>();

        // Create a File object for the folder path
        File folder = new File(datasetPath);

        // Check if the folder exists and is a directory
        if (folder.exists() && folder.isDirectory()) {
            processSubfolders(folder, documentsFilePath);
        }
        return documentsFilePath;
    }

    private void processSubfolders(File folder, ArrayList<String> filePaths) {
        // Get the list of files in the folder
        File[] filesInFolder = folder.listFiles();

        // Add each file's absolute path to the result list
        if (filesInFolder != null) {
            for (File file : filesInFolder) {
                if (file.isFile()) {
                    filePaths.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    // recursion
                    processSubfolders(file, filePaths);
                }
            }
        }
    }

    private ArrayList<String> extractWordsFromFile(File file) throws FileNotFoundException {
        ArrayList<String> words = new ArrayList<>();

        // Create a Scanner object to read the file line by line
        Scanner scanner = new Scanner(file);

        // Use a normal regex to split on whitespace or punctuation
        scanner.useDelimiter("[^\\p{Alnum}]+");

        // Read each line and extract words
        while (scanner.hasNext()) {
            String word = scanner.next();
            words.add(word);
        }

        // Close the scanner to release resources
        scanner.close();

        return words;
    }

    /*
     * search files code and helper methods start here
     */
    // TO-DO implement search files
    // pass a list of words as arg
    // return sth, results
    // store.lookupIndex(wordList);

    public ArrayList<String> searchFiles(ArrayList<String> wordList) {
        long startTime = System.currentTimeMillis(); // Start time for measuring search duration
        ArrayList<String> searchResultList = new ArrayList<>();

        // Initialize a HashMap to store document ID and its total frequency
        Map<String, Integer> matchingDocuments = new HashMap<>();

        // Perform intersection search and update matchingDocuments with frequencies
        performIntersectionSearch(wordList, matchingDocuments);

        List<Map.Entry<String, Integer>> sortedDocuments = new ArrayList<>(matchingDocuments.entrySet());
        sortedDocuments.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        long endTime = System.currentTimeMillis(); // End time for measuring search duration
        double searchTime = (endTime - startTime) / 1000.0; // Search time in seconds

        searchResultList.add("Search completed in " + searchTime + " seconds");
        searchResultList.add("Search results (top 10):");

        for (int i = 0; i < Math.min(sortedDocuments.size(), 10); i++) {
            Map.Entry<String, Integer> entry = sortedDocuments.get(i);
            String document = entry.getKey();
            int frequency = entry.getValue();
            // String updatedPathString [] = document.split("../datasets/", 2);
            // searchResultList.add("* " + updatedPathString[1] + " " + frequency);
            searchResultList.add("* " + document + " " + frequency);
        }

        // Return the top 10 documents
        ArrayList<String> topDocuments = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedDocuments.subList(0, Math.min(sortedDocuments.size(), 10))) {
            topDocuments.add(entry.getKey());
        }
        return searchResultList;
    }

    // Update performIntersectionSearch to handle HashMap and document frequencies
    private void performIntersectionSearch(ArrayList<String> wordList, Map<String, Integer> matchingDocuments) {
        // Iterate through each word in the search query
        for (String word : wordList) {
            // Get results (document IDs) from the index for the current word
            ArrayList<Pair<String, Integer>> lookupResults = store.lookupIndex(word);

            // Update frequencies for documents in matchingDocuments based on the current
            // word
            for (Pair<String, Integer> result : lookupResults) {
                String document = result.getKey();
                int frequency = result.getValue();

                // Handle documents not found in previous iterations (frequency of 0)
                matchingDocuments.put(document, matchingDocuments.getOrDefault(document, 0) + frequency);
            }
        }
    }

}
