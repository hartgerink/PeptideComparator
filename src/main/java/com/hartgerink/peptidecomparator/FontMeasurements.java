/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hartgerink.peptidecomparator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author kevinhartgerink
 */
public class FontMeasurements {

    
    private static final String ALL_CHARACTERS  =   "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                                +   "abcdefghijklmnopqrstuvwxyz"
                                                +   "023456789"
                                                +   "!@#$%^&*()-_=+[{]}|;:',<.>/?"
                                                +   "\""    //Escaped doublequote
                                                +   "\\";   //Escaped backslash
                                       
    
    
    
    
    private Font font;
    private float stroke;
    private int width;
    private int height;
    private int drop;
    
    
    
    public FontMeasurements(Font givenFont, float givenStroke) {
        
        font = givenFont;
        stroke = givenStroke;
        
        //Create a Buffered Image
        int bWidth = 400;
        int bHeight = bWidth;
        int bCenter = bWidth /2;
        BufferedImage bImage = new BufferedImage(bWidth, bHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bImage.createGraphics();

        //Fill the buffered image in with white.
        g2.setColor(Color.white);
        g2.fillRect(0, 0, bWidth, bHeight);
        int backgroundColor = bImage.getRGB(bCenter, bCenter);
        
        //Setup the font to be drawn on the buffered image.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(givenStroke));
        g2.setFont(givenFont);        
        g2.setColor(Color.black);
        
        //Draw each of the characters onto the buffered image.
        //Each character will be drawn starting at the bottom-left corner
        //of the buffered image. 
        //The characters will write ontop of each other into a scribbled mess.

        //This loop would be used for a broader set of characters.
        //for(int i=0; i<ALL_CHARACTERS.length(); i++){
        //    String s = ALL_CHARACTERS.substring(i, i+1);
        //    g2.drawString(s, bCenter, bCenter);
        //}
        
        //This loop is used for the set of characters used by Amino Acids.
        //These are the characters relevant to Peptide Comparator project.
        for(int i=0; i<AminoAcid.GET_LETTER_ARRAY().length; i++) {
           String s = Character.toString(AminoAcid.GET_LETTER_ARRAY()[i]);
           g2.drawString(s, bCenter, bCenter);
        }
        
        g2.dispose();


        //Scan the buffered image horizontal line by horizontal line from top to
        //bottom. Along the way, record the top-most, left-most, right-most, and
        //bottom-most pixels that do not match the background color.
        
        //Set top, bottom, left and right to their extreams.
        int top = bHeight;
        int bottom = 0;
        int left = bWidth;
        int right = 0;
        
        for(int y=0; y<bHeight; y++) {
            for(int x=0; x<bWidth; x++) {
                int RGB = bImage.getRGB(x, y);
                if (RGB != backgroundColor) {
                    if (y < top)    {top    = y;}
                    if (y > bottom) {bottom = y;}
                    if (x < left)   {left   = x;}
                    if (x > right)  {right  = x;}                   
                }
            }
        }

        width = right - left;
        height = bottom - top;
        drop = bottom - bCenter;
        
        
        
    }

    public Font getFont() {
        return font;
    }
    public float getStroke() {
        return stroke;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getDrop() {
        return drop;
    }
    public static String getAllCharacters() {
        return ALL_CHARACTERS;
        
    }
    



    
}
