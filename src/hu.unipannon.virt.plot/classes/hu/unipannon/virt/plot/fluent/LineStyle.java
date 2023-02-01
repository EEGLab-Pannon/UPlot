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
package hu.unipannon.virt.plot.fluent;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import hu.unipannon.virt.plot.frame.Marker;
import hu.unipannon.virt.plot.frame.StrokeStyle;
import hu.unipannon.virt.plot.util.Parser;

/**
 * Fluent interface responsible of storing styling elements to a Line object.
 * Provides functionallity to set the following properties in a single method chain:
 * Style, Color, Width, Marker, Line error.
 * 
 * @author Tóth Bálint
 */
public class LineStyle {

    private Paint color;
    private double width;
    private StrokeStyle style;
    private Marker.MarkerType marker;
    
    private double[] lowerError;
    private double[] upperError;
    
    private boolean errorLines, errorArea;
    private LineStyle errorLineStyle;
    private Paint errorAreaColor;

    /**
     * Default constructor.
     */
    public LineStyle() {
	// factory defaults
	color = null;
	width = Defaults.DEFAULT_LINE_WIDTH;
	style = Defaults.DEFAULT_LINE_STYLE;
	marker = Defaults.DEFAULT_MARKER;
        lowerError = null;
        upperError = null;
        errorLines = false;
        errorArea = false;
        errorLineStyle = null;
        errorAreaColor = color;
    }

    /**
     * Constructs a LineStyle instance with the given stroke style.
     * @param style stroke style of the line.
     */
    public LineStyle(StrokeStyle style) {
	this();
        this.style = style;
    }
    
    /**
     * Constructs a LineStyle instance with the given stroke style as MATLAB
     * format string.
     * @param style stroke style of the line as format string ("-", "-.", ":", "--"). 
     */
    public LineStyle(String style) {
	this();
        this.style = Parser.parseStrokeStyle(style ); 
   }

    /**
     * Fluent interface starter method with empty parameter list.
     * This function can be used to start the method chain without setting a 
     * stroke style. In this case the default line style is used.
     * @return new instance of LineStyle.
     */
    public static LineStyle style() {
	return new LineStyle();
    }
    
    /**
     * Fluent interface starter method that sets the style as MATLAB format string.
     * @param style stroke style of the line as format string ("-", "-.", ":", "--"). 
     * @return new instance of LineStyle.
     */
    public static LineStyle style(String style) {
        return new LineStyle(style);
    }
    
    /**
     * Fluent interface starter method that sets the style.
     * @param style stroke style of the line. 
     * @return new instance of LineStyle.
     */
    public static LineStyle style(StrokeStyle style) {
        return new LineStyle(style);
    }

    /**
     * Fluent interface method, sets the width of the line.
     * @param width width of the line in points.
     * @return itself.
     */
    public LineStyle width(double width) {
	this.width = width;
	return this;
    }

    /**
     * Fluent interface method, sets the color of the line.
     * @param color color of the line as JavaFX Paint.
     * @return itself.
     */
    public LineStyle color(Paint color) {
        this.color = color;
        return this;
    }

    /**
     * Fluent interface method, sets the color of the line.
     * Accepted colors: red (r), green (g), blue (b), black (k), yellow (y), cyan (c), magenta (m).
     * Hexadecimal (web notation) form of RGB colors can also be given to this function.
     * @param color color of the line as MATLAB color string or hex code.
     * @return itself.
     */
    public LineStyle color(String color) {
        this.color = Parser.parseColor(color);
        return this;
    }

    /**
     * Fluent interface method, sets the marker for the line.
     * @param markerType marker as enumerated value (ASTERISK, CIRCLE, CROSS, DOT, PLUS, NONE).
     * @return itself.
     */
    public LineStyle marker(Marker.MarkerType markerType) {
	marker = markerType;
	return this;
    }

    /**
     * Fluent interface method, sets the marker for the line from the given 
     * MATLAB format string.
     * @param markerType marker as string value ("*", "o", "X", ".", "+").
     * @return itself.
     */
    public LineStyle marker(String markerType) {
	marker = Marker.fromString(markerType);
	return this;
    }
    
    /**
     * Sets the measurement error intervals for the data points.
     * @param lower lowest value measured at the given point.
     * @param upper highest value measured at the given point.
     * @return itself.
     */
    public LineStyle error(double[] lower, double[] upper) {
        lowerError = lower;
        upperError = upper;
        return this;
    }
    
    /**
     * Enables error lines. Use this function alongside the error() function.
     * @return itself.
     */
    public LineStyle errorLines() {
        errorLines = true;
        return this;
    }
    
    /**
     * Enables error lines with the given style. 
     * Use this function alongside the error() function.
     * @param style style of the error lines.
     * @return itself.
     */
    public LineStyle errorLines(LineStyle style) {
        errorLines = true;
        errorLineStyle = style;
        return this;
    }
    
    /**
     * Enables error area with the default color. 
     * Use this function alongside the error() function.
     * @return itself.
     */
    public LineStyle errorArea() {
        errorArea = true;
        return this;
    }

    /**
     * Enables error area with the given color. 
     * Use this function alongside the error() function.
     * @param color color of the patch on the plot.
     * @return itself.
     */
    public LineStyle errorArea(Paint color) {
        errorArea = true;
        errorAreaColor = color;
        return this;
    }
    
    /**
     * Query for the error line showing mode.
     * @return true if the error lines are turned on.
     */
    public boolean isShowErrorLines() {
        return errorLines;
    }
    
    /**
     * Query for the error area showing mode.
     * @return true if the error area is turned on.
     */
    public boolean isShowErrorArea() {
        return errorArea;
    }
    
    /**
     * Query for the error line style.
     * @return error line style.
     */
    public LineStyle getErrorLineStyle() {
        if (errorLineStyle == null) {
            errorLineStyle = style("-").color(getColor())
		.width(Defaults.DEFAULT_ERROR_LINE_WIDTH);
        }
        return errorLineStyle;
    }
    
    /**
     * Query for the error area color.
     * @return error area color.
     */
    public Paint getErrorAreaColor() {
        if (errorAreaColor == null) {
            var dummy = (Color)getColor();
            errorAreaColor = Color.rgb((int)(dummy.getRed() * 255.0),
				       (int)(dummy.getGreen() * 255.0),
				       (int)(dummy.getBlue() * 255.0),
				       Defaults.DEFAULT_ERROR_AREA_OPACITY);
        }
        return errorAreaColor;
    }
       
    /**
     * Query for the lower measurement error values.
     * @return lower measurement error values.
     */
    public double[] getLowerError() {
        return lowerError;
    }
    
    /**
     * Query for the higher measurement error values.
     * @return higher measurement error values.
     */
    public double[] getUpperError() {
        return upperError;
    }
    
    /**
     * Query for the color of the line.
     * @return color of the line.
     */
    public Paint getColor() {
        if (color == null)
            color = Defaults.nextFromPalette(); 
        return color;
    }
    
    /**
     * Query for the width of the line.
     * @return width of the line.
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Query for the stroke style of the line.
     * @return stroke style of the line.
     */
    public StrokeStyle getStrokeStyle() {
        return this.style;
    }
    
    /**
     * Query for the marker of the line.
     * @return marker of the line.
     */
    public Marker.MarkerType getMarker() {
        return this.marker;
    }
    
    /**
     * Can be used to set the attributes as string "key;value" pair.
     * @param pair a name and an attribute value separated by a semicolon as a string.
     */
    public void setAttrib(String pair) {
        String[] arr = pair.split(";");
        if (arr.length == 2)
            parseAttrib(arr[0].toUpperCase(), arr[1].toUpperCase());
    }

    private void parseAttrib(String key, String value) {
        // parse the color
        if (key.equals("COLOR")) {
            // uses the color parser function
            this.color = Parser.parseColor(value);
        // parse line width
        } else if (key.equals("LINEWIDTH")) {
            try {
                // if the width is a double value, then sets the value
                this.width = Double.parseDouble(value);
            } catch (Exception e) {
                // otherwise displays an warning and proceeds with the default 
                System.err.println("Wrong parameter for LineWidth: " + value);
                this.width = 1;
            }
        // parse line style
        } else if (key.equals("LINESTYLE")) {
            this.style = Parser.parseStrokeStyle(value);
        } else if (key.equals("MARKER")) {
	    // TODO marker
            this.marker = Marker.fromString(value);
        }
    }
    
}
