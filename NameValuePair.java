package com.PMSystems.util;

/**
 * NameValuePair.java         1.0  18 October 2006
 *
 * Copyright (c) 2002 Makesbridge Techonolgy.
 * Los Gatos, USA
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of Makesbridge Technology. ("Confidential Information").
 * You shall not disclose such Confidential Information.
 */

import java.io.Serializable;

/**
 * This bean will be used to act as one record of a HashMap. This will help in replacing
 * HashMap with an array or vector of NameValuePair elements to use in web service
 * as Axis doesn't support complex datatypes like HashMap, SortedMap, LinkedMap etc.
 *
 * @version         1.0       18 October 2006
 * @author          Shahbaz Ahmed
 *
 * History
 * 18 October 2006     Shahbaz Ahmed      Development of Interface.
 */


public class NameValuePair implements Serializable {

    private String name;
    private Object value = null;
    public NameValuePair() {
        name="";
        value= new Object();
    }

    public NameValuePair(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

}