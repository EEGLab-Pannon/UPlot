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

import java.util.LinkedList;
import java.util.List;
import hu.unipannon.virt.plot.control.PlotSpaceController;

/**
 * Wrapper class storing possible Axis related data and settings that can be 
 * used in the controller classes.
 * 
 * @see PlotSpaceController
 * @author Tóth Bálint
 */
public class AxesSettings {
    /**
     * Array of X tick point locations in absolute space. (Not normalized)
     */
    public double[] xTickPoints;
    /**
     * Array of X tick point locations in absolute space. (Not normalized)
     */
    public double[] yTickPoints;
    /**
     * Array of X tick Labels.
     */
    public String[] xTickLabels;
    /**
     * Array of X tick Labels.
     */
    public String[] yTickLabels;
    /**
     * X axis minimum point in absolute value.
     */
    public double xlimMin; 
    /**
     * X axis maximum point in absolute value.
     */
    public double xlimMax;
    /**
     * Y axis minimum point in absolute value.
     */
    public double ylimMin;
    /**
     * Y axis maximum point in absolute value.
     */
    public double ylimMax;
    /**
     * True if X limits are set manually. Otherwise the controller will use 
     * default values.
     */
    public boolean xlimSet;
    /**
     * True if Y limits are set manually. Otherwise the controller will use 
     * default values.
     */
    public boolean ylimSet;
    
    /**
     * List of horizontal based reference lines.
     */
    public List<PlotSpaceController.RefLine> hRefLines;
    /**
     * List of horizontal based reference lines.
     */
    public List<PlotSpaceController.RefLine> vRefLines;

    /**
     * Default constructor, sets every field tu null, empty or 0.
     */
    public AxesSettings() {
	xTickLabels = null;
	xTickPoints = null;
	yTickLabels = null;
	yTickPoints = null;
	xlimMin = 0;
	ylimMin = 0;
	xlimMax = 0;
	ylimMax = 0;
	xlimSet = false;
	ylimSet = false;

	hRefLines = new LinkedList<>();
	vRefLines = new LinkedList<>();
    }

    // interface
    /**
     * Puts all data items in the given controller.
     * @param controller the controller the data is applied to.
     */
    public void applyTo(PlotSpaceController controller) {
	if (xTickPoints != null) 
	    controller.setXTick(xTickPoints);
	if (yTickPoints != null)
	    controller.setYTick(yTickPoints);

	if (xTickLabels != null)
	    controller.setXTickLabels(xTickLabels);
	if (yTickLabels != null)
	    controller.setYTickLabels(yTickLabels);

	if (xlimSet)
	    controller.setXLim(xlimMin,xlimMax);

	if (ylimSet)
	    controller.setYLim(ylimMin,ylimMax);

	for (var l : hRefLines) {
	    controller.addHRefLine(l);
	}

	for (var l : vRefLines) {
	    controller.addVRefLine(l);
	}
    }
}
