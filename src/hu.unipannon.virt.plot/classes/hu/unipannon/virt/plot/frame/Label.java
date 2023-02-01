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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Custom Label implementation that can rotate the label and align it to
 * a specific VPos and HPos. Rotate can be true or false.<br>
 * <pre>
 * LEFT CENTER   RIGHT<br>
 *   O----O----O       TOP<br>
 *   |         |<br>
 *   O    O    O       CENTER<br>
 *   |         |<br>
 *   O----O----O       BOTTOM<br>
 * </pre>
 * @see Component
 * @author Tóth Bálint
 */
public class Label implements Component {
    
    private VBox wrapper;
    private Text t;

    private HPos alignX;
    private VPos alignY;
    private final double rotateAngle = 270;
    
    private boolean isRotate = false;
    
    
    private final boolean DEBUG_BORDER = false;
    
    private DoubleProperty heightProperty;
    private DoubleProperty widthProperty;
    
    /**
     * Default constructs a Label objct.
     * Alignment is center / center.
     * Position comes from the wrapper VBox.
     */
    public Label() {        

        this.t = new Text("Title");
        t.setTextOrigin(VPos.CENTER);
        Group inner = new Group();
        inner.getChildren().add(t);
        wrapper = new VBox();
        wrapper.getChildren().add(inner);
        wrapper.setManaged(true);
        if (DEBUG_BORDER)
            wrapper.setStyle("-fx-border-color: red;");
        
        
        alignX = HPos.CENTER;
        alignY = VPos.CENTER;
        
        align(alignX,alignY);    
        
        widthProperty = new SimpleDoubleProperty();
        widthProperty.bind(wrapper.widthProperty());
        heightProperty = new SimpleDoubleProperty();
        heightProperty.bind(wrapper.heightProperty());
    }
    
    /**
     * Creates a label with the specified text.
     * @param text text to be displayed.
     */
    public Label(String text) {
        this();
        t.setText(text);
    }
    
    /**
     * Updates the label with the selected settings.
     */
    private void refresh() {
        rotate(isRotate);
        align(alignX,alignY);
        // width and height
    }
    
    /**
     * Sets the text on the label.
     * @param text text to be displayed.
     */
    public void setText(String text) {
        t.setText(text);
        refresh();
    }

    /**
     * Sets the font parameters of the label.
     * @param font font type name as String
     * @param weight font weight.
     * @param size font size in int.
     */
    public void setFont(String font,FontWeight weight,int size) {
        t.setFont(Font.font(font,weight,size));
        refresh();
    }

    /**
     * Sets the rotation of the label.
     * For the purposes of this plotting library, a label can be rotated 90 degrees
     * to the left.
     * @param isRotate true = rotated by 90° left 
     */
    public void rotate(boolean isRotate) {
        this.isRotate = isRotate;
        
        if (this.isRotate) {
            wrapper.getTransforms().clear();            
            wrapper.setRotate(rotateAngle);
        }
        align(alignX,alignY);
    }
    
    // need it once, then modify h/v Offset
    /**
     * Component interface implementation.
     * @param translateX x coordinate of the component with a property binding.
     * @param translateY y coordinate of the component with a property binding.
     */
    @Override
    public void position(DoubleBinding translateX, DoubleBinding translateY) {
        wrapper.layoutXProperty().bind(translateX);
        wrapper.layoutYProperty().bind(translateY);
    }

    /**
     * Component interface implementation.
     * @param alignX vertical component of the alginment point.
     * @param alignY horizontal component of the alginment point. 
     */
    @Override
    public void align(HPos alignX, VPos alignY) {
        this.alignX = alignX;
        this.alignY = alignY;
        wrapper.translateXProperty().unbind();
        wrapper.translateYProperty().unbind();

        switch (alignX) {
            case CENTER: {
                //hOffsetProperty.set(-1 * (t.getLayoutBounds().getWidth() / 2));
                wrapper.translateXProperty().bind(wrapper.widthProperty().divide(-2));
                break;
            } 
            case LEFT: {
                //hOffsetProperty.set(t.wrappingWidthProperty().get() / 2);
                if (isRotate)
                    wrapper.translateXProperty().bind(wrapper.widthProperty().divide(-2).add(wrapper.heightProperty().divide(2)));
                else
                    wrapper.translateXProperty().set(0);
                break;
            }
            case RIGHT: {
                if (isRotate)
                    wrapper.translateXProperty().bind(wrapper.widthProperty().divide(-2).subtract(wrapper.heightProperty().divide(2)));
                else
                    wrapper.translateXProperty().bind(wrapper.widthProperty().multiply(-1));
                break;
            }
            default: break;
        }

        switch (alignY) {
            case BOTTOM:
                wrapper.translateYProperty().bind(wrapper.heightProperty().multiply(-1));
                break;
            case TOP:
                wrapper.translateYProperty().bind(wrapper.heightProperty().multiply(0));
                break;
            case CENTER:
                wrapper.translateYProperty().bind(wrapper.heightProperty().divide(-2));
                break;
            default: break;
        }
        
    }

    /**
     * Component interface implementation.
     * @return contained Scene Graph node of a component.
     */
    @Override
    public Node getNode() {
        return wrapper;
    }

    /**
     * Custom height property that can be different based on rotation.
     * @return height property counting the rotation
     */
    public ReadOnlyDoubleProperty heightProperty() {
        if (isRotate)
            return wrapper.widthProperty();
        else
            return wrapper.heightProperty();
    }
    
    /**
     * Custom width property that can be different based on rotation.
     * @return width property counting the rotation
     */
    public ReadOnlyDoubleProperty widthProperty() {
        if (isRotate)
            return wrapper.heightProperty();
        else
            return wrapper.widthProperty();
    }
    
    /**
     * Query for the length of the text.
     * @return length of the String displayed in the label.
     */
    public double textLength() {
        return t.getText().length();
    }
    
}
