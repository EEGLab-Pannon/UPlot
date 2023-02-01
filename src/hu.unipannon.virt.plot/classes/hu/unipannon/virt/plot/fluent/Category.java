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

import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Paint;
import hu.unipannon.virt.plot.data.BarData;
import hu.unipannon.virt.plot.frame.BarStyle;
import hu.unipannon.virt.plot.util.Parser;

/**
 * Fluent interface to store BarData objects and corresponding styling data.
 * @author Tóth Bálint
 */
public class Category {
    
    /**
     * Wraps a JavaFX paint object and provides static factory methods to create 
     * it from another Paint object or MATLAB string.
     */
    public static class ColorWrapper {
	private Paint color;

	private ColorWrapper(Paint color) {
	    this.color = color;
	}

        /**
         * Query for the wrapped value.
         * @return the wrapped color.
         */
	public Paint getColor() {
	    return this.color;
	}

        /**
         * Static factory method to create a new ColorWrapper with the given 
         * color object.
         * @param color color value.
         * @return new instance of ColorWrapper.
         */
	public static ColorWrapper color(Paint color) {
	    return new ColorWrapper(color);
	}

        /**
         * Static factory method to create a new ColorWrapper with the given 
         * MATLAB specified color string.
         * Accepted colors: red (r), green (g), blue (b), black (k), yellow (y), cyan (c), magenta (m).
         * Hexadecimal (web notation) form of RGB colors can also be given to this function.
         * @param color color of the line as MATLAB color string or hex code.
         * @return new instance of ColorWrapper.
         */
	public static ColorWrapper color(String color) {
	    return new ColorWrapper(Parser.parseColor(color));
	}

    }

    /**
     * Fluent interface responsible of storing styling elements to a Line object.
     * Provides functionallity to set the following properties in a single method chain:
     * Style, Color, Width, Marker, Line error.
     */
    public static class CategoryStyle {
	private BarStyle style;
	private double width;

	private CategoryStyle() {
	    style = Defaults.DEFAULT_BAR_STYLE;
	    width = Defaults.DEFAULT_BAR_WIDTH;
	}

        /**
         * Fluent interface starter method, creates a new instance with the given 
         * width.
         * @param width width of the bar relaive to the available space. Must be in the [0.0, 1.0] interval.
         * @return new instance of category style.
         */
	public static CategoryStyle width(double width) {
	    CategoryStyle res = new CategoryStyle();
	    res.width = width;

	    return res;
	}

         /**
         * Fluent interface starter method, creates a new instance with the default 
         * width.
         * @return new instance of category style.
         */
	public static CategoryStyle width() {
	    return new CategoryStyle();
	}

        /**
         * Fluent interface method, sets the bar style.
         * @param s style as enumerated value (GROUPED, STACKED).
         * @return itself.
         */
	public CategoryStyle style(BarStyle s) {
	    this.style = s;
	    return this;
	}

        /**
         * Fluent interface method, sets the bar style as MATLAB format string.
         * @param s style as enumerated value ("grouped", "stacked").
         * @return itself.
         */
	public CategoryStyle style(String s) {
	    this.style = Parser.parseBarStyle(s);
	    return this;
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
	    if (key.equals("BARWIDTH")) {
		try {
		    // if the width is a double value, then sets the value
		    this.width = Double.parseDouble(value);
		} catch (Exception e) {
		    System.err.println("Wrong parameter for BarWidth: "
				       + value);
		    this.width = Defaults.DEFAULT_BAR_WIDTH;
		}
	    } else if (key.equals("BARSTYLE")) {
		this.style = Parser.parseBarStyle(value);
	    }
	}
    
    }
    private double[] xs;
    private BarStyle style;
    private double width;

    private List<BarData> data;


    /**
     * Default constructs a Category object.
     * We recommend using the static factory methods instead.
     * @param xs data points.
     * @param style style of the category.
     */
    public Category(double[] xs, CategoryStyle style) {
	this.xs = xs;
	this.style = style.style;
	this.width = style.width;
	data = new LinkedList<>();
    }

    /**
     * Fluent interface starter method, creates a default instance.
     * The domain values will be integers from 1 to the number of data series.
     * @return new instance of Category.
     */
    public static Category category() {
	return new Category(null, new CategoryStyle());
    }

    /**
     * Fluent interface starter method, creates a category with the given 
     * base values.
     * @param xs array of domain points.
     * @return new instance of Category.
     */
    public static Category category(double[] xs) {
	return new Category(xs,new CategoryStyle());
    }

    /**
     * Fluent interface starter method, creates a category with the given 
     * base values and style.
     * @param xs array of domain points.
     * @param s style of the category.
     * @return new instance of Category.
     */
    public static Category category(double[] xs, CategoryStyle s) {
	return new Category(xs,s);
    }

     /**
     * Fluent interface starter method, creates a category with the given 
     * base values and style.
     * @param xs array of domain points.
     * @param s attribute settings ar a variadic array of "key;value" strings.
     * @return new instance of Category.
     */
    public static Category category(double[] xs, String... s) {
	return new Category(xs,parseAttribs(s));
    }

     /**
     * Fluent interface starter method, creates a default instance with the given
     * style.The domain values will be integers from 1 to the number of data series.
     * @param s style of the category.
     * @return new instance of Category.
     */    
    public static Category category(CategoryStyle s) {
	return new Category(null,s);
    }

    /**
     * Fluent interface starter method, creates a default instance with the given
     * style.The domain values will be integers from 1 to the number of data series.
     * @param s attribute settings ar a variadic array of "key;value" strings.
     * @return new instance of Category.
     */  
    public static Category category(String... s) {
	return new Category(null,parseAttribs(s));
    }

    /**
     * Fluent interface method adds a data series to the category.
     * Same index elements of the different series will form a group.
     * @param ys data points.
     * @return itself.
     */
    public Category series(double[] ys) {
	data.add(new BarData(ys,Defaults.nextFromPalette()));
	return this;
    }

    /**
     * Fluent interface method adds a data series to the category.
     * Same index elements of the different series will form a group.
     * @param ys data points.
     * @param cw wrapped color value.
     * @return itself.
     */
    public Category series(double[] ys, ColorWrapper cw) {
	data.add(new BarData(ys,cw.getColor()));
	return this;
    }

    /**
     * Query for bar data.
     * @return bar data.
     */
    public List<BarData> getData() {
	return data;
    }

    /**
     * Query for the domain values.
     * @return array of domain values.
     */
    public double[] getXs() {
	return xs;
    }

    /**
     * Query for the bar style.
     * @return bar style.
     */
    public BarStyle getStyle() {
	return style;
    }

    /**
     * Query for the bar width.
     * @return bar width.
     */
    public double getWidth() {
	return width;
    }

    private static CategoryStyle parseAttribs(String[] args) {
	CategoryStyle style = new CategoryStyle();
        for (String arg : args) {
            style.setAttrib(arg);
        }
	return style;
    }
}
