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
package hu.unipannon.virt.plot.frame.vertical;

import java.util.LinkedList;
import java.util.List;
import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import hu.unipannon.virt.plot.frame.Container;

/**
 * The <code>VGrid</code> class implements the generation of the horizontal gridlines
 * based on the divisor points, as well as contains functions to set the 
 * graphical properties of the labels. The class is a container and is usually
 * bound to the VerticalAxis.
 * 
 * @see VerticalAxis
 * @author Tóth Bálint
 */
public class VGrid extends Container {
     private List<Line> lines;
    double[] divisors;
    
    /**
     * Default constructs an empty VGrid object.
     */
    public VGrid() {
        lines = new LinkedList<>();
    }
    
    /**
     * Generates grid lines to the previously given divisors.
     */
    public void makeGrid() {
        lines.clear();
        for (double d : divisors) {
            Line l = new Line();
            
            l.startXProperty().bind(
                frameWidthPropertyA.multiply(plotSpaceLeftPropertyR)
            );
            
            l.endXProperty().bind(
                frameWidthPropertyA.multiply(plotSpaceLeftPropertyR
                    .add(plotSpaceWidthPropertyR))
            );
            

            l.startYProperty().bind(
                    frameHeightPropertyA
                    .subtract(
                        frameHeightPropertyA
                            .multiply(plotSpaceBottomPropertyR
                                .add(plotSpaceHeightPropertyR
                                    .multiply(d))))  
            );


            l.endYProperty().bind(
                frameHeightPropertyA
                    .subtract(
                        frameHeightPropertyA
                            .multiply(plotSpaceBottomPropertyR
                                .add(plotSpaceHeightPropertyR
                                    .multiply(d))))  
            );
            
            lines.add(l);
        }
        addLines();
    }
    
    /**
     * Setter for the divisor points. Divisors must be normalized to the [0;1]
     * interval.
     * @param divisors array of divisor points.
     */
    public void setDivisors(double[] divisors) {
        getChildren().clear();
        this.divisors = divisors;
    }
    
    /**
     * Adds all generated line elements to the inside JavaFX Group instance.
     */
    public void addLines() {
        getChildren().addAll(
            lines
                .stream()
                .map(
                    (Line l) -> {
                        l.setStroke(Color.BLACK);
                        l.setStrokeWidth(1);
                        return l;
                    })
                .toArray(Node[]::new));
    }
    
    /**
     * Setter for line paramters. This applies to all ticks already generated.
     * @param p color as JavaFX Paint object
     * @param w width of the tick marks in points.
     */
    public void setLineStroke(Paint p, double w) {
        lines
            .stream()
            .forEach((Line l) -> {
                l.setStroke(p);
                l.setStrokeWidth(new Size(w, SizeUnits.PT).pixels());
            });
    }
}
