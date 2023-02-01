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
package hu.unipannon.virt.plot.util;

import java.util.function.Function;

/**
 * The FunctionGenerator class generates an array of x and y coordinates 
 * that can make up a function. The domain of the function and the 
 * mapping rule of the function can be suppplied via a fluent interface.
 * Then the x and y arrays can be queried with the <code>getXs()</code> and 
 * <code>getYs()</code> function to be used in a lineplot.<br>
 * By default the generator uses 100 sampling points, equivalent to MATLAB's 
 * linspace function. Example:<br>
 * <pre>{@code
 *   var fun = FunctionGenerator.function()
 *             .startValue(0.0)
 *             .endValue(2.0 * Math.PI)
 *             .mapping(x -> Math.sin(x))
 *             .makeFunction();
 * }</pre>
 * 
 * @author Tóth Bálint
 */
public class FunctionGenerator {
   
   
    private double startValue;            // start of domain
    private double endValue;              // end of domain
    private int frequency;                // number of samples
    private Function<Double,Double> fun;  // function to be evaluated

    private double[] xs;  // the points of the domain
    private double[] ys;  // the points of the image
    
    /**
     * Default constructs an object
     */
    public FunctionGenerator() {
        // the self is completely optional, so we set it to null
        initComponents();
    }

    /**
     * initialization with default values.
     */
    private void initComponents() {
        // the identic function on the [-1,1] interval, can be customized
        startValue = -1.f;
        endValue = 1.f;
        frequency = 64;
        fun = (x) -> x;
    }
    
    /**
     * Static factory method to hide the new keyword.
     * Creates a default FunctionGenerator instance.
     * @return FunctionGenerator instance.
     */
    public static FunctionGenerator function() {
        return new FunctionGenerator();
    }

    // setter functions for the fluent API
    
    /**
     * Setter for the starter value of the interval.
     * Given value must be less then the end value.
     * @param startValue start point (included) of the domain.
     * @return itself.
     */
    public FunctionGenerator startValue(double startValue) {
        this.startValue = startValue;
        return this;
    }

    /**
     * Setter for the end value of the interval.
     * Given value must be greater then the start value.
     * @param endValue end point (included) of the domain.
     * @return itself.
     */
    public FunctionGenerator endValue(double endValue) {
        this.endValue = endValue;
        return this;
    }

    /**
     * Setter for the mapping as a double to double lambda function.
     * @param fun lambda implementing the mathematical function to be displayed.
     * @return itself.
     */
    public FunctionGenerator mapping(Function<Double,Double> fun) {
        this.fun = fun;
        return this;
    }
    
    /**
     * Setter for the number of sampling points. Given value must be at least 2.
     * @param frequency number of sampling points.
     * @return itself.
     */
    public FunctionGenerator frequency(int frequency) {
        this.frequency = Math.max(frequency, 2);   
        return this;
    }


    // production function
    /**
     * Builder function, computes the mapping.
     * @return itself.
     */
    public FunctionGenerator makeFunction() {
        // for a function generator to work, the start should be lower than the end value
        // and a valid function should exist
        if (this.endValue <= this.startValue)
            return null;
        if (this.fun == null)
            return null;
        
        // allocate the arrays
        this.xs = new double[frequency];
        this.ys = new double[frequency];

        // evaluate the function:
        // sampling by the start + (i*span) / (freq-1) formula
        // required parameters
        double span = this.endValue - this.startValue; 
        double actual = 0.d;
        // eval for every point
        for (int i=0;i<this.frequency;i++) {
            actual = this.startValue + (i*span) / (this.frequency-1);
            this.xs[i] = actual;
            this.ys[i] = fun.apply(actual);
        }
        return this;
    }


    // getters
    /**
     * Query for the produced X values.
     * @return array of X values.
     */
    public double[] getXs() {
        return this.xs;
    }

    /**
     * Query for the produced Y values.
     * @return array of Y values.
     */
    public double[] getYs() {
        return this.ys;
    }
    
    /**
     * Query for the distance between the X values.
     * @return distance between the X values.
     */
    public double getDx() {
        return (endValue - startValue) / (frequency - 1);
    }
    
}
