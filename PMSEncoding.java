/**
 * PMSEncoding.java         1.0  26 Sep 2002
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

import com.PMSystems.PMSException;
import java.io.*;
import com.PMSystems.logger.*;



/**
 * This class provides encoding and decoding of only Integer numbered data.
 *
 * @version   1.0     26 Sept 2002
 * @author    Muhammad Ramzan
 *
 * History
 * 26 Sep 2002       Muhammad Ramzan     Initial Development of Class.
 * 02 Oct 2002       Naaman Musawwir     Changed encoding algorithm.
 */
public class PMSEncoding {

    private static final String CLASS_NAME = "PMSEncoding";
    private static final char encbase[] = {'j','q', 'x', 'k', 'z', 'B'};
    private static final int diff[] = {2, 3, 4, 5, 6, 7};
    private static final String encValues[][] = {{"a", "b", "c", "d", "e", "f"},
                                      {"Ty", "Qw", "cW", "Nm", "aS", "DF"},
                                      {"qwE", "Rty", "asd", "hDf", "nHt", "nGy"},
                                      {"sdrt", "vgGu", "bhui", "vfre", "zaqw", "JUoi"},
                                      {"zAqws", "cdZrt", "vfrtg", "bgFyh", "gytRf", "dTyio"},
                                      {"zAEqws", "cdDZrt", "vfrFtg", "bgsFyh", "gyStRf", "dTMyio"}};
    private static int randomizer = 0;
    private static int encsalt1[] = {17, 20, 21, 30, 33, 26};
    private static int encsalt2[] = {65, 50, 59, 49, 55, 58};

    /**
     * Returns the encoded data as a string.
     *
     * @param data as String
     * @return a String containing encoded data
     */
    public static synchronized String encode(String data) {
        String encoded = "";
        setRandom();
        int r1 = randomizer;
        setRandom();
        int r2 = randomizer;
        encoded += encbase[r1]+encValues[r1][r2];
        for (int i = 0; i < data.length(); i++) {
            setRandom();
            int r = randomizer;
            char one = data.charAt(i);
            encoded += (char)((int)one+encsalt1[r])+""+(char)((int)one+encsalt2[r1])+encsalt1[r];
        }
        setRandom();
        r2 = randomizer;
        encoded += encbase[r1]+encValues[r1][r2];
        return encoded;
    }//end encode()

    /**
     * Returns the decoded string as a String.
     * An exception is thrown if the decoding fails.
     *
     * @param data  The data to be decode.
     * @return a String containing decoded data
     * @throws PMSDecodeException
     */
    public static String decode(String data) throws PMSDecodeException {
        String origValue = data;
        char decbase = origValue.charAt(0);
        for (int i = 0; i < encbase.length; i++) {
            if(decbase==encbase[i]) {
                origValue = origValue.substring(diff[i], origValue.length()-diff[i]);
                break;
            }
        }

        String decoded = "";
        try {
            for (int i = 0; i < origValue.length(); i+=4) {
                String diff = origValue.substring(i+2, i+4);
                int decint = (int)origValue.charAt(i) - Integer.parseInt(diff);
                decoded += (char)decint;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PMSDecodeException(PMSException.getErrorCode(), CLASS_NAME,
                    "decode", "Cannot decode; wrong encoded value", null);
        }
        return decoded;
    }//end decode()

    /**
     * Sets random value
     */
    private static void setRandom() {
        randomizer++;
        if (randomizer > encbase.length-1) randomizer = 0;
    }

    /**
     * Tests the whole funcionality of class.
     * Reads a binary file, encodes it, again decodes it
     * and writes the file back. Then reads encoded file,
     * decodes it and compares with the original file.
     *
     * @throws PMSDecodeException
     * @throws IOException
     */
    public static void test() throws PMSDecodeException, IOException {
        // read file
        String abc = "test";

        // encode
        System.out.println("Encoding ....."+abc);
        String encoded = PMSEncoding.encode(abc);
        System.out.println("Encoded Value ......"+encoded);

        // decode
        String decoded = PMSEncoding.decode(encoded);
        System.out.println("Decoded Value ......"+decoded);
    }//end test()

    public static long decodeToLong(String data){
        if(data == null)
            return -1;
        try{
            return Long.parseLong(decode(data));
        }catch(Exception ex){
            //WebServerLogger.getLogger().log(ex);
            ex.printStackTrace();
        }
        return -1;
    }
    public static String decodeToString(String data){
    try{
        if (data ==null || data.equals(""))
            return "";
        return decode(data);
    }catch(Exception ex){
        WebServerLogger.getLogger().log(ex);
        ex.printStackTrace();
    }
    return "-1";
}

    /**
     * Calls the test method of class and
     * tests performance of IAIK and Apache libraries
     *
     * @param args as String[]
     */
    public static void main(String[] args) {
        try {
            //sub=xhDfUo33Rs26Dn17El, cmp=kzaqwQh26Aa17kvgGu
            //System.out.println("Decoded: "+PMSEncoding.decode("xhDfUo33Rs26Dn17El"));
            System.out.println("xhDfUo33Rs26Dn17El".length());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }//end main()
} // end class