package com.thisBetterwork;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Process {
    private Sort sort;
    private ArrayList<String> fileData = new ArrayList<>();
    private int stringSize;
    private int numberOfRemovedStrings;
    private int newFileSize;
    private int originalFileSize;
    private String writePath;
    private String readPath;
    private boolean fileChosenCheck;



    //Constructor
    public Process(Sort sort) {
        this.sort = sort;
    }

    //The code that processes the data
    public void runProcess(int errorSize, boolean rewriteOrNewFile){

        //Letting the user choose if they want to create a new file with the processed data. Default option is new file
        if (rewriteOrNewFile){
            writePath = readPath;
        }else {
            writePath = readPath.replaceAll(".txt$", "Version2.txt");
        }

        //Reading the data and making it into a ArrayList
        try {
            FileReader reader = new FileReader(readPath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            reader.close();
            stringSize = fileData.get(0).length();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Checking that the values are compatible
        if (stringSize < errorSize){
            JOptionPane.showMessageDialog(null, "The error size is bigger than the strings, the system will exit");
            System.exit(-5);
        }

        //Recording down the original file size
        originalFileSize = fileData.size();

        //Passing the ArrayList into the class sort
        if (!sort.isSorted(fileData)){
            sort.quickSort(fileData, 0 , fileData.size() - 1);
        }


        //Compares each part of string to the string after it and removing if exact copies of chosen error size long found
        for (int i = 0; i < fileData.size() -1; i ++) {
            for (int j = 0; j < (stringSize-errorSize) ; j++) {
                if (fileData.get(i).regionMatches(j, fileData.get(i + 1), j, errorSize) && i < fileData.size() - 2){
                    fileData.remove(i + 1);
                    j = -1;
                }
                if (j > 0){
                    if (fileData.get(i).regionMatches(j, fileData.get(i + 1), j-1, errorSize) && i < fileData.size() - 2){
                        fileData.remove(i + 1);
                        j = -1;
                    }
                }
                if (j+1 != stringSize-errorSize && j != -1){
                    if (fileData.get(i).regionMatches(j, fileData.get(i + 1), j+1, errorSize) && i < fileData.size() - 2){
                        fileData.remove(i + 1);
                        j = -1;
                    }
                }
            }
        }

        //Writes the new data to a file
        try {
            FileWriter writer = new FileWriter(writePath, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (int j = 0; j < fileData.size() ; j++) {
                bufferedWriter.write(fileData.get(j));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Calculates and prints details of what has been done
        newFileSize = fileData.size();
        numberOfRemovedStrings = originalFileSize - newFileSize;
        fileData.clear();

    }

    //Required getters and setters
    public void setReadPath(String readPath) {
        this.readPath = readPath;
    }

    public boolean isFileChosenCheck() {
        return fileChosenCheck;
    }

    public void setFileChosenCheck(boolean fileChosenCheck) {
        this.fileChosenCheck = fileChosenCheck;
    }

    public int getNumberOfRemovedStrings() {
        return numberOfRemovedStrings;
    }

    public int getNewFileSize() {
        return newFileSize;
    }

    public int getOriginalFileSize() {
        return originalFileSize;
    }
}
