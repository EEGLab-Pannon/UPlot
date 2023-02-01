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

import javafx.scene.layout.Pane;

import hu.unipannon.virt.plot.frame.horizontal.HorizontalAxis;
import hu.unipannon.virt.plot.frame.vertical.VerticalAxis;
import hu.unipannon.virt.plot.frame.Frame;
import hu.unipannon.virt.plot.fluent.Defaults;

/**
 * The Frame Controller handles all the frame settings that are the same for 
 * every plot type: axes, legend etc.
 * For the plot to show properly, the plot space needs to be filled in. This 
 * operation can be implemented with a Plot Space Controller.
 * 
 * @see PlotSpaceController
 * @author Tóth Bálint
 */
public class FrameController {

    // MATLAB style enumerated types
    /**
     * Represents tick directon related to the axis line.
     */
    public static enum TickDir {
	IN,
	OUT,
	BOTH,
	NONE
    }

    /**
     * Represents possible locations of the horizontal axis.
     */
    public static enum XLocation {
	BOTTOM,
	ORIGIN,
	TOP,
    }

    /**
     * Represents possible locations of the vertical axis.
     */
    public static enum YLocation {
	LEFT,
	ORIGIN,
	RIGHT,
    }

    // constants
    private final int WIDTH = 560;
    private final int HEIGHT = 420;
    private final double TICK_LEN = 0.01; // relative


    // components
    private Frame frame;
    private HorizontalAxis hax;
    private VerticalAxis vax;
    private PlotSpaceController psController;

    // settings
    private XLocation xLocation = XLocation.BOTTOM;
    private YLocation yLocation = YLocation.LEFT;

    private boolean xlabel = false;
    private boolean ylabel = false;

    private boolean box = true;

    private boolean grid = false;
		
    /**
     * Default constructs a Frame Controller.
     * Every value is null, or contains default settings.
     */
    public FrameController() {
	frame = new Frame(); 
	hax = frame.getHorizontalAxis();
	vax = frame.getVerticalAxis();
	psController = null;
    }

    /**
     * Sets a plot space controller.
     * @param psc some implementation of the plot space controller.
     */
    public void addPlotSpaceController(PlotSpaceController psc) {
	this.psController = psc;
	psc.setPlotSpace(frame.getPlotSpace());
    }

    /**
     * Sets the border of the plot space.
     * @param box true enables all the axis items around the plot space.
     */
    public void box(boolean box) {
	this.box = box;
    }

    /**
     * Sets the text value of the horizontal label.
     * @param xlabel value of the x label.
     */
    public void xLabel(String xlabel) {
	hax.getLowerAxisLabel().setText(xlabel);
	hax.getUpperAxisLabel().setText(xlabel);
	hax.getOriginAxisLabel().setText(xlabel);

	this.xlabel = true;
    }

    /**
     * Sets the text value of the vertical label.
     * @param ylabel value of the y label.
     */
    public void yLabel(String ylabel) {
	vax.getLeftAxisLabel().setText(ylabel);
	vax.getRightAxisLabel().setText(ylabel);
	vax.getOriginAxisLabel().setText(ylabel);

	this.ylabel = true;
    }

    /**
     * Sets the tick direction with the TickDir enum.
     * @param dir direction of the ticks. (in / out / both / none).
     */
    public void tickDir(TickDir dir) {
	frame.innerTickLength().unbind();
	frame.outerTickLength().unbind();

	switch (dir) {
	case IN:
	    frame.innerTickLength().set(TICK_LEN);
	    frame.outerTickLength().set(0);
	    break;
	case OUT:
	    frame.innerTickLength().set(0);
	    frame.outerTickLength().set(TICK_LEN);
	    break;
	case BOTH:
	    frame.innerTickLength().set(TICK_LEN);
	    frame.outerTickLength().set(TICK_LEN);
	    break;
	case NONE:
	    frame.innerTickLength().set(0);
	    frame.outerTickLength().set(0);
	    break;
	default:
	    break;
	}
    }

    /**
     * Sets horizontal axis location.
     * @param location location of the x axis.
     */
    public void xAxisLocation(XLocation location) {
	xLocation = location;
    }

    /**
     * Sets vertical axis location.
     * @param location location of the y axis.
     */
    public void yAxisLocation(YLocation location) {
	yLocation = location;
    }

    /**
     * Turns the grid on / off.
     * @param on grid toggle value.
     */
    public void grid(boolean on) {
	this.grid = on;
    }

    /**
     * Sets the text value of the title.
     * @param title value of the title.
     */
    public void title(String title) {
	// this can actually stay
	frame.getTitle().setText(title);
	frame.showTitle();
    }

    /**
     * Creates a JavaFX Pane object that contains the previously configured 
     * Frame. Requires a PlotSpaceController to be set.
     * @return pane containg the frame.
     */
    public Pane display() {
	// == BOIlERPLATE ==
	Pane pane = new Pane();
	if (psController == null)
	    return pane;

	pane.setPrefSize(WIDTH,HEIGHT);
	pane.setStyle("-fx-background-color: #eaeaea");
	pane.getChildren().add(frame);
	frame.frameWidth().bind(pane.widthProperty());
	frame.frameHeight().bind(pane.heightProperty());

	// == SETTINGS == 


	hax.setStroke(Defaults.DEFAULT_AXIS_COLOR, Defaults.DEFAULT_AXIS_WIDTH);
	vax.setStroke(Defaults.DEFAULT_AXIS_COLOR, Defaults.DEFAULT_AXIS_WIDTH);

	// order: plot background, grid, plot space
	frame.showPlotSpaceBackground();

	showHorizontalAxis();
	showVerticalAxis();
	if (grid) {
	    hax.showGrid();
	    vax.showGrid();
	}

	frame.showPlotSpace();
	// == PLOT SPACE CONTROL ==
	// properly abstracted away, so this is all

	psController.display();
	hax.setDivisors(psController.getHDivPoints(),
			psController.getHDivLabels());
	vax.setDivisors(psController.getVDivPoints(),
			psController.getVDivLabels());

	frame.originHorizontal().unbind();
	frame.originHorizontal().set(psController.getHOrigin());

	frame.originVerticalProperty().unbind();
	frame.originVerticalProperty().set(psController.getVOrigin());

	// == AFTER SETTINGS ==
	// this one needs to be after setting the divisors
	hax.getGrid().setLineStroke(Defaults.DEFAULT_GRID_COLOR,Defaults.DEFAULT_GRID_WIDTH);
	vax.getGrid().setLineStroke(Defaults.DEFAULT_GRID_COLOR,Defaults.DEFAULT_GRID_WIDTH);
	return pane;
    }

    /**
     * Sets horizontal axis mode. This influences the position of the axis 
     * labels and tick labels.
     */
    private void showHorizontalAxis() {
	frame.originHorizontal().set(psController.getHOrigin());
	switch (xLocation) {
	case BOTTOM:
	    hax.showLowerLine();
	    hax.showLowerTickMarks();
	    hax.showLowerTickLabels();
	    if (xlabel)
		hax.showLowerAxisLabel();
	    if (box) {
		hax.showUpperLine();
		hax.showUpperTickMarks();
	    }
	    break;
	case TOP:
	    hax.showUpperLine();
	    hax.showUpperTickMarks();
	    hax.showUpperTickLabels();
	    if (xlabel)
		hax.showUpperAxisLabel();
	    if (box) {
		hax.showLowerLine();
		hax.showLowerTickMarks();
	    }
	    break;
	case ORIGIN:
	    hax.showOriginLine();
	    hax.showOriginTickLabels();
	    hax.showOriginTickMarks();
	    if (xlabel)
		hax.showOriginAxisLabel();
	    if (box) {
		hax.showUpperLine();
		hax.showUpperTickMarks();
		hax.showLowerLine();
		hax.showLowerTickMarks();
	    }
	    break;
	default:
	    break;
	}
	frame.showHorizontalAxis();
    }

    /**
     * Sets vertical axis mode. This influences the position of the axis 
     * labels and tick labels.
     */
    private void showVerticalAxis() {
	frame.originVerticalProperty().set(psController.getVOrigin());
	switch (yLocation) {
	case LEFT:
	    vax.showLeftLine();
	    vax.showLeftTickMarks();
	    vax.showLeftTickLabels();
	    if (ylabel)
		vax.showLeftAxisLabel();
	    if (box) {
		vax.showRightLine();
		vax.showRightTickMarks();
	    }
	    break;
	case RIGHT:
	    vax.showRightLine();
	    vax.showRightTickMarks();
	    vax.showRightTickLabels();
	    if (ylabel)
		vax.showRightAxisLabel();
	    if (box) {
		vax.showLeftLine();
		vax.showLeftTickMarks();
	    }	
	    break;
	case ORIGIN:
	    vax.showOriginLine();
	    vax.showOriginTickMarks();
	    vax.showOriginTickLabels();
	    if (ylabel)
		vax.showOriginAxisLabel();
	    if (box) {
		vax.showLeftLine();
		vax.showLeftTickMarks();
		vax.showRightLine();
		vax.showRightTickMarks();
	    }
	    break;
	default:
	    break;
	}
	frame.showVerticalAxis();
    }

    // the Pandora's box of settings functions
    /**
     * Query for the frame graphical object.
     * Can be used for fine modifications directly on the Frame.
     * @return reference to the Frame.
     */
    public Frame getFrame() {
	return frame;
    }

}
