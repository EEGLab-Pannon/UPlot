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


import java.util.LinkedList;
import java.util.List;

import hu.unipannon.virt.plot.data.Line;
import hu.unipannon.virt.plot.frame.Legend;

/**
 * PlotSpaceController implementation responsible of handling a Line Plot.
 * Stores Line data items and displays them inside the given Plot Space.
 * 
 * @see Line
 * @author Tóth Bálint
 */
public class LinePlotController extends PlotSpaceController {

    // line data (and style)
    private List<Line> lines;
    
    // static counter for the data line number
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
    public LinePlotController() {
	super();
	lines = new LinkedList<>();
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
     * Generates the divisor points for the line storage.
     * Uses the whole interval that the lines span across.
     */
    @Override
    protected void genDivPoints() {
	if (!manualXTick) {
	    this.setXTickAuto(AxisGenerator.lpoints(minx,maxx));
	}

	if (!manualYTick) {
	    this.setYTickAuto(AxisGenerator.lpoints(miny, maxy));
	}
	if (miny < 0 && maxy > 0)
	    originX = -miny / (maxy-miny);
	if (minx < 0 && maxx > 0)
	    originY = -minx / (maxx-minx);
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
	// iterate & display
	this.genDivPoints();
        
	for (var l : lines) {
            if (l.isShowErrorArea()) {
                plotSpace.addPatch(l.getNormalXs(minx,maxx),
                                   l.getLowerErrorNormal(miny, maxy),
                                   l.getUpperErrorNormal(miny,maxy), 
                                   l.getErrorAreaColor());
            }
            if (l.isShowErrorLines()) {
                plotSpace.addLine(l.getNormalXs(minx, maxx),
                                  l.getLowerErrorNormal(miny,maxy),
                                  l.getLowerErrorLine().getColor(),
                                  l.getLowerErrorLine().getWidth(),
                                  l.getLowerErrorLine().getStyle(),
                                  l.getLowerErrorLine().getMarker());
                plotSpace.addLine(l.getNormalXs(minx, maxx),
                                  l.getUpperErrorNormal(miny,maxy),
                                  l.getUpperErrorLine().getColor(),
                                  l.getUpperErrorLine().getWidth(),
                                  l.getUpperErrorLine().getStyle(),
                                  l.getUpperErrorLine().getMarker());
            }
	    plotSpace.addLine(l.getNormalXs(minx,maxx),
			      l.getNormalYs(miny,maxy),
			      l.getColor(),
			      l.getWidth(),
			      l.getStyle(),
			      l.getMarker());
	}
	displayRefLines();
	if (showLegend) {
	    Legend legend = genLegend();
	    addLegendToPosition(plotSpace,legend,legendLocation);
	}
    }
}
