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

package hu.unipannon.virt.plot.fluent;

import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Highest level of the fluent interface in standalone mode. The figure has 
 * functions to create and display plots in a window like MATLAB.
 * 
 * @see Plot
 * @see Bar
 * @see Scatter
 * @see Log
 * @see Line
 * @see LineStyle
 * @see ScatterStyle
 * @see Series
 * @see FrameStyle
 * @see Category
 * @author Tóth Bálint
 */
public class Figure {
    // fluent iface mid-types --------------------------------------------------
    
    /**
     * Mid-interface wrapper type for the Fluent interface.
     * It encapsulates a row and a column number.
     */
    public static class Tiles {
	private int rows, cols;

	private Tiles(int rows) {
	    this.rows = rows;
            cols = 1;
	}

        /**
         * Starter function for the class' fluent interface.
         * @param rows Rows value
         * @return itself, to be continued by cols
         */
	public static Tiles rows(int rows) {
	    return new Tiles(rows);
	}

        /**
         * Fluent setter function for columns.
         * @param cols Columns value
         * @return itself, so it can be supplied into Figure
         */
	public Tiles cols(int cols) {
	    this.cols =  cols;
	    return this;
	}
    }

    /**
     * Mid-interface wrapper type for the Fluent interface.
     * It encapsulates an array of numbers, that can be used to position a plot.
     */
    public static class Positions {
	private int[] positions;


	private Positions(int[] positions) {
	    this.positions = positions;
	}

        /**
         * The only static factory method that wraps the array and returns it.
         * @param init 'Keypad number' values of the position
         * @return itself with the array
         */
	public static Positions pos(int... init) {
	    return new Positions(init);
	}
    }

    // settings ----------------------------------------------------------------
    private String title;
    private int xpos;
    private int ypos;


    // swing stuff -------------------------------------------------------------
    private JFXPanel panel = new JFXPanel();
    private GridPane mainPane;


    // subplot system ----------------------------------------------------------
    private int gridWidth, gridHeight;

    // basics
    /**
     * Sets up Figure with factory defaults.
     */
    public Figure() {
	gridWidth = 1;
	gridHeight = 1;
	mainPane = new GridPane();
        title = Defaults.DEFAULT_WINDOW_TITLE;
        xpos = Defaults.DEFAULT_WINDOW_POS_X;
        ypos = Defaults.DEFAULT_WINDOW_POS_Y;

    }

    // figure creation and settings --------------------------------------------
    
    /**
     * Empty starter function for the fluent interface.
     * @return a new instance of Figure
     */
    static public Figure figure() {
        return new Figure();
    }


    /**
     * Fluent setter for the figure title: the title of the window that contains
     * the figure.
     * @param title title value.
     * @return itself.
     */
    public Figure title(String title) {
        this.title = title;
        return this;
    }


    /**
     * Fluent setter for the window position on the screen.
     * @param xpos x coordinate of the window on the sccreen.
     * @param ypos y coordinate of the window on the screen.
     * @return itself.
     */
    public Figure position(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
        return this;
    }

    /**
     * Initiates multiplot mode. With the <code>Tiles</code> class and its 
     * fluent interface, 
     * the number of rows and cloumns of the grid can be set. Those tiles then
     * can hold plots.<br>
     * After this function the plots can only be added with the <code>subplot
     * </code> function.
     * 
     * @param t the tiles wrapper object of a rows and columns value..
     * @return itself.
     */
    public Figure tiles(Tiles t) {
	this.gridHeight = t.cols;
	this.gridWidth = t.rows;
	return this;
    }

    /**
     * Adds a plot object to the multiplot grid. Can only be used after calling 
     * <code>tiles</code>. The position of the used tiles can be added with the 
     * <code>Positions</code> wrapper class.<br> 
     * The positioning starts from the 
     * upper left corner and goes from left to right, up to down. If more than 
     * one position is specified, then the plot will strech all the added tiles.
     * @param pos the positions specified by the Positions wrapper.
     * @param plot the plot to be displayed. Can be any implementation of 
     * Displayable.
     * @return itself.
     */
    public Figure subplot(Positions pos, Displayable plot) {
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE); // I don't like this

	// this is where the magic happens

	// pos = col * rows + row
	// r = P div RS
	// c = P % RS
	int[] pos_normal = Arrays.stream(pos.positions)
	    .map(x -> x-1)
	    .sorted()
	    .toArray();

	// the first item is the row and column index
	int rowIndex = pos_normal[0] / gridHeight;
	int colIndex = pos_normal[0] % gridHeight;

	int rowSpan = 1;
	int colSpan = 1;

	// get row span, col span
	for (var i : pos_normal) {
	    if (pos_normal[0] + (gridWidth * rowSpan) == i)
		rowSpan++;
	    if (pos_normal[0] + colSpan == i)
		colSpan++;
	}
	mainPane.add(p,colIndex,rowIndex,colSpan,rowSpan);
	return this;
    }


    /**
     * The method to show the created figure from code. This can also be used in
     * the JShell command-line environment.
     */
    public void show() {
        // start the frame 
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    System.err.println("UI management error");
	}
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
            }
        });
    }
        

    


    // plotter functions -------------------------------------------------------
    
    /**
     * Fluent interface function to add a lineplot object to the figure.
     * @param style outer styling of the plot.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure plot(FrameStyle style, Line... lines) {
	Plot plot = Plot.plot(style,lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a lineplot object to the figure. 
     * This function will use the default frame styling.
     * @param lines lines created by the Line's fluent interface.
     * @return itself
     */
    public Figure plot(Line... lines) {
	Plot plot = Plot.plot(lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a line plot with both axes logarithm
     * transformed.
     * @param style outer styling of the plot.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure loglog(FrameStyle style, Line... lines) {
	Plot plot = Log.loglog(style,lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a line plot with both axes logarithm
     * transformed.
     * This function will use the default frame styling.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure loglog(Line... lines) {
	Plot plot = Log.loglog(lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a line plot with the horizontal
     * axis logarithm transformed.
     * @param style outer styling of the plot.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure semilogx(FrameStyle style, Line... lines) {
	Plot plot = Log.semilogx(style,lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a line plot with the horizontal
     * axis logarithm transformed.
     * This function will use the default frame styling.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure semilogx(Line... lines) {
	Plot plot = Log.semilogx(lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a line plot with the vertical
     * axis logarithm transformed.
     * @param style outer styling of the plot.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure semilogy(FrameStyle style, Line... lines) {
	Plot plot = Log.semilogy(style,lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a line plot with the vertical
     * axis logarithm transformed.
     * This function will use the default frame styling.
     * @param lines lines created by the Line's fluent interface.
     * @return itself.
     */
    public Figure semilogy(Line... lines) {
	Plot plot = Log.semilogy(lines);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a Scatter plot with data created by
     * the Series' fluent interface.
     * This function will use the default frame styling.
     * @param series data series created by Series' fluent interface.
     * @return itself.
     */
    public Figure scatter(Series... series) {
	Scatter plot = Scatter.scatter(series);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

     /**
     * Fluent interface function to add a Scatter plot with data created by
     * the Series' fluent interface.
     * @param style outer styling of the plot.
     * @param series data series created by Series' fluent interface.
     * @return itself.
     */
    public Figure scatter(FrameStyle style, Series... series) {
	Scatter plot = Scatter.scatter(style,series);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }

    /**
     * Fluent interface function to add a Bar plot with data created by 
     * Category's fluent interface.
     * This function will use the default frame styling.
     * @param category the data for the bar plot.
     * @return itself.
     */
    public Figure bar(Category category) {
	Bar plot = Bar.bar(category);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }	   
	   
    /**
     * Fluent interface function to add a Bar plot with data created by 
     * Category's fluent interface.
     * @param style outer styling of the plot.
     * @param category the data for the bar plot.
     * @return itself.
     */
    public Figure bar(FrameStyle style, Category category) {
	Bar plot = Bar.bar(style,category);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }	  
	  
    /**
     * Fluent interface function to add a horizontal Bar plot with data created 
     * by Category's fluent interface.
     * This function will use the default frame styling.
     * @param category the data for the bar plot.
     * @return itself.
     */
    public Figure hbar(Category category) {
	Bar plot = Bar.hbar(category);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }	 
	
    /**
     * Fluent interface function to add a horizontal Bar plot with data created 
     * by Category's fluent interface.
     * @param style outer styling of the plot.
     * @param category the data for the bar plot.
     * @return itself.
     */
    public Figure hbar(FrameStyle style, Category category) {
	Bar plot = Bar.hbar(style,category);
	Pane p = plot.display();
	p.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);
	mainPane.add(p,0,0,1,1);
	return this;
    }
    
    // Swing integration facilities --------------------------------------------
    
    /*
        Interoperablilty function with the swing panel.
    */
    private void initFrame() {
        // first, a new java swing pane is created with a JFXPanel, which can hold javaFX items in the frame
        JFrame frame = new JFrame(title);
        
        
        // adding the panel to the frame and setting the defaults
        frame.add(panel);
        frame.setVisible(true);
        frame.setLocation(xpos, ypos);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //!!!!!!!!!!!!!!!!!!!!!
        frame.setSize(560,420);
        //!!!!!!!!!!!!!!!!!!!!!

	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("About");
	JMenuItem exportItem = new JMenuItem("Save as PNG");
	JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem infoItem = new JMenuItem("Information");

	menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

	fileMenu.add(exportItem);
	fileMenu.add(exitItem);
        
        aboutMenu.add(infoItem);

	frame.setJMenuBar(menuBar);

	exportItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

		    BufferedImage image = new BufferedImage(panel.getBounds().width,
							    panel.getBounds().height,
							    BufferedImage.TYPE_INT_RGB);
		    panel.paint(image.getGraphics());
		    
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Save");
		    int selection  = fileChooser.showSaveDialog(frame);

		    if (selection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			try {
			    ImageIO.write(image,
					  "png",
					  fileToSave);
			} catch (IOException ex) {
			    JOptionPane.showMessageDialog(frame,"File save failed!",
							  "Image export",
							  JOptionPane.ERROR_MESSAGE);
			}
		    }
		}
	    });
        
        infoItem.addActionListener(new ActionListener() {
            @Override
		public void actionPerformed(ActionEvent e) {
                    // TODO! Edit this before upload
		    JOptionPane.showMessageDialog(frame, "UPlot\nUniversity of Pannonia Plotting Library",
                            "Information",JOptionPane.INFORMATION_MESSAGE);
		}
        });

	exitItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	    });

        
        // the platform creates the scene on a different thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScene(panel);
            }
        });
    }
    
    // returns a javafx scene with this figure
    private void createScene(JFXPanel panel) {
        panel.setScene(new Scene(mainPane));
    }

}
