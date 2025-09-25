/*******************************************************************
 ∗ @file: Parser.java
 ∗ @description: This program implements the methods process(), operate_BST(), and writeToFile(). These methods
                    parse the input file, do the given commands to load and print the csv contents, and write
                    each song into the output file.
 ∗ @author: Aidan Broadhead
 ∗ @date: September 26, 2025
 ********************************************************************/

import java.io.*;
import java.util.Scanner;

public class Parser {

    private String outputFile = "./output.txt";

    //Create a BST tree of Song type
    private BST<Song> mybst = new BST<>();

    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename));
    }

    // Implement the process method
    // Remove redundant spaces for each input command
    public void process(File input) throws FileNotFoundException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false))) {

        } catch (IOException e) {
            System.out.println("IOException");
        }

        try (Scanner scnr = new Scanner(input)) {

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();

                // remove whitespace before and after the command and skip empty lines
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                // replace long whitespaces with single space
                line = line.replaceAll("\\s+", " ");

                // split the command
                String[] command = line.split(" ");

                //call operate_BST method;
                operate_BST(command);
            }
        }
    }

    // Implement the operate_BST method
    // Determine the incoming command and operate on the BST
    public void operate_BST(String[] command) {
        switch (command[0]) {
            // add your cases here
            case "loadcsv" -> {

                String path = command[1];
                // track number of entries loaded into BST
                int entriesLoaded = 0;

                try (BufferedReader br = new BufferedReader((new FileReader(path)))) {
                    // skip header
                    br.readLine();

                    String line;

                    while ((line = br.readLine()) != null) {
                        // trim line
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }

                        // split data
                        String[] column = line.split(",");

                        // parse and create new song object
                        try {
                            String id = column[0].trim();
                            String name = column[1].trim();
                            double duration = Double.parseDouble(column[2].trim());
                            double energy = Double.parseDouble(column[3].trim());
                            int key = Integer.parseInt(column[4].trim());
                            double loudness = Double.parseDouble(column[5].trim());
                            int mode = Integer.parseInt(column[6].trim());
                            double speechiness = Double.parseDouble(column[7].trim());
                            double acousticness = Double.parseDouble(column[8].trim());
                            double instrumentalness = Double.parseDouble(column[9].trim());
                            double liveness = Double.parseDouble(column[10].trim());
                            double valence = Double.parseDouble(column[11].trim());
                            double tempo = Double.parseDouble(column[12].trim());
                            double danceability = Double.parseDouble(column[13].trim());

                            // create new song object with the entry data
                            Song song = new Song(id, name, duration, energy, key, loudness, mode, speechiness,
                                    acousticness, instrumentalness, liveness, valence, tempo, danceability);

                            // insert song object into BST
                            mybst.insertNode(song);
                            entriesLoaded = entriesLoaded + 1;

                        } catch (NumberFormatException e) {
                            System.out.println("NumberFormatException" + line);
                        }

                    }

                    writeToFile("loaded " + entriesLoaded + " songs", outputFile);

                } catch (IOException e) {
                    writeToFile("load failed", outputFile);
                }

            }

            case "print" -> {
                // prints elements in alphabetical order
                mybst.print(outputFile);
            }

            case "size" -> {
                // writes size of BST
                writeToFile("size " + mybst.size(), outputFile);
            }

            case "clear" -> {
                // clears the BST
                mybst.clear();
                writeToFile("cleared", outputFile);
            }

            // call writeToFile
            // default case for Invalid Command
            default -> writeToFile("Invalid Command", outputFile);
        }
    }

    // Implement the writeToFile method
    // Generate the result file
    public void writeToFile(String content, String filePath) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }

}
