/**
 * MathSupport.java       1.0  Sep 20, 2002
 *
 * Copyright (c) 2000 Fourth House Security Inc.
 * 16467 Turnbury Oak Dr, Odessa FL 33556, USA
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of Fourth House Security Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information.
 */

package com.PMSystems.util;

/**
 * This class gives the support for setting up precision for different floating
 * point and double values. It has static functions for getting the values
 *
 * @author     Asim Saghir
 * @date       April 21, 2001
 *
 * April 21, 2001     Asim Saghir          Initial class development.
 * April 21, 2001     Naaman Musawwir      Revised from standardization.
 * May 28, 2001       Naaman Musawwir      Changes to remove Exponentially
 *                                         displayed values
 * Sep 20, 2002       Naaman Musawwir      Copied to this package.
 */

import java.text.*;
import java.util.Locale;

public class MathSupport {

    private static NumberFormat numberFormatter = NumberFormat.
                                                  getNumberInstance(Locale.US);

    private static NumberFormat numberSimplifier = new DecimalFormat("0.00");

    /*
     *  Default constructor
     */
    public MathSupport() {
    }  //end constructor

    /**
     *  getFormattedValue(int scale, double number) handles double values
     *
     *  @param      scale as integer represents how many digits after decimal user wants
     *  @param      number as double that needs precision
     *  @return     a String as formatted value of passed number
     */
    public static String getFormattedValue(int scale, double number) {
        numberFormatter.setMaximumFractionDigits(scale);
        numberFormatter.setMinimumFractionDigits(scale);
        return Double.isNaN(number) ? "Not Available" :
               Double.isInfinite(number) ? "Not Available" :
               numberFormatter.format(number);
    } //end getFormattedValue(int scale, double number)

    /**
     *  getFormattedValue(int scale, float number) handles float values
     *
     *  @param      scale as integer represents how many digits after decimal user wants
     *  @param      number as float that needs precision
     *  @return     a String as formatted value of passed number
     */
    public static String getFormattedValue(int scale, float number) {
        numberFormatter.setMaximumFractionDigits(scale);
        numberFormatter.setMinimumFractionDigits(scale);
        return Float.isNaN(number) ? "Not Available" :
               Float.isInfinite(number) ? "Not Available" :
               numberFormatter.format(number);
    } //end getFormattedValue(int scale, float number)

    /**
     *  getSimpleValue(double number) handles double values
     *
     *  @param      number as double that needs precision
     *  @return     a String as simple value of passed number
     */
    public static String getSimpleValue(double number) {
        return Double.isNaN(number) ? "0.00" :
               Double.isInfinite(number) ? "0.00" :
               numberSimplifier.format(number);
    } //end getSimpleValue(double number)

    /**
     *  getSimpleValue(float number) handles double values
     *
     *  @param      number as float that needs precision
     *  @return     a String as simple value of passed number
     */
    public static String getSimpleValue(float number) {
        return Float.isNaN(number) ? "0.00" :
               Float.isInfinite(number) ? "0.00" :
               numberSimplifier.format((double)number);
    } //end getSimpleValue(float number)

    public static void main(String[] args) {
        System.out.println(MathSupport.getFormattedValue(2, 555555555.005));
        System.out.println(MathSupport.getSimpleValue(555555555555555.005));

        java.math.BigDecimal bd = new java.math.BigDecimal("55.005");
        bd = bd.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
        //bd  = bd.set
        System.out.println(bd.toString());

    }
} //end class
