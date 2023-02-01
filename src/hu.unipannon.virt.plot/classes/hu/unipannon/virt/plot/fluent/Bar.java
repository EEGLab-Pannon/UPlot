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

import hu.unipannon.virt.plot.control.FrameController;
import hu.unipannon.virt.plot.control.PlotSpaceController;
import javafx.scene.layout.Pane;
import hu.unipannon.virt.plot.control.BarController;
import hu.unipannon.virt.plot.control.HBarController;

/**
 * Fluent interface responsible for creating a bar plot.
 * This class initialises the controllers for the plot graphics, and applies the
 * style elements from the <code>FrameStyle</code> and <code>Category</code> classes.
 * @author Tóth Bálint
 */
public class Bar implements Displayable {

    protected FrameController frameController;

    // unique to the line plot
    protected PlotSpaceController plotController;

    /**
     * Alternative constructor with all the categories in an array, the style object
     * and the horizontal flag.
     * We recommend using the static factory methods instead.
     * @param style style elements for the plot.
     * @param category categories (bar data lines) to be displayed.
     * @param horizontal true for horizontal bars.
     */
    public Bar(FrameStyle style, Category category, boolean horizontal) {
	frameController = style.getController();
	if (horizontal) {
	    HBarController barController = new HBarController();
	    barController.addCategories(category.getXs());
	    barController.setStyle(category.getStyle());
	    barController.setWidth(category.getWidth());
	    for (var d : category.getData()) {
		barController.addData(d);
	    }
	    barController.resetDataLineNumbers();
	    plotController = barController;
	} else {
	    BarController barController = new BarController();
	    barController.addCategories(category.getXs());
	    barController.setStyle(category.getStyle());
	    barController.setWidth(category.getWidth());
	    for (var d : category.getData()) {
		barController.addData(d);
	    }
	    barController.resetDataLineNumbers();
	    plotController = barController;
	}
	Defaults.resetPalette();
	style.getAxisSettings().applyTo(plotController);
	style.getLegendSettings().applyTo(plotController);
    }

    /**
     * Fluent interface starter method with a given category object.
     * @param category data for the bar plot.
     * @return new instance of Bar.
     */
    public static Bar bar(Category category) {
	return new Bar(new FrameStyle(), category, false);
    }

    /**
     * Fluent interface starter method with a given category object and the 
     * Frame Style.
     * @param category data for the bar plot.
     * @param style the style formatting of the frame.
     * @return new instance of Bar.
     */
    public static Bar bar(FrameStyle style, Category category) {
	return new Bar(style, category, false);
    }

    /**
     * Fluent interface starter method with a given category object.
     * Creates a horizontal bar.
     * @param category data for the bar plot.
     * @return new instance of Bar.
     */
    public static Bar hbar(Category category) {
	return new Bar(new FrameStyle(), category, true);
    }

    /**
     * Fluent interface starter method with a given category object and the 
     * Frame Style. Creates a horizontal bar.
     * @param category data for the bar plot.
     * @param style the style formatting of the frame.
     * @return new instance of Bar.
     */
    public static Bar hbar(FrameStyle style, Category category) {
	return new Bar(style, category, true);
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
