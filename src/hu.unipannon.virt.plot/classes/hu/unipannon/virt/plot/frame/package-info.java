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
 * The <code>frame</code> package is the core of the library's graphical system.
 * It contains all the displayable components and basic functions that can turn
 * them on/off. The controllers can later use these functions to automate the 
 * process of activating and deactivating plot components such as certain 
 * axes or tick marks.
 * <br><br>
 * We call the complete graphical representation of a general plot a Frame.
 * The Frame contains every component of the plot i.e. Axes, Tick Marks, Labels.
 * These are the components that can be found in every type of plots (Line, Bar, etc.).
 * The Plot Type specific graphical items, such as lines or boxes are in a separate
 * container, the Plot Space. The Plot Space can be inserted into any kind of Frame, 
 * and can contain any kind of plot elements. This makes combined line and bar 
 * plots possible.
 * <br><br>
 * To make plots fully customizable, the Frame generates all possible axis elements
 * at all positions. Those elements can be turned on and off individually with
 * the specific component's methods. Components that belong to specific axes are 
 * grouped together and managed with their own methods. The <code>Frame</code>
 * class contains these groups, that can be turned on and queried for their specific
 * operations. (Important: turning on an Axis does not turn on any lines or ticks, 
 * they need to be turned on separately with the Axis object).
 * <br>Example:<br>
 * <pre>{@code
 *   frame.showVerticalAxis();
 *   frame.getVerticalAxis().showLeftLine();
 * }</pre>
 * <br><br>
 * The Graphics System works with properties. Component positions and offsets
 * can be expressed with relations to other componenst properties (position,
 * width, height). The basis of the coordinate system are the bottom left corner
 * and the width and height parameters of components. This correlates with
 * MATLAB's positioning system.
 * 
 * @author Tóth Bálint
 */
package hu.unipannon.virt.plot.frame;