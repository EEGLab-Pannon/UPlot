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
package hu.unipannon.virt.plot.frame;

import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import hu.unipannon.virt.plot.util.Parser;

/**
 * Represents a marker item that can be positioned on the Plot Space.
 * Can be used for line item markers, or just by itself in the case of the 
 * Scatter Plot.
 * 
 * @author Tóth Bálint
 */
public class Marker {
    
    /**
     * All the possible types of markers currently implemented.
     * NONE refers to the empty marker.
     */
    public static enum MarkerType {
        ASTERISK,
        CIRCLE,
        CROSS,
        DOT,
        PLUS,
        NONE,
    }
    
    private double size;
    private double width;
    private Paint color;
    
    // one marker to rule them all
    public Marker() {}

    /**
     * Translates the MATLAB format string representation of a marker to the
     * MarkerType enum.
     * @param str MATLAB character code of a marker, can be: +, X, *, O, '.'
     * @return MarkerType representation of the character, or NONE if character is invalid.
     */
    public static MarkerType fromString(String str) {
	String s = str.toUpperCase();
        if      (Parser.parseCharRef(s, '+'))
            return MarkerType.PLUS;
        else if (Parser.parseCharRef(s, 'X'))
            return MarkerType.CROSS;
        else if (Parser.parseCharRef(s, '*'))
            return MarkerType.ASTERISK;
        else if (Parser.parseCharRef(s, 'O'))
            return MarkerType.CIRCLE;
        else if (Parser.parseCharRef(s, '.'))
            return MarkerType.DOT;
        else 
            return MarkerType.NONE;
    }
    
    // one way to set size & width
    /**
     * Attribute settings for a marker. 
     * @param size size of the marker in points.
     * @param width width of the marker line in points.
     * @param color color of the marker in JavaFX Paint object.
     * @return itself, so the method can be chained after a constructor or factory method.
     */
    public Marker setAttribs(double size, double width, Paint color) {
        this.size = new Size(size, SizeUnits.PT).pixels();
        this.width = new Size(width, SizeUnits.PT).pixels();
        this.color = color;
        return this;
    }
    
    // one way to get the size
    /**
     * Queries the size of the marker in points.
     * @return size of the marker in points.
     */
    public double getSize() {
        return size;
    }
    
    // one way to draw it depending on the type
    /**
     * Factory method that creates a JavaFX Group object of graphical items 
     * representing a marker given by the type.
     * @param type type of the marker in MarkerType enum.
     * @return JavaFX Group representation of the marker with the given parameters (color, width, size).
     */
    public Group draw(MarkerType type) {
        Group result;
        switch (type) {
            case ASTERISK:
                result = asterisk();
                break;
            case CIRCLE:
                result = circle();
                break;
            case CROSS:
                result = cross();
                break;
            case DOT:
                result = dot();
                break;
            case PLUS:
                result = plus();
                break;
            case NONE:
                result = new Group();
                break;
            default:
                result = new Group();
                break;
        }
        return result;
    }
    
    /**
     * Creates a Group representing an asterisk (*) type marker.
     * @return JavaFX group of the marker.
     */
    private Group asterisk() {
        Group symbol = new Group();
        Line horizontal = new Line(0,size/2,size,size/2);
        Line vertical = new Line(size/2,0,size/2,size);
        horizontal.setStroke(color);
        horizontal.setStrokeWidth(width);
        vertical.setStroke(color);
        vertical.setStrokeWidth(width);
   
        double sin45 = Math.sqrt(2.d) / 2.d;
        Line cross1 = new Line(
                  (size/2.d) - (sin45*(size/2.d))
                , (size/2.d) + (sin45*(size/2.d))
                , (size/2.d) + (sin45*(size/2.d))
                , (size/2.d) - (sin45*(size/2.d)));
        cross1.setStroke(color);
        cross1.setStrokeWidth(width);
        Line cross2 = new Line(
                  (size/2.d) - (sin45*(size/2.d))
                , (size/2.d) - (sin45*(size/2.d))
                , (size/2.d) + (sin45*(size/2.d))
                , (size/2.d) + (sin45*(size/2.d)));

        cross2.setStroke(color);
        cross2.setStrokeWidth(width);
        symbol.getChildren().addAll(horizontal,vertical,cross1,cross2);
        return symbol;
    }
    
    /**
     * Creates a Group representing a circle (O) type marker.
     * @return JavaFX group of the marker.
     */
    private Group circle() {
        Group symbol = new Group();
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(size/2.d,size/2.d,size/2.d);
        circle.setStroke(color);
        circle.setStrokeWidth(width);
        circle.setFill(null);
        symbol.getChildren().add(circle);
        return symbol;
    }
    
    /**
     * Creates a Group representing a cross (X) type marker.
     * @return JavaFX group of the marker.
     */
    private Group cross() {
        Group symbol = new Group();
        Line l1 = new Line(0,0,size,size);
        Line l2 = new Line(0,size,size,0);
        l1.setStroke(color);
        l1.setStrokeWidth(width);
        l2.setStroke(color);
        l2.setStrokeWidth(width);
        symbol.getChildren().addAll(l1,l2);
        return symbol;
    }
    
    /**
     * Creates a Group representing a dot (.) type marker.
     * @return JavaFX group of the marker.
     */
    private Group dot() {
        Group symbol = new Group();
        Circle circle = new Circle(size/2.d,size/2.d,size/4.d);
        circle.setStroke(color);
        circle.setStrokeWidth(width);
        circle.setFill(color);
        symbol.getChildren().add(circle);
        return symbol;
    }
    
    /**
     * Creates a Group representing a plus (+) type marker.
     * @return JavaFX group of the marker.
     */
    private Group plus() {
        Group symbol = new Group();
        Line horizontal = new Line(0,size/2,size,size/2);
        Line vertical = new Line(size/2,0,size/2,size);
        horizontal.setStroke(color);
        horizontal.setStrokeWidth(width);
        vertical.setStroke(color);
        vertical.setStrokeWidth(width);
        symbol.getChildren().addAll(horizontal,vertical);
        return symbol;
    }
}
