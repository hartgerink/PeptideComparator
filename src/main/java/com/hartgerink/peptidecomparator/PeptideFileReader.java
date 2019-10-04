/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hartgerink.peptidecomparator;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author kevinhartgerink
 */
public class PeptideFileReader {
    
    private static final long MAX_FILE_SIZE = 2048;
    private static final String[] PEPTIDE_TAGS = {"<PEPTIDE>", "<NAME>", "</NAME>", "<BODY>", "</BODY>", "</PEPTIDE>"};
    
    private String theFilePath;
    private String rawStringFromFile;
    
    private int[] tagLocations;
    boolean tagsAreOK;
    
    private String theName;
    private String theBody;
    
    private boolean chooseOK, attributesOK, readOK;    
    
    
    
    public PeptideFileReader() {    
        theFilePath = "";
        rawStringFromFile = "";
        tagLocations = new int[6];
        tagsAreOK = false;
        theName = "";
        theBody = "";
        chooseOK = attributesOK = readOK = false;
    }
    
    
    public boolean chooseFile() {
        chooseOK = false;   
        
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Peptide Files", "peptide");
        fileChooser.setFileFilter(extensionFilter);

        File fileHelper = new File (System.getProperty("user.home"));
        fileChooser.setCurrentDirectory (fileHelper);
        
        int returnOption = fileChooser.showOpenDialog(fileChooser);
        
        if (returnOption == JFileChooser.APPROVE_OPTION) {
            fileHelper = fileChooser.getSelectedFile();
            theFilePath = fileHelper.getAbsolutePath();
            chooseOK = true;
        }
        else {
            chooseOK = false;
        }
        return chooseOK;
    }
    
    //This public method uses the private methods "checkFileAttributes" and "readFile"
    //to open the desired file.
    public boolean openFile() {
        attributesOK = readOK = false;
        
        if(chooseOK){
           attributesOK = checkFileAttributes();            
           if(attributesOK) {
               readOK = readFile();
           }
        }
        return (chooseOK && attributesOK && readOK);      
    }
    
    //This public method uses the private methods "checkAndMarkTags()" and "parsePeptideFile()"
    //to extract data from a marked up peptide file.
    public boolean collectData() {
        tagsAreOK = false;
        if (chooseOK && attributesOK && readOK) {
            tagsAreOK = checkAndMarkTags();
            if(tagsAreOK) {
                parsePeptideFile();
            }                        
        }
        return tagsAreOK;
    }
  
    
    //Check to see if the file is: a file, is readable, and iisn't too big.
    private boolean checkFileAttributes() {
        boolean fileIsAFile;
        boolean fileIsReadable;
        boolean fileSizeIsLessThanMax;

        File testFile = new File(theFilePath);
        
        fileIsAFile = testFile.isFile();
        
        if(fileIsAFile) {
            fileIsReadable = testFile.canRead();
            
            long fileSize = testFile.length();
            fileSizeIsLessThanMax = (fileSize < MAX_FILE_SIZE);    
        }
        else {
            fileIsReadable = false;
            fileSizeIsLessThanMax = false;
        }        
        
        return (fileIsAFile && fileIsReadable && fileSizeIsLessThanMax);
    }
    
    //Try to open the file and read it into a string.
    private boolean readFile() {
        boolean fileHasBeenRead = true;

        try {
            rawStringFromFile = new String (Files.readAllBytes(Paths.get(theFilePath)));
            rawStringFromFile = rawStringFromFile.toUpperCase();
        }
        catch (IOException e) {
            fileHasBeenRead = false;
        }
        return fileHasBeenRead;
    }      

    

    
    //There are 6 tags defined above by "PEPTIDE_TAGS". This function will check to see if all 6
    //tags appear within "rawStringFromFile", without duplicates and in the correct order. Also,
    //it will mark the positions of each found tag so that the desired data of "theName" and "theBody"
    //can be extracted from "rawStringFromFile" later on using the "ParsePeptideFile()" method.
    private boolean checkAndMarkTags() {
        tagsAreOK = true;
        tagLocations = new int[6];
        
        int whileCounter = 0;
        while((whileCounter<6) && tagsAreOK) {
            
            int firstIndex;
            int secondIndex;
            int startIndex;            

            //Search the rawStringFromFile for a Tag. Will return -1 if not found.
            firstIndex = rawStringFromFile.indexOf(PEPTIDE_TAGS[whileCounter]);
 
            //If the tag was never found...
            if(firstIndex == -1) {
                tagsAreOK = false;
                System.out.println("Tag not found: " + PEPTIDE_TAGS[whileCounter]);
                break;
            }
           
            //Search for a duplicate tag by setting the starting index beyond the first occurance of the tag.
            startIndex = firstIndex + PEPTIDE_TAGS[whileCounter].length();
            
            if(startIndex < rawStringFromFile.length()) {
                secondIndex = rawStringFromFile.indexOf(PEPTIDE_TAGS[whileCounter], startIndex);
            }
            else {
                secondIndex = -1;
            }
            
            //If a second occurance of the tag was found...
            if(secondIndex != -1) {
                tagsAreOK = false;
                System.out.println("Duplicate tag found: " + PEPTIDE_TAGS[whileCounter]);
                break;
            }
            
            tagLocations[whileCounter] = firstIndex;
            
            whileCounter++;
        }
        
        //In this while loop, the "tagLocations[]" array will be check to be sure that each
        //of the tags was found in the correct order.
        int previousValue = 0;
        if (tagsAreOK) {
            previousValue = tagLocations[0];
        }
        whileCounter = 1;
        while(tagsAreOK && whileCounter<6) {
            if(previousValue > tagLocations[whileCounter]) {
                tagsAreOK = false;
                System.out.println("Tags were _not_ found in the correct order!");
                break;
            }
            previousValue = tagLocations[whileCounter];
            whileCounter++;
            
        }
        return tagsAreOK;
    }

    
    //This method is used after "checkAndMarkTags()" has run without errors.
    //"checkAndMarkTags() did the work of marking the positions of all the tags.
    //This method will then extract "theName" and "theBody" data from "rawStringFromFile".
    private void parsePeptideFile () {
        int startName, endName, startBody, endBody;
        
        startName = tagLocations[1] + PEPTIDE_TAGS[1].length();
        endName   = tagLocations[2];
        startBody = tagLocations[3] + PEPTIDE_TAGS[3].length();
        endBody   = tagLocations[4];
        
        theName = rawStringFromFile.substring(startName, endName);
        theBody = rawStringFromFile.substring(startBody, endBody);
    }          

    
    

public String getTheFilePath() {
    return theFilePath;
}

public String getRawStringFromFile() {
    return rawStringFromFile;
}

public String getName() {
    return theName;
}

public String getBody() {
    return theBody;
}

public boolean errorFree() {
    return tagsAreOK;
}

public boolean chooseOK() {
    return chooseOK;
}

}
