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
package hu.unipannon.virt.plot.frame.horizontal;

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
 * The <code>HTickMarks</code> class implements the generation of the tick mark
 * objects based on the divisor points, as well as contains functions to set the 
 * graphical properties of the tick marks. The class is a container and is usually
 * bound to the HorizontalAxis.
 * 
 * @see HorizontalAxis
 * @author Tóth Bálint
 */
public class HTickMarks extends Container {
    
    private double[] divisors;
    
    private List<Line> lines;
    
    /**
     * Default constructs a HTickMarks object with empty divisors and lines.
     */
    public HTickMarks() {
        this.divisors = new double[]{};
        lines = new LinkedList<>();
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
    
    /**
     * Generates lower tick marks based on the already set divisors.
    */
    public void makeLower() {
        lines.clear();
        for (double d : divisors) {
            Line t = new Line();
            t.startXProperty().bind(
                    frameWidthPropertyA
                        .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR
                            .multiply(d)))
            );
            
            t.startYProperty().bind(
                    frameHeightPropertyA
                    .subtract(plotSpaceBottomPropertyR
                            .multiply(frameHeightPropertyA)
                        .subtract(outerTickLengthPropertyR
                            .multiply(frameWidthPropertyA)))
            );
            
            t.endXProperty().bind(
                    frameWidthPropertyA
                        .multiply(plotSpaceLeftPropertyR
                            .add(plotSpaceWidthPropertyR
                                .multiply(d)))
            );
            
            t.endYProperty().bind(
                    frameHeightPropertyA
                    .subtract(plotSpaceBottomPropertyR
                        .multiply(frameHeightPropertyA)
                        .add(innerTickLengthPropertyR
                            .multiply(frameWidthPropertyA)))
            );
            lines.add(t);
        }
        addLines();
    }
    
    /**
     * Generates upper tick marks based on the already set divisors.
    */
    public void makeUpper() {
        lines.clear();
        for (double d : divisors) {
            Line t = new Line();
            t.startXProperty().bind(
                    frameWidthPropertyA
                        .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR
                            .multiply(d)))
            );
            
            t.startYProperty().bind(
                    frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                            .multiply(plotSpaceBottomPropertyR
                                .add(plotSpaceHeightPropertyR))
                            .add(outerTickLengthPropertyR
                                .multiply(frameWidthPropertyA)))
            );
            
            t.endXProperty().bind(
                    frameWidthPropertyA
                        .multiply(plotSpaceLeftPropertyR
                            .add(plotSpaceWidthPropertyR
                                .multiply(d)))
            );
            
            t.endYProperty().bind(
                    frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                            .multiply(plotSpaceBottomPropertyR
                                .add(plotSpaceHeightPropertyR))
                            .subtract(innerTickLengthPropertyR
                                .multiply(frameWidthPropertyA)))
            );
            lines.add(t);
        }       
        addLines();
    }
    
    /**
     * Generates origin tick marks based on the already set divisors.
     */
    public void makeOrigin() {
        lines.clear();
        for (double d : divisors) {
            Line t = new Line();
            t.startXProperty().bind(
                    frameWidthPropertyA
                        .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR
                            .multiply(d)))
            );
            
            t.startYProperty().bind(
                    frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                        .multiply(
                            plotSpaceBottomPropertyR
                            .add(originHorizontalPropertyR
                                .multiply(plotSpaceHeightPropertyR)))
                        .subtract(outerTickLengthPropertyR
                            .multiply(frameWidthPropertyA)))
            );
            
            t.endXProperty().bind(
                    frameWidthPropertyA
                        .multiply(plotSpaceLeftPropertyR
                            .add(plotSpaceWidthPropertyR
                                .multiply(d)))
            );
            
            t.endYProperty().bind(
                    frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                            .add(originHorizontalPropertyR
                                .multiply(plotSpaceHeightPropertyR)))
                        .add(innerTickLengthPropertyR
                            .multiply(frameWidthPropertyA)))
            );

            lines.add(t);
        }       
        addLines();
    }
    
}
