[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/lZXSBlN2)
## CSC 435 Programming Assignment 2 (Fall 2024)
**Jarvis College of Computing and Digital Media - DePaul University**

**Student**: Annes Jebamony (annes.jebamony@dePaul.edu)  
**Solution programming language**: Java
## Overview

This project implements a simple File Retrieval Engine using Java. The engine supports indexing and searching of text files located within directories. The purpose of the assignment is to create a basic retrieval system that demonstrates how inverted indices work for querying textual content.

## Components

The project contains the following main components:

1. **AppInterface.java**: This class handles the command-line interface for interacting with the user. It supports commands like `index <folder>` and `search <query>`.
2. **ProcessingEngine.java**: Responsible for processing and indexing text files. It uses the `IndexStore` class to create mappings of terms and their frequencies.
3. **IndexStore.java**: Manages the inverted index data structure and provides methods to access term frequencies.
4. **Pair.java**: A helper class used for handling pair objects in data structures.

## How to Compile and Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/transcendental-software/csc-435-pa2-annesjebamony.git
   cd csc-435-pa2-annesjebamony
   cd app-java
   
2. **Compile:**:
   ```bash
   mvn clean package
   mvn clean install
   mvn compile
   
3. **Run the program:**
   ```bash
   java -jar target/app-java-1.0-SNAPSHOT-jar-with-dependencies.jar
  
4. **Commands:**
   ```bash
   > index /media/sf_dataset1_client_server
     Completed indexing 313400430 bytes of data from /media/sf_dataset1_client_server
     Completed indexing in 79.552 seconds
   
    > search her
      Search results (top 10):
      * /media/sf_dataset1_client_server/4_clients/client_4/folder8/Document11052.txt 3104
      * /media/sf_dataset1_client_server/1_client/client_1/folder8/Document11052.txt 3104
      * /media/sf_dataset1_client_server/2_clients/client_2/folder8/Document11052.txt 3104
      * /media/sf_dataset1_client_server/1_client/client_1/folder3/folderB/Document10509.txt 2665
      * /media/sf_dataset1_client_server/2_clients/client_1/folder3/folderB/Document10509.txt 2665
      * /media/sf_dataset1_client_server/4_clients/client_2/folder3/folderB/Document10509.txt 2665
      * /media/sf_dataset1_client_server/4_clients/client_4/folder7/folderD/Document11050.txt 2542
      * /media/sf_dataset1_client_server/2_clients/client_2/folder7/folderD/Document11050.txt 2542
      * /media/sf_dataset1_client_server/1_client/client_1/folder7/folderD/Document11050.txt 2542
      * /media/sf_dataset1_client_server/1_client/client_1/folder3/Document10379.txt 2242

      > quit

      > index /media/sf_dataset2_client_server
      Completed indexing 1250030136 bytes of data from /media/sf_dataset2_client_server
      Completed indexing in 392.334 seconds

      > search her AND she
      Search completed in 0.048 seconds
      Search results (top 10):
      * /media/sf_dataset2_client_server/2_clients/client_2/folder8/Document15607.txt 8342
      * /media/sf_dataset2_client_server/1_client/client_1/folder8/Document15607.txt 8342
      * /media/sf_dataset2_client_server/4_clients/client_4/folder8/Document15607.txt 8342
      * /media/sf_dataset2_client_server/4_clients/client_4/folder8/Document15384.txt 7414
      * /media/sf_dataset2_client_server/1_client/client_1/folder8/Document15384.txt 7414
      * /media/sf_dataset2_client_server/2_clients/client_2/folder8/Document15384.txt 7414
      * /media/sf_dataset2_client_server/2_clients/client_2/folder8/Document15275.txt 6454
      * /media/sf_dataset2_client_server/4_clients/client_4/folder8/Document15275.txt 6454
      * /media/sf_dataset2_client_server/1_client/client_1/folder8/Document15275.txt 6454
      * /media/sf_dataset2_client_server/4_clients/client_4/folder8/Document15321.txt 6445
  
      > quit

![image](https://github.com/user-attachments/assets/c7fc9248-4fa8-4fb7-8aff-e52d26443ba3)

   
