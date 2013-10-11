/**
 * ErrorMessage.java         1.0  20 Aug 2002
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
 * This class is used to make an error message.
 *
 * @version         1.0       20 Aug 2002
 * @author          Zeeshan Chughtai
 *
 * History
 * 20 Aug 2002     Zeeshan Chughtai        Initial Development of Class.
 */
public class ErrorMessage {

    StringBuffer errors = null;
    String errorHead = null;
    boolean anyError;

    public ErrorMessage() {
        errors = new StringBuffer();
        anyError = false;
    }

    public void addError(String error) {
        errors.append("<TR><TD CLASS=error>&nbsp;&nbsp;-&nbsp;").
               append(error).
               append("</TD></TR>\n");
        anyError = true;
    }

    public void setErrorHead(String errorHead) {
        this.errorHead = errorHead;
    }

    public boolean getAnyError() {
        return anyError;
    }

    public void reset() {
        errors = new StringBuffer();
        this.errorHead = null;
    }

    public String toString() {
        StringBuffer message = new StringBuffer();
        message.append("<BR>\n<TABLE BRODER=\"0\">\n").
                append("<TR><TD CLASS=errorHead>").
                append(errorHead!=null?errorHead:
                       "Please correct the following errors").
                append(":</TD></TR>\n").
                append(errors.toString()).
                append("</TABLE>\n<BR>\n");
        return message.toString();
    }
}
