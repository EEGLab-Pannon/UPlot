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

import javafx.scene.paint.Color;
import hu.unipannon.virt.plot.control.FrameController;
import hu.unipannon.virt.plot.control.PlotSpaceController;
import hu.unipannon.virt.plot.data.AxesSettings;
import hu.unipannon.virt.plot.data.LegendSettings;
import hu.unipannon.virt.plot.data.LegendSettings.LegendPosition;
import hu.unipannon.virt.plot.data.LogSettings;
import hu.unipannon.virt.plot.frame.StrokeStyle;

/**
 * Fluent interface responsible for setting attributes of the frame that are not
 * related to the plot actually displayed. Holds different setting data in 
 * Setting classes
 * 
 * @see Figure
 * @author Tóth Bálint
 */
public class FrameStyle {

    private FrameController controller;
    private AxesSettings axisSettings;
    private LegendSettings legendSettings;
    private LogSettings logSettings;

    /**
     * Initializes every compontent.
     */
    public FrameStyle() {
	controller = new FrameController();
	axisSettings = new AxesSettings();
	legendSettings = new LegendSettings();
	logSettings = new LogSettings();
    }

    /**
     * Query for the controller.
     * @return the frame controller that will be supplied to a plot.
     */
    public FrameController getController() {
	return this.controller;
    }

    /**
     * Query for the Axis settings object.
     * @return axis settings.
     */
    public AxesSettings getAxisSettings() {
	return this.axisSettings;
    }

    /**
     * Query for the Legend settings object.
     * @return legend settings.
     */
    public LegendSettings getLegendSettings() {
	return this.legendSettings;
    }

    /**
     * Query for the possible logarithmic scale settings.
     * @return logarighm settings object.
     */
    public LogSettings getLogSettings() {
	return this.logSettings;
    }


    /**
     * Fluent interface starter method, creates an instance of FrameStyle and 
     * sets the title.
     * @param title title of the plot. (Empty string for no title).
     * @return the newly created instance.
     */
    public static FrameStyle title(String title) {
	FrameStyle res = new FrameStyle();
	res.getController().title(title);
	return res;
    }

    /**
     * Fluent interface method, sets the label of the horizontal axis.
     * @param xlabel text for the horizontal axis.
     * @return itself.
     */
    public FrameStyle xlabel(String xlabel) {
	controller.xLabel(xlabel);
	return this;
    }

    /**
     * Fluent interface method, sets the label of the vertical axis.
     * @param ylabel text for the vertical axis.
     * @return itself.
     */
    public FrameStyle ylabel(String ylabel) {
	controller.yLabel(ylabel);
	return this;
    }

    /**
     * Fluent interface method, sets the grid display mode.
     * @param status true if grid is displayed.
     * @return itself.
     */
    public FrameStyle grid(boolean status) {
	controller.grid(status);
	return this;
    }

    /**
     * Fluent interface method, sets the grid display mode with MATLAB specified
     * format text.
     * @param status "on" for displaying the grid and off for hiding the grid.
     * @return itself.
     */
    public FrameStyle grid(String status) {
        if (status.toUpperCase().equals("ON"))
            controller.grid(true);
        else if (status.toUpperCase().equals("OFF"))
            controller.grid(false);
        return this;
    }

    /**
     * Fluent interface method, sets if all four axes around the plot are displayed.
     * @param status true if box is on.
     * @return itself.
     */
    public FrameStyle box(boolean status) {
	controller.box(status);
	return this;
    }

    /**
     * Fluent interface method, sets if all four axes around the plot are displayed
     * with MATLAB specified format text.
     * @param status "on" for displaying the box and off for only displaying the selected axes.
     * @return itself.
     */
    public FrameStyle box(String status) {
        if (status.toUpperCase().equals("ON"))
            controller.box(true);
        else if (status.toUpperCase().equals("OFF"))
            controller.box(false);
        return this;
    }

    /**
     * Fluent interface method, sets the direction of the axis ticks.
     * @param tickDir tick direction as enumerated value.
     * @return itself.
     */
    public FrameStyle tickDir(FrameController.TickDir tickDir) {
	controller.tickDir(tickDir);
	return this;
    }

    /**
     * Fluent interface method, sets the direction of the axis ticks with the 
     * MATLAB specified direction string.
     * @param tickDir tick direction as text. (in, out, both, none).
     * @return itself.
     */
    public FrameStyle tickDir(String tickDir) {
	if (tickDir.toUpperCase().equals("IN"))
	    controller.tickDir(FrameController.TickDir.IN);
	else if (tickDir.toUpperCase().equals("OUT"))
	    controller.tickDir(FrameController.TickDir.OUT);
	else if (tickDir.toUpperCase().equals("BOTH"))
	    controller.tickDir(FrameController.TickDir.BOTH);
	else if (tickDir.toUpperCase().equals("NONE"))
	    controller.tickDir(FrameController.TickDir.NONE);
	return this;
    }

    /**
     * Fluent interface method, sets the location of the horizontal axis.
     * @param xlocation location of the horizontal axis as enumerated value.
     * @return itself.
     */
    public FrameStyle xAxisLocation(FrameController.XLocation xlocation) {
	controller.xAxisLocation(xlocation);
	return this;
    }

    /**
     * Fluent interface method, sets the location of the horizontal axis with the 
     * MATLAB specified location string.
     * @param xlocation location of the horizontal axis as text (bottom, top, origin).
     * @return itself.
     */
    public FrameStyle xAxisLocation(String xlocation) {
	if (xlocation.toUpperCase().equals("BOTTOM"))
	    controller.xAxisLocation(FrameController.XLocation.BOTTOM);
	else if (xlocation.toUpperCase().equals("TOP"))
	    controller.xAxisLocation(FrameController.XLocation.TOP);
	else if (xlocation.toUpperCase().equals("ORIGIN"))
	    controller.xAxisLocation(FrameController.XLocation.ORIGIN);
	return this;
    }

    /**
     * Fluent interface method, sets the location of the vertical axis.
     * @param ylocation location of the vertical axis as enumerated value.
     * @return itself.
     */
    public FrameStyle yAxisLocation(FrameController.YLocation ylocation) {
	controller.yAxisLocation(ylocation);
	return this;
    }

    /**
     * Fluent interface method, sets the location of the vertical axis with the 
     * MATLAB specified location string.
     * @param ylocation location of the vertical axis as text (left, right, origin).
     * @return itself.
     */
    public FrameStyle yAxisLocation(String ylocation) {
	if (ylocation.toUpperCase().equals("LEFT"))
	    controller.yAxisLocation(FrameController.YLocation.LEFT);
	else if (ylocation.toUpperCase().equals("RIGHT"))
	    controller.yAxisLocation(FrameController.YLocation.RIGHT);
	else if (ylocation.toUpperCase().equals("ORIGIN"))
	    controller.yAxisLocation(FrameController.YLocation.ORIGIN);
	return this;
    }

    /**
     * Fluent interface method, enables the default-generated legend.
     * @return itself.
     */
    public FrameStyle legend() {
	legendSettings.showLegend = true;
	return this;
    }

    /**
     * Fluent interface method, enables the legend at the default location with 
     * the given labels. 
     * @param labels array of labels on the legend. Same order as the data lines they are labeling.
     * @return itself.
     */
    public FrameStyle legend(String... labels) {
	legendSettings.showLegend = true;
	legendSettings.legendLabels = labels;
	return this;
    }

    /**
     * Fluent interface method, enables the legend at the give, location with 
     * the given labels.
     * @see LegendSettings
     * @param location the location of the legend as a LegendPosition object that can be created from a String with the LegendPosition.legend() function.
     * @param labels array of labels on the legend. Same order as the data lines they are labeling.
     * @return itself.
     */
    public FrameStyle legend(LegendPosition location, String... labels) {
	legendSettings.showLegend = true;
	legendSettings.legendLabels = labels;
	legendSettings.legendLocation = location.getLocation();
	return this;
    }

    /**
     * Fluent interface method, sets the extrema of the horizontal axis (end points included).
     * @param min lower end of the axis.
     * @param max higher end of the axis.
     * @return itself.
     */
    public FrameStyle xlim(double min, double max) {
	axisSettings.xlimMin = min;
	axisSettings.xlimMax = max;
	axisSettings.xlimSet = true;
	return this;
    }

    /**
     * Fluent interface method, sets the extrema of the vertical axis (end points included).
     * @param min lower end of the axis.
     * @param max higher end of the axis.
     * @return itself.
     */
    public FrameStyle ylim(double min, double max) {
	axisSettings.ylimMin = min;
	axisSettings.ylimMax = max;
	axisSettings.ylimSet = true;
	return this;
    }

    /**
     * Fluent interface method, sets the tick points of the horizontal axis.
     * @param xTickPoints variadic amount or array of tick points in absolute space.
     * @return itself.
     */
    public FrameStyle xTick(double... xTickPoints) {
	axisSettings.xTickPoints = xTickPoints;
	return this;
    }

    /**
     * Fluent interface method, sets the tick points of the vertcial axis.
     * @param yTickPoints variadic amount or array of tick points in absolute space.
     * @return itself.
     */
    public FrameStyle yTick(double... yTickPoints) {
	axisSettings.yTickPoints = yTickPoints;
	return this;
    }

    /**
     * Fluent interface method, sets the tick labels of the hoizontal axis.
     * @param xTickLabels variadic amount or array of tick labels.
     * @return itself.
     */
    public FrameStyle xTickLabel(String... xTickLabels) {
	axisSettings.xTickLabels = xTickLabels;
	return this;
    }

    /**
     * Fluent interface method, sets the tick labels of the vertical axis.
     * @param yTickLabels variadic amount or array of tick labels.
     * @return itself.
     */
    public FrameStyle yTickLabel(String... yTickLabels) {
	axisSettings.yTickLabels = yTickLabels;
	return this;
    }

    // this one is for the specific log based thing
    /**
     * Fluent interface method, specific to the logarithmically scaled line plot.
     * Sets the log bases of the axes.
     * @param x log base of the hoizontal axis.
     * @param y log base of the vertical axis.
     * @return itself.
     */
    public FrameStyle logBase(int x, int y) {
	logSettings.xLogBase = x;
	logSettings.yLogBase = y;
	return this;
    }

    /**
     * Fluent interface method, specific to the logarithmically scaled line plot.
     * Sets the log base of the hoiztontal axis.
     * @param x log base of the hoizontal axis.
     * @return itself.
     */
    public FrameStyle logBaseX(int x) {
	logSettings.xLogBase = x;
	return this;
    }

    /**
     * Fluent interface method, specific to the logarithmically scaled line plot.
     * Sets the log base of the vertical axis.
     * @param y log base of the vertical axis.
     * @return itself.
     */
    public FrameStyle logBaseY(int y) {
	logSettings.yLogBase = y;
	return this;
    }

    /**
     * Fluent interface method, adds a reference line based on the horizontal axis, 
     * described by the y = m*x + b equation.
     * @param m tangent of the line.
     * @param b offset of the line.
     * @return itself.
     */
    public FrameStyle xRefline(double m, double b) {
	axisSettings.hRefLines.add(new PlotSpaceController.RefLine(m,b,Color.BLACK,0.5,StrokeStyle.NORMAL));
	return this;
    }

    /**
     * Fluent interface method, adds a reference line based on the horizontal axis, 
     * described by the y = m*x + b equation.
     * @param m tangent of the line.
     * @param b offset of the line.
     * @param style style of the line configured with LineStyle's fluent interface.
     * @return itself.
     */
    public FrameStyle xRefline(double m, double b, LineStyle style) {
	axisSettings.hRefLines.add(new PlotSpaceController.RefLine(m,b,style.getColor(),style.getWidth(),style.getStrokeStyle()));
	return this;
    }

    /**
     * Fluent interface method, adds a reference line based on the vertical axis, 
     * described by the x = m*y + b equation.
     * @param m tangent of the line.
     * @param b offset of the line.
     * @return itself.
     */
    public FrameStyle yRefline(double m, double b) {
	axisSettings.vRefLines.add(new PlotSpaceController.RefLine(m,b,Color.BLACK,0.5,StrokeStyle.NORMAL));
	return this;
    }

    /**
     * Fluent interface method, adds a reference line based on the vertical axis, 
     * described by the x = m*y + b equation.
     * @param m tangent of the line.
     * @param b offset of the line.
     * @param style style of the line configured with LineStyle's fluent interface.
     * @return itself.
     */
    public FrameStyle yRefline(double m, double b, LineStyle style) {
	axisSettings.vRefLines.add(new PlotSpaceController.RefLine(m,b,style.getColor(),style.getWidth(),style.getStrokeStyle()));
	return this;
    }

    // kinda semi useless
    /**
     * Specific to the logarithmically scaled line plot, but can be called without
     * chaining it into a fluent interface method call.
     * Sets the log base of the horizontal axis.
     * @param x log base of the horizontal axis.
     */
    public void setLogBaseX(int x) {
	logSettings.xLogBase = x;
    }

    /**
     * Specific to the logarithmically scaled line plot, but can be called without
     * chaining it into a fluent interface method call.
     * Sets the log base of the vertical axis.
     * @param y log base of the vertical axis.
     */
    public void setLogBaseY(int y) {
	logSettings.yLogBase = y;
    }


}
