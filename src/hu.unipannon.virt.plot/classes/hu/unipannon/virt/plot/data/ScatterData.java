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

import java.util.Arrays;

import javafx.scene.paint.Paint;
import hu.unipannon.virt.plot.frame.Marker;

/**
 * Data class to store a line as two arrays of double.
 * Works exactly like the Line class, with the exception of not containing the
 * graphical line's parameters.
 * 
 * @author Tóth Bálint
 */
public class ScatterData {
    private double[] xs;
    private double[] ys;
    
    // extremes
    private double minx, maxx, miny, maxy;
    private Marker.MarkerType marker;

    private double[] size;
    private Paint[] color;

    /**
     * Constructs a ScatterData instance.
     * xs and ys must be same size arrays.
     * @param xs X coordinates of the series' points.
     * @param ys Y coordinates of the series' points.
     * @param marker marker of the data points.
     * @param size sizes of the different markers representing the data points. If contains 1 element, it will be used for all points.
     * @param color colors of the markers representing the data points. If contains 1 item, it will be used for all points.
     */
    public ScatterData(double[] xs,
		       double[] ys,
		       Marker.MarkerType marker,
		       double[] size,
		       Paint[] color) {
	this.size = size;
	this.color = color;
	this.marker = marker;
	this.xs = xs;
	this.ys = ys;
	this.minx = xs[0];
	this.maxx = xs[0];
	this.miny = ys[0];
	this.maxy = ys[0];

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

    /**
     * Query for the normalized X coordinates of the contained series.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized X coordinates of the data.
     */
    public double[] getNormalXs(double min, double max) {
	double span = max - min;
	return Arrays.stream(xs)
	    .map(x -> (x - min) / span)
	    .toArray();
    }

    /**
     * Query for the normalized Y coordinates of the contained series.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized Y coordinates of the data.
     */
    public double[] getNormalYs(double min, double max) {
	double span = max - min;
	return Arrays.stream(ys)
	    .map(y -> (y - min) / span)
	    .toArray();
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

    public Marker.MarkerType getMarker() {
	return marker;
    }

    /**
     * Query for the data points' size(s).
     * @return size of each stored data point.
     */
    public double[] getSize() {
	return size;
    }

    /**
     * Query for the data series' color(s).
     * @return color of each stored data point
     */
    public Paint[] getColor() {
	return color;
    }
}
