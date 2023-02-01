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

import hu.unipannon.virt.plot.control.LogPlotController;

/**
 * Handle class for logarithm base settings for plots.
 * 
 * @author Tóth Bálint
 */
public class LogSettings {
    /**
     * Logarithm base of the X axis.
     */
    public int xLogBase;
    /**
     * Logarithm base of the Y axis.
     */
    public int yLogBase;

    /**
     * Default constructs a LogSettings instance.
     * Both log base values are 10.
     */
    public LogSettings() {
	xLogBase = 10;
	yLogBase = 10;
    }
    /**
     * Exports the stored value to a PlotSpaceController implementation.
     * @param controller controller the values are applied to.
     */
    public void applyTo(LogPlotController controller) {
	controller.setLogBase(xLogBase,yLogBase);
    }
}
