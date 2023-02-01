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

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;

/**
 * Common interface for components featured in the Frame.
 * Defines methods for standard ways of positioning and aligning components.
 * @see Frame
 * @see Label
 * @author Tóth Bálint
 */
public interface Component {
    /**
     * Method for components and containers to be able to set each others position.
     * The position connects to the alignment point.
     * with a double property binding.
     * @param translateX x coordinate of the component with a property binding.
     * @param translateY y coordinate of the component with a property binding.
     */
    void position(DoubleBinding translateX, DoubleBinding translateY);
    
    /**
     * Sets the alignment point inside the component. Uses JavaFX position values.
     * @param alignX vertical component of the alginment point.
     * @param alignY horizontal component of the alginment point.
     */
    void align(HPos alignX, VPos alignY);
    
    /**
     * Getter for the contained JavaFX node.
     * @return contained Scene Graph node of a component.
     */
    Node getNode();

}
