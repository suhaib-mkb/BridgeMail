/**
 * LinkFinder.java         1.0  05 Dec 2003
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

import java.util.*;
import java.net.*;
import java.io.*;

/**
 *
 * @version         1.0       05 Dec 2003
 * @author          Muhammad Bilal Fazal
 *
 * History
 * 05 Dec 2003     Muhammad Bilal Fazal        Initial Development of Class.
 */
public class LinkFinder {

    private String target;
    private String nextStr;

    private int from;
    private int to;
    private int tokenLength;
    private int length;

    private boolean theEnd=false;
    /**
     *
     * @param target String
     */
    public LinkFinder( String target ) {
        this.target = target;
        length = target.length();
        if(length == 0)
            this.theEnd = true;
        else
            this.theEnd = false;

        tokenLength = 4; //href
        from = 0;
        to = tokenLength-1;
    }//end Costructor

    /**
     *
     * @return String
     */
    public String next(){
        StringTokenizer a;
        if( !theEnd ){
            if(length < tokenLength){
                this.theEnd = true;
                return target;
            }
            if(from < length && to >= length){
                this.theEnd=true;
                nextStr = target.substring(from);
            }else if(from >= length){
                this.theEnd=true;
                return null;
            }
            nextStr = target.substring(from, to);
            from = to++;
            to = to + tokenLength;
            return nextStr;
        }//if not end
        return null;
    }//next()

    /**
     *
     * @return boolean
     */
    public boolean isLinkExpected(){
        return false;
    }//isLinkExpected()

    /**
     *
     * @return String
     */
    public String getLink(){
        return null;
    }//getLink()

    /**
     *
     * @return boolean
     */
    public boolean isEOF(){
        return this.theEnd;
    }//ifEOF()

    public static void main(String stre[]){
        String sample = "hello <a href=\"www.bilal.com\" > bilal </a>";
        sample = "";
        //InetAddress a = InetAddress.getByName("www.yahoo.com");
        try{
            URL u = new URL("http://192.168.10.108:81/pms/parse.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
//            reader.
            String str = reader.readLine();
            for(;str != null;){
                sample = sample + str;
                str = reader.readLine();
            }
        }catch(MalformedURLException male){
            System.out.println("$$ MalformedURLException: "+male);
        }catch(IOException ioe){
            System.out.println("$$ IOException: "+ioe);
        }
        System.out.println(" ||| Sample: "+ sample);

        char h, r, e, f;
        char SPACE=' ';
        char EQUALITY='=';
        char DOUBLE_QUOTE = '"';
        char SINGLE_QUOTE = '\'';
        String link="";
        StringBuffer bag = new StringBuffer();
        int i=0;
        int len = sample.length();
        for(;i+3<len; i++){
            h = sample.charAt(i);
            r = sample.charAt(i+1);
            e = sample.charAt(i+2);
            f = sample.charAt(i+3);

            if( (h=='h'||h=='H') && (r=='r'||r=='R') && (e=='e'||e=='E') && (f=='f'||f=='F')){
                bag.append( ""+h + r + e + f );
                int j = i+4;
                for(; sample.charAt(j)==SPACE; j++)
                    bag.append(""+SPACE);
                if(sample.charAt(j)==EQUALITY){
                    bag.append(""+EQUALITY);
                    j++;
                    for(; sample.charAt(j)==SPACE; j++)
                        bag.append(""+SPACE);
                    char c = sample.charAt(j++);
                    boolean quoted = false;
                    if( c==DOUBLE_QUOTE || c==SINGLE_QUOTE){
                        bag.append(""+c);
                        quoted=true;
                    }else{
                        link = "";
                        link=link+c;
                    }
                    if(quoted){
                        link = "";
                        for(;sample.charAt(j)!=DOUBLE_QUOTE && sample.charAt(j)!=SINGLE_QUOTE;j++){
                            link=link+sample.charAt(j);
                        }
                        /*******link******/
                        //System.out.println("LINK = "+link);
                        /*****************/
                   //     bag.append(""+sample.charAt(j)); //inserting closing quote
                   //     i=j;
                    }//quoted
                    else{
                        link = "";
                        for(;sample.charAt(j)!=SPACE && sample.charAt(j)!='>';j++){
                            link=link+sample.charAt(j);
                        }
                   //     bag.append(""+sample.charAt(j)); //inserting SPACE or >
                    //    i=j;
                    }//not quoted
                    /*******link******/
                    System.out.println("LINK = '"+link+"'");
                    /*****************/
                    bag.append(""+sample.charAt(j)); //inserting closing quote
                    i=j;
                }else{//if equallity
                    bag.append(""+sample.charAt(j));
                    i=j;
                }
            }else{// if href
                bag.append( ""+h);
                //System.out.println(" >: "+ h+r+e+f);
            }
        }//for

        String s = bag.toString();
        System.out.println(" ||| Bag: "+ s);
    }//

}//class