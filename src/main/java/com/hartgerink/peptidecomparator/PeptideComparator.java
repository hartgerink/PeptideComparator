/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hartgerink.peptidecomparator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.JColorChooser;
/**
 *
 * @author kevinhartgerink
 */
public class PeptideComparator extends JPanel {
    private int magnification;
    private int scroll;
    private boolean match;
    private boolean showCompare;
    
    //Establish a full set of fonts and their measurments. This avoids
    //recalculations of font measurments as fonts are shrunk or enlarged.
    private static final FontSet FONT_SET = new FontSet(4,100);
    private int charHeight, charWidth, whiteSpace;
    
    private int width, height;

    private Peptide p1, p2;
    private int peptide1X, peptide1Y, peptide2X, peptide2Y;
    
    Color highlightColor;
 
    
    
    private boolean[] comparison;
    private int matchX, matchY, matchW, matchH;
   
    
    public PeptideComparator() {
        magnification = 4;
        scroll = 0;
        
        match = false;
        showCompare = true;
       
        p1 = new Peptide();
        p1.buildPeptideStringFromDefault(1);
        p1.buildAminoAcidArrayFromString();
        
        p2 = new Peptide();
        p2.buildPeptideStringFromDefault(2);
        p2.buildAminoAcidArrayFromString();
        
        comparison = Peptide.compare(p1, p2);
        
        highlightColor = Color.yellow;
        
    }

    
    
    @Override
    public void paintComponent(Graphics g) {
        Dimension d = this.getSize();
        width = (int)d.getWidth();
        height = (int)d.getHeight(); 
        
        charWidth = FONT_SET.getFont(magnification).getWidth();
        charHeight = FONT_SET.getFont(magnification).getHeight();
        whiteSpace = (int)(charWidth * 0.2f);
        
        peptide1X = 10;
        peptide1Y = (int)(height * 0.5f);
         
        peptide2X = peptide1X;
        peptide2Y = peptide1Y + (charHeight * 2);
        
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, width, height);
        
        Font f = FONT_SET.getFont(magnification).getFont();
        g2.setFont(f);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1.0f));

        
        //Display Highlighting
        if (showCompare) {
            for(int i=scroll; i<comparison.length; i++) {
                if(comparison[i] == match) {
                    int j = i - scroll;
                    int x = (j * charWidth) + (j * whiteSpace) + + charWidth/4 + peptide1X;
                    int y = peptide1Y - (int)(charHeight * 1.5);
                    g2.setColor(highlightColor);
                    g2.fillRect(x, y, charWidth/2, charHeight * 4);
                }
            }
        }
                
        //Display Peptide 1
        for(int i=scroll; i<p1.length(); i++) {
                String s = Character.toString(p1.getAminoAcid(i).getLetter());
                int j = i - scroll;
                int x = (j * charWidth) + (j * whiteSpace) + peptide1X;
                int y = peptide1Y;
                g2.setColor(p1.getAminoAcid(i).getColor());
                g2.drawString(s, x, y);
                
        }
        //Display Peptide 2
        for(int i=scroll; i<p2.length(); i++) {
                String s = Character.toString(p2.getAminoAcid(i).getLetter());
                int j = i - scroll;
                int x = (j * charWidth) + (j * whiteSpace) + peptide2X;
                int y = peptide2Y;
                g2.setColor(p2.getAminoAcid(i).getColor());
                g2.drawString(s, x, y);
                
        }

        
    }


public void setMagnification(int i) {
    magnification = i;
    repaint();
}

public void setScroll(int i) {
    scroll = i;
    repaint();
}

public void setMatch(boolean b) {
    match = b;
    repaint();
}

public void setShowCompare(boolean b) {
    showCompare = b;
    repaint();
}

public Peptide getPeptideA() {
    return p1;
}

public Peptide getPeptideB() {
    return p2;
}

public void setPeptide1(Peptide p) {
    p1 = p;
    comparison = Peptide.compare(p1, p2);
    repaint();
}

public void setPeptide2(Peptide p) {
    p2 = p;
    comparison = Peptide.compare(p1, p2);
    repaint();
}

public void setHighlightColor() {
    
    highlightColor = JColorChooser.showDialog(this, "Choose a color", highlightColor);
    repaint();
}

public void setDefautPeptide1(int i) {
    p1.buildPeptideStringFromDefault(i);
    p1.buildAminoAcidArrayFromString();
    comparison = Peptide.compare(p1, p2);
    repaint();
}

public void setDefaultPeptide2(int i) {
    p2.buildPeptideStringFromDefault(i);
    p2.buildAminoAcidArrayFromString();
    comparison = Peptide.compare(p1, p2);
    repaint();
}




    
}
