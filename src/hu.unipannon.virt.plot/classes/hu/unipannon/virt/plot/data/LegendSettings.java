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

import hu.unipannon.virt.plot.control.PlotSpaceController;
import hu.unipannon.virt.plot.control.PlotSpaceController.LegendLocation;

/**
 * Wrapper class storing possible Legend related data and settings that can be 
 * used in the controller classes.
 * 
 * 
 * @see PlotSpaceController
 * @author Tóth Bálint
 */
public class LegendSettings {

    /**
     * Wrapper for a LegendLocation enum.
     */
    public static class LegendPosition {

	private LegendLocation location;
	
        /**
         * Creates a new LegendPosition instance with the given location enum value.
         * @param location value of the instance.
         */
	public LegendPosition(LegendLocation location) {
	    this.location = location;
	}
	
        /**
         * Static factory method.
         * @param l location value.
         * @return new instance of LegendPosition.
         */
	public static LegendPosition location(LegendLocation l) {
	    return new LegendPosition(l);
	}

        /**
         * Static factory method to construct a LegendPosition from a string.
         * @param l string representation of the legend locations.
         * @return new instance of LegendPosition with the given value.
         */
	public static LegendPosition location(String l) {
	    if (l.toUpperCase().equals("NORTH"))
		return new LegendPosition(LegendLocation.NORTH);
	    else if (l.toUpperCase().equals("SOUTH"))
		return new LegendPosition(LegendLocation.SOUTH);
	    else if (l.toUpperCase().equals("EAST"))
		return new LegendPosition(LegendLocation.EAST);
	    else if (l.toUpperCase().equals("WEST"))
		return new LegendPosition(LegendLocation.WEST);
	    else if (l.toUpperCase().equals("NORTHEAST"))
		return new LegendPosition(LegendLocation.NORTHEAST);
	    else if (l.toUpperCase().equals("NORTHWEST"))
		return new LegendPosition(LegendLocation.NORTHWEST);
	    else if (l.toUpperCase().equals("SOUTHEAST"))
		return new LegendPosition(LegendLocation.SOUTHEAST);
	    else if (l.toUpperCase().equals("SOUTHWEST"))
		return new LegendPosition(LegendLocation.SOUTHWEST);
	    else if (l.toUpperCase().equals("CENTER"))
		return new LegendPosition(LegendLocation.CENTER);
	    else
		return new LegendPosition(LegendLocation.NORTHEAST);
	}

        /**
         * Query for the wrapped value.
         * @return value of location attribute.
         */
	public LegendLocation getLocation() {
	    return location;
	}
    }
    
    /**
     * Labels on the legend box.
    */
    public String[] legendLabels;
    /**
     * Legend activation flag. True if the legend is showing on the plot space.
     */
    public boolean showLegend;
    
    /**
     * Location of the legend in the Plot Space.
     */
    public LegendLocation legendLocation;


    /**
     * Default constructs a LegendSettings object.
     */
    public LegendSettings() {
	showLegend = false;
	legendLabels = null;
	legendLocation = LegendLocation.NORTHEAST;
    }

    /**
     * Exports the stored value to a PlotSpaceController implementation.
     * @param controller controller the values are applied to.
     */
    public void applyTo(PlotSpaceController controller) {
	if (showLegend) {
	    controller.showLegend(true);
	    controller.setLegendLabels(legendLabels);
	    controller.setLegendLocation(legendLocation);
	}
    }

}
