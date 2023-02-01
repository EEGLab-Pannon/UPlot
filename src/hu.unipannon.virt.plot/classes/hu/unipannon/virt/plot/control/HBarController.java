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
package hu.unipannon.virt.plot.control;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Paint;
import hu.unipannon.virt.plot.data.BarData;
import hu.unipannon.virt.plot.frame.BarStyle;
import hu.unipannon.virt.plot.frame.Legend;

/**
 * PlotSpaceController implementation responsible of handling a Horizontal Bar Plot.
 * Stores Bar Data items and displays them in the given plot space.
 * 
 * @see BarData
 * @author Tóth Bálint
 */
public class HBarController extends PlotSpaceController {
    private List<BarData> data;
    private double[] xs;

    private double width;
    private int groupCount;
    private BarStyle style;

    private static int dataNum = 0;

    private static int getDataLineNum() {
	int snapshot = dataNum;
	dataNum++;
	return snapshot;
    }

    /**
     * Resets the line numbering for the plot.
     * The counter operated by this function indexes the default palette for the 
     * line colors.
     */
    public static void resetDataLineNumbers() {
	dataNum = 0;
    }

    /**
     * Default constructs a PlotSpaceController and inits the bar data storage.
     */
    public HBarController() {
	super();
	data = new LinkedList<>();
	groupCount = 0;
	xs = null;
	width = 0.8;
	style = BarStyle.GROUPED;
    }
    
    /**
     * Sets the numbering of groups.
     * @param xs array of group identifiers.
     */
    public void addCategories(double[] xs) {
	this.xs = xs;
	groupCount = xs == null ? 0 : xs.length;
    }

    /**
     * Sets the width percentage of the bars.
     * Each bar has a precalculated width available to it based on the number 
     * of groups and data items inside the groups. 
     * @param w value between 0.0 and 1.0 describing the width percentage of the bars.
     */
    public void setWidth(double w) {
	this.width = w;
    }

    /**
     * Sets the bar plot's style.
     * @param s style (group, stack)
     */
    public void setStyle(BarStyle s) {
	this.style = s;
    }

    /**
     * Adds a bar data item to the list.
     * @param d bar data.
     */
    public void addData(BarData d) {
	if (data.isEmpty()) {
	    minx = d.getMin();
	    maxx = d.getMax();
	    groupCount = d.getLength();
	} else {
	    if (d.getLength() != groupCount)
		return;
	    if (d.getMin() < minx)
		minx = d.getMin();
	    if (d.getMax() > maxx)
		maxx = d.getMax();
	}
	this.data.add(d);
    }

    /**
     * Generates the legend based on the legend labels and the colors of the
     * grups.
     * @return Legend with the labels and the bar representations.
     */
    @Override
    protected Legend genLegend() {
	Legend legend = new Legend();
	if (names == null) {
	    for (var d : data) {
		legend.addBar(d.getColor(),
			      "data" + getDataLineNum());
	    }
	} else {
	    int i = 0;
	    if (names.length < data.size())
		return legend;
	    for (var d : data) {
		legend.addBar(d.getColor(),
			      names[i++]);
	    }
	}
	legend.outline();
	return legend;
    }

    /**
     * Generates the divisor points for the data storage.
     */
    @Override
    protected void genDivPoints() {
	vDivPoints = new double[groupCount];
	// fix
	for (int i=0;i<groupCount;i++) {
	    vDivPoints[i] = (double)(i+1) / ((double)groupCount + 1.0);
	}
	miny = 0;
	maxy = 1;
	vDivLabels = Arrays.stream(xs)
	    .mapToObj(x -> String.valueOf(x))
	    .toArray(String[]::new);

	if (!manualXTick) {
	    this.setXTick(AxisGenerator.lpoints(minx,maxx));
	    if (style == BarStyle.STACKED) {
		double posMax = 0;
		double negMax = 0;
		int len = data.get(0).getYs().length;
		for (int i = 0; i < len; i++) {
		    double negSum = 0;
		    double posSum = 0;
		    for (int j = 0; j < data.size(); j++) {
			double val = data.get(j).getYs()[i];
			if (val < 0)
			    negSum += val;
			if (val > 0)
			    posSum += val;
		    }
		    if (negSum < negMax)
			negMax = negSum; 
		    if (posSum > posMax)
			posMax = posSum; 
		}
		minx = Math.min(negMax, 0);
		maxx = Math.max(posMax, 0);
		this.setXTick(AxisGenerator.lpoints(minx,maxx));
	    }
	}

	if (minx < 0 && maxx > 0)
	    originY = -minx / (maxx-minx);
    }

    /**
     * Sets the X tick points in absolute space.
     * @param tick tick points for the X axis.
     */
    @Override
    public void setXTick(double[] tick) {
	super.setXTick(tick);
	manualXLab = true;
    }

    /**
     * Function is invalidated, the bar's y values can't be changed.
     * @param tick ignored.
     */
    @Override
    public void setYTick(double[] tick) {}

    /**
     * Function is invalidated, the bar's y values can't be changed.
     * @param min ignored.
     * @param max ignored.
     */
    @Override
    public void setYLim(double min,double max) {}

    /**
     * Operates the graphics functions in the frame to produce a bar plot.
     */
    @Override
    public void display() {
	// if no domain is specified, then just get some numbers
	if (xs == null) {
	    xs = new double[groupCount];
	    for (int i=0;i<groupCount;i++)
		xs[i] = i+1;
	}
	// generating div stuff
	genDivPoints();

	// turn on the zero line if needed
	if (originY != 0) {
	    plotSpace.getFrame().getVerticalAxis().showOriginLine();
	}


	
	// put all the lines together
	int imLength = data.stream()
	    .map(d -> d.getLength())
	    .reduce(0,(a,b) -> a+b);
	double[] im = new double[imLength];

	int index = 0;
	for (var d : data) {
	    for (var i : d.getNormals(minx,maxx))
		im[index++] = i;
	}

	plotSpace.addHBar(vDivPoints,
			 im,
			 originY,
			 data.stream()
			 .map(d -> d.getColor())
			 .toArray(Paint[]::new),
			 width,
			 style);
	
	displayRefLines();
	if (showLegend) {
	    Legend legend = genLegend();
	    addLegendToPosition(plotSpace,legend,legendLocation);
	}
    }

}
