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

import hu.unipannon.virt.plot.data.ScatterData;
import hu.unipannon.virt.plot.frame.Legend;

/**
 * PlotSpaceController implementation responsible of handling a Scatter Plot.
 * Stores Scatter Data items and displays them in the given plot space.
 * 
 * @see ScatterData
 * @author Tóth Bálint
 */
public class ScatterPlotController extends PlotSpaceController {

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

    private List<ScatterData> lines;

    /**
     * Default constructs a PlotSpaceController and inits the line storage.
     */
    public ScatterPlotController() {
	super();
	lines = new LinkedList<>();
    }

    /**
     * Adds a scatter data object.
     * @param l scatter data line.
     */
    public void addData(ScatterData l) {
	if (lines.size() == 0) {
	    minx = l.getMinx();
	    maxx = l.getMaxx();
	    miny = l.getMiny();
	    maxy = l.getMaxy();
	    genDivPoints();
	} else {
	    boolean genNew = false;
	    if (l.getMinx() < minx) {
		minx = l.getMinx();
		genNew = true;
	    }
	    if (l.getMaxx() > maxx) {
		maxx = l.getMaxx();
		genNew = true;
	    }
	    if (l.getMiny() < miny) {
		miny = l.getMiny();
		genNew = true;
	    }
	    if (l.getMaxy() > maxy) {
		maxy = l.getMaxy();
		genNew = true;
	    }
	    if (genNew) {
		genDivPoints();
	    }
	}
	lines.add(l);
    }

    /**
     * Generates the legend based on the legend labels and the colors of the
     * data markers.
     * @return Legend with the labels and the marker representations.
     */
    @Override
    protected Legend genLegend() {
	Legend legend = new Legend();
	if (names == null) {
	    for (var l : lines) {
		legend.addMarker(l.getColor()[0],
			       l.getMarker(),
			       "data" + getDataLineNum());
	    }
	} else {
	    int i = 0;
	    if (names.length < lines.size())
		return legend;
	    for (var l : lines) {
		legend.addMarker(l.getColor()[0],
			       l.getMarker(),
			       names[i++]);
	    }
	}
	legend.outline();
	return legend;
    }

    /**
     * Generates the divisor points for the data line storage.
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
     * Operates the graphics functions in the frame to produce a scatter plot.
     */
    @Override
    public void display() {
	for (var l : lines) {
	    plotSpace.addScatter(l.getNormalXs(minx, maxx),
				 l.getNormalYs(miny, maxy),
				 l.getSize(),
				 l.getColor(),
				 l.getMarker());
	}
	displayRefLines();
	if (showLegend) {
	    Legend legend = genLegend();
	    addLegendToPosition(plotSpace,legend,legendLocation);
	}
    }

}
