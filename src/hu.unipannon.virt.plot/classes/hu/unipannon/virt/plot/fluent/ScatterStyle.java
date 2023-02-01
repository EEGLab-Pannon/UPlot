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

import java.util.Arrays;

import javafx.scene.paint.Paint;
import hu.unipannon.virt.plot.frame.Marker;
import hu.unipannon.virt.plot.util.Parser;

/**
 * Fluent interface responsible of storing styling elements to a Series object.
 * Provides functionallity to set the following properties in a single method chain:
 * Color, Width, Marker.
 * @author Tóth Bálint
 */
public class ScatterStyle {

    private Paint[] color;
    private double[] size;
    private Marker.MarkerType marker;

    /**
     * Default constructs a ScatterStyle object.
     */
    public ScatterStyle() {
	color = new Paint[1];
	color[0] = Defaults.nextFromPalette();
	size = new double[1];
	size[0] = Defaults.DEFAULT_MARKER_SIZE;
	marker = Marker.MarkerType.CIRCLE;
    }

    private ScatterStyle(Marker.MarkerType marker) {
	this();
	this.marker = marker;
    }

    /**
     * Fluent interface starter method with empty parameter list.
     * Creates a new instance with the default marker.
     * @return new instance.
     */
    public static ScatterStyle marker() {
	return new ScatterStyle();
    }

    /**
     * Fluent interface starter method with marker setting.
     * @param marker marker as enumerated value (ASTERISK, CIRCLE, CROSS, DOT, PLUS, NONE).
     * @return new instance.
     */
    public static ScatterStyle marker(Marker.MarkerType marker) {
	return new ScatterStyle(marker);
    }

    /**
     * Fluent interface starter method with marker setting as MATLAB format string.
     * @param marker marker as string value ("*", "o", "X", ".", "+").
     * @return new instance.
     */
    public static ScatterStyle marker(String marker) {
	return new ScatterStyle(Marker.fromString(marker));
    }

    /**
     * Fluent interface method. Sets the sizes of the markers representing 
     * data points. If only one value is given, the markers will be uniform size.
     * @param size variadic amout (or array) of sizes.
     * @return itself.
     */
    public ScatterStyle size(double... size) {
	this.size = size;
	return this;
    }

    /**
     * Fluent interface method. Sets the colors of the markers representing 
     * data points. If only one value is given, the markers will be uniform color.
     * @param color variadic amount of colors.
     * @return itself.
     */
    public ScatterStyle color(Paint... color) {
    	this.color = color;
    	return this;
    }

    /**
     * Fluent interface method. Sets the colors of the markers representing 
     * data points. If only one value is given, the markers will be uniform color.
     * Accepted colors: red (r), green (g), blue (b), black (k), yellow (y), cyan (c), magenta (m).
     * @param col variadic amount of color strings.
     * @return itself.
     */
    public ScatterStyle color(String... col) {
	this.color = Arrays.stream(col)
	    .map(c -> Parser.parseColor(c))
	    .toArray(Paint[]::new);
	return this;
    }

    /**
     * Query for marker color array.
     * @return colors.
     */
    public Paint[] getColor() {
	return color;
    }

    /**
     * Query for marker size array.
     * @return sizes
     */
    public double[] getSize() {
	return size;
    }

    /**
     * Query for the marker type.
     * @return marker type.
     */
    public Marker.MarkerType getMarker() {
	return marker;
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
	if (key.equals("SIZE")) {
	    try {
		// if the width is a double value, then sets the value
		this.size = new double[1];
		size[0] = Double.parseDouble(value);
	    } catch (Exception e) {
		System.err.println("Wrong parameter for BarWidth: "
				    + value);
		this.size[0] = Defaults.DEFAULT_MARKER_SIZE;
	    }
	} else if (key.equals("COLOR")) {
	    this.color = new Paint[1]; 
	    this.color[0] = Parser.parseColor(value);
        } else if (key.equals("MARKER")) {
	    this.marker = Marker.fromString(value);
	}
    }
}
