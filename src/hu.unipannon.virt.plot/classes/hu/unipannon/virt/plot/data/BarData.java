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

/**
 * Data class to store a series of data for a bar plot.
 * Only contains Y coordinates (values), these data points will be colored the
 * same on the graphical representation of the plot.
 * 
 * @author Tóth Bálint
 */
public class BarData {

    private double[] ys;
    private Paint color;

    private double min, max;

    /**
     * Constructs a series for the bar with the given Y values and color.
     * @param ys absolute values of the data points.
     * @param color color of the data series.
     */
    public BarData(double[] ys, Paint color) {
	this.ys = ys;
	this.color = color;

	min = ys[0];
	max = ys[0];

	for (int i=0;i<ys.length;i++) {
	    if (ys[i] < min)
		min = ys[i];
	    if (ys[i] > max)
		max = ys[i];
	}
    }

    /**
     * Query for the normalized Y coordinates of the contained series.
     * @param min absolute minimum value of the "display window".
     * @param max absolute maximum value of the "display window".
     * @return array of screen-space normalized Y points of the series' elements.
     */
    public double[] getNormals(double min, double max) {
	double span = max - min;
	return Arrays.stream(ys)
	    .map(y -> y / span)
	    .toArray();
    }

    /**
     * Query for the absolute Y values.
     * @return Y coordinates of the stored data points.
     */
    public double[] getYs() {
	return ys;
    }

    /**
     * Query for the lowest value in the Y coordinates.
     * @return minimum of the stored Y coordinates.
     */
    public double getMin() {
	return min;
    }

    /**
     * Query for the highest value in the Y coordinates.
     * @return maximum of the stored Y coordinates.
     */
    public double getMax() {
	return max;
    }

    /**
     * Query for the series' color.
     * @return color of the stored data series.
     */
    public Paint getColor() {
	return color;
    }

    /**
     * Query for the data's length.
     * @return length of the stored data (array).
     */
    public int getLength() {
	return ys.length;
    }

}
