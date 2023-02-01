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
package hu.unipannon.virt.plot.control;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.floor;
import static java.lang.Math.abs;
		
/**
 * Provides automatically generated divisor points for the controllers to use.
 * If the end user does not want automatic axis divisors, they can always set 
 * it manually.
 * MATLAB has quite a complex algorithm to generate axis divisor points, that can
 * depend on available space on screen, size of the interval, etc. This class 
 * contains several simpler approaches to the problem.
 * The one used in the final version is based on the amount of divisor points.
 * The algorithm rounds up the interval's high point and down the low point. 
 * Then the algorithm tries to fill in the new interval with predefined values
 * and their 10x multiples. Then the division with the most liked number of divisor
 * points will be selected based on a goal function.
 * @author Tóth Bálint
 */
public class AxisGenerator {

    private static double[] snapPoints = new double[] {0,1,2,4,5,10};

    private static  double[] divUnit(int magnitude) {
	return Arrays.stream(snapPoints)
	    .map(x -> x * pow(10,magnitude))
	    .toArray();
    }

    private static double flog(double d) {
	if (d ==0)
	    return 0;
	else
	    return floor(log10(Math.abs(d)));
    }

    private static int difindex(double min, double max) {
	if (min == max || min > max)
	    return -69;
	double dif = 0;
	if (min >= 0 && max >= 0 || min <= 0 && max <= 0)
	    dif = abs(flog(max)) - abs(flog(min));
	else
	    dif = abs(flog(min)) - abs(flog(max));

	if (dif != 0)
	    return (int)dif;

	int j = (int)flog(max) - (int)dif;
	min /= pow(10,j);
	max /= pow(10,j);
	double minCmp = Math.floor(min);
	double maxCmp = Math.floor(max);
	while (minCmp % 10.0 == maxCmp % 10.0) {
	    min *= 10;
	    max *= 10;
	    minCmp = Math.floor(min);
	    maxCmp = Math.floor(max);
	    j--;
	}
	return j;
    }

    /**
     * This function can be used to request an array of precalculated divisor 
     * points for an interval. End points included: [min;max].
     * @param min lowest value of the interval.
     * @param max highest value of the interval.
     * @return array of divisor points on the interval.
     */
    public static double[] lpoints(double min, double max) {
	if (min >= max) {
	    System.out.println("Error");
	    return null;
	}

	// step 1.
	// get the original difference index
	int didx = difindex(min,max);

	// step 2.
	// round up and down to the original index
	final double minRes = roundDownToIdx(didx,min);
	final double maxRes = roundUpToIdx(didx,max);

	// step 3.
	// correction based on the divisor interval

	// 3.1 find a suitable dipoint list with the original divs
	// needs a fittness function

	final double span = maxRes-minRes;

	double optimalDivUnit = 1;
	double optimalGoal = 0.0;

	for (var x : divUnit(didx)) {
	    if (x != 0 && (span / x) % 1 == 0) {
		double goal = heuristicFunction1(span/x);
		if (goal > optimalGoal) {
		    optimalGoal = goal;
		    optimalDivUnit = x;
		}
	    }
	}

	for (var x : divUnit(didx-1)) {
	    if (x != 0 && (span / x) % 1 == 0) {
		double goal = heuristicFunction1(span/x);
		if (goal > optimalGoal) {
		    optimalGoal = goal;
		    optimalDivUnit = x;
		}
	    }
	}
	// System.out.println(" Optimal div unit: " + optimalDivUnit);
	// System.out.print("[" + minRes);
	// double acc = minRes;
	// acc += optimalDivUnit;
	// while (acc <= maxRes) {
	//     System.out.print(", " + acc);
	//     acc += optimalDivUnit;
	// }
	// System.out.print("]");

	List<Double> divPoints = new LinkedList<>();
	double acc = minRes;
	divPoints.add(acc);
	acc += optimalDivUnit;
	while (acc <= maxRes) {
	    divPoints.add(acc);
	    acc += optimalDivUnit;
	}
	int arrSize = divPoints.size();
	var result = new double[arrSize];
	for (int i=0;i<divPoints.size();i++) {
	    result[i] = (double)Math.round(divPoints.get(i) * 100) / 100.0;
	}
	return result;

    }

    private static double roundDownToIdx2(int didx, double min) {
	var minDigits = min % pow(10,didx+1);
	var minRem = min - minDigits;

	double minRes = 0.0;

	int j = 10;
	if (min >= 0) {
	    j = 10;
	    while (minDigits < j*pow(10,didx) && j >= 0) {
		j--;
	    }
	    minRes = minRem + j*pow(10,didx);
	} else {
	    j = 0;
	    while (-minDigits > j*pow(10,didx) && j <= 10) {
		j++;
	    }
	    minRes = minRem - j*pow(10,didx);
	}
	return minRes;
    }

    private static double roundDownToIdx(int didx, double min) {
	var minDigits = min % pow(10,didx+1);
	var minRem = min - minDigits;

	double minRes = 0.0;
	var roundingPoints = new double[] {
	    0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,6,7,8,9,10
	};

	int len = roundingPoints.length;

	var shiftedRoundingPoints = Arrays.stream(roundingPoints)
	    .map(x -> x * pow(10,didx))
	    .toArray();

	int j = len - 1;
	if (min >= 0) {
	    j = len - 1;
	    while (minDigits < shiftedRoundingPoints[j] && j >= 0) {
		j--;
	    }
	    minRes = minRem + shiftedRoundingPoints[j];
	} else {
	    j = 0;
	    while (-minDigits > shiftedRoundingPoints[j] && j < len) {
		j++;
	    }
	    minRes = minRem - shiftedRoundingPoints[j];
	}
	return minRes;
    }

    private static  double roundUpToIdx2(int didx, double max) {
	var maxDigits = max % pow(10,didx+1);
	var maxRem = max - maxDigits;

	double maxRes = 0.0;

	int i = 0;
	if (max >= 0) {
	    i = 0;
	    while (maxDigits > i*pow(10,didx) && i <= 10) {
		i++;
	    }
	    maxRes = maxRem + i*pow(10,didx);
	} else {
	    i = 10;
	    while (-maxDigits < i*pow(10,didx) && i >= 0) {
		i--;
	    }
	    maxRes = maxRem - i*pow(10,didx);
	}

	return maxRes;
    }

    private static double roundUpToIdx(int didx, double max) {
	var maxDigits = max % pow(10,didx+1);
	var maxRem = max - maxDigits;

	var roundingPoints = new double[] {
	    0,0.5,
	    1,1.5,
	    2,2.5,
	    3,3.5,
	    4,4.5,
	    5,
	    6,
	    7,
	    8,
	    9,
	    10
	};

	var shiftedRoundingPoints = Arrays.stream(roundingPoints)
	    .map(x -> x * pow(10,didx))
	    .toArray();
	int len = shiftedRoundingPoints.length;


	double maxRes = 0.0;

	int i = 0;
	if (max >= 0) {
	    i = 0;
	    while (maxDigits > shiftedRoundingPoints[i] && i < len) {
		i++;
	    }
	    maxRes = maxRem + shiftedRoundingPoints[i];
	} else {
	    i = len - 1;
	    while (-maxDigits < shiftedRoundingPoints[i] && i >= 0) {
		i--;
	    }
	    maxRes = maxRem - shiftedRoundingPoints[i];
	}

	return maxRes;
    }

    
    private static double heuristicFunction2(double x) {
	return 4*(Math.exp((-0.1) * x) - Math.exp((-0.2) * x));
    }

    private static double heuristicFunction3(double x) {
	return Math.exp(-(pow((x-6),2) / 5));
    }

    private static double heuristicFunction1(double x) {
	return 4*(Math.exp((-0.14) * x) - Math.exp((-0.28) * x));
    }
}
