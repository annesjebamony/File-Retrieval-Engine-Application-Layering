package csc435.app;

import java.lang.System;
import java.util.ArrayList;
import java.util.Scanner;

public class AppInterface {
    private ProcessingEngine engine;

    public AppInterface(ProcessingEngine engine) {
        this.engine = engine;
    }

    public void readCommands() {
        // TO-DO implement the read commands method
        Scanner sc = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("> ");

            // read from command line
            command = sc.nextLine();

            // if the command is quit, terminate the program
            if (command.compareTo("quit") == 0) {
                break;
            }

            // if the command begins with index, index the files from the specified
            // directory
            if (command.length() >= 5 && command.substring(0, 5).compareTo("index") == 0) {
                // TO-DO parse command and call index operation                
                String[] parts = command.split(" ", 2);
                if (parts.length >= 2) {
                    String indexPath = parts[1];
                    if (indexPath != null && !indexPath.isEmpty()) {
                        ArrayList<String> indexResultList = engine.indexFiles(indexPath.trim());
                        for(String item : indexResultList) {
                            System.out.println(item);
                        }
                    } else {
                        System.out.println("Invalid path provided. Please specify a valid dataset path.");
                    }
                } else {
                    System.out.println("Invalid command format. Expected: 'index <path>'");
                }
                continue;

            }

            // if the command begins with search, search for files that matches the query
            if (command.length() >= 6 && command.substring(0, 6).compareTo("search") == 0) {
                // TO-DO parse command and call search operation
                // System.out.println(command);
                // Extract search terms (assuming space-separated)
                String[] parts = command.split(" ", 2);
                if (parts.length >= 2) {
                    String searchTerms = parts[1].trim();

                    // Handle "AND" logic (assuming space-separated keywords)
                    String[] keywords = searchTerms.split(" AND ");

                    // Add keywords to the list, handling empty keywords
                    ArrayList<String> wordsList = new ArrayList<>();
                    for (String keyword : keywords) {
                        if (!keyword.isEmpty()) {
                            wordsList.add(keyword);
                        }
                    }

                    if (!wordsList.isEmpty()) {
                        // Call search operation with the list of keywords
                        ArrayList<String> searchResultList = engine.searchFiles(wordsList); 
                        for(String item : searchResultList) {
                            System.out.println(item);
                        }
                    } else {
                        System.out.println("Invalid search query. Please provide keywords.");
                    }
                } else {
                    System.out.println("Invalid command format. Expected: 'search <keywords>'");
                }
                continue;
            }

            System.out.println("unrecognized command!");
        }

        sc.close();
    }
}
