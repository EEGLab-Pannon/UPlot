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
import java.util.stream.Stream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.text.FontWeight;
import hu.unipannon.virt.plot.frame.Container;
import hu.unipannon.virt.plot.frame.Label;

/**
 * The <code>HTickLabels</code> class implements the generation of the tick label
 * objects based on the divisor points, as well as contains functions to set the 
 * graphical properties of the labels. The class is a container and is usually
 * bound to the HorizontalAxis.
 * 
 * @see HorizontalAxis
 * @author Tóth Bálint
 */
public class HTickLabels extends Container {
    
    private double[] divisors;
    private String[] labels;
    
    List<Label> labelObjs;
    
    private DoubleProperty offsetPropertyA;
    
    /**
     * Default constructs an empty HTickLabels object.
     */
    public HTickLabels() {
        this.divisors = new double[]{};
        this.labels = new String[]{};
        offsetPropertyA = new SimpleDoubleProperty();
    }
    
    /**
     * Setter for the divisor points and the labels displayed at those points. 
     * Divisors must be normalized to the [0;1] interval.
     * The two arrays must be of same size.
     * @param divisors normalized divisor points.
     * @param labels labels for the division points. 
     */
    public void setDivisors(double[] divisors, String[] labels) {
        getChildren().clear();
        this.divisors = divisors;
        this.labels = labels;
        this.labelObjs = new LinkedList<>();
    }
    
    /**
     * Adds all labels to the inside JavaFX Group object aligned to the given
     * positions.
     * @param hpos horizontal alignment positions of the labels.
     * @param vpos vertical alignment positions of the labels.
     */
    public void addLabels(HPos hpos, VPos vpos) {
        getChildren().addAll(
            labelObjs
                .stream()
                .map(
                    (Label l) -> {
                        l.align(hpos, vpos);
                        l.setFont("Arial", FontWeight.LIGHT, 12);
                        return l;
                    })
                .flatMap(
                    (Label l) -> Stream.of(l.getNode()))
                .toArray(Node[]::new));
        setOffset();
    }
    
    /**
     * Sets font type and size for the tick labels and axis labels.
     * @param font font type as String.
     * @param weight font weight.
     * @param height font size in points.
     */
    public void setLabelFont(String font, FontWeight weight, int height) {
        labelObjs
            .stream()
            .forEach((Label l) -> l.setFont(font, weight, height));
        setOffset();
    }
    
    /**
     * used to set the ofset for all the labels
     * gap ofset not included!
     */
    private void setOffset() {
        if (labelObjs.size() != 0) {
            offsetPropertyA.unbind();
            offsetPropertyA.bind(labelObjs.get(0).heightProperty());
        } else {
            offsetPropertyA.unbind();
            offsetPropertyA.set(0);
        }
    }
    
    /**
     * Query of the labels' distance from the tickmarks' lower position.
     * @return absolute value of the offset.
     */
    public DoubleProperty offsetProperty() {
        return offsetPropertyA;
    }
    
    /**
     * Generate lower tick labels.
     */
    public void makeLower() {
        labelObjs.clear();
        for (int i=0;i<divisors.length;i++) {
            Label l = new Label(labels[i]);
            l.position(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                    .add(plotSpaceWidthPropertyR
                        .multiply(divisors[i])))
            ,
                frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                            .subtract(gapOffsetPropertyR
                            .add(outerTickLengthPropertyR))))
            );
            labelObjs.add(l);
        }
        addLabels(HPos.CENTER, VPos.TOP);
    }
    
    /**
     * Generate upper tick labels.
     */
    public void makeUpper() {
        labelObjs.clear();
        for (int i=0;i<divisors.length;i++) {
            Label l = new Label(labels[i]);
            l.position(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                    .add(plotSpaceWidthPropertyR
                        .multiply(divisors[i])))
            ,
                frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                            .add(plotSpaceHeightPropertyR)
                            .add(gapOffsetPropertyR
                            .add(outerTickLengthPropertyR))))
            );
            labelObjs.add(l);
        }
            addLabels(HPos.CENTER, VPos.BOTTOM);
    }
    
    /**
     * Generate origin tick labels.
     */
    public void makeOrigin() {
        labelObjs.clear();
        for (int i=0;i<divisors.length;i++) {
            Label l = new Label(labels[i]);
            l.position(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR
                    .add(plotSpaceWidthPropertyR
                        .multiply(divisors[i])))
            ,
                frameHeightPropertyA
                    .subtract(frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR
                        .add(originHorizontalPropertyR.multiply(plotSpaceHeightPropertyR))
                        .subtract(gapOffsetPropertyR
                            .add(outerTickLengthPropertyR))))
            );
            labelObjs.add(l);
        }
            addLabels(HPos.CENTER, VPos.TOP);
    }
    
}
