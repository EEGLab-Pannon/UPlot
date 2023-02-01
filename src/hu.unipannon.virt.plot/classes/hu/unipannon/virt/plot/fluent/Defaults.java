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

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import hu.unipannon.virt.plot.frame.BarStyle;
import hu.unipannon.virt.plot.frame.Marker;
import hu.unipannon.virt.plot.frame.StrokeStyle;
import hu.unipannon.virt.plot.frame.Marker.MarkerType;

/**
 * Contains default values for the controllers.
 * Each value is represented with a public static field.
 * @author Tóth Bálint
 */
public class Defaults {
    private static int paletteIndex = 0;
    
    /**
     * Gets the next global value from the default palette.
     * @return color of the next data line.
     */
    public static Paint nextFromPalette() {
	if (paletteIndex < DEFAULT_PALETTE.length)
	    return DEFAULT_PALETTE[paletteIndex++];
	else
	    return Color.web("#0072BD");
    }

    /**
     * Resets the global palette index for another plot.
     */
    public static void resetPalette() {
	paletteIndex = 0;
    }
    
    /**
     * Colors of the data series without specified color.
     * Palette:<br>
     * <pre>
     *  #0072BD
     *  #D95319
     *  #EDB120
     *  #7E2F8E
     *  #77AC30
     *  #4DBEEE
     *  #A2142F
     * </pre>
     */
    public static Paint[] DEFAULT_PALETTE = new Paint[] {
	Color.web("#0072BD"),
	Color.web("#D95319"),
	Color.web("#EDB120"),
	Color.web("#7E2F8E"),
	Color.web("#77AC30"),
	Color.web("#4DBEEE"),
	Color.web("#A2142F"),
    };
    
    /**
     * Line width in line based plots in points. Value: 0.5
     */
    public static double DEFAULT_LINE_WIDTH = 0.5d;
    /**
     * Default stroke style for lines. Value: normal.
     */
    public static StrokeStyle DEFAULT_LINE_STYLE = StrokeStyle.NORMAL;
    /**
     * Default marker for lines. Value: none.
     */
    public static Marker.MarkerType DEFAULT_MARKER = MarkerType.NONE;
    /**
     * Default marker for scatter plots. Value: circle.
     */
    public static Marker.MarkerType DEFAULT_SCATTER_MARKER = MarkerType.CIRCLE;
    
    /**
     * Default marker size in points. Value: 6.0
     */
    public static double DEFAULT_MARKER_SIZE = 6.d;
    /**
     * Default width of the lines forming markers in points. Value: 0.5
     */
    public static double DEFAULT_MARKER_STROKE_WIDTH = 0.5d;
    
    /**
     * Default width of a window in points. Value: 489
     */
    public static int DEFAULT_WINDOW_SIZE_X = 489;
    /**
     * Default height of a window in points. Value: 343
     */
    public static int DEFAULT_WINDOW_SIZE_Y = 343;
    /**
     * Default X position of a window in pixels. Value: 560
     */
    public static int DEFAULT_WINDOW_POS_X = 560;
    /**
     * Default Y position of a window in pixels. Value: 420
     */
    public static int DEFAULT_WINDOW_POS_Y = 420;
    
    /**
     * Default title of a figure window. Value: "Figure".
     */
    public static String DEFAULT_WINDOW_TITLE = "Figure";
    
    /**
     * Box on by default for plots. Value: true.
     */
    public static boolean DEFAULT_PLOT_BOX = true;
    
    /**
     * Grid on by default for plots. Value: false.
     */
    public static boolean DEFAULT_GRID = false;
    /**
     * Default title for plots (line, bar, scatter ...). Value: "".
     */
    public static String DEFAULT_PLOT_TITLE = "";
    /**
     * Default X axis label for plots. Value: "".
     */
    public static String DEFAULT_PLOT_XLABEL = "";
    /**
     * Default Y axis label for plots. Value: "".
     */
    public static String DEFAULT_PLOT_YLABEL = "";
    
    /**
     * Default length of tick marks relative to the width of the window. Value: 0.01
     */
    public static double DEFAULT_TICKMARK_LENGTH = 0.01d;
    
    /**
     * Default color of the axes. Value: #000000
     */
    public static Paint DEFAULT_AXIS_COLOR = Color.rgb(0,0,0);
    /**
     * Default width of the axes in points. Value: 0.5
     */
    public static double DEFAULT_AXIS_WIDTH = 0.5;
    
    /**
     * Default color of the grids. Value: #0f0f0f
     */
    public static Paint DEFAULT_GRID_COLOR = Color.rgb((int)(15/255),(int)(15/255),(int)(15/255),0.15);
    /**
     * Default width of the grid lines in points. Value: 0.5
     */
    public static double DEFAULT_GRID_WIDTH = 0.5;
    
    /**
     * Default font face for labels. Value: "Helvetica".
     */
    public static String DEFAULT_FONT = "Helvetica";
    /**
     * Default font size for titles in points. Value: 16
     */
    public static int DEFAULT_TITLE_SIZE = 16;
    /**
     * Default font size for labels in points. Value: 15
     */
    public static int DEFAULT_LABEL_SIZE = 15;

    /**
     * Default style of bar plots. Value: grouped.
     */
    public static BarStyle DEFAULT_BAR_STYLE = BarStyle.GROUPED;
    
    /**
     * Default width ratio of bar plots. Value: 0.8
     */
    public static double DEFAULT_BAR_WIDTH = 0.8;

    /**
     * Default alpha of the error areas. Value: 0.3
     */ 
    public static double DEFAULT_ERROR_AREA_OPACITY = 0.3;
    /**
     * Default width of error lines in points. Value: 0.5
     */
    public static double DEFAULT_ERROR_LINE_WIDTH = 0.5;
}
