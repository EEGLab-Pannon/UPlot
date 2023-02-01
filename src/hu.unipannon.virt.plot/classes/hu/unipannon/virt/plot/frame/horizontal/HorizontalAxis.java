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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.FontWeight;
import hu.unipannon.virt.plot.frame.Container;
import hu.unipannon.virt.plot.frame.Label;

/**
 * Base class of the Horizontal Axis, it contains all the components in the axis.
 * In order to turn on items, the Axis object must be turned on too. The class 
 * extends the <code>Container</code> class, it is usually bound to the <code>
 * Frame</code> class.
 * 
 * @author Tóth Bálint
 */
public class HorizontalAxis extends Container {
    
    private double[] divisors;
    private String[] labels;
    
    private Line lowerLine;
    private Line upperLine;
    private Line originLine;
    
    private HTickMarks lowerHtm;
    private HTickMarks upperHtm;
    private HTickMarks originHtm;
    
    private HTickLabels lowerHtl;
    private HTickLabels upperHtl;
    private HTickLabels originHtl;
    
    private Label lowerLab;
    private Label upperLab;
    private Label originLab;
    
    private DoubleProperty lowerTickLabOffsetA;
    private DoubleProperty upperTickLabOffsetA;  
    
    private DoubleProperty upperAxisLabOffsetA;
    private DoubleProperty lowerAxisLabOffsetA;
    
    private HGrid grid;
    
    /**
     * Constructs all components and binds them to this objects properties.
     */
    public HorizontalAxis() {
        lowerLine = new Line();
        makeLowerLine();
        
        upperLine = new Line();
        makeUpperLine();
        
        originLine = new Line();
        makeOriginLine();
        
        lowerHtl = new HTickLabels();
        lowerHtl.connectTo(this);
        
        upperHtl = new HTickLabels();
        upperHtl.connectTo(this);
        
        originHtl = new HTickLabels();
        originHtl.connectTo(this);
        
        lowerHtm = new HTickMarks();
        lowerHtm.connectTo(this);
        
        upperHtm = new HTickMarks();
        upperHtm.connectTo(this);
        
        originHtm = new HTickMarks();
        originHtm.connectTo(this);
 
        lowerTickLabOffsetA = new SimpleDoubleProperty(0);
        upperTickLabOffsetA = new SimpleDoubleProperty(0);

        
        lowerLab = new Label();
        makeLowerLab();
        
        upperLab = new Label();
        makeUpperLab();
        
        originLab = new Label();
        makeOriginLab();
        
        grid = new HGrid();
        grid.connectTo(this);
        
        upperAxisLabOffsetA = new SimpleDoubleProperty(0);
        lowerAxisLabOffsetA = new SimpleDoubleProperty(0);
        

        tightInsetTopProperty.bind(
                ((upperAxisLabOffsetA.add(upperTickLabOffsetA))
                        .divide(frameHeightPropertyA))
                        .add(outerTickLengthPropertyR));
        
        tightInsetBottomProperty.bind(
                ((lowerAxisLabOffsetA.add(lowerTickLabOffsetA))
                        .divide(frameHeightPropertyA))
                        .add(outerTickLengthPropertyR));
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
        
        lowerHtl.setDivisors(this.divisors, this.labels);
        lowerHtl.makeLower();
        
        upperHtl.setDivisors(this.divisors, this.labels);
        upperHtl.makeUpper();
        
        originHtl.setDivisors(this.divisors, this.labels);
        originHtl.makeOrigin();
        
        lowerHtm.setDivisors(this.divisors);
        lowerHtm.makeLower();
        
        upperHtm.setDivisors(this.divisors);
        upperHtm.makeUpper();
        
        originHtm.setDivisors(this.divisors);
        originHtm.makeOrigin();
        
        grid.setDivisors(this.divisors);
        grid.makeGrid();
    }
        
    /**
     * Sets stroke color for the axis lines and all the tick marks.
     * @param p color as JavaFX Paint object.
     * @param w width in points.
     */
    public void setStroke(Paint p, double w) {
        upperLine.setStroke(p);
        upperLine.setStrokeWidth(new Size(w, SizeUnits.PT).pixels());
        
        lowerLine.setStroke(p);
        lowerLine.setStrokeWidth(new Size(w, SizeUnits.PT).pixels());
        
        originLine.setStroke(p);
        originLine.setStrokeWidth(new Size(w, SizeUnits.PT).pixels());
        
        upperHtm.setLineStroke(p, w);
        lowerHtm.setLineStroke(p, w);
        originHtm.setLineStroke(p,w);
    }
    
    /**
     * Sets font type and size for the tick labels and axis labels.
     * @param font font type as String.
     * @param weight font weight.
     * @param height font size in points.
     */
    public void setFont(String font, FontWeight weight, int height) {
        lowerHtl.setLabelFont(font, weight, height);
        upperHtl.setLabelFont(font, weight, height);
        originHtl.setLabelFont(font, weight, height);
        
        
        originLab.setFont(font, weight, (int)Math.ceil(height * 1.2));
        lowerLab.setFont(font, weight, (int)Math.ceil(height * 1.2));
        upperLab.setFont(font, weight, (int)Math.ceil(height * 1.2));
    }
    
    // GRID SECTION ============================================================
    
    /**
     * Query for the contained grid.
     * @return grid object.
     */
    public HGrid getGrid() {
        return grid;
    }
    
    /**
     * Turn on horizontal grid component.
     */
    public void showGrid() {
        add(grid);
    }
    
    /**
     * Turn off horizontal grid component.
     */
    public void hideGrid() {
        remove(grid);
    }
    
    // LOWER SECTION ===========================================================
    
    /**
     * Generate the lower line.
     */
    private void makeLowerLine() {
        lowerLine.startXProperty().bind(
                plotSpaceLeftPropertyR
                    .multiply(frameWidthPropertyA));
        lowerLine.endXProperty().bind(
                frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                .add(plotSpaceWidthPropertyR))
        );
        lowerLine.startYProperty().bind(
                frameHeightPropertyA
                .subtract(plotSpaceBottomPropertyR
                    .multiply(frameHeightPropertyA))
        );
        lowerLine.endYProperty().bind(
                frameHeightPropertyA
                .subtract(plotSpaceBottomPropertyR
                    .multiply(frameHeightPropertyA))
        );
        
        lowerLine.setStroke(Color.BLACK);
        lowerLine.setStrokeWidth(1); 
    }  
        
    /**
     * Generate label for the lower axis component.
     */
    private void makeLowerLab() {
        lowerLab.setText("xlabel");
        lowerLab.position(frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR
                    .divide(2)))
        ,
            frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .subtract(outerTickLengthPropertyR
                            .add(gapOffsetPropertyR))))
                .add(lowerTickLabOffsetA)
        );
        lowerLab.align(HPos.CENTER, VPos.TOP);
        lowerLab.setFont("Arial", FontWeight.LIGHT, 14);
    }
    
    /**
     * Turn on lower line. (Just the line, it does not show the other line components.)
     */
    public void showLowerLine() {
        add(lowerLine);
    }
    
    /**
     * Turn off the lower line.
     */
    public void hideLowerLine() {
        remove(lowerLine);
    }
    
    /**
     * Turn on lower tick marks.
     */
    public void showLowerTickMarks() {
        add(lowerHtm);
    }
    
    /**
     * Turn off lower tick marks.
     */
    public void hideLowerTickMarks() {
        remove(lowerHtm);
    }
    
    /**
     * Turn on lower tick labels.
     */
    public void showLowerTickLabels() {
        add(lowerHtl);
        lowerTickLabOffsetA.unbind();
        lowerTickLabOffsetA.bind(
                lowerHtl.offsetProperty()
                        .add(frameHeightPropertyA.multiply(gapOffsetPropertyR))
        );
    }
    
    /**
     * Turn on lower tick labels.
     */
    public void hideLowerTickLabels() {
        remove(lowerHtl);
        lowerTickLabOffsetA.unbind();
        lowerTickLabOffsetA.set(0);
    }
  
    /**
     * Turn on the lower label.
     */
    public void showLowerAxisLabel() {
        add(lowerLab.getNode());
        lowerAxisLabOffsetA.unbind();
        lowerAxisLabOffsetA.bind(
                lowerLab.heightProperty()
                        .add(frameHeightPropertyA.multiply(gapOffsetPropertyR))
        );
    }
    
    /**
     * Turn off the lower label.
     */
    public void hideLowerAxisLabel() {
        remove(originLab.getNode());
        lowerAxisLabOffsetA.unbind();
        lowerAxisLabOffsetA.set(0);
    }
    
    /**
     * Query for the lower axis label.
     * @return lower axis label object.
     */
    public Label getLowerAxisLabel() {
        return lowerLab;
    }
   
    /**
     * Query for the offset between the lower tick labels and lower tick marks.
     * @return absolute value of the lower tick label offset in pixels.
     */
    public DoubleProperty lowerTickLabOffset() {
        return lowerTickLabOffsetA;
    }
    
    /**
     * Query for the offset between the lower axis label and the lower tick labels.
     * @return absolute value of the lower axis label offset in pixels.
     */
    public DoubleProperty lowerAxisLabOffset() {
        return lowerAxisLabOffsetA;
    }
    
    // UPPER SECTION ===========================================================
    
    /**
     * Generates the upper line component.
     */
    private void makeUpperLine() {
        upperLine.startXProperty().bind(
                plotSpaceLeftPropertyR
                    .multiply(frameWidthPropertyA));
        upperLine.endXProperty().bind(
                frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                .add(plotSpaceWidthPropertyR))
        );
        upperLine.startYProperty().bind(
                frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(plotSpaceHeightPropertyR)))
        );
        upperLine.endYProperty().bind(
                frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(plotSpaceHeightPropertyR)))
        );
        
        upperLine.setStroke(Color.BLACK);
        upperLine.setStrokeWidth(1); 
    }
            
    /**
     * Generates upper axis label.
     */
    private void makeUpperLab() {
        upperLab.setText("xlabel");
        upperLab.position(frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                        .add(plotSpaceWidthPropertyR
                    .divide(2)))
        ,
            frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(plotSpaceHeightPropertyR)
                        .add(gapOffsetPropertyR)
                        .add(outerTickLengthPropertyR)))
                .subtract(upperTickLabOffsetA)
        );
        upperLab.align(HPos.CENTER, VPos.BOTTOM);
        upperLab.setFont("Arial", FontWeight.LIGHT, 14);
    }
    
    /**
     * Turn on upper axis line (just the line, not show other axis components).
     */
    public void showUpperLine() {
        add(upperLine);
    }
    
    /**
     * Turn off upper axis line.
     */
    public void hideUpperLine() {
        remove(upperLine);
    }
        
    /**
     * Turn on upper tick marks.
     */
    public void showUpperTickMarks() {
        add(upperHtm);
    }
    
    /**
     * Turn off upper tick marks.
     */
    public void hideUpperTickMarks() {
        remove(upperHtm);
    }
        
    /**
     * Turn on upper tick labels.
     */
    public void showUpperTickLabels() {
        add(upperHtl);
        upperTickLabOffsetA.unbind();
        upperTickLabOffsetA.bind(
                upperHtl.offsetProperty()
                        .add(frameHeightPropertyA.multiply(gapOffsetPropertyR))
        );
    }
    
    /**
     * Turn off upper tick labels.
     */
    public void hideUpperTickLabels() {
        remove(upperHtl);
        upperTickLabOffsetA.unbind();
        upperTickLabOffsetA.set(0);
    }
    
    /**
     * Turn on upper axis label.
     */
    public void showUpperAxisLabel() {
        add(upperLab.getNode());
        
        upperAxisLabOffsetA.unbind();
        upperAxisLabOffsetA.bind(
                upperLab.heightProperty()
                        .add(frameHeightPropertyA.multiply(gapOffsetPropertyR))
        );
    }
    
    /**
     * Turn off upper axis labels.
     */
    public void hideUpperAxisLabel() {
        remove(upperLab.getNode());
        
        upperAxisLabOffsetA.unbind();
        upperAxisLabOffsetA.set(0);
    }
    
    /**
     * Query upper axis label.
     * @return upper axis label object.
     */
    public Label getUpperAxisLabel() {
        return upperLab;
    }
        

    /**
     * Query for the offset between the upper tick labels and tick marks.
     * @return absolute value of the upper tick label offset in pixels.
     */
    public DoubleProperty upperTickLabOffset() {
        return upperTickLabOffsetA;
    }
    
    /**
     * Query for the offset between the upper axis label and the upper tick labels.
     * @return absolute value of the upper axis label offset in pixels.
     */
    public DoubleProperty upperAxisLabOffset() {
        return upperAxisLabOffsetA;
    }
    
    // ORIGIN SECTION ==========================================================
    
    /**
     * Generates origin axis line component.
     */
    private void makeOriginLine() {
        originLine.startXProperty().bind(
                plotSpaceLeftPropertyR
                    .multiply(frameWidthPropertyA));
        originLine.endXProperty().bind(
                frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                .add(plotSpaceWidthPropertyR))
        );
        originLine.startYProperty().bind(
                frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(originHorizontalPropertyR
                            .multiply(plotSpaceHeightPropertyR))))
        );
        originLine.endYProperty().bind(
                frameHeightPropertyA
                .subtract(frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(originHorizontalPropertyR
                            .multiply(plotSpaceHeightPropertyR))))
        );
        
        originLine.setStroke(Color.BLACK);
        originLine.setStrokeWidth(1);
    }
    
    /**
     * Generates origin axis label.
     */
    private void makeOriginLab() {
        originLab.setText("xlabel");
        originLab.position(
            frameWidthPropertyA
                .multiply(plotSpaceLeftPropertyR
                    .add(plotSpaceWidthPropertyR
                        .subtract(gapOffsetPropertyR)))
        ,
            frameHeightPropertyA
                .subtract((frameHeightPropertyA
                    .multiply(plotSpaceBottomPropertyR
                        .add(originHorizontalPropertyR
                            .multiply(plotSpaceHeightPropertyR))
                        .add(gapOffsetPropertyR)))
                    .add(innerTickLengthPropertyR
                        .multiply(frameWidthPropertyA)))
        );
        originLab.align(HPos.RIGHT, VPos.BOTTOM);
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
    public void hideOriginline() {
        remove(originLine);
    }
    
    /**
     * Turn on origin tick marks.
     */
    public void showOriginTickMarks() {
        add(originHtm);
    }
    
    /**
     * Turn off origin tick marks.
     */
    public void hideOriginTickMarks() {
        remove(originHtm);
    }
    
    /**
     * Turn on origin tick labels.
     */
    public void showOriginTickLabels() {
        add(originHtl);
    }
    
    /**
     * Turn off origin tick labels.
     */
    public void hideOriginTickLabels() {
        remove(originHtl);
    }

    /**
     * Turn on origin axis label.
     */
    public void showOriginAxisLabel() {
        add(originLab.getNode());
    }
    
    /**
     * Turn off origin axis label.
     */
    public void hideOriginAxisLabel() {
        remove(originLab.getNode());
    }
    
    /**
     * Query origin axis label.
     * @return origin label object.
     */
    public Label getOriginAxisLabel() {
        return originLab;
    }
}