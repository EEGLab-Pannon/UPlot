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
 * The <code>frame.horizontal</code> sub package contains all graphical componets
 * of the horizontal axis. These items are separated in this package in order to
 * make package level maintenance easier.<br>
 * The contained components are the following: <br>
 * <pre>
 *  - The axis lines on the top, bottom and middle of the plot space area.
 *  - Tick marks for the 3 lines on the inside and outside. These modes can be turned on and off.
 *  - Tick labels for the 3 lines. The labels can be turned on and off separately.
 *  - The horizontal main grid.
 * </pre>
 * Much like the Frame class, these components are pre-generated upon recieving data
 * and can be toggled.
 * 
 * @author Tóth Bálint
 */
package hu.unipannon.virt.plot.frame.horizontal;