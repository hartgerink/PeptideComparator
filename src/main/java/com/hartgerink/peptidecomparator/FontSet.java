/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hartgerink.peptidecomparator;
import java.awt.*;
/**
 *
 * @author kevinhartgerink
 */
public class FontSet {
    
    private FontMeasurements[] fontArray;
    
    public FontSet(int min, int max) {
        
        fontArray = new FontMeasurements[max - min];
        int length = fontArray.length;
        
        for(int i=0; i<length; i++) {
            Font font = new Font("Monospaced", Font.BOLD, min + i);
            float stroke = 1.0f;
            fontArray[i] = new FontMeasurements(font, stroke);
            
        }
    }
    
    public FontMeasurements getFont(int i){
        return fontArray[i];
    }
}
