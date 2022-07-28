package com.thisBetterwork;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {

    public static void main(String[] args) {

        //Making new instance of class Process and Sort
        Sort mySort = new Sort();
        Process process = new Process(mySort);

        //Making the user interface, buttons, text fields and labels
        JFrame gui = new JFrame("Interface");
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel pnl = new JPanel();
        pnl.setLayout(null);

        JCheckBox rewriteOrNo = new JCheckBox("Rewrite");
        rewriteOrNo.setBounds(20, 70, 70, 30);
        rewriteOrNo.setVisible(true);

        JCheckBox sortedOrNo = new JCheckBox("Is the file already sorted");
        rewriteOrNo.setBounds(20, 70, 70, 30);
        rewriteOrNo.setVisible(true);

        JTextField errorSizeTextField = new JTextField("Enter error size");
        errorSizeTextField.setBounds(20,200,340,40);
        errorSizeTextField.setVisible(true);

        JButton fileChoiceButton = new JButton("Click to choose file");
        fileChoiceButton.setBounds(100, 50, 200, 100);

        JButton runProgButton = new JButton("Run program");
        runProgButton.setBounds(100,250, 200,50);
        runProgButton.setEnabled(false);


        JLabel chosenFileInfo = new JLabel();
        chosenFileInfo.setBounds(20, 150, 3000, 60);

        JLabel fileSizeInfo = new JLabel();
        fileSizeInfo.setBounds(20, 300, 3000, 60);

        JLabel removedStrInfo = new JLabel();
        removedStrInfo.setBounds(20, 320, 3000, 60);



        pnl.setBounds(800, 800, 200, 100);

        pnl.add(runProgButton);
        pnl.add(rewriteOrNo);
        pnl.add(removedStrInfo);
        pnl.add(fileSizeInfo);
        pnl.add(errorSizeTextField);
        pnl.add(chosenFileInfo);
        pnl.add(fileChoiceButton);
        gui.add(pnl);
        gui.setSize(400, 450);
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);

        //Making and adding an action listener to the fileChoiceButton
        ActionListener actLisFileChoice = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Allows user to chose file and confirms the file is a text file
                FileDialog fileChoice = new FileDialog((Frame)null, "Select text file to be processed", FileDialog.LOAD);
                fileChoice.setVisible(true);
                String readPath = fileChoice.getDirectory() + fileChoice.getFile();
                if (readPath.equals("nullnull")){
                    System.out.println("No file was chosen therefor the program will terminate.");
                    System.exit(-2);
                }else if (readPath.charAt(readPath.length() -1) != 't'){
                    System.out.println( readPath + " is a non text file. The program will now terminate.");
                    System.exit(-3);
                }
                //Closing fileChoice
                fileChoice.dispose();

                //Passing the info into class process
                process.setReadPath(readPath);
                process.setFileChosenCheck(true);
                chosenFileInfo.setText("Chosen file: " + readPath);

                //Checking if the program is ready to be ran
                try {
                    if (Integer.parseInt(errorSizeTextField.getText()) > 2){
                        runProgButton.setEnabled(true);
                    }
                }catch (Exception ignored){

                }
                errorSizeTextField.requestFocus();
                errorSizeTextField.selectAll();

            }
        };
        fileChoiceButton.addActionListener(actLisFileChoice);

        //Making and adding an action listener to the runProgButton
        ActionListener actLisRunProg = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Running the main piece of code in process class
                process.runProcess(Integer.parseInt(errorSizeTextField.getText()), rewriteOrNo.isSelected());
                fileSizeInfo.setText("The original size was " + process.getOriginalFileSize() + " and the new file size is " + process.getNewFileSize());
                removedStrInfo.setText("The number of strings removed was " + process.getNumberOfRemovedStrings());

            }
        };
        runProgButton.addActionListener(actLisRunProg);

        errorSizeTextField.requestFocus();
        errorSizeTextField.selectAll();

        errorSizeTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkTextField();
            }
            public void removeUpdate(DocumentEvent e) {
                checkTextField();
            }
            public void insertUpdate(DocumentEvent e) {
                checkTextField();
            }

            public void checkTextField() {
                try {
                    if (Integer.parseInt(errorSizeTextField.getText()) > 2 && process.isFileChosenCheck()){
                        runProgButton.setEnabled(true);
                    }else {
                        runProgButton.setEnabled(false);
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Enter a number over 2, recommended value is 15");
                    runProgButton.setEnabled(false);

                }
            }
        });

    }
}
