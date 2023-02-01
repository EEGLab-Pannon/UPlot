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
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import hu.unipannon.virt.plot.frame.horizontal.HorizontalAxis;
import hu.unipannon.virt.plot.frame.vertical.VerticalAxis;

/**
 * Highest level Container class of the graphical system. The Frame class contains all
 * components of the graphical Plot.
 * Components can be turned on with the show... and off with the hide... methods.
 * 
 * @see PlotSpace
 * @see HorizontalAxis
 * @see VerticalAxis
 * @see Container
 * @author Tóth Bálint
 */
public class Frame extends Container {
    
    // components
    private Label title;
    private PlotSpace ps;
    private Rectangle psbg; // the plot space background
    private HorizontalAxis ha;
    private VerticalAxis va;
    
    // for the inset
    private DoubleProperty titleOffsetProperty;
    
    // debug
    private final boolean DEBUG_BODRDER = false;
    
    // defeault values
    private final double plotSpaceBottomPropertyDefault = 0.11;
    private final double plotSpaceLeftPropertyDefault = 0.13;
    private final double plotSpaceWidthPropertyDefault = 0.775;
    private final double plotSpaceHeightPropertyDefault = 0.815;
    
    // resize trackers
    boolean leftSnap = false;
    boolean rightSnap = false;
    boolean bottomSnap = false;
    boolean topSnap = false;

    
    /**
     * Default constructs a frame with all the properties and their default values.
     * The contained objects bind to these properties.
     */
    public Frame() {
        // setting default variables
        plotSpaceBottomPropertyR.set(plotSpaceBottomPropertyDefault);
        plotSpaceLeftPropertyR.set(plotSpaceLeftPropertyDefault);
        plotSpaceWidthPropertyR.set(plotSpaceWidthPropertyDefault);
        plotSpaceHeightPropertyR.set(plotSpaceHeightPropertyDefault);
 
        gapOffsetPropertyR.set(0.01);
        innerTickLengthPropertyR.set(0.01);
        outerTickLengthPropertyR.set(0.0);
        
        originHorizontalPropertyR.set(0.33);
        originVerticalPropertyR.set(0.5);

        ha = new HorizontalAxis();
        ha.connectTo(this); 
        va = new VerticalAxis();
        va.connectTo(this);
        
        ps = new PlotSpace(this);

        titleOffsetProperty = new SimpleDoubleProperty(0.0);
        title = new Label("Test title");
        makeTitle();      
        
        makePlotSpace();
    }
    
    private void makePlotSpace() {
        ps.position(
                frameWidthPropertyA
                    .multiply(plotSpaceLeftPropertyR)
                ,
                frameHeightPropertyA
                .subtract(frameHeightPropertyA
                        .multiply(plotSpaceBottomPropertyR))
        );
        ps.align(HPos.LEFT, VPos.BOTTOM); 

	// create the plot space background
        psbg = new Rectangle();
        psbg.widthProperty().bind(frameWidthPropertyA.multiply(plotSpaceWidthPropertyR));
        psbg.heightProperty().bind(frameHeightPropertyA.multiply(plotSpaceHeightPropertyR));
	psbg.translateXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR));
	psbg.translateYProperty().bind(frameHeightPropertyA
				       .subtract(frameHeightPropertyA
						 .multiply(plotSpaceBottomPropertyR
							   .add(plotSpaceHeightPropertyR))));
	
				       
        psbg.setFill(Color.web("#ffffff"));
        
        
        // This needs to be better ---------------------------------------------
        ((PlotSpace)ps).widthProperty().bind(
                frameWidthPropertyA
                    .multiply(plotSpaceWidthPropertyR)
        );
        ((PlotSpace)ps).heightProperty().bind(
                frameHeightPropertyA
                    .multiply(plotSpaceHeightPropertyR)
        );
        // ---------------------------------------------------------------------
        
        // set the tight insets
        this.tightInsetBottomProperty.bind(ha.tightInsetBottomProperty);
        
        this.tightInsetTopProperty.bind(ha.tightInsetTopProperty.add(titleOffsetProperty));

        this.tightInsetLeftProperty.bind(va.tightInsetLeftProperty);
        this.tightInsetRightProperty.bind(va.tightInsetRightProperty);
        
        if (DEBUG_BODRDER) {  // no hard feelings pls
            Line b = new Line();
            b.startXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.subtract(tightInsetLeftProperty)));
            b.endXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.add(plotSpaceWidthPropertyR).add(tightInsetRightProperty)));
            b.startYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.subtract(tightInsetBottomProperty))));
            b.endYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.subtract(tightInsetBottomProperty)))); 
            b.setStroke(Color.BLUE);
            getChildren().add(b);
            
            Line t = new Line();
            t.startXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.subtract(tightInsetLeftProperty)));
            t.endXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.add(plotSpaceWidthPropertyR).add(tightInsetRightProperty)));
            t.startYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.add(plotSpaceHeightPropertyR).add(tightInsetTopProperty))));
            t.endYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.add(plotSpaceHeightPropertyR).add(tightInsetTopProperty)))); 
            t.setStroke(Color.GREEN);
            getChildren().add(t);
            
            Line l = new Line();
            l.startXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.subtract(tightInsetLeftProperty)));
            l.endXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.subtract(tightInsetLeftProperty)));
            l.startYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.subtract(tightInsetBottomProperty))));
            l.endYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.add(plotSpaceHeightPropertyR).add(tightInsetTopProperty))));
            l.setStroke(Color.ORANGE);
            getChildren().add(l);
            
            Line r = new Line();
            r.startXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.add(plotSpaceWidthPropertyR).add(tightInsetRightProperty)));
            r.endXProperty().bind(frameWidthPropertyA.multiply(plotSpaceLeftPropertyR.add(plotSpaceWidthPropertyR).add(tightInsetRightProperty)));
            r.startYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.subtract(tightInsetBottomProperty))));
            r.endYProperty().bind(frameHeightPropertyA.subtract(frameHeightPropertyA.multiply(plotSpaceBottomPropertyR.add(plotSpaceHeightPropertyR).add(tightInsetTopProperty))));
            r.setStroke(Color.MAGENTA);
            getChildren().add(r);
        }

        
        
        // vertical resize detecting
        this.frameWidthPropertyA.addListener((observable, oldValue, newValue) -> {
            // left
            if (plotSpaceLeftPropertyR.get() < tightInsetLeftProperty.get() && !leftSnap) {
                plotSpaceLeftPropertyR.unbind();
                plotSpaceLeftPropertyR.bind(tightInsetLeftProperty);
                leftSnap = true;
            }
            
           if (plotSpaceLeftPropertyR.get() < plotSpaceLeftPropertyDefault && leftSnap) {
                plotSpaceLeftPropertyR.unbind();
                plotSpaceLeftPropertyR.set(plotSpaceLeftPropertyDefault);
                leftSnap = false;
            }
            
           // right
           if (1 - (plotSpaceLeftPropertyR.get() + plotSpaceWidthPropertyR.get()) < tightInsetRightProperty.get() && !rightSnap) {
               plotSpaceWidthPropertyR.unbind();
               plotSpaceWidthPropertyR.bind(tightInsetRightProperty.multiply(-1).add(1).subtract(plotSpaceLeftPropertyR));
               rightSnap = true;
           }
           
           if (plotSpaceWidthPropertyR.get() >= plotSpaceWidthPropertyDefault && rightSnap) {
               plotSpaceWidthPropertyR.unbind();
               plotSpaceWidthPropertyR.set(plotSpaceWidthPropertyDefault);
               rightSnap = false;
           }
            
            
        });
        
        this.frameHeightPropertyA.addListener((observable, oldValue, newValue) -> {
            // bottom
            if (plotSpaceBottomPropertyR.get() < tightInsetBottomProperty.get() && !bottomSnap) {
                plotSpaceBottomPropertyR.unbind();
                plotSpaceBottomPropertyR.bind(tightInsetBottomProperty);
                bottomSnap = true;
            }
            
            if (plotSpaceBottomPropertyR.get() < plotSpaceBottomPropertyDefault && bottomSnap) {
                plotSpaceBottomPropertyR.unbind();
                plotSpaceBottomPropertyR.set(plotSpaceBottomPropertyDefault);
                bottomSnap = false;
            }
            
            // top
            if (1 - (plotSpaceBottomPropertyR.get() + plotSpaceHeightPropertyR.get()) < tightInsetTopProperty.get() && !topSnap) {
                plotSpaceHeightPropertyR.unbind();
                plotSpaceHeightPropertyR.bind(tightInsetTopProperty.multiply(-1).add(1).subtract(plotSpaceBottomPropertyR));
                topSnap = true;
            }
            
            if (plotSpaceHeightPropertyR.get() >= plotSpaceHeightPropertyDefault && topSnap) {
                plotSpaceHeightPropertyR.unbind();
                plotSpaceHeightPropertyR.set(plotSpaceHeightPropertyDefault);
                topSnap = false;
            }
        });
    }
    
    // just a hack, used for manual size correcting without resize event
    public void reconstraint() {
    // left
        if (plotSpaceLeftPropertyR.get() < tightInsetLeftProperty.get() && !leftSnap) {
            plotSpaceLeftPropertyR.unbind();
            plotSpaceLeftPropertyR.bind(tightInsetLeftProperty);
            leftSnap = true;
        }

       if (plotSpaceLeftPropertyR.get() < plotSpaceLeftPropertyDefault && leftSnap) {
            plotSpaceLeftPropertyR.unbind();
            plotSpaceLeftPropertyR.set(plotSpaceLeftPropertyDefault);
            leftSnap = false;
        }

       // right
       if (1 - (plotSpaceLeftPropertyR.get() + plotSpaceWidthPropertyR.get()) < tightInsetRightProperty.get() && !rightSnap) {
           plotSpaceWidthPropertyR.unbind();
           plotSpaceWidthPropertyR.bind(tightInsetRightProperty.multiply(-1).add(1).subtract(plotSpaceLeftPropertyR));
           rightSnap = true;
       }

       if (plotSpaceWidthPropertyR.get() >= plotSpaceWidthPropertyDefault && rightSnap) {
           plotSpaceWidthPropertyR.unbind();
           plotSpaceWidthPropertyR.set(plotSpaceWidthPropertyDefault);
           rightSnap = false;
       }
        // bottom
        if (plotSpaceBottomPropertyR.get() < tightInsetBottomProperty.get() && !bottomSnap) {
            plotSpaceBottomPropertyR.unbind();
            plotSpaceBottomPropertyR.bind(tightInsetBottomProperty);
            bottomSnap = true;
        }

        if (plotSpaceBottomPropertyR.get() < plotSpaceBottomPropertyDefault && bottomSnap) {
            plotSpaceBottomPropertyR.unbind();
            plotSpaceBottomPropertyR.set(plotSpaceBottomPropertyDefault);
            bottomSnap = false;
        }

        // top
        if (1 - (plotSpaceBottomPropertyR.get() + plotSpaceHeightPropertyR.get()) < tightInsetTopProperty.get() && !topSnap) {
            plotSpaceHeightPropertyR.unbind();
            plotSpaceHeightPropertyR.bind(tightInsetTopProperty.multiply(-1).add(1).subtract(plotSpaceBottomPropertyR));
            topSnap = true;
        }

        if (plotSpaceHeightPropertyR.get() >= plotSpaceHeightPropertyDefault && topSnap) {
            plotSpaceHeightPropertyR.unbind();
            plotSpaceHeightPropertyR.set(plotSpaceHeightPropertyDefault);
            topSnap = false;
        }
    }
    
    // PLOT SPACE SECTION ======================================================
    
    /**
     * Turn on the plot space.
     */
    public void showPlotSpace() {      
        add(ps.getNode());
    }
    
    /**
     * Turn off the plot space.
     */
    public void hidePlotSpace() {
        remove(ps.getNode());
    }
    
    /**
     * Query for the plot space.
     * @return the stored PlotSpace object.
     */
    public PlotSpace getPlotSpace() {
        return ps;
    }

    /**
     * Turn on set background for plot space.
     * If not invoked, the plot space's background will be transparent.
     */
    public void showPlotSpaceBackground() {
	add(psbg);
    }

    /**
     * Turn off set background for plot space.
     */
    public void hidePlotSpaceBackground() {
	remove(psbg);
    }

    /**
     * Query for the plot space background.
     * Can be used for settings on its rectangle object.
     * @return plot space's background as JavaFX Rectangle.
     */
    public Rectangle getPlotSpaceBackground() {
	return psbg;
    }
    
    // TITLE SECTION ===========================================================
    
    private void makeTitle() {
        title.position(
            frameWidthPropertyA
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
                .subtract(ha.upperAxisLabOffset())
                .subtract(ha.upperTickLabOffset())
        );
        title.setFont("Arial",FontWeight.BOLD,16);
        title.align(HPos.CENTER, VPos.BOTTOM); 
    }
    
    /**
     * Turn on the title.
     */
    public void showTitle() {         
        add(title.getNode());  
        titleOffsetProperty.unbind();
        titleOffsetProperty.bind(title.heightProperty()
                .divide(frameHeightPropertyA).add(gapOffsetPropertyR));
    }
    
    /**
     * Turn off the title.
     */
    public void hideTitle() {
        remove(title.getNode());
        titleOffsetProperty.unbind();
        titleOffsetProperty.set(0.0);
    }
    
    /**
     * Query for the title for further settings.
     * @return title as a Label object.
     */
    public Label getTitle() {
        return title;
    }
    
    // AXIS SECTION ============================================================
    
    /**
     * Turn on horizontal axis.
     * After this the axis' specific components have to turned on individually.
     */
    public void showHorizontalAxis() {                      
        add(ha);
    }
    
    /**
     * Turn off horizontal axis.
     * Will remove any axis elements turned on before.
     */
    public void hideHorizontalAxis() {
        remove(ha);
    }
    
    /**
     * Query for the horizontal axis.
     * @return horizontal axis object.
     */
    public HorizontalAxis getHorizontalAxis() {
        return ha;
    }
    
    /**
     * Turn on vertical axis.
     * After this the axis' specific components have to turned on individually.
     */
    public void showVerticalAxis() {       
        add(va);     
    }
    
    /**
     * Turn off vertical axis.
     * Will remove any axis elements turned on before.
     */
    public void hideVerticalAxis() {
        remove(va);
    }
    
    /**
     * Query for the vertical axis.
     * @return vertical axis object.
     */
    public VerticalAxis getVerticalAxis() {
        return va;
    }

}
