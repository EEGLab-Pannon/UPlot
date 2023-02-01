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

import javafx.scene.layout.Pane;
import hu.unipannon.virt.plot.control.FrameController;
import hu.unipannon.virt.plot.control.PlotSpaceController;
import hu.unipannon.virt.plot.control.LinePlotController;

/**
 * Fluent interface responsible for creating a line plot.
 * This class initialises the controllers for the plot graphics, and applies the
 * style elements from the <code>FrameStyle</code> and <code>Line</code> classes.
 * @author Tóth Bálint
 * 
 * @see FrameStyle
 * @see Line
 */
public class Plot implements Displayable {

    // controller for the frame of the current plot
    protected FrameController frameController;

    // unique to the line plot
    protected PlotSpaceController plotController;

    /**
     * Connects the style's axis and legend settings to the plot controller.
     * @param controller plot space controller.
     * @param style frame style.
     */
    protected final void setupFrameController(PlotSpaceController controller, FrameStyle style) {
	style.getAxisSettings().applyTo(controller);
	style.getLegendSettings().applyTo(controller);
    }

    /**
     * Initialises the frame controller with the specified frame style.
     * @param style previously specified frame style object from the fluent interface.
     */  
    protected Plot(FrameStyle style) {
	frameController = style.getController();
    }

    /**
     * Alternative constructor with all the lines in an array and the style object.
     * We recommend using the <code>plot()</code> static factory method instead.
     * @param style style elements for the plot.
     * @param lines line objects to be displayed.
     */
    public Plot(FrameStyle style, Line[] lines) {
	this(style);
	// hacking like some pro 
	LinePlotController lineController = new LinePlotController();
	for (var l : lines) {
	    lineController.addLine(l.getLineData());
	}
	Defaults.resetPalette();
	LinePlotController.resetDataLineNumbers();
	setupFrameController(lineController,style);

	// after done with line specific stuff, just set it
	plotController = lineController;
    }

    /**
     * Fluent interface component, creates a Plot instance with the given lines.
     * @param lines variadic amount of <code>Line</code> objects.
     * @return newly created Plot instance.
     */
    public static Plot plot(Line... lines) {
	return new Plot(new FrameStyle(),lines);
    }

    /**
     * Fluent interface component, creates a Plot instance with the given 
     * <code>FrameStyle</code> instance and the given line objects.
     * @param style style elements for the plot.
     * @param lines Variable amount of <code>Line</code> objects.
     * @return newly created Plot instance.
     */
    public static Plot plot(FrameStyle style, Line... lines) {
	return new Plot(style, lines);
    }

    /**
     * Gives back a <code>JavaFX.Pane</code> object containing the lineplot's 
     * graphical items. This object than can be added into some JavaFX container.
     * @return <code>Pane</code> object with the plot's representation.
     */
    @Override
    public Pane display() {
	frameController.addPlotSpaceController(plotController);
	return frameController.display();
    }
}
