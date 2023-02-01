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
import hu.unipannon.virt.plot.control.ScatterPlotController;

/**
 * Fluent interface responsible for creating a scatter plot.
 * This class initialises the controllers for the plot graphics, and applies the
 * style elements from the <code>FrameStyle</code> and <code>Series</code> classes.
 * @author Tóth Bálint
 */
public class Scatter implements Displayable {

    private FrameController frameController;

    private PlotSpaceController plotController;
    
    /**
     * Alternative constructor with all the data lines in an array and the style object.
     * We recommend using the <code>scatter()</code> static factory method instead.
     * @param style frame style.
     * @param series data lines.
     */
    public Scatter(FrameStyle style, Series... series) {
	frameController = style.getController();
	ScatterPlotController scatterController =
	    new ScatterPlotController();
	for (var s : series) {
	    scatterController.addData(s.getScatterData());
	}
	Defaults.resetPalette();
	scatterController.resetDataLineNumbers();
	style.getAxisSettings().applyTo(scatterController);
	style.getLegendSettings().applyTo(scatterController);
	plotController = scatterController;
    }

    /**
     * Fluent interface starter method with a variadic amount of series objects.
     * @param series data series to be displayed.
     * @return new instance of Scatter.
     */
    public static Scatter scatter(Series... series) {
	return new Scatter(new FrameStyle(), series);
    }

    /**
     * Fluent interface starter method with a variadic amount of series objects 
     * and the style of the containing frame.
     * @param series data series to be displayed.
     * @param style frame style.
     * @return new instance of Scatter.
     */
    public static Scatter scatter(FrameStyle style, Series... series) {
	return new Scatter(style,series);
    }

    /**
     * Gives back a <code>JavaFX.Pane</code> object containing the scatter plot's 
     * graphical items. This object than can be added into some JavaFX container.
     * @return <code>Pane</code> object with the plot's representation.
     */
    public Pane display() {
	frameController.addPlotSpaceController(plotController);
	return frameController.display();
    }
}
