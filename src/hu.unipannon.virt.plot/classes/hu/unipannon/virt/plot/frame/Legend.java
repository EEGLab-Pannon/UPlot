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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Legends can be displayed inside the Plot space.
 * Each legend entry contains a String description and a graphical item.
 * The graphical item can be a line (with marker), a marker and a filled rectangle
 * based on the item it represents
 * 
 * @see Component
 * @author Tóth Bálint
 */
public class Legend implements Component {
    
    private final boolean DEBUG_BORDER = false;
    
    // layout variables
    private DoubleProperty hOffsetProperty;
    private DoubleProperty vOffsetProperty;
   
    // container variables
    private BorderPane node;
    private GridPane inner;
    int activeRow = 0;
    
    /**
     * Default constructs an empty legend object.
     */
    public Legend() {
        hOffsetProperty = new SimpleDoubleProperty();
        vOffsetProperty = new SimpleDoubleProperty();
        
        node = new BorderPane();
        inner = new GridPane();
        node.setCenter(inner);
        if (DEBUG_BORDER) {
            node.setStyle("-fx-border-color: red;-fx-background-color: white;");
            inner.setStyle("-fx-border-color: blue;");
        } else {
            node.setStyle("-fx-background-color: white;");
            node.applyCss();
        }
        
        inner.setPadding(new Insets(5));
        inner.setHgap(3);
        inner.setVgap(5);
        node.toFront();
    }

    /**
     * Sets a custom title for the legend. If not called, the legend will appear
     * without a title.
     * @param title title as String.
     */
    public void setTitle(String title) {
        Text t = new Text(title);
        t.setFont(Font.font("Arial",FontWeight.BOLD,14));
        t.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(t, Pos.CENTER);
        node.setTop(t);
    }
    
    /**
     * Adds a line item to the legend.
     * @param color the color of the line.
     * @param style style of the line.
     * @param width width of the line in points.
     * @param markerType marker type. If you don't want a marker to be displayed, pass in Marker.MarkerType.NONE.
     * @param description the label that appears nex to the line.
     */
    public void addLine(Paint color, StrokeStyle style, double width, Marker.MarkerType markerType, String description) {
        Line l = new Line();
        l.setStroke(color);
        l.setStrokeWidth(width);
        switch (style) {
            case NORMAL:
                break;
            case DASH:
                l.getStrokeDashArray().addAll(8d,11d);
                break;
            case DOT:
                l.getStrokeDashArray().addAll(2d);
                break;
            case DASH_DOT:
                l.getStrokeDashArray().addAll(10d,5d,3d,5d);
                break;
            default:
                break;
        }
        
        Marker m = new Marker().setAttribs(Defaults.DEFAULT_MARKER_SIZE, width, color);
        Pane p = new Pane();
        p.setPrefWidth(40);
        l.startXProperty().set(0);
        l.endXProperty().bind(p.widthProperty());
        l.startYProperty().bind(p.heightProperty().divide(2));
        l.endYProperty().bind(p.heightProperty().divide(2));
        Group g = m.draw(markerType);
        g.translateXProperty().bind(p.widthProperty().divide(2).subtract(m.getSize()/2));
        g.translateYProperty().bind(p.heightProperty().divide(2).subtract(m.getSize()/2));
        p.getChildren().addAll(l,g);
        inner.addRow(activeRow++,p,new Text(description));
        
    }
    
    /**
     * Adds a marker item to the legend.
     * @param color color of the marker.
     * @param markerType type of the marker.
     * @param description the label that appears nex to the marker.
     */
    public void addMarker(Paint color, Marker.MarkerType markerType, String description) {
        Marker m = new Marker().setAttribs(Defaults.DEFAULT_MARKER_SIZE, 1, color);
        Group g = m.draw(markerType);
        Pane p = new Pane();
        g.translateXProperty().bind(p.widthProperty().divide(2).subtract(m.getSize()/2));
        g.translateYProperty().bind(p.heightProperty().divide(2).subtract(m.getSize()/2));
        p.getChildren().add(g);
        inner.addRow(activeRow++,p,new Text(description));
    }
    
    /**
     * Adds a colored rectangle item to the legend representing a bar.
     * @param color color of the rectangle.
     * @param description the label that appears nex to the rectangle.
     */
    public void addBar(Paint color, String description) {
        Rectangle rect = new Rectangle();
        rect.widthProperty().set(40);
        rect.heightProperty().set(15);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        inner.addRow(activeRow++,rect,new Text(description));
    }
    
    /**
     * Sets a black border around the legend.
     */
    public void outline() {
        node.setStyle("-fx-border-color: black;-fx-background-color: white;");
        node.applyCss();
    }
    
    // Component interface
    /**
     * Component interface implementation.
     * @param translateX x coordinate of the component with a property binding.
     * @param translateY y coordinate of the component with a property binding.
     */
    @Override
    public void position(DoubleBinding translateX, DoubleBinding translateY) {
        node.translateXProperty().bind(translateX.add(hOffsetProperty));
        node.translateYProperty().bind(translateY.add(vOffsetProperty));
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
                vOffsetProperty.bind(node.heightProperty().divide(-2));
            }; break;
            case TOP: {
                vOffsetProperty.unbind();
                vOffsetProperty.set(0);
            }; break;
            case BOTTOM: {
                vOffsetProperty.unbind();
                vOffsetProperty.bind(node.heightProperty().multiply(-1));
            }; break;
        }
        switch (alignX) {
            case CENTER: {
                hOffsetProperty.unbind();
                hOffsetProperty.bind(node.widthProperty().divide(-2));
            }; break;
            case LEFT: {
                hOffsetProperty.unbind();
                hOffsetProperty.set(0);
            }; break;
            case RIGHT: {
                hOffsetProperty.unbind();
                hOffsetProperty.bind(node.widthProperty().multiply(-1));
            }; break;
        }
    }

    /**
     * Component interface implementation.
     * @return contained Scene Graph node of a component.
     */
    @Override
    public Node getNode() {
        return node;
    }
    
}
