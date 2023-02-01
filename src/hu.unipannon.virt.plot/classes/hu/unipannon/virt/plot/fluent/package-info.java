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

/**
 * The <code>fluent</code> package implements the MATLAB like embedded DSL interface.
 * Each part of a diagram can be configured in three groups: figure, frame and plot.
 * The Figure class creates and configures the window that the plots are positioned in.
 * Configurable properties include the position and title of the window, as well as
 * the inner position of the plots. The frame styling include legend positioning,
 * axis positioning as well as title and axis labels. The plot settings and
 * creation are implemented with their specific classes. <br>
 * The interface structure reflects this top-down system with the parameters
 * of the functions forming the interface the following way:<br> 
 * 
 * <pre>{@code
 *   var fig = Figure.figure()
 *               .title("My Figure")  // function of figure: configures the figure (window)
 *               .plot(               // function of figure: adds a plot to the figure
 *                  FrameStyle.title("My Plot")    // parameter of the plot() function: it acts inside that plot
 *                            .xlabel("My X axis")
 *                 ,Line.line(
 *                      new double[]{0.0, 1.0, 2.0}
 *                    , new double[]{0.0, 1.0, 2.0}
 *                    , LineStyle.style("-.")   // parameter of the line: configures the line's style
 *                               .color("red")) // function of the line style: sets the color of the line
 *                    
 *                );
 *   fig.show();
 * }</pre>
 * 
 * @author Tóth Bálint
 */
package hu.unipannon.virt.plot.fluent;
