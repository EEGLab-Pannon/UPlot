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

/**
 * Fluent Interface wrapper around the data.Line.
 * 
 * @author Tóth Bálint
 */
public class Line {

    private hu.unipannon.virt.plot.data.Line lineData;

    private Line(double[] xs, double[] ys, LineStyle style) {
	lineData = new hu.unipannon.virt.plot.data.Line(xs,
				 ys,
				 style.getColor(),
				 style.getWidth(),
				 style.getStrokeStyle(),
				 style.getMarker());
            lineData.setError(style.isShowErrorLines(), style.isShowErrorArea(), 
                    style.getLowerError(), 
                    style.getUpperError(), 
                    style.getErrorLineStyle().getColor(), 
                    style.getErrorLineStyle().getWidth(), 
                    style.getErrorLineStyle().getStrokeStyle(), 
                    style.getErrorLineStyle().getMarker(),
                    style.getErrorAreaColor());
    }

    /**
     * Fluent interface starter method with the coordinates of the data points.
     * @param xs array of x coordinates.
     * @param ys array of y coordinates.
     * @return a new instance of Line.
     */
    public static Line line(double[] xs, double[] ys) {
	return new Line(xs,ys,new LineStyle());
    }

    /**
     * Fluent interface starter method with the coordinates of the data points
     * and the style.
     * @param xs array of x coordinates.
     * @param ys array of y coordinates.
     * @param style stlye of the line with the LineStyle fluent interface.
     * @return a new instance of Line.
     */
    public static Line line(double[] xs, double[] ys, LineStyle style) {
	return new Line(xs,ys,style);
    }
    
    /**
     * Fluent interface starter method with the coordinates of the data points
     * and the style.
     * @param xs array of x coordinates.
     * @param ys array of y coordinates.
     * @param style stlye formatting strings with the key-value pairs.
     * @return a new instance of Line.
     */
    public static Line line(double[] xs, double[] ys, String... style) {
        return new Line(xs,ys,parseAttribs(style));
    }
    
    private static LineStyle parseAttribs(String[] args) {
        LineStyle toReturn = new LineStyle();
        for (String arg : args) {
            toReturn.setAttrib(arg);
        }
        return toReturn;
    }

    /**
     * Query for the stored line data.
     * @return stored line data.
     */
    public hu.unipannon.virt.plot.data.Line getLineData() {
	return lineData;
    }
}
