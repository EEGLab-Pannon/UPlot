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

import hu.unipannon.virt.plot.data.ScatterData;

/**
 * Stores data for a Scatter Plot.
 * Fluent interface wrapper for the data.ScatterData class.
 * @author Tóth Bálint
 */
public class Series {

    private ScatterData scatterData;

    private Series(double[] xs, double[] ys, ScatterStyle style) {
	scatterData = new ScatterData(xs,ys,
				      style.getMarker(),
				      style.getSize(),
				      style.getColor());
    }

    /**
     * Fluent interface starter method, creates a data series with the given points.
     * @param xs array of x coordinates of the data points.
     * @param ys array of y coordinates of the data points.
     * @return new instance of Series.
     */
    public static Series series(double[] xs, double[] ys) {
	return new Series(xs,ys,new ScatterStyle());
    }

    /**
     * Fluent interface starter method, creates a data series with the given points
     * and style.
     * @param xs array of x coordinates of the data points.
     * @param ys array of y coordinates of the data points.
     * @param style style of the plot.
     * @return new instance of Series.
     */
    public static Series series(double[] xs,
				double[] ys,
				ScatterStyle style) {
	return new Series(xs,ys,style);
    }

    /**
     * Fluent interface starter method, creates a data series with the given points
     * and style as string "key;value" attribute pairs.
     * @param xs array of x coordinates of the data points.
     * @param ys array of y coordinates of the data points.
     * @param style style of the plot.
     * @return new instance of Series.
     */
    public static Series series(double[] xs,
				double[] ys,
				String... style) {
	return new Series(xs,ys,parseAttribs(style));
    }

    private static ScatterStyle parseAttribs(String[] args) {
	ScatterStyle style = new ScatterStyle();
        for (String arg : args) {
            style.setAttrib(arg);
        }
	return style;
    }

    /**
     * Query for wrapped scatter data object.
     * @return scatter data object.
     */
    public ScatterData getScatterData() {
	return scatterData;
    }
}
