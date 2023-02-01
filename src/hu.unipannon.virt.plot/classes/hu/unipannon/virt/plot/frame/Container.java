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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
/**
 * Abstract base class that is used to persist the same properties in the Frame.
 * Instead of Container instances refering to each other in the JavaFX scene 
 * graph, this method "duplicates" the parent's properties. This makes it possible
 * to modify certain properties that than doesn't change properties on higher
 * level of the component tree. At the same time, this class implements all the 
 * getter codes for all the posssible properties.
 * <br><br>
 * Properties ending with the letter A are absolute values in pixels.<br>
 * Properties ending with the letter R are values relative to the screen's width
 * and height. Values range from 0.0 to 1.0.
 * <br><br>
 * This class extends the <code>Group</code> class from JavaFX, nodes can be added
 * and removed from the object. This is the basis of the plot component placement.
 * 
 * @author Tóth Bálint
 */
public abstract class Container extends Group {
    
    // Absolute
    protected DoubleProperty frameWidthPropertyA;
    protected DoubleProperty frameHeightPropertyA;
    protected DoubleProperty frameLeftPropertyA;
    protected DoubleProperty frameBottomPropertyA;
    
    // Relative
    protected DoubleProperty plotSpaceLeftPropertyR;
    protected DoubleProperty plotSpaceBottomPropertyR;
    protected DoubleProperty plotSpaceWidthPropertyR;
    protected DoubleProperty plotSpaceHeightPropertyR;
    
    // Relative
    protected DoubleProperty gapOffsetPropertyR;
    protected DoubleProperty innerTickLengthPropertyR;
    protected DoubleProperty outerTickLengthPropertyR;
    
    // Relative
    protected DoubleProperty originHorizontalPropertyR;
    protected DoubleProperty originVerticalPropertyR;
    
    // Relative
    protected DoubleProperty tightInsetTopProperty;
    protected DoubleProperty tightInsetBottomProperty;
    protected DoubleProperty tightInsetLeftProperty;
    protected DoubleProperty tightInsetRightProperty;
    
    /**
     * Default constructor, does not initialise its properties.
     */
    public Container() {
        plotSpaceLeftPropertyR = new SimpleDoubleProperty();
        plotSpaceBottomPropertyR = new SimpleDoubleProperty();
        plotSpaceWidthPropertyR = new SimpleDoubleProperty();
        plotSpaceHeightPropertyR = new SimpleDoubleProperty();
        
        frameWidthPropertyA = new SimpleDoubleProperty();
        frameHeightPropertyA = new SimpleDoubleProperty();
        frameLeftPropertyA = new SimpleDoubleProperty();
        frameBottomPropertyA = new SimpleDoubleProperty();
        
        gapOffsetPropertyR = new SimpleDoubleProperty();
        innerTickLengthPropertyR = new SimpleDoubleProperty();
        outerTickLengthPropertyR = new SimpleDoubleProperty();
        
        originHorizontalPropertyR = new SimpleDoubleProperty();
        originVerticalPropertyR = new SimpleDoubleProperty();
        
        tightInsetBottomProperty = new SimpleDoubleProperty();
        tightInsetLeftProperty = new SimpleDoubleProperty();
        tightInsetRightProperty = new SimpleDoubleProperty();
        tightInsetTopProperty = new SimpleDoubleProperty();
    }
    
    /**
     * Connects the container object to a given parent.
     * Binds all properties.
     * @param c parent to be connected to.
     */
    public void connectTo(Container c) {
        plotSpaceLeftPropertyR.bind(c.plotSpaceLeftPropertyR);
        plotSpaceBottomPropertyR.bind(c.plotSpaceBottomPropertyR);
        plotSpaceHeightPropertyR.bind(c.plotSpaceHeightPropertyR);
        plotSpaceWidthPropertyR.bind(c.plotSpaceWidthPropertyR);
        
        frameLeftPropertyA.bind(c.frameLeftPropertyA);
        frameBottomPropertyA.bind(c.frameBottomPropertyA);        
        frameHeightPropertyA.bind(c.frameHeightPropertyA);
        frameWidthPropertyA.bind(c.frameWidthPropertyA);
        
        gapOffsetPropertyR.bind(c.gapOffsetPropertyR);
        innerTickLengthPropertyR.bind(c.innerTickLengthPropertyR);
        outerTickLengthPropertyR.bind(c.outerTickLengthPropertyR);
        
        originHorizontalPropertyR.bind(c.originHorizontalPropertyR);
        originVerticalPropertyR.bind(c.originVerticalPropertyR);
    }
    
    // Getters for the properties.
    
    public DoubleProperty plotSpaceLeft() {
        return plotSpaceLeftPropertyR;
    }
    
    public DoubleProperty plotSpaceBottom() {
        return plotSpaceBottomPropertyR;
    }
    
    public DoubleProperty plotSpaceWidth() {
        return plotSpaceWidthPropertyR;
    }
    
    public DoubleProperty plotSpaceHeight() {
        return plotSpaceHeightPropertyR;
    }
    
    
    
    public DoubleProperty frameLeft() {
        return frameLeftPropertyA;
    }
    
    public DoubleProperty frameBottom() {
        return frameBottomPropertyA;
    }
    
    public DoubleProperty frameWidth() {
        return frameWidthPropertyA;
    }
    
    public DoubleProperty frameHeight() {
        return frameHeightPropertyA;
    }
    
    
    
    public DoubleProperty gapOffset() {
        return gapOffsetPropertyR;
    }
    
    public DoubleProperty innerTickLength() {
        return innerTickLengthPropertyR;
    }
    
    public DoubleProperty outerTickLength() {
        return outerTickLengthPropertyR;
    }
    
    
    
    public DoubleProperty originHorizontal() {
        return originHorizontalPropertyR;
    }
    
    public DoubleProperty originVerticalProperty() {
        return originVerticalPropertyR;
    }
    
    
    /**
     * Add a node to the group.
     * @param n node to be added.
     */
    public void add(Node n) {
        if (!getChildren().contains(n))
            getChildren().add(n);
    }
    
    /**
     * Removes a node from the group.
     * @param n node to be removed.
     */
    public void remove(Node n) {
        getChildren().remove(n);
    }
}
