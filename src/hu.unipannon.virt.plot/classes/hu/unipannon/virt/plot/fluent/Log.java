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

import hu.unipannon.virt.plot.control.LogPlotController;

/**
 * Fluent interface responsible for creating a log scale line plot.
 * This class initialises the controllers for the plot graphics, and applies the
 * style elements from the <code>FrameStyle</code> and <code>Line</code> classes.
 * @author Tóth Bálint
 */
public class Log extends Plot {
    /**
     * Alternative constructor with all the lines in an array and the style object.
     * We recommend using the static factory methods instead.
     * @param style style elements for the plot.
     * @param lines line objects to be displayed.
     */
    public Log(FrameStyle style, Line[] lines) {
	super(style);
	LogPlotController logController = new LogPlotController();

	for (var l : lines) {
	    logController.addLine(l.getLineData());
	}
	Defaults.resetPalette();
	LogPlotController.resetDataLineNumbers();
	
	setupFrameController(logController, style);
	plotController =  logController;
    }

    // shadowing
    protected void setupFrameController(LogPlotController controller, FrameStyle style) {
	super.setupFrameController(controller,style);
	style.getLogSettings().applyTo(controller);
    }

    /**
     * Fluent interface starter method, creates a log scale line plot.
     * Both axes are log scaled.
     * @param lines variadic amount of <code>Line</code> objects.
     * @return new Log instance.
     */
    public static Log loglog(Line... lines) {
	FrameStyle style = new FrameStyle();
	return new Log(style,lines);
    }

    /**
     * Fluent interface starter method, creates a log scale line plot with the 
     * given frame style.
     * Both axes are log scaled.
     * @param lines variadic amount of <code>Line</code> objects.
     * @param style frame style.
     * @return new Log instance.
     */
    public static Log loglog(FrameStyle style, Line... lines) {
	return new Log(style, lines);
    }

    /**
     * Fluent interface starter method, creates a log scale line plot.
     * The horizontal axis is log scaled.
     * @param lines variadic amount of <code>Line</code> objects.
     * @return new Log instance.
     */
    public static Log semilogx(Line... lines) {
	FrameStyle style = new FrameStyle();
	style.setLogBaseY(0);
	return new Log(style,lines);
    }

    /**
     * Fluent interface starter method, creates a log scale line plot with the 
     * given frame style.
     * The horizontal axis is log scaled.
     * @param lines variadic amount of <code>Line</code> objects.
     * @param style frame style.
     * @return new Log instance.
     */
    public static Log semilogx(FrameStyle style, Line... lines) {
	style.setLogBaseY(0);
	return new Log(style,lines);
    }

    /**
     * Fluent interface starter method, creates a log scale line plot.
     * The vertical axis is log scaled.
     * @param lines variadic amount of <code>Line</code> objects.
     * @return new Log instance.
     */
    public static Log semilogy(Line... lines) {
	FrameStyle style = new FrameStyle();
	style.setLogBaseX(0);
	return new Log(style,lines);
    }

    /**
     * Fluent interface starter method, creates a log scale line plot with the 
     * given frame style.
     * The vertical axis is log scaled.
     * @param lines variadic amount of <code>Line</code> objects.
     * @param style frame style.
     * @return new Log instance.
     */
    public static Log semilogy(FrameStyle style, Line... lines) {
	style.setLogBaseX(0);
	return new Log(style,lines);
    }
}
