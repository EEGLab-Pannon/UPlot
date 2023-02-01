/*
MIT License

Copyright (c) 2023 Electrical Brain Imaging Lab

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package hu.unipannon.virt.plot.util;

import hu.unipannon.virt.plot.frame.BarStyle;
import hu.unipannon.virt.plot.frame.StrokeStyle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * The Parser is a utility class that can parse characters, strings and floating point numbers.
 * 
 * @author Tóth Bálint
 */
public class Parser {
    /**
     * Parses a character. The input is a string, and the function returns true if 
     * and only if the string is the reference character.
     * @param input The input in a String form.
     * @param ref The reference character for the input to be checked against.
     * @return True if the input is equal to the reference.
     */
    public static boolean parseCharRef(String input, char ref) {
        if (input.trim().equals(String.valueOf(ref)))
            return true;
        return false;
    }
    
    /**
     * Checks if the input is equal to the reference string.
     * @param input The word to be checked.
     * @param ref The reference string for the input to be checked against.
     * @return True if the two strings are equal
     */
    public static boolean parseStringRef(String input, String ref) {
        if (input.trim().equals(ref))
            return true;
        return false;
    }

    /**
     * Function used to create a StrokeStyle enum from the style's MATLAB
     * string representation.
     * @param style MATLAB format string of line style. Can be: (: -- -. -)
     * @return StrokeStyle enum of the given style.
     */
    public static StrokeStyle parseStrokeStyle(String style) {
        if (Parser.parseStringRef(style, "-"))
            return StrokeStyle.NORMAL;
        else if (Parser.parseStringRef(style, ":"))
            return StrokeStyle.DOT;
        else if (Parser.parseStringRef(style, "--"))
            return StrokeStyle.DASH;
        else if (Parser.parseStringRef(style, "-."))
            return StrokeStyle.DASH_DOT;
        else 
            return StrokeStyle.NORMAL;
    }
    
    /**
     * Creates JavaFX Paint object representing the color given with its string name, or 
     * MATLAB color abbreviation.
     * Accepted colors: red (r), green (g), blue (b), black (k), yellow (y), cyan (c), magenta (m).
     * @param colorStr String representation of a color.
     * @return color as JavaFX Paint object.
     */
    public static Paint parseColor(String colorStr) {
        if (Parser.parseStringRef(colorStr.toUpperCase(), "RED") || Parser.parseCharRef(colorStr.toUpperCase(), 'R')) {
            return Color.RED;
        } else if (Parser.parseStringRef(colorStr.toUpperCase(), "GREEN") || Parser.parseCharRef(colorStr.toUpperCase(),'G')) {
            return Color.GREEN;
        } else if (Parser.parseStringRef(colorStr.toUpperCase(), "BLUE") || Parser.parseCharRef(colorStr.toUpperCase(),'B')) {
            return Color.BLUE;
        } else if (Parser.parseStringRef(colorStr.toUpperCase(), "BLACK") || Parser.parseCharRef(colorStr.toUpperCase(),'K')) {
            return Color.BLACK;
        } else if (Parser.parseStringRef(colorStr.toUpperCase(), "YELLOW") || Parser.parseCharRef(colorStr.toUpperCase(),'Y')) {
            return Color.YELLOW;
        } else if (Parser.parseStringRef(colorStr.toUpperCase(), "CYAN") || Parser.parseCharRef(colorStr.toUpperCase(),'C')) {
            return Color.CYAN;
        } else if (Parser.parseStringRef(colorStr.toUpperCase(), "MAGENTA") || Parser.parseCharRef(colorStr.toUpperCase(),'M')) {
            return Color.MAGENTA;
        // if the color string starts with a #, it should be a hex color
        } else if (colorStr.startsWith("#")) {
            return parseHex(colorStr);
        }
        return Color.BLACK;
    }
    
    /**
     * Creates a Javafx Paint object of the color given with hexadecimal format 
     * as String. Example: "#ff0000" corresponds to red.
     * @param colorStr hex code of an RGB encoded color as String.
     * @return color as JavaFX Paint object.
     */
    public static Paint parseHex(String colorStr) {
        try {
            // built in support for this color format
            return Color.web(colorStr);
        } catch (Exception e) {
            // if the parameter is invalid, then display an error and
            // proceed with the default color
            System.err.println("Wrong parameter for color: " + colorStr);
            return Color.BLACK;
        }
    }

    /**
     * Function used to create a BarStyle enum from the style's MATLAB
     * string representation.
     * @param barStr MATLAB format string of line style. Can be: (stacked, grouped)
     * @return BarStyle enum of the given style.
     */
    public static BarStyle parseBarStyle(String barStr) {
	if (Parser.parseStringRef(barStr.toUpperCase(), "STACKED"))
	    return BarStyle.STACKED;
	if (Parser.parseStringRef(barStr.toUpperCase(), "GROUPED"))
	    return BarStyle.GROUPED;
	    
	else
	    return BarStyle.GROUPED;
    }
}
