/**
 *   PMSDecodeException.java         1.0  07 June 2002
 *
 *   Copyright (c) 2000 Fourth House Security Inc.
 *   16467 Turnbury Oak Dr, Odessa FL 33556, USA
 *   All Rights Reserved.
 *
 *   This software is the confidential and proprietary information
 *   of Fourth House Security Inc. ("Confidential Information").
 *   You shall not disclose such Confidential Information.
 */

package com.PMSystems.util;

import com.PMSystems.PMSException;

/**
 *   Base64DecodeException is thrown when Base64Operations class fails to decode
 *   the passed array of bytes. Indicates that the passed data is not Base64
 *   encoded data and cannot be decoded using this encoding scheme.
 *
 *   @version        1.0       07 June 2002
 *   @author         Naaman Musawwir
 *
 *   History
 *   07 June 2002    Naaman Musawwir        Initial Development.
 *   10 June 2002    Naaman Musawwir        Changes to accomodate new standards.
 */
public class PMSDecodeException extends PMSException {

    private static String CLASS_NAME = "PMSDecodeException";
    /**
     * Constructor
     *
     * @param root as Exception
     * @param userMessage as String
     * @param className as String
     * @param methodName as String
     */
    public PMSDecodeException(int errorCode, String className, String methodName, String message, Exception source) {
        super(errorCode, className, methodName, message, source);
    }

    /**
     * returns an error code assigned to this type of exception
     *
     * @return error code in form of int
     */
    public static int getErrorCode() {
        return 1002;
    }
}