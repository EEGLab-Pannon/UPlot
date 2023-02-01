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
package hu.unipannon.virt.plot.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * A simple class that can create color gradients based on the given number of points.
 * 
 * @author Tóth Bálint
 */
public class GradientGenerator {
    /**
     * Creates a color gradient from red to blue.
     * @param points number of items in the gradient.
     * @return array of colors.
     */
    public static Paint[] redToBlue(int points) {
	Paint[] gradient = new Paint[points];
	for (int i=0;i<points;i++)
	    gradient[i] = Color.rgb((int)(i * (255 / points)),
				    0,
				    255 - (int)(i * (255 / points)));
	return gradient;
    }

    /**
     * Creates a color gradient from blue to yellow.
     * @param points number of items in the gradient.
     * @return array of colors.
     */
    public static Paint[] blueToYellow(int points) {
	Paint[] gradient = new Paint[points];
	for (int i=0;i<points;i++)
	    gradient[i] = Color.rgb((int)(i * (255 / points)),
	    			    (int)(i * (255 / points)),
	    			    255 - (int)(i * (255 / points)));
	return gradient;
    }
}
