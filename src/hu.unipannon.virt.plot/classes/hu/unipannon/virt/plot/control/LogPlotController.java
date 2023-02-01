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

import hu.unipannon.virt.plot.data.Line;
import hu.unipannon.virt.plot.frame.Legend;

/**
 * PlotSpaceController implementation responsible of handling a Logarithmic Line 
 * Plot. Stores Line data items and displays them inside the given Plot Space. 
 * Operates the same way as a LinePlotController.
 * 
 * @see Line
 * @see LinePlotController
 * @author Tóth Bálint
 */
public class LogPlotController extends PlotSpaceController {

    private int logBaseX, logBaseY;

    private List<Line> lines;

    private double[] rawTicksX, rawTicksY;

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
     * Default constructs a PlotSpaceController and inits the line storage.
     */
    public LogPlotController() {
	super();
	lines = new LinkedList<>();
	logBaseX = 10;
	logBaseY = 10;
    }

    /**
     * Adds a line data item.
     * @param l line to be displayed.
     */
    public void addLine(Line l) {
	if (lines.size() == 0) {
	    minx = l.getMinx();
	    maxx = l.getMaxx();
	    miny = l.getMiny();
	    maxy = l.getMaxy();
	    // genDivPoints();
	} else {
	    if (l.getMinx() < minx) {
		minx = l.getMinx();
	    }
	    if (l.getMaxx() > maxx) {
		maxx = l.getMaxx();
	    }
	    if (l.getMiny() < miny) {
		miny = l.getMiny();
	    }
	    if (l.getMaxy() > maxy) {
		maxy = l.getMaxy();
	    }
	}
	lines.add(l);
    }

    /**
     * Sets the logarithm bases for the X and Y axis.
     * If either value is 0, the axis will be linear.
     * @param logBaseX logarightm base of the X axis.
     * @param logBaseY logarightm base of the Y axis.
     */
    public void setLogBase(int logBaseX, int logBaseY) {
	this.logBaseX = logBaseX;
	this.logBaseY = logBaseY;
    }

    /**
     * Sets tick points for the X axis.
     * @param tick tick point values in absolute space.
     */
    @Override
    public void setXTick(double[] tick) {
	rawTicksX = tick;
	manualXLim = true;
	manualXTick = true;
	minx = tick[0];
	maxx = tick[tick.length - 1];
    }

    /**
     * Sets tick points for the Y axis.
     * @param tick tick point values in absolute space.
     */
    @Override
    public void setYTick(double[] tick) {
	rawTicksY = tick;
	manualYLim = true;
	manualYTick = true;
	miny = tick[0];
	maxy = tick[tick.length-1];
    }

    /**
     * Generates the divisor points for the line storage.
     * Uses the whole interval that the lines span across and applies the logarithmic
     * transformation.
     */
    @Override
    protected void genDivPoints() {
	originX = 0;
	originY = 0;
	if (!manualXTick) {
	    this.setXTick(AxisGenerator.lpoints(minx,maxx));
	}

	if (!manualYTick) {
	    this.setYTick(AxisGenerator.lpoints(miny, maxy));
	}
	double logMinx = logN(logBaseX,minx);
	double hspan = logN(logBaseX,maxx) - logMinx;
	double logMiny = logN(logBaseY,miny);
	double vspan = logN(logBaseY,maxy) - logMiny;
	if (logBaseX == 0) {
	    double span = maxx - minx;
	    hDivPoints = Arrays.stream(rawTicksX)
		.map(x -> (x - minx) / span)
		.toArray();
	    if (minx < 0 && maxx > 0)
		originY = -minx / (maxx-minx);
	} else {
	    hDivPoints = Arrays.stream(rawTicksX)
		.map(x -> logN(logBaseX,x))
		.map(x -> (x - logMinx) / hspan)
		.toArray();
	}

	hDivLabels = Arrays.stream(rawTicksX)
	    .mapToObj(String::valueOf)
	    .toArray(String[]::new);

	if (logBaseY == 0) {
	    double span = maxy - miny;
	    vDivPoints = Arrays.stream(rawTicksY)
		.map(x -> (x - miny) / span)
		.toArray();
	    if (miny < 0 && maxy > 0)
		originX = -miny / (maxy-miny);
	} else {
	    vDivPoints = Arrays.stream(rawTicksY)
		.map(x -> logN(logBaseY,x))
		.map(x -> (x - logMiny) / vspan)
		.toArray();
	}

	vDivLabels = Arrays.stream(rawTicksY)
	    .mapToObj(String::valueOf)
	    .toArray(String[]::new);
    }

    /**
     * Generates the legend based on the legend labels and the colors of the
     * lines.
     * @return Legend with the labels and the line representations.
     */
    @Override
    protected Legend genLegend() {
	Legend legend = new Legend();
	if (names == null) {
	    for (var l : lines) {
		legend.addLine(l.getColor(),
			       l.getStyle(),
			       l.getWidth(),
			       l.getMarker(),
			       "data" + getDataLineNum());
	    }
	} else {
	    int i = 0;
	    if (names.length < lines.size())
		return legend;
	    for (var l : lines) {
		legend.addLine(l.getColor(),
			       l.getStyle(),
			       l.getWidth(),
			       l.getMarker(),
			       names[i++]);
	    }
	}
	legend.outline();
	return legend;
    }

    /**
     * Operates the graphics functions in the frame to produce a line plot.
     */
    @Override
    public void display() {
	genDivPoints();
	// loglog
	if (logBaseX != 0 && logBaseY != 0) 
	    for (var l : lines)
		plotSpace.addLine(l.getLogNormalXs(minx,maxx,logBaseX),
				  l.getLogNormalYs(miny,maxy,logBaseY),
				  l.getColor(),
				  l.getWidth(),
				  l.getStyle(),
				  l.getMarker());
	// semilogx
	else if (logBaseX != 0 && logBaseY == 0)
	    for (var l : lines)
		plotSpace.addLine(l.getLogNormalXs(minx,maxx,logBaseX),
				  l.getNormalYs(miny,maxy),
				  l.getColor(),
				  l.getWidth(),
				  l.getStyle(),
				  l.getMarker());

	// semilogy
	else if (logBaseX == 0 && logBaseY != 0)
	    for (var l : lines)
		plotSpace.addLine(l.getNormalXs(minx,maxx),
				  l.getLogNormalYs(miny,maxy,logBaseY),
				  l.getColor(),
				  l.getWidth(),
				  l.getStyle(),
				  l.getMarker());

	displayRefLines();
	if (showLegend) {
	    Legend legend = genLegend();
	    addLegendToPosition(plotSpace,legend,legendLocation);
	}
    }
    
    private double logN(int logBase, double x) {
	return Math.log(x) / Math.log(logBase);
    }
    
}
