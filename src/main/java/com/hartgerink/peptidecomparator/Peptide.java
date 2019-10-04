/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hartgerink.peptidecomparator;

/**
 *
 * @author kevinhartgerink
 */
public class Peptide {
    public static final String DEFAULT_PEPTIDE_STRING_ONE = "GPMGPSGPRGLPGPPGAPGPQGFQGPPGEPGEPGASGPMGPRGPPGPPGKNGDDGEAGKPGRPGERGPPGPQGARGLPGTAGLPGMKGHRGFSGLDGAKGDAGPAGPKGEPGSPGENGAPGQMGPRGLPGERGRPGAPGPAGARGNDGATGAAGPPGPTGPAGPPGFPGAVGAKGEAGPQGPRGSEGPQGVRGEPGPPGPAGAAGPAGNPGANGLTGAKGANGAPGIAGAPGFPGARGPSGPQGPGGPPGPKGNSGEPGAPGSKGDTGAKGEPGPVGVQGPPGPAGEEGKRGARGEPGPTGLPGPPGERGGPGSRGFPGADGVA";
    public static final String DEFAULT_PEPTIDE_STRING_TWO = "GPMGLMGPRGPPGAAGAPGPQGFQGPAGEPGEPGQTGPAGARGPAGPPGKAGEDGHPGKPGRPGERGVVGPQGARGFPGTPGLPGFKGIRGHNGLDGLKGQPGAPGVKGEPGAPGENGTPGQTGARGLPGERGRVGAPGPAGARGSDGSVGPVGPAGPIGSAGPPGFPGAPGPKGEIGAVGNAGPAGPAGPRGEVGLPGLSGPVGPPGNPGADGQPGAKGAAGLPGVAGAPGLPGPRGIPGPVGAAGATGARGLVGEPGPAGSKGESGNKGEPGSAGPQGPPGPSGEEGKRGPNGEAGSAGPPGPPGLRGSPGSRGLPGADGRA";
    
    private AminoAcid[] aminoAcidArray;
    private String peptideName;
    private String peptideString;
    private String peptideSource;

    
    
    public Peptide() {
        aminoAcidArray = new AminoAcid[0];
        peptideName = "";
        peptideString = "";
        peptideSource = "";        
    }
    
    //Build peptideString based on one of two default Peptides specified above.
    public void buildPeptideStringFromDefault(int x) {        
        switch (x) {
            case 1:
                peptideName = "Default Peptide One";
                peptideString = DEFAULT_PEPTIDE_STRING_ONE;
                peptideSource = "DEFAULT_PEPTIDE_ONE";
                break;
            case 2:
                peptideName = "Default Peptide Two";
                peptideString = DEFAULT_PEPTIDE_STRING_TWO;
                peptideSource = "DEFAULT_PEPTIDE_TWO";
                break;
            default:
                peptideName = "";
                peptideString = "";
                peptideSource = "";
                break;
        }      
    }
    
    //Build peptideString based on a file selected by the user.
    public boolean buildPeptideStringFromFile() {
        boolean chooseOK, openOK;
        chooseOK = openOK = false;

        PeptideFileReader PFR = new PeptideFileReader();
        
        //Choose
        chooseOK = PFR.chooseFile();
        
        //Open
        if(chooseOK) {
            openOK = PFR.openFile();
        }
        
        //Collect
        if(chooseOK && openOK) {
            PFR.collectData();
            peptideName = PFR.getName();
            peptideString = PFR.getBody();
            peptideSource = PFR.getTheFilePath();
        }
        
        return (chooseOK && openOK);
    }
    
    
    //Build aminoAcidArray from peptideString.
    public boolean buildAminoAcidArrayFromString() {
        boolean functionErrorFree = true;
        
        aminoAcidArray = new AminoAcid[peptideString.length()];
        for(int i=0; i<peptideString.length(); i++) {
            AminoAcid newAminoAcid = new AminoAcid(peptideString.charAt(i));
            aminoAcidArray[i] = newAminoAcid;
            
        }
        return functionErrorFree;
    }
    
    //Provide the AminoAcid object at the specified array index. 
    public AminoAcid getAminoAcid(int index) {
        AminoAcid a;
        if((index >= 0) && (index <aminoAcidArray.length)) {
            a = aminoAcidArray[index];
        }
        else {
            //When asking for an AminoAcid that does not exist, provide a broken AminoAcid.
            a = new AminoAcid('-');
        }
        return a;
    }

    //Provide the peptideStringLength of the array of AminoAcid objects.
    public int length() {
        return aminoAcidArray.length;
    }

    //Provide the string of characters representing the Peptide.
    public String getString() {
        return peptideString;
    }
    
    public String getName() {
        return peptideName;
    }
    
    public String getSource() {
        return peptideSource;
    }

    //Provide an array of boolans based on comparing two Peptides. True for matching amino acids, False for unmatching amino acids.
    public static boolean[] compare(Peptide a, Peptide b) {
        int maxLength;
        
        maxLength = java.lang.Math.max(a.length(), b.length());
        
        boolean[] comparisonResults = new boolean[maxLength];
        
        for (int i=0; i<comparisonResults.length; i++) {
            comparisonResults[i] = AminoAcid.compare(a.getAminoAcid(i), b.getAminoAcid(i));
        }
        
        return comparisonResults;
    }
}
