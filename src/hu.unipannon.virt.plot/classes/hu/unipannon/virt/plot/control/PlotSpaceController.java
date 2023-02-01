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

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Paint;
import hu.unipannon.virt.plot.data.Line;
import hu.unipannon.virt.plot.frame.Legend;
import hu.unipannon.virt.plot.frame.Marker.MarkerType;
import hu.unipannon.virt.plot.frame.PlotSpace;
import hu.unipannon.virt.plot.frame.StrokeStyle;

/**
 * This abstract class provides the plot-specific control functionallity inside
 * a Frrame. General purpose functions are implemented here, the ones varying 
 * by the Plot type are abstract.
 * 
 * @see FrameController
 * @author Tóth Bálint
 */
public abstract class PlotSpaceController {

    /**
     * Possible mounting points for the legend box specified by MATLAB.
     * The legend can only be positioned inside the plot space.
     */
    public static enum LegendLocation {
	NORTH,
	SOUTH,
	EAST,
	WEST,
	NORTHEAST,
	NORTHWEST,
	SOUTHEAST,
	SOUTHWEST,
	CENTER
    }

    protected PlotSpace plotSpace;

    protected String[] names;

    protected boolean showLegend = false;

    protected LegendLocation legendLocation = LegendLocation.NORTHEAST;


    // global extremes
    protected double minx, maxx, miny, maxy;
    protected double originX, originY;

    protected boolean
	manualXTick, manualYTick,
	manualXLab, manualYLab,
	manualXLim, manualYLim;

    // data for the outside
    protected double[] hDivPoints;
    protected String[] hDivLabels;

    protected double[] vDivPoints;
    protected String[] vDivLabels;


    // reference lines
    /**
     * Class to store properties of reference lines.
     * Reference lines are only a controller level construct, because the graphics
     * subsystem can handle line drawing. The reference lines can have the same 
     * properties as normal lineplot lines.
     */
    public static class RefLine {
	private Line line;
	private double m;
	private double b;

        /**
         * Creates a reference line described by the y = m*x + b equation that has
         * the given properties.
         * @param m tangent of the line.
         * @param b offset of the line.
         * @param color color of the line.
         * @param width width of the line in points.
         * @param style style of the line.
         */
	public RefLine(double m, double b, Paint color, double width, StrokeStyle style) {
	    line = new Line(null,null,color,width,style,MarkerType.NONE);
	    this.m = m;
	    this.b = b;
	}
    }

    /**
     * Drop-in type for a multiple return value function.
     * Represents an optional point.
     */
    private static class IntersectPoint {
	private boolean isIntersecting;
	private double x,y;

	private IntersectPoint(boolean isIntersecting, double x, double y) {
	    this.isIntersecting = isIntersecting;
	    this.x = x;
	    this.y = y;
	}
    }

    private List<RefLine> hReferenceLines;
    private List<RefLine> vReferenceLines;
    

    /**
     * Default constructs a controller with 0 and false default values.
     */
    protected PlotSpaceController() {
	minx = 0;
	miny = 0;
	maxx = 0;
	maxy = 0;

	originX = 0;
	originY = 0;

	manualXTick = false;
	manualYTick = false;
	manualXLab = false;
	manualYLab = false;
	manualXLim = false;
	manualYLim = false;

	hReferenceLines = new LinkedList<>();
	vReferenceLines = new LinkedList<>();
    }

    /**
     * Sets the stored plot space object.
     * @param plotSpace plot space.
     */
    public void setPlotSpace(PlotSpace plotSpace) {
	this.plotSpace = plotSpace;
    }

    /**
     * Sets the text values for the legend to display.
     * The items given in the list appeare from top to bottom.
     * @param labels array of label texts.
     */
    public void setLegendLabels(String[] labels) {
	this.names = labels;
    }

    /**
     * Sets the location of the legend inside the plot space.
     * @param location legend location.
     */
    public void setLegendLocation(LegendLocation location) {
	this.legendLocation = location;
    }

    /**
     * Turn on legend display.
     * @param val true means legend displayed.
     */
    public void showLegend(boolean val) {
	showLegend = val;
    }

    /**
     * Sets the horizontal axis' bounding values.
     * Min must be lower than max.
     * @param min lowest point of the x axis.
     * @param max highest point of the x axis.
     */
    public void setXLim(double min, double max) {
	manualXLim = true;
	minx = min;
	maxx = max;
    }
    
    /**
     * Sets the vertical axis' bounding values.
     * Min must be lower than max.
     * @param min lowest point of the y axis.
     * @param max highest point of the y axis.
     */
    public void setYLim(double min, double max) {
	manualYLim = true;
	miny = min;
	maxy = max;
    }

    /**
     * Sets the tick points of the horizontal axis.
     * @param tick array of division points in absoulte space.
     */
    public void setXTick(double[] tick) {
	manualXLim = true;
	manualXTick = true;
	//hDivPoints = tick;
	minx = tick[0];
	maxx = tick[tick.length - 1];
	final double span = maxx - minx;
	hDivPoints = Arrays.stream(tick)
	    .map(x -> (x - minx) / span)
	    .toArray();
	hDivLabels = Arrays.stream(tick)
	    .mapToObj(String::valueOf)
	    .toArray(String[]::new);
    }

    /**
     * Sets the tick points of the vertical axis.
     * @param tick array of division points in absoulte space.
     */
    public void setYTick(double[] tick) {
	manualYLim = true;
	manualYTick = true;
	//vDivPoints = tick;
	miny = tick[0];
	maxy = tick[tick.length-1];
	final double span = maxy - miny;
	vDivPoints = Arrays.stream(tick)
	    .map(x -> (x - miny) / span)
	    .toArray();
	vDivLabels = Arrays.stream(tick)
	    .mapToObj(String::valueOf)
	    .toArray(String[]::new);
    }

    /**
     * Sets the labels for every tick point on the horizontal axis. 
     * @param lab array of label texts.
     */
    public void setXTickLabels(String[] lab) {
	manualXLab = true;
	hDivLabels = lab;
    }

    /**
     * Sets the labels for every tick point on the vertical axis. 
     * @param lab array of label texts.
     */
    public void setYTickLabels(String[] lab) {
	manualYLab = true;
	vDivLabels = lab;
    }
    
  
    protected void addLegendToPosition(PlotSpace plotSpace,
				       Legend legend,
				       LegendLocation location) {
	switch (legendLocation) {
	case CENTER:
	    plotSpace.addLegend(legend,HPos.CENTER,VPos.CENTER);
	    break;
	case EAST:
	    plotSpace.addLegend(legend,HPos.RIGHT,VPos.CENTER);
	    break;
	case NORTH:
	    plotSpace.addLegend(legend,HPos.CENTER,VPos.TOP);
	    break;
	case NORTHEAST:
	    plotSpace.addLegend(legend,HPos.RIGHT,VPos.TOP);
	    break;
	case NORTHWEST:
	    plotSpace.addLegend(legend,HPos.LEFT,VPos.TOP);
	    break;
	case SOUTH:
	    plotSpace.addLegend(legend,HPos.CENTER,VPos.BOTTOM);
	    break;
	case SOUTHEAST:
	    plotSpace.addLegend(legend,HPos.RIGHT,VPos.BOTTOM);
	    break;
	case SOUTHWEST:
	    plotSpace.addLegend(legend,HPos.LEFT,VPos.BOTTOM);
	    break;
	case WEST:
	    plotSpace.addLegend(legend,HPos.LEFT,VPos.CENTER);
	    break;
	default:
	    break;
	}
    }

    /**
     * Adds a horizontal based reference line described by the y = m*x + b equation.
     * @param m tangent of the line.
     * @param b offset of the line.
     * @param color color of the line.
     * @param width width of the line in points.
     * @param style style of the line.
     */
    public void addHRefLine(double m, double b, Paint color, double width, StrokeStyle style) {
	hReferenceLines.add(new RefLine(m,b,color,width,style));
    }

    /**
     * Adds a vertical based reference line described by the x = m*y + b equation.
     * @param m tangent of the line.
     * @param b offset of the line.
     * @param color color of the line.
     * @param width width of the line in points.
     * @param style style of the line.
     */
    public void addVRefLine(double m, double b, Paint color, double width, StrokeStyle style) {
	vReferenceLines.add(new RefLine(m,b,color,width,style));
    }

    /**
     * Adds a horizontal based reference line described by the given reference
     * line object.
     * @param l object storing reference line properties.
     */
    public void addHRefLine(RefLine l) {
	hReferenceLines.add(l);
    }

    /**
     * Adds a horizontal based reference line described by the given reference
     * line object.
     * @param l object storing reference line properties.
     */
    public void addVRefLine(RefLine l) {
	vReferenceLines.add(l);
    }

    // to call this function, min and max values must already be set to their final values
    protected void displayRefLines() {
	displayHRefLines();
	displayVRefLines();
    }

    private void displayVRefLines() {
	for (var l : vReferenceLines) {

	    var left = lineLine(minx,miny,minx,maxy,
				l.m*miny+l.b,miny,l.m*maxy+l.b,maxy);
	    var right = lineLine(maxx,miny,maxx,maxy,
				l.m*miny+l.b,miny,l.m*maxy+l.b,maxy);
	    var bot = lineLine(minx,miny,maxx,miny,
				l.m*miny+l.b,miny,l.m*maxy+l.b,maxy);
	    var top = lineLine(minx,maxy,maxx,maxy,
				l.m*miny+l.b,miny,l.m*maxy+l.b,maxy);

	    // System.out.println("collision checking: "
	    // 		       + (left.isIntersecting ? (" left " + left.x + "," + left.y + " " ) : "")
	    // 		       + (right.isIntersecting ? (" right " + right.x + "," + right.y + " " ) : "")
	    // 		       + (bot.isIntersecting ? (" bot " + bot.x + "," + bot.y + " " ) : "")
	    // 		       + (top.isIntersecting ? (" top " + top.x + "," + top.y + " " ) : "")
	    // 		       );

	    List<Double> rxs = new LinkedList<>();
	    List<Double> rys = new LinkedList<>();
	    int pointCount = 0;
	    if (left.isIntersecting) {
		rxs.add(left.x);
		rys.add(left.y);
		pointCount++;
	    }
	    if (right.isIntersecting) {
		rxs.add(right.x);
		rys.add(right.y);
		pointCount++;
	    }
	    if (bot.isIntersecting) {
		rxs.add(bot.x);
		rys.add(bot.y);
		pointCount++;
	    }
	    if (top.isIntersecting) {
		rxs.add(top.x);
		rys.add(top.y);
		pointCount++;
	    }

	    if (pointCount >= 2) {
		double[] arxs = new double[rxs.size()];
		for (int i=0;i<rxs.size();i++) arxs[i] = rxs.get(i);
		double[] arys = new double[rys.size()];
		for (int i=0;i<rys.size();i++) arys[i] = rys.get(i);
		l.line.setPoints(arxs,arys);
		plotSpace.addLine(l.line.getNormalXs(minx,maxx),
				  l.line.getNormalYs(miny,maxy),
				  l.line.getColor(),
				  l.line.getWidth(),
				  l.line.getStyle(),
				  l.line.getMarker());
	    }
	    
	}
    }
    private void displayHRefLines() {
	for (var l : hReferenceLines) {

	    var left = lineLine(minx,miny,minx,maxy,
				minx,l.m*minx+l.b,maxx,l.m*maxx+l.b);
	    var right = lineLine(maxx,miny,maxx,maxy,
				minx,l.m*minx+l.b,maxx,l.m*maxx+l.b);
	    var bot = lineLine(minx,miny,maxx,miny,
				minx,l.m*minx+l.b,maxx,l.m*maxx+l.b);
	    var top = lineLine(minx,maxy,maxx,maxy,
				minx,l.m*minx+l.b,maxx,l.m*maxx+l.b);

	    // System.out.println("collision checking: "
	    // 		       + (left.isIntersecting ? (" left " + left.x + "," + left.y + " " ) : "")
	    // 		       + (right.isIntersecting ? (" right " + right.x + "," + right.y + " " ) : "")
	    // 		       + (bot.isIntersecting ? (" bot " + bot.x + "," + bot.y + " " ) : "")
	    // 		       + (top.isIntersecting ? (" top " + top.x + "," + top.y + " " ) : "")
	    // 		       );

	    List<Double> rxs = new LinkedList<>();
	    List<Double> rys = new LinkedList<>();
	    int pointCount = 0;
	    if (left.isIntersecting) {
		rxs.add(left.x);
		rys.add(left.y);
		pointCount++;
	    }
	    if (right.isIntersecting) {
		rxs.add(right.x);
		rys.add(right.y);
		pointCount++;
	    }
	    if (bot.isIntersecting) {
		rxs.add(bot.x);
		rys.add(bot.y);
		pointCount++;
	    }
	    if (top.isIntersecting) {
		rxs.add(top.x);
		rys.add(top.y);
		pointCount++;
	    }

	    if (pointCount >= 2) {
		double[] arxs = new double[rxs.size()];
		for (int i=0;i<rxs.size();i++) arxs[i] = rxs.get(i);
		double[] arys = new double[rys.size()];
		for (int i=0;i<rys.size();i++) arys[i] = rys.get(i);
		l.line.setPoints(arxs,arys);
		plotSpace.addLine(l.line.getNormalXs(minx,maxx),
				  l.line.getNormalYs(miny,maxy),
				  l.line.getColor(),
				  l.line.getWidth(),
				  l.line.getStyle(),
				  l.line.getMarker());
	    }
	    
	}
    }

    // interface functions
    /**
     * Must rewrite this in order to produce the correct graphical items inside
     * a legend.
     * @return legend object ready to be displayed.
     */
    protected abstract Legend genLegend();
    
    /**
     * Generates the correct normalized division points on the X and Y axes.
     */
    protected abstract void genDivPoints();
    
    /**
     * Finalises every initialisation steps and genereate the inner plot space object.
     */
    public abstract void display();


    /**
     * Query for the horizontal tick labels.
     * @return array of label texts.
     */
    public String[] getHDivLabels() {
	return hDivLabels;
    }

    /**
     * Query for the horizontal tick points in absolute space.
     * @return array of division points.
     */
    public double[] getHDivPoints() {
	return hDivPoints;
    }

    /**
     * Query for the origin point of the horizontal axis.
     * @return origin point of the horizontal axis in normalized space.
     */
    public double getHOrigin() {
	return originX;
    }

    /**
     * Query for the vertical tick labels.
     * @return array of label texts.
     */
    public String[] getVDivLabels() {
	return vDivLabels;
    }

    /**
     * Query for the vertical tick points in absolute space.
     * @return array of division points.
     */
    public double[] getVDivPoints() {
	return vDivPoints;
    }

    /**
     * Query for the origin point of the vertkcal axis.
     * @return origin point of the vertical axis in normalized space.
     */
    public double getVOrigin() {
	return originY;
    }

    private IntersectPoint lineLine(double fixStartX, double fixStartY, double fixEndX, double fixEndY,
			     double varStartX, double varStartY, double varEndX, double varEndY) {
	double uA
	    = ((fixEndX-fixStartX)*(varStartY-fixStartY) - (fixEndY-fixStartY)*(varStartX-fixStartX))
	    / ((fixEndY-fixStartY)*(varEndX-varStartX) - (fixEndX-fixStartX)*(varEndY-varStartY));
	double uB
	    = ((varEndX-varStartX)*(varStartY-fixStartY) - (varEndY-varStartY)*(varStartX-fixStartX))
	    / ((fixEndY-fixStartY)*(varEndX-varStartX) - (fixEndX-fixStartX)*(varEndY-varStartY));

	if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
	    double interX = varStartX + (uA * (varEndX-varStartX));
	    double interY = varStartY + (uA * (varEndY-varStartY));
	    return new IntersectPoint(true,interX,interY);
	} else 
	    return new IntersectPoint(false,0,0);
    }
        protected void setXTickAuto(double[] tick) {
	//hDivPoints = tick;
	minx = tick[0];
	maxx = tick[tick.length - 1];
	final double span = maxx - minx;
	hDivPoints = Arrays.stream(tick)
	    .map(x -> (x - minx) / span)
	    .toArray();
	hDivLabels = Arrays.stream(tick)
	    .mapToObj(String::valueOf)
	    .toArray(String[]::new);
    }

    protected void setYTickAuto(double[] tick) {
	//vDivPoints = tick;
	miny = tick[0];
	maxy = tick[tick.length-1];
	final double span = maxy - miny;
	vDivPoints = Arrays.stream(tick)
	    .map(x -> (x - miny) / span)
	    .toArray();
	vDivLabels = Arrays.stream(tick)
	    .mapToObj(String::valueOf)
	    .toArray(String[]::new);
    }
}

