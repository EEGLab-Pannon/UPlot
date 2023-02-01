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
package hu.unipannon.virt.plot.frame;

import hu.unipannon.virt.plot.fluent.Defaults;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
/**
 * The Plot Space is the section in the frame surrounded by the axes.
 * The Plot Space contains the legend and the plot type specific graphical items,
 * so it is the only part that differs between i.e. a line plot and a scatter plot.
 * For easier data representation, the Plot Space has its own coordinate system 
 * and properties.
 * <br>
 * Properties are established based on the MATLAB graph system, so the base point
 * is the bottom left, and the width / height are given. Items inside the Plot Space
 * can bind to the plot space's properties.
 * 
 * @see Frame
 * @see Component
 * @author Tóth Bálint
 */
public class PlotSpace extends Group implements Component {
    
    private DoubleProperty widthProperty;
    private DoubleProperty heightProperty;
    
    private DoubleProperty hOffsetProperty;
    private DoubleProperty vOffsetProperty;

    private Frame parent;
    
    /**
     * Creates a new Plot Space instance with a reference to its parent Frame.
     * @param parent the Frame the Plot Space will later be embedded into.
     */
    public PlotSpace(Frame parent) {
	this.parent = parent;
        widthProperty = new SimpleDoubleProperty();
        heightProperty = new SimpleDoubleProperty();
        
        hOffsetProperty = new SimpleDoubleProperty();
        vOffsetProperty = new SimpleDoubleProperty();
        
    }

    /**
     * Query for the owner Frame.
     * @return the parent.
     */
    public Frame getFrame() {
	return parent;
    }

    /**
     * Component interface implementation.
     * @param translateX x coordinate of the component with a property binding.
     * @param translateY y coordinate of the component with a property binding.
     */
    @Override
    public void position(DoubleBinding translateX, DoubleBinding translateY) {
        this.translateXProperty().bind(translateX.add(hOffsetProperty));
        this.translateYProperty().bind(translateY.add(vOffsetProperty));
    }

    /**
     * Component interface implementation.
     * @param alignX vertical component of the alginment point.
     * @param alignY horizontal component of the alginment point. 
     */
    @Override
    public void align(HPos alignX, VPos alignY) {
        switch (alignY) {
            case CENTER: {
                vOffsetProperty.unbind();
                vOffsetProperty.bind(heightProperty.divide(-2));
            }; break;
            case TOP: {
                vOffsetProperty.unbind();
                vOffsetProperty.set(0);
            }; break;
            case BOTTOM: {
                vOffsetProperty.unbind();
                vOffsetProperty.bind(heightProperty.multiply(-1));
            }; break;
        }
        switch (alignX) {
            case CENTER: {
                hOffsetProperty.unbind();
                hOffsetProperty.bind(widthProperty.divide(-2));
            }; break;
            case LEFT: {
                hOffsetProperty.unbind();
                hOffsetProperty.set(0);
            }; break;
            case RIGHT: {
                hOffsetProperty.unbind();
                hOffsetProperty.bind(widthProperty.multiply(-1));
            }; break;
        }
    }

    /**
     * Component interface implementation.
     * @return contained Scene Graph node of a component.
     */
    @Override
    public Node getNode() {
        return this;
    }
    
    /**
     * Query for width.
     * @return width of the Plot Space in pixels.
     */
    public DoubleProperty widthProperty() {
        return widthProperty;
    }

    /**
     * Query for height.
     * @return height of the Plot Space in pixels.
     */
    public DoubleProperty heightProperty() {
        return heightProperty;
    }
    
    // lines can be paths (instead of polylines, so I can bind the lines), bars can be rectangles
    
    // dom and im are already normalised
    /**
     * Adds a data line to the plot space. Every item needs to be given to the function.
     * The domain and image of the data must be of same length and containing values
     * in the [0;1] interval. The function draws lines between these points.
     * @param dom normalized X coordinates of the line's individual data points.
     * @param im normalized Y coordinates of the line's data points.
     * @param color color of the lines as JavaFX Paint object.
     * @param width width of the line in points.
     * @param style style of the line.
     * @param markerType type of the markers. (Can be <code>Marker.MarkerType.NONE</code>.)
     */
    public void addLine(double[] dom, double[] im, Paint color, double width, StrokeStyle style, Marker.MarkerType markerType) {
        // array length check
        if (dom.length != im.length)
            return;
        
        if (dom.length < 2)
            return;
        
        // the line itself
        Path path = new Path();
        MoveTo m = new MoveTo();
        m.xProperty().bind(widthProperty.multiply(dom[0]));
        m.yProperty().bind(heightProperty.subtract(heightProperty.multiply(im[0])));
        path.getElements().add(m);
        
        for (int i=1;i<dom.length;i++) {
            // this would be ideal, but the style will mess it up
            LineTo l = new LineTo();
            l.xProperty().bind(widthProperty.multiply(dom[i]));
            l.yProperty().bind(heightProperty.subtract(heightProperty.multiply(im[i])));
            path.getElements().add(l);
        }
        getChildren().add(path);
        
        // color and style
        path.setStroke(color);
        //path.getStrokeDashArray().addAll(10d,5d,3d,5d);
        path.setStrokeWidth(new Size(width,SizeUnits.PT).pixels());
        
        switch (style) {
            case NORMAL:
                break;
            case DASH:
                path.getStrokeDashArray().addAll(8d,11d);
                break;
            case DOT:
                path.getStrokeDashArray().addAll(2d);
                break;
            case DASH_DOT:
                path.getStrokeDashArray().addAll(10d,5d,3d,5d);
                break;
            default:
                break;
        }
        
        
        // marker
        if (markerType != Marker.MarkerType.NONE) {
            Marker marker = new Marker().setAttribs(Defaults.DEFAULT_MARKER_SIZE, width, color);
            for (int i=0;i<dom.length;i++) {
                // getting the marker
                Group mk = marker.draw(markerType);
                mk.translateXProperty().bind(widthProperty.multiply(dom[i]).subtract(marker.getSize() / 2));
                mk.translateYProperty().bind(heightProperty.subtract(heightProperty.multiply(im[i])).subtract(marker.getSize() / 2));      
                getChildren().add(mk);
            }
        }
        
    }
    
    /**
     * Adds a "patch", a color-filled 2D shape with a lower line and an upper line.
     * Can be used to display intervals. The domain, lower and upper lines must 
     * be of the same length and contain values in the [0;1] interval.
     * Vertical edges of the patch are connected with vertical lines.
     * @param dom normalized X coordinates of the domain points of the lower and upper line.
     * @param lower normalized Y coordinates of the upper line's points.
     * @param upper normalized Y coordinates of the lower line's points.
     * @param color color of the patch in JavaFX Paint format. (Can be RGBA for transparency, or multiple images of Nicholas Cage for comedic effect.)
     */
    public void addPatch(double[] dom, double[] lower, double[] upper, Paint color) {
        if (dom.length != upper.length || dom.length != lower.length) 
            return;
        
        if (dom.length < 2)
            return;
        
        Path p = new Path();
        MoveTo initial = new MoveTo();
        initial.xProperty().bind(widthProperty.multiply(dom[0]));
        initial.yProperty().bind(heightProperty.subtract(heightProperty.multiply(upper[0])));
        p.getElements().add(initial);
        
        for (int i=1;i<dom.length;i++) {
            LineTo m = new LineTo();
            m.xProperty().bind(widthProperty.multiply(dom[i]));
            m.yProperty().bind(heightProperty.subtract(heightProperty.multiply(upper[i])));
            p.getElements().add(m);
        }
        
        LineTo reverse = new LineTo();
        reverse.xProperty().bind(widthProperty.multiply(dom[dom.length-1]));
        reverse.yProperty().bind(heightProperty.subtract(heightProperty.multiply(lower[dom.length-1])));
        p.getElements().add(reverse);
        
        for (int i=dom.length-2;i>=0;i--) {
            LineTo m = new LineTo();
            m.xProperty().bind(widthProperty.multiply(dom[i]));
            m.yProperty().bind(heightProperty.subtract(heightProperty.multiply(lower[i])));
            p.getElements().add(m);
        }
        
        LineTo finish = new LineTo();
        finish.xProperty().bind(widthProperty.multiply(dom[0]));
        finish.yProperty().bind(heightProperty.subtract(heightProperty.multiply(upper[0])));
        
        p.setFill(color);
        p.setStroke(Color.TRANSPARENT);
        //p.fillProperty().set(color);
        getChildren().add(p);
    }
    
    // either this, or the line is going to be modified somehow different (not sure if we need it)
    public void /*some line*/ getLine(/*some identifier*/) {}
    
    // dom and im are normalised
    /**
     * Wrapper function to add groups of Bar plots to the Plot Space.
     * Domain and Image array must be normalized between the [0;1] interval.
     * The dom array contains the X coordinates of the bars (or groups) and the 
     * im array contains the matrix of the different groups' data in row continous
     * form. The first dom's length long data forms the bars of data for the domain 
     * points, and values separated by dom's length form a "data group".  <br>
     * If the image array contains negative values, the baseline offset can be 
     * specified to be the largest absolute value in the negative numbers.
     * @param dom normalized X coordinates of the data points (or groups).
     * @param im normalized matrix of Y coordinates of the data points.
     * @param baselineOffset offset value from the bottom line of the plot space. All bars will be shifted upwards by this value.
     * @param color array of colors, used to separate items in a data group.
     * @param width [0;1] normalised width of a bar or group. Bars fill in their available space based on this value. (0: bars are invisible, 1: bars fill in the max aval. space)
     * @param style style of the bar plot, can be Stacked or Grouped.
     */
    public void addBar(double[] dom, double[] im, double baselineOffset, Paint[] color, double width, BarStyle style) {   
        
        if (im.length % dom.length != 0)
            return;
        
        switch (style) {
            case GROUPED: {
                addGroup(dom,im,baselineOffset,color,width,false);
                break;
            }
            case STACKED: {
                addStacked(dom,im,baselineOffset,color,width,false);
                break;
            }
            default:
                break;
        }
    }
    
    /**
     * Wrapper function to add groups of Bar plots to the Plot Space. The only 
     * difference from the <code>addBar()</code> function, that this one 
     * draws horizontal bars from bottom of the Plot Space to the top.
     * Domain and Image array must be normalized between the [0;1] interval.
     * The dom array contains the X coordinates of the bars (or groups) and the 
     * im array contains the matrix of the different groups' data in row continous
     * form. The first dom's length long data forms the bars of data for the domain 
     * points, and values separated by dom's length form a "data group".  <br>
     * If the image array contains negative values, the baseline offset can be 
     * specified to be the largest absolute value in the negative numbers.
     * @param dom normalized X coordinates of the data points (or groups).
     * @param im normalized matrix of Y coordinates of the data points.
     * @param baselineOffset offset value from the bottom line of the plot space. All bars will be shifted right by this value.
     * @param color array of colors, used to separate items in a data group.
     * @param width [0;1] normalised width of a bar or group. Bars fill in their available space based on this value. (0: bars are invisible, 1: bars fill in the max aval. space)
     * @param style style of the bar plot, can be Stacked or Grouped.
     */
    public void addHBar(double[] dom, double[] im, double baselineOffset, Paint[] color, double width, BarStyle style) {   
        
        if (im.length % dom.length != 0)
            return;
        
        switch (style) {
            case GROUPED: {
                addGroup(dom,im,baselineOffset,color,width,true);
                break;
            }
            case STACKED: {
                addStacked(dom,im,baselineOffset,color,width,true);
                break;
            }
            default:
                break;
        }
    }
    
    // works like the line
    /**
     * Adds a scatter data series to the Plot Space, works like the <code>addLine()</code>
     * function, except this one does not connect the data points with lines.
     * The domain and image of the data must be of same length and containing values
     * in the [0;1] interval. The function draws lines between these points.
     * @param dom normalized X coordinates of the series' individual data points.
     * @param im normalized Y coordinates of the series' data points.     
     * @param size sizes of the different markers representing the data points. If contains 1 element, it will be used for all points.
     * @param color colors of the markers representing the data points. If contains 1 item, it will be used for all points.
     * @param markerType type of the markers.
     */
    public void addScatter(double[] dom, double[] im, double[] size, Paint[] color, Marker.MarkerType markerType) {
        boolean sameSize = size.length == 1;
        boolean sameColor = color.length == 1;
        
        if (dom.length != im.length)
            return;
        if (!sameSize && size.length != dom.length)
            return;
        if (!sameColor && color.length != dom.length)
            return;

        // TODO marker width somehow
        
        for (int i=0;i<dom.length;i++) {
            // getting the marker
            Marker marker = new Marker().setAttribs(
                sameSize ? size[0] : size[i],
                1, 
                sameColor ? color[0] : color[i]);
            Group mk = marker.draw(markerType);
            mk.translateXProperty().bind(widthProperty.multiply(dom[i]).subtract(marker.getSize() / 2));
            mk.translateYProperty().bind(heightProperty.subtract(heightProperty.multiply(im[i])).subtract(marker.getSize() / 2));      
            getChildren().add(mk);
        }
        
    }
    
    /**
     * Adds a legend object to the Plot Space to the specified location. 
     * The legend's alignment position is based on the given position.
     * @param l preconfigured label to be added.
     * @param alignX horizontal position of the legend.
     * @param alignY vertical position of the legend.
     */
    public void addLegend(Legend l, HPos alignX, VPos alignY) {
        DoubleBinding vpos = heightProperty.divide(2);
        DoubleBinding hpos = widthProperty.divide(2);
        
        double padding = 0.1;
        
        switch (alignY) {
            case CENTER: {
                vpos = heightProperty.divide(2);
            }; break;
            case TOP: {
                vpos = heightProperty.multiply(padding);
            }; break;
            case BOTTOM: {
                vpos = heightProperty.multiply(1.0-padding);
            }; break;
        }
        switch (alignX) {
            case CENTER: {
                hpos = widthProperty.divide(2);
            }; break;
            case LEFT: {
               hpos = widthProperty.multiply(padding);
            }; break;
            case RIGHT: {
                hpos = widthProperty.multiply(1.0-padding);
            }; break;
        }
        
        l.position(hpos,vpos);  
        l.align(alignX,alignY);
        
        
        getChildren().add(l.getNode());
    }
    
    /**
     * Adds Bar plot data in stacked configuration.
     * @param dom normalized X coordinates of the data points (or groups).
     * @param im normalized matrix of Y coordinates of the data points.
     * @param baselineOffset offset value from the bottom line of the plot space. All bars will be shifted right by this value.
     * @param color array of colors, used to separate items in a data group.
     * @param width [0;1] normalised width of a bar or group. Bars fill in their available space based on this value. (0: bars are invisible, 1: bars fill in the max aval. space)
     * @param horizontal boolean if the bar stlye is horizontal.
     */
    private void addStacked(double[] dom, double[] im, double baselineOffset, Paint[] color, double width, boolean horizontal) {
        int groupSize = im.length / dom.length;
        int groupCount = dom.length;
        double availableWidth = groupCount > 0 ? dom[1] - dom[0] : 0.75;

        
        for (int group=0;group<groupCount;group++) {
            double top = 0;
            double bot = 0;
            for (int i=0;i<groupSize;i++) {
                int index = (i * groupCount) + group;
                Rectangle r = new Rectangle();
                if (horizontal) {
                    // y position, probably static
                    r.yProperty().bind(heightProperty.subtract(
                            heightProperty.multiply(dom[group])).subtract(r.heightProperty().divide(2)));
                    r.heightProperty().bind(heightProperty.multiply(width * availableWidth));

                    // x position, needs to introduce the top/bottom to the translate
                    r.xProperty().bind(widthProperty.multiply(baselineOffset));
                    r.widthProperty().bind(widthProperty.multiply(Math.abs(im[index])));

                    if (im[index] < 0) {
                        r.translateXProperty().bind(widthProperty.multiply(im[index] - bot));
                        bot -= im[index];
                    } else {
                        r.translateXProperty().bind(widthProperty.multiply(top));
                        top += im[index];
                    }
                } else {
                    // x position, probably static
                    r.xProperty().bind(widthProperty.multiply(dom[group]).subtract(r.widthProperty().divide(2)));
                    r.widthProperty().bind(widthProperty.multiply(width * availableWidth));

                    // y position, needs to introduce the top/bottom to the translate
                    r.yProperty().bind(heightProperty.subtract(heightProperty.multiply(baselineOffset)));
                    r.heightProperty().bind(heightProperty.multiply(Math.abs(im[index])));

                    if (im[index] < 0) {
                        r.translateYProperty().bind(heightProperty.multiply(bot));
                        bot -= im[index];
                    } else {
                        r.translateYProperty().bind(heightProperty.multiply(-im[index] - top));
                        top += im[index];
                    }
                }
                
                // style and colour
                r.setStroke(Color.BLACK);
                r.setStrokeWidth(new Size(1, SizeUnits.PT).pixels());
                r.setFill(color[i]);
                getChildren().add(r);
            }
        }
        
    }
    
    /**
     * Adds Bar plot data in grouped configuration.
     * @param dom normalized X coordinates of the data points (or groups).
     * @param im normalized matrix of Y coordinates of the data points.
     * @param baselineOffset offset value from the bottom line of the plot space. All bars will be shifted right by this value.
     * @param color array of colors, used to separate items in a data group.
     * @param width [0;1] normalised width of a bar or group. Bars fill in their available space based on this value. (0: bars are invisible, 1: bars fill in the max aval. space)
     * @param horizontal boolean if the bar stlye is horizontal.
     */
    private void addGroup(double[] dom, double[] im, double baselineOffset, Paint[] color, double width, boolean horizontal) {
        int groupSize = im.length / dom.length;
        int groupCount = dom.length;
        if (color.length < groupSize)
            return;

        // total width for a single group
        double availableWidth = groupCount > 0 ? dom[1] - dom[0] : 0.75;

        // for group size of one, it's 0, but for bigger groups, i'll just guess it
        // calculated to every side of every group!
        double groupPadding = groupSize == 1 ? 0 : 0.01;

        double barWidth = (availableWidth - 2 * groupPadding) / (double)groupSize;

        // the normal case is just a group size of 1
        for (int group=0;group<groupCount;group++) {
            for (int i=0;i<groupSize;i++) {       
                int index = (i * groupCount) + group; // index it like a 2D array
                Rectangle r = new Rectangle();
                
                if (horizontal) {
                    
                    r.yProperty().bind(heightProperty.subtract(
                            heightProperty.multiply(dom[group])).subtract(r.heightProperty().divide(2)));
                    r.heightProperty().bind(heightProperty.multiply(width * barWidth));

                    // shift the bars
                    r.translateYProperty().bind(heightProperty.multiply(
                        (barWidth / 2) * (2*i + 1 - groupSize)
                    ));

                    // height and y position
                    // start Y goes to the bottom of the rectangle
                    r.xProperty().bind(widthProperty.multiply(baselineOffset));
                    // height is the absolute relative height value
                    r.widthProperty().bind(widthProperty.multiply(Math.abs(im[index])));
                    // translating it down = it's equal to the height
                    // if upper, then nothing else, if lower then the its height as extra
                    r.translateXProperty().bind(widthProperty.multiply(im[index] < 0 ? im[index] : 0));
                } else {
                    // width and x position
                    r.xProperty().bind(widthProperty.multiply(dom[group]).subtract(r.widthProperty().divide(2)));
                    r.widthProperty().bind(widthProperty.multiply(width * barWidth));

                    // shift the bars
                    r.translateXProperty().bind(widthProperty.multiply(
                        (barWidth / 2) * (2*i + 1 - groupSize)
                    ));

                    // height and y position
                    // start Y goes to the bottom of the rectangle
                    r.yProperty().bind(heightProperty.subtract(heightProperty.multiply(baselineOffset)));
                    // height is the absolute relative height value
                    r.heightProperty().bind(heightProperty.multiply(Math.abs(im[index])));
                    // translating it down = it's equal to the height
                    // if upper, then nothing else, if lower then the its height as extra
                    r.translateYProperty().bind(heightProperty.multiply(-Math.abs(im[index]) - (im[index] < 0 ? im[index] : 0)));
                }
                // style and colour
                r.setStroke(Color.BLACK);
                r.setStrokeWidth(new Size(1, SizeUnits.PT).pixels());
                r.setFill(color[i]);
                getChildren().add(r);
            }
        }
    }

}
