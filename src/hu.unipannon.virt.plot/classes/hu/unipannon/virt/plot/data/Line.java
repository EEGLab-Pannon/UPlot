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
package hu.unipannon.virt.plot.data;

import hu.unipannon.virt.plot.frame.Marker;
import hu.unipannon.virt.plot.frame.StrokeStyle;
import java.util.Arrays;

import javafx.scene.paint.Paint;

/** 
 * Data class to store a line as two arrays of double.
 * This class handles the normalization and computation of the extremum points.
 * 
 * @author Tóth Bálint
 */
public class Line {
    // data
    private double[] xs;
    private double[] ys;
    
    // extremes
    private double minx, maxx, miny, maxy;
    // style
    private Paint color;
    private double width;
    private StrokeStyle style;
    private Marker.MarkerType marker;
    
    private Line lowerErrLine = null, upperErrLine = null;
    private Paint errAreaColor = null;
    private boolean showErrorLines = false;
    private boolean showErrorArea = false;

    /**
     * Constructs a line data instance.
     * xs and ys must be same size arrays.
     * @param xs X coordinates of the line's points.
     * @param ys Y coordinates of the line's points.
     * @param color color of the line as JavaFX Paint.
     * @param width width of the line in  points.
     * @param style style of the line.
     * @param marker marker of the line's data points.
     */
    public Line(double[] xs,
		double[] ys,
		Paint color,
		double width,
		StrokeStyle style,
		Marker.MarkerType marker) {
	// data and boilerplate
	this.xs = xs;
	this.ys = ys;
	this.color = color;
	this.width = width;
	this.style = style;
	this.marker = marker;
	if (xs != null && ys != null) {
	    minx = xs[0];
	    maxx = xs[0];
	    miny = ys[0];
	    maxy = ys[0];

	    // calculate the extremes
	    if (xs.length == ys.length) {
		for (int i=0;i<xs.length;i++) {
		    if (xs[i] < minx)
			minx = xs[i];
		    if (xs[i] > maxx)
			maxx = xs[i];
		    if (ys[i] < miny)
			miny = ys[i];
		    if (ys[i] > maxy)
			maxy = ys[i];
		}
	    }
	}
    }
    
    // C style b'by
    /**
     * Set error area parameters for the line.
     * The error area will appear with the specified color and edge curves (lower and upper).
     * The low and up arrays must be the same size as the line's xs and ys arrays
     * specified in the constructor.
     * @param lines if true the error area's edges will be shown as a line with the parameters specified below.
     * @param area if true the error area will be shown as a patch in the color given by areaColor.
     * @param low lower points of the error area (only Y coordinates, as X coordinates are given in the constructor).
     * @param up lower points of the error area (only Y coordinates, as X coordinates are given in the constructor).
     * @param color color of the edge lines as JavaFX Paint object.
     * @param width width of the edge lines in points.
     * @param style style of the edge lines.
     * @param marker markers for the edge lines. (Can be NONE.)
     * @param areaColor color of the error area as JavaFX Paint object.
     */
    public void setError(boolean lines, 
                         boolean area, 
                         double[] low, 
                         double[] up, 
                         Paint color, 
                         double width, 
                         StrokeStyle style, 
                         Marker.MarkerType marker, 
                         Paint areaColor) {
        showErrorLines = lines;
        showErrorArea = area;
        lowerErrLine = new Line(this.xs,low,color,width,style,marker);
        upperErrLine = new Line(this.ys,up,color,width,style,marker);
        errAreaColor = areaColor;
    }
    
    /**
     * Query for the error line display.
     * @return if the error lines are given TRUE in the setError function.
     */
    public boolean isShowErrorLines() {
        return showErrorLines;
    }
    
    /**
     * Query for the error area display.
     * @return if the error area is given TRUE in the setError function.
     */
    public boolean isShowErrorArea() {
        return showErrorArea;
    }

    /**
     * Setter of the error area.
     * @param color color of the area.
     */
    public void setErrorAreaColor(Paint color) {
        this.errAreaColor = color;
    }
    
    /**
     * Query for the lower edge of the error area.
     * @return lower line as Line object.
     */
    public Line getLowerErrorLine() {
        return lowerErrLine;
    }
    
    /**
     * Query for the upper edge of the error area.
     * @return upper line as Line object.
     */
    public Line getUpperErrorLine() {
        return upperErrLine;
    }
    
    /**
     * Query for the error area's color.
     * @return color of the error area.
     */
    public Paint getErrorAreaColor() {
        return errAreaColor;
    }
    
    /**
     * Query for the normalized Y coordinates of the lower error line.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized Y coordinates of the lower error line.
     */
    public double[] getLowerErrorNormal(double min, double max) {
        double span = max - min;
        return Arrays.stream(lowerErrLine.ys)
            .map(x -> Math.max(0,(x - min) / span))
            .toArray(); 
        
    }
    
    /**
     * Query for the normalized Y coordinates of the upper error line.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized Y coordinates of the upper error line.
     */
    public double[] getUpperErrorNormal(double min, double max) {
        double span = max - min;
        return Arrays.stream(upperErrLine.ys)
            .map(x -> Math.min(1,(x - min) / span))
            .toArray(); 
        
    } 

    /**
     * Query for the normalized X coordinates of the contained line.
     * This function will be used for the error lines' X coordinates.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized X coordinates of the line.
     */
    public double[] getNormalXs(double min, double max) {
	double span = max - min;
	return Arrays.stream(xs)
	    .map(x -> (x - min) / span)
	    .toArray();
    }

    /**
     * Query for the normalized Y coordinates of the contained line.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized Y coordinates of the line.
     */
    public double[] getNormalYs(double min, double max) {
	double span = max - min;
	return Arrays.stream(ys)
	    .map(y -> (y - min) / span)
	    .toArray();
    }

    /**
     * Query for the normalized X coordinates of the contained line that are 
     * already transformed into a logarithmic scale.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @param logBase the base of the logarithm used for the X axis.
     * @return array of screen-space normalized X coordinates of the line.
     */
    public double[] getLogNormalXs(double min, double max, int logBase) {
	double logMin = logN(logBase,min);
	double span = logN(logBase,max) - logMin;
	return Arrays.stream(xs)
	    .map(x -> logN(logBase,x))
	    .map(x -> (x - logMin) / span)
	    .toArray();
    }

    /**
     * Query for the normalized Y coordinates of the contained line that are 
     * already transformed into a logarithmic scale.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @param logBase the base of the logarithm used for the Y axis.
     * @return array of screen-space normalized Y coordinates of the line.
     */
    public double[] getLogNormalYs(double min, double max, int logBase) {
	double logMin = logN(logBase,min);
	double span = logN(logBase,max) - logMin;
	return Arrays.stream(ys)
	    .map(y -> logN(logBase,y))
	    .map(y -> (y - logMin) / span)
	    .toArray();
    }

    /**
     * Helper function, calculates N-base logarightm  of a given number.
     * @param logBase base of the logarithm.
     * @param x input of the logarithm.
     * @return value of the N-base logarithm of the input.
     */
    private double logN(int logBase, double x) {
	return Math.log(x) / Math.log(logBase);
    }

    /**
     * Query for the absolute X values.
     * @return X coordinates of the stored data points.
     */
    public double[] getXs() {
	return xs;
    }

    /**
     * Query for the absolute Y values.
     * @return Y coordinates of the stored data points.
     */
    public double[] getYs() {
	return ys;
    }

    /**
     * Query for the lowest value in the X coordinates.
     * @return minimum of the stored X coordinates.
     */
    public double getMinx() {
	return minx;
    }

    /**
     * Query for the highest value in the X coordinates.
     * @return maximum of the stored X coordinates.
     */
    public double getMaxx() {
	return maxx;
    }

    /**
     * Query for the lowest value in the Y coordinates.
     * @return minimum of the stored Y coordinates.
     */
    public double getMiny() {
	return miny;
    }

    /**
     * Query for the highest value in the Y coordinates.
     * @return maximum of the stored Y coordinates.
     */
    public double getMaxy() {
	return maxy;
    }

    /**
     * Query for the line's color.
     * @return color of the stored line.
     */
    public Paint getColor() {
	return color;
    }

    /**
     * Query for the line's width.
     * @return width of the stored line in points.
     */
    public double getWidth() {
	return width;
    }

    /**
     * Query for the line's style.
     * @return stroke style of the stored line in points.
     */
    public StrokeStyle getStyle() {
	return style;
    }

    /**
     * Query for the line's marker type.
     * @return marker of the stored line in points.
     */
    public Marker.MarkerType getMarker() {
	return marker;
    }

    /**
     * Resets the data points and recalculates the min and max points.
     * xs and ys must be same size arrays.
     * @param xs absolute values of the X coordinates of the data points.
     * @param ys absolute values of the Y coordinates of the data points.
     */
    public void setPoints(double[] xs, double[] ys) {
	this.xs = xs;
	this.ys = ys;
	for (int i=0;i<xs.length;i++) {
	    if (xs[i] < minx)
		minx = xs[i];
	    if (xs[i] > maxx)
		maxx = xs[i];
	    if (ys[i] < miny)
		miny = ys[i];
	    if (ys[i] > maxy)
		maxy = ys[i];
	}
    }
}
