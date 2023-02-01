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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.FontWeight;
import hu.unipannon.virt.plot.frame.Container;
import hu.unipannon.virt.plot.frame.Label;

/**
 * Base class of the Vertical Axis, it contains all the components in the axis.
 * In order to turn on items, the Axis object must be turned on too. The class 
 * extends the <code>Container</code> class, it is usually bound to the <code>
 * Frame</code> class.
 * 
 * @author Tóth Bálint
 */
public class VerticalAxis extends Container {
    
    private double[] divisors;
    private String[] labels;
    
    private Line leftLine;
    private Line rightLine;
    private Line originLine;
    
    private VTickLabels leftVtl;
    private VTickLabels rightVtl;
    private VTickLabels originVtl;
    
    private VTickMarks leftVtm;
    private VTickMarks rightVtm;
    private VTickMarks originVtm;
    
    private Label leftLab;
    private Label originLab;
    private Label rightLab;
    
    private DoubleProperty leftTickLabOffsetA;
    private DoubleProperty rightTickLabOffsetA;
    
    private DoubleProperty leftAxisLabOffsetA;
    private DoubleProperty rightAxisLabOffsetA;
    
    private VGrid grid;
    
    /**
     * Constructs all components and binds them to this objects properties.
     */
    public VerticalAxis() {
        
        leftLine = new Line();
        makeLeftLine();
        
        rightLine = new Line();
        makeRightLine();
        
        originLine = new Line();
        makeOriginLine();
       
        
        leftVtl = new VTickLabels();
        leftVtl.connectTo(this);
        
        rightVtl = new VTickLabels();
        rightVtl.connectTo(this);

        originVtl = new VTickLabels();
        originVtl.connectTo(this); 
        
        leftVtm = new VTickMarks();
        leftVtm.connectTo(this);
        
        rightVtm = new VTickMarks();
        rightVtm.connectTo(this);
        
        originVtm = new VTickMarks();
        originVtm.connectTo(this);
        
        leftTickLabOffsetA = new SimpleDoubleProperty(0);
        rightTickLabOffsetA = new SimpleDoubleProperty(0);
        
        leftLab = new Label();
        makeLeftLab();
        
        rightLab = new Label();
        makeRightLab();
        
        originLab = new Label();
        makeOriginLab();
        
        grid = new VGrid();
        grid.connectTo(this);
        
        leftAxisLabOffsetA = new SimpleDoubleProperty(0);
        rightAxisLabOffsetA = new SimpleDoubleProperty(0);
        
        tightInsetLeftProperty.bind(
                (leftTickLabOffsetA.add(leftAxisLabOffsetA))
                        .divide(frameWidthPropertyA)
                        .add(outerTickLengthPropertyR)
        );
   
        tightInsetRightProperty.bind(
                (rightTickLabOffsetA.add(rightAxisLabOffsetA))
                        .divide(frameWidthPropertyA)
                        .add(outerTickLengthPropertyR)
        );
    }
    
    /**
     * Sets the divisor points and labels of the axis. Divisor points must be
     * normalized in the [0;1] interval. The two arrays must be of same length.
     * @param divisors divisor points for the axis.
     * @param labels labels for the points.
     */
    public void setDivisors(double[] divisors, String[] labels) {
        this.divisors = divisors;
        this.labels = labels;
        
        leftVtl.setDivisors(this.divisors, this.labels);
        leftVtl.makeLeft();
        
        rightVtl.setDivisors(this.divisors, this.labels);
        rightVtl.makeRight();
        
        originVtl.setDivisors(this.divisors, this.labels);
        originVtl.makeOrigin();
        
        leftVtm.setDivisors(this.divisors);
        leftVtm.makeLeft();
        
        rightVtm.setDivisors(this.divisors);
        rightVtm.makeRight();
        
        originVtm.setDivisors(this.divisors);
        originVtm.makeOrigin();
        
        grid.setDivisors(this.divisors);
        grid.makeGrid();
    }
    
    /**
     * Sets stroke color for the axis lines and all the tick marks.
     * @param p color as JavaFX Paint object.
     * @param w width in points.
     */
    public void setStroke(Paint p, double w) {
        leftLine.setStroke(p);
        leftLine.setStrokeWidth(w);
        
        rightLine.setStroke(p);
        rightLine.setStrokeWidth(w);
        
        originLine.setStroke(p);
        originLine.setStrokeWidth(w);
        
        leftVtm.setLineStroke(p, w);
        rightVtm.setLineStroke(p, w);
        originVtm.setLineStroke(p, w);
    }
    /**
     * Sets font type and size for the tick labels and axis labels.
     * @param font font type as String.
     * @param weight font weight.
     * @param height font size in points.
     */    
    public void setFont(String font, FontWeight weight, int height) {
        leftVtl.setLabelFont(font, weight, height);
        rightVtl.setLabelFont(font, weight, height);
        originVtl.setLabelFont(font, weight, height);
        
        leftLab.setFont(font, weight,(int)Math.ceil(height * 1.2));
        rightLab.setFont(font, weight, (int)Math.ceil(height * 1.2));
        originLab.setFont(font, weight, (int)Math.ceil(height * 1.2));
    }
    
    // GRID SECTION ============================================================
    
    /**
     * Query for the contained grid.
     * @return grid object.
     */
    public VGrid getGrid() {
        return grid;
    }
    
    /**
     * Turn on vertical grid component.
     */
    public void showGrid() {
        add(grid);
    }
    
    /**
     * Turn off vertical grid component.
     */
    public void hideGrid() {
        remove(grid);
    }
    
    // LEFT SECTION ============================================================
    
    /**
     * Generate the left line.
     */
    private void makeLeftLine() {
        leftLine.startXProperty().bind(
                plotSpaceLeftPropertyR
                    .multiply(frameWidthPropertyA)
        );
        leftLine.endXProperty().bind(
                plotSpaceLeftPropertyR                    
                    .multiply(frameWidthPropertyA)
        );
        leftLine.startYProperty().bind(
                frameHeightPropertyA
                .subtract(plotSpaceBottomPropertyR
                    .multiply(frameHeightPropertyA))
        );
        leftLine.endYProperty().bind(
                frameHeightPropertyA
                .subtract(
                    frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                            .add(plotSpaceHeightPropertyR)))
        );
        
        leftLine.setStroke(Color.BLACK);
        leftLine.setStrokeWidth(1);
    }       
    
    /**
     * Generate the left axis label.
     */
    private void makeLeftLab() {
        leftLab.setText("ylabel");
        leftLab.position(frameWidthPropertyA.multiply(
                    plotSpaceLeftPropertyR
                    .subtract(gapOffsetPropertyR)
                    .subtract(outerTickLengthPropertyR))
                .subtract(leftTickLabOffsetA)
        ,
            frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(plotSpaceHeightPropertyR
                            .divide(2))))
        );
        leftLab.rotate(true);
        leftLab.align(HPos.RIGHT,VPos.CENTER);
        leftLab.setFont("Arial", FontWeight.LIGHT, 14);   

    }
    
    /**
     * Turn on left line. (Just the line, it does not show the other line components.)
     */
    public void showLeftLine() {
        add(leftLine);
    }
    
    /**
     * Turn off left line.
     */
    public void hideLeftLine() {
        remove(leftLine);
    }
    
    /**
     * Turn on left tick labels.
     */
    public void showLeftTickLabels() {
        add(leftVtl);
        leftTickLabOffsetA.unbind();
        leftTickLabOffsetA.bind(leftVtl.offsetProperty()
                .add(frameWidthPropertyA.multiply(gapOffsetPropertyR)));
    }
    
    /**
     * Turn off left tick labels.
     */
    public void hideLeftTickLabels() {
        remove(leftVtl);
        leftTickLabOffsetA.unbind();
        leftTickLabOffsetA.set(0);
    }
        
    /**
     * Turn on left tick marks.
     */
    public void showLeftTickMarks() {
        add(leftVtm);
    }
    
    /**
     * Turn off left tick marks.
     */
    public void hideLeftTickMarks() {
        remove(leftVtm);    
    }
    
    /**
     * Turn on left axis label.
     */
    public void showLeftAxisLabel() {
        add(leftLab.getNode());
        leftAxisLabOffsetA.unbind();
        leftAxisLabOffsetA.bind(
                leftLab.widthProperty()
                        .add(frameWidthPropertyA.multiply(gapOffsetPropertyR))
        );
    }
    
    /**
     * Turn off left axis label.
     */
    public void hideLeftAxisLabel() {
        remove(leftLab.getNode());
        leftAxisLabOffsetA.unbind();
        leftAxisLabOffsetA.set(0);
    }
    
    /**
     * Query left axis label object.
     * @return left axis label.
     */
    public Label getLeftAxisLabel() {
        return leftLab;
    }
    
    /**
     * Query for the offset between the left tick labels and left tick marks.
     * @return absolute value of the left tick label offset in pixels.
     */
    public DoubleProperty leftTickLabOffset() {
        return leftTickLabOffsetA;
    }
        
    /**
     * Query for the offset between the left axis label and the left tick labels.
     * @return absolute value of the left axis label offset in pixels.
     */
    public DoubleProperty leftAxisLabOffset() {
        return leftAxisLabOffsetA;
    }
    
    // RIGHT SECTION ===========================================================
    
    /**
     * Generates right line. (Just the line, it does not show the other line components.)
     */
    private void makeRightLine() {
        rightLine.startXProperty().bind(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR))
        );
        rightLine.endXProperty().bind(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR))
        );
        rightLine.startYProperty().bind(
                frameHeightPropertyA
                .subtract(plotSpaceBottomPropertyR
                    .multiply(frameHeightPropertyA))
        );
        rightLine.endYProperty().bind(
                frameHeightPropertyA
                .subtract(
                    frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                            .add(plotSpaceHeightPropertyR)))
        );
        
        rightLine.setStroke(Color.BLACK);
        rightLine.setStrokeWidth(1);
    }
    
    /**
     * Generates right axis label.
     */
    private void makeRightLab() {
        rightLab.setText("ylabel");
        rightLab.position(frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                    .add(plotSpaceWidthPropertyR)
                    .add(gapOffsetPropertyR)
                    .add(outerTickLengthPropertyR))
                .add(rightTickLabOffsetA)
        ,
            frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(plotSpaceHeightPropertyR
                            .divide(2))))
        );
        rightLab.rotate(true);
        rightLab.align(HPos.LEFT, VPos.CENTER);
        rightLab.setFont("Arial", FontWeight.LIGHT, 14);
    }
    
    /**
     * Turn on right axis line.
     */
    public void showRightLine() {
        add(rightLine);
    }
    
    /**
     * Turn of right axis line.
     */
    public void hideRightLine() {
        remove(rightLine);
    }
    
    /**
     * Turn on right tick labels.
     */
    public void showRightTickLabels() {
        add(rightVtl);
        rightTickLabOffsetA.unbind();
        rightTickLabOffsetA.bind(rightVtl.offsetProperty()
                .add(frameWidthPropertyA.multiply(gapOffsetPropertyR)));
    }
    
    /**
     * Turn off right tick labels.
     */
    public void hideRightTickLabels() {
        remove(rightVtl);
        rightTickLabOffsetA.unbind();
        rightTickLabOffsetA.set(0);
    }
    
    /**
     * Turn on origin tick labels.
     */
    public void showOriginTickLabels() {
        add(originVtl);
    }
    
    /**
     * Turn off origin tick labels.
     */
    public void hideOriginTickLabels() {
        remove(originVtl);
    }
    
    /**
     * Turn on right tick marks.
     */
    public void showRightTickMarks() {
        add(rightVtm);       
    }
    
    /**
     * Turn off right tick marks.
     */
    public void hideRightTickMarks() {
        remove(rightVtm);
    }
    
    /**
     * Turn on right axis label.
     */
    public void showRightAxisLabel() {
        add(rightLab.getNode());
        rightAxisLabOffsetA.unbind();
        rightAxisLabOffsetA.bind(
                rightLab.widthProperty()
                        .add(frameWidthPropertyA.multiply(gapOffsetPropertyR))
        );
    } 
    
    /**
     * Turn off right axis label.
     */
    public void hideRightAxisLabel() {
        remove(rightLab.getNode());
        rightAxisLabOffsetA.unbind();
        rightAxisLabOffsetA.set(0);
    }
    
    /**
     * Query for the right axis label object.
     * @return right axis label.
     */
    public Label getRightAxisLabel() {
        return rightLab;
    }
    
    /**
     * Query for the offset between the right tick labels and right tick marks.
     * @return absolute value of the right tick label offset in pixels.
     */
    public DoubleProperty rightTickLabOffset() {
        return rightTickLabOffsetA;
    }
    
    /**
     * Query for the offset between the right axis label and the right tick labels.
     * @return absolute value of the right axis label offset in pixels.
     */
    public DoubleProperty rightAxisLabOffset() {
        return rightAxisLabOffsetA;
    }
    
    // ORIGIN SECTION ==========================================================
    
    /**
     * Generate origin axis line. (Just the line, it does not show the other line components.)
     */
    private void makeOriginLine() {
        originLine.startXProperty().bind(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                        .add(originVerticalPropertyR
                            .multiply(plotSpaceWidthPropertyR)))
        );
        originLine.endXProperty().bind(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                        .add(originVerticalPropertyR
                            .multiply(plotSpaceWidthPropertyR)))
        );
        originLine.startYProperty().bind(
                frameHeightPropertyA
                .subtract(plotSpaceBottomPropertyR
                    .multiply(frameHeightPropertyA))
        );
        originLine.endYProperty().bind(
                frameHeightPropertyA
                .subtract(
                    frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                            .add(plotSpaceHeightPropertyR)))
        );
        
        originLine.setStroke(Color.BLACK);
        originLine.setStrokeWidth(1);
    }
    
    /**
     * Generates origin axis label.
     */
    private void makeOriginLab() {
        originLab.setText("ylabel");
        originLab.position(
            frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                    .add(originVerticalPropertyR
                        .multiply(plotSpaceWidthPropertyR))
                    .add(gapOffsetPropertyR))
                .add(innerTickLengthPropertyR
                    .multiply(frameWidthPropertyA))
        ,
            frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(plotSpaceHeightPropertyR)))
                .subtract(gapOffsetPropertyR)
        );
        originLab.align(HPos.LEFT,VPos.TOP);
        originLab.setFont("Arial", FontWeight.LIGHT, 14);
    }
    
    /**
     * Turn on origin axis line.
     */
    public void showOriginLine() {
        add(originLine);
    }
    
    /**
     * Turn off origin axis line.
     */
    public void hideOriginLine() {
        remove(originLine);
    }

    /**
     * Turn on origin tick marks.
     */
    public void showOriginTickMarks() {
        add(originVtm);
    }
    
    /**
     * Turn off origin tick marks.
     */
    public void hideOriginTickMarks() {
        remove(originVtm);
    }
    
    /**
     * Turn on origin axis labels.
     */
    public void showOriginAxisLabel() {
        add(originLab.getNode());
    }
    
    /**
     * Turn off origin axis labels.
     */
    public void hideOriginAxisLabel() {
        remove(originLab.getNode());
    }
    
    /**
     * Query origin axis label object.
     * @return origin axis label.
     */
    public Label getOriginAxisLabel() {
        return originLab;
    }

    

    

    
    
    

    
}
