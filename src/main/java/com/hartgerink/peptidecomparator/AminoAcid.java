/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hartgerink.peptidecomparator;

import java.awt.Color;
/**
 *
 * @author kevinhartgerink
 */
public class AminoAcid {
//                                                  0           1            2            3          4            5          6            7            8             9            10           11          12           13           14            15           16           17           18           19 
	private static char[] LETTER_ARRAY      = {'A',        'R',         'N',         'D',       'C',         'E',       'Q',         'G',         'H',          'I',         'L',         'K',        'M',         'F',         'P',          'S',         'T',         'W',         'Y',         'V'};
	private static Color[] COLOR_ARRAY      = {Color.black, Color.blue, Color.black, Color.red, Color.black, Color.red, Color.black, Color.black, Color.black, Color.black, Color.black, Color.blue, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black, Color.black};

        private int index;
     
        
        public AminoAcid(char c) {
            
            Character letter = Character.toUpperCase(c);      
            switch (letter) {
                    case'A': index = 0; break;
                    case'R': index = 1; break;
                    case'N': index = 2; break;
                    case'D': index = 3; break;
                    case'C': index = 4; break;
                    case'E': index = 5; break;
                    case'Q': index = 6; break;
                    case'G': index = 7; break;
                    case'H': index = 8; break;
                    case'I': index = 9; break;
                    case'L': index = 10; break;
                    case'K': index = 11; break;
                    case'M': index = 12; break;
                    case'F': index = 13; break;
                    case'P': index = 14; break;
                    case'S': index = 15; break;
                    case'T': index = 16; break;
                    case'W': index = 17; break;
                    case'Y': index = 18; break;
                    case'V': index = 19; break;

                    default:
                            //System.out.println("Class: AminoAcid, Error: Invalid Character: " + letter);
                            index = -1;
                            break;
            }
	}

	private boolean isWithinBounds(int x) {
            boolean b = false;
            if ((x >= 0) && (x < LETTER_ARRAY.length)) {
                b = true;
            }
            return b;
        }
        
        public int getIndex() {
            return index;
	}

	public char getLetter() {
            char c = '-';
            if (isWithinBounds(index)) {
                c = LETTER_ARRAY[index];
            }
            return c;
	}

	public Color getColor() {
            Color c = Color.MAGENTA;
            if (isWithinBounds(index)) {
                c = COLOR_ARRAY[index];
            }
            return c;
	}

        public static char[] GET_LETTER_ARRAY() {
            return LETTER_ARRAY;
        }
        
        public static boolean compare(AminoAcid a, AminoAcid b) {
            boolean match = false;
            if(a.getIndex() == b.getIndex()) {
                match = true;
            }          
            return match;
        }
        
}
