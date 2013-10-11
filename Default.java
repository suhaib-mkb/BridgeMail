package com.PMSystems.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005-2012</p>
 * <p>Company: </p>
 *
 * @author Ahmad Suhaib
 *
 * @version 1.0
 */

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.sql.Timestamp;
import java.security.*;
import java.math.*;

import org.apache.commons.lang.*;

public class Default {

    public static final long MILLIES_FOR_ONE_DAY = 24*60*60*1000l;
    public static final long MILLIES_FOR_ONE_HOUR = 60*60*1000l;
    public static final long MILLIES_FOR_ONE_MINUTE = 60*1000l;
    public static final long MILLIES_FOR_ONE_SECOND = 1000l;

    public static final String EMAIL_VALIDATION_REG_EXP = "[A-Za-z0-9'_`-]+(?:\\.[A-Za-z0-9'_`-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])";

  private Default() {
  }

  /**
   *
   * @param ruleBasedFields
   * @param subHash
   * @return
   */
  public static boolean isAnySubsRuleFieldsNull(String[] ruleBasedFields, Hashtable subHash) {

       for(int i=0; i<ruleBasedFields.length; i++) {
           if(subHash.get(ruleBasedFields[i])==null)
               return true;
       }
       return false;
  }

   /**
    *
    * @param matchValuesVec
    * @param subsRuleFieldData
    * @return
    */
   public static boolean isVecContains(Vector matchValuesVec, String subsRuleFieldData) {
       for(int i=0; i<matchValuesVec.size(); i++) {
           if(((String)matchValuesVec.get(i)).equals(subsRuleFieldData))
               return true;
       }
       return false;
  }

  /**
   *
   * @param numericRanges
   * @param subsRuleFieldData
   * @return
   */
  public static boolean isWithInNumericRanges(Vector numericRanges, String subsRuleFieldData) {

       try {
           for (int i = 0; i < numericRanges.size(); i++) {
               StringTokenizer stok = new StringTokenizer((String)numericRanges.get(i));
               String min = stok.nextToken();
               String max = stok.nextToken();

               //Return false as subsValue or min or max value is not numeric.
               if(!Default.isNumericValue(subsRuleFieldData) || !Default.isNumericValue(min)
                  || !Default.isNumericValue(max))
                   return false;

               int subsValue = Default.defaultInt(subsRuleFieldData);
               //Return true, if found a single match
               if(subsValue>=Default.defaultInt(min) && subsValue<=Default.defaultInt(max))
                    return true;
           }

       } catch (Exception ex) {
       }
       return false;//Subscriber data not lies in given numeric ranges.
  }

  /**
   *
   * @param dateRanges
   * @param subsRuleFieldData
   * @param format
   * @return
   */
  public static boolean isWithInDateRanges(Vector dateRanges, String subsRuleFieldData, String format) {

       try {
           for (int i = 0; i < dateRanges.size(); i++) {
               StringTokenizer stok = new StringTokenizer((String)dateRanges.get(i));
               String min = stok.nextToken();
               String max = stok.nextToken();

               //Return false as subsValue or min or max value is not valid date.
               if(!Default.isValidDate(subsRuleFieldData, format) || !Default.isValidDate(min, format)
                  || !Default.isValidDate(max, format))
                   return false;

               long subsTime = Default.parseDate(subsRuleFieldData, format);
               //Return true, if found a single match
               if(subsTime>=Default.parseDate(min,format) && subsTime<=Default.parseDate(max,format))
                    return true;
           }

       } catch (Exception ex) {
       }
       return false;//Subscriber data not lies in given date ranges.
  }

  /**
   *
   * @param matchValuesVec
   * @param subsRuleFieldData
   * @return
   */
  public static boolean isContains(Vector matchValuesVec, String subsRuleFieldData) {

       try {
           for (int i = 0; i < matchValuesVec.size(); i++) {
               try {
                   String substring = (String) matchValuesVec.get(i);
                   if (subsRuleFieldData.indexOf(substring) >= 0)
                       return true;
               } catch (Exception ex) { ; }
           }

       } catch (Exception ex) {
       }
       return false;//Subscriber data not lies in given numeric ranges.
  }

  /**
   * Returns true if given date is equal to-date OR Greater than to-date.
   *
   * @param subsRuleFieldData
   * @param format
   * @return
   */
  public static boolean isDayOfRuleApplied(String subsRuleFieldData, String format) {

      if(!Default.isValidDate(subsRuleFieldData, format))
          return false;

      long parsedTime = Default.parseDate(subsRuleFieldData, format);
      if(Default.removeHourPart(parsedTime)>=Default.removeHourPart(System.currentTimeMillis()))
          return true;

      return false;
  }
  /**
   *
   * @param subsRuleFieldData
   * @param format
   * @return
   */
  public static boolean isBirthDayRuleApplied(String subsRuleFieldData, String format) {

      if(!Default.isValidDate(subsRuleFieldData, format))
          return false;

      long parsedTime = Default.parseDate(subsRuleFieldData, format);
      if(Default.removeHourPart(parsedTime)>=Default.removeHourPart(System.currentTimeMillis()))
          return true;

      return false;
  }
  /**
   * @param subsRuleFieldData
   * @param format
   * @param gap
   * @return
   */
  public static boolean isDayPriorRuleApplied(String subsRuleFieldData, String format, int daysPrior) {

      if(!Default.isValidDate(subsRuleFieldData, format))
          return false;

      long parsedTime = Default.removeHourPart(Default.parseDate(subsRuleFieldData, format));
      if( (parsedTime-(Default.MILLIES_FOR_ONE_DAY*daysPrior)) >= Default.removeHourPart(System.currentTimeMillis()))
          return true;
      return false;
  }

  /**
   * @param subsRuleFieldData
   * @param format
   * @param gap
   * @return
   */
  public static boolean isDayAfterRuleApplied(String subsRuleFieldData, String format, int daysAfter) {

      if(!Default.isValidDate(subsRuleFieldData, format))
          return false;

      long parsedTime = Default.removeHourPart(Default.parseDate(subsRuleFieldData, format));
      if( (parsedTime+(Default.MILLIES_FOR_ONE_DAY*daysAfter)) >= Default.removeHourPart(System.currentTimeMillis()))
          return true;
      return false;
  }

  public static String updateURL(String str, boolean isSecure) {
    String sub = (isSecure)? "https://": "http://";
    str = Default.toDefault(str);
    str = (str.startsWith(sub))? str: sub+str;
    return str;
  }

  public static String parseURL(String str) {
    str = Default.toDefault(str);
    str = Default.replaceChar(str, '{', '&');
    return str;
  }

  public static String unParseURL(String str) {
    str = Default.toDefault(str);
    str = Default.replaceChar(str, '&', '{');
    return str;
  }

  public static String decodeURL(String str) {
    return Default.replaceString(str.trim(), "&amp;", "&").trim();
  }
  public static String encodeURL(String str) {
    return Default.replaceString(str.trim(), "&", "&amp;").trim();
  }

  public static Vector toVector(Object obj) {
      Vector vec = new Vector();
      vec.add(obj);
      return vec;
  }

  public static String toCSV(Vector data) {
      String csv = "";
      for (int i = 0; data != null && i < data.size(); i++) {
          if (data.size() == 1 || i + 1 >= data.size())
              csv += (String) data.get(i);
          else
              csv += ((String)data.get(i) + ",");
      }
      return csv;
  }

  public static String toStringCSV(Vector data) {
      String csv = "";
      for (int i = 0; data != null && i < data.size(); i++) {
          if (data.size() == 1 || i + 1 >= data.size())
              csv += ("'"+(String)data.get(i)+"'");
          else
              csv += ( ("'"+(String)data.get(i)+"'") + ",");
      }
      return csv;
  }

  public static Vector toVecFromArray(String ary[]) {
      Vector vec = new Vector();
      if(ary==null || ary.length==0)
          return vec;

      for(int i=0;i<ary.length; i++)
          vec.add(ary[i]);

      return vec;
  }

  public static String toValidatedStringCSV(String ary[]) {
      return toValidatedStringCSV(toVecFromArray(ary));
  }

  public static String toValidatedStringCSV(Vector data) {
      String csv = "";
      for (int i = 0; data != null && i < data.size(); i++) {
          if (data.size() == 1 || i + 1 >= data.size())
              csv += validateField((String)data.get(i));
          else
              csv += (validateField((String)data.get(i)) + ",");
      }
      return csv;
  }

  public static Vector fromCSV(String data) {

      Vector vec = new Vector();
      if(data==null || data.equals(""))
          return vec;

      StringTokenizer tok = new StringTokenizer(data, ",");
      while(tok.hasMoreTokens()) {
          vec.add(tok.nextToken().trim());
      }
      return vec;
  }

  public static String toDefault(String str) {
      return (str == null) ? "" : str;
  }

  public static Date toDefault(Date date) {
      return (date == null)? Calendar.getInstance().getTime() : date;
  }

  public static int defaultInt(String str) {
    try {
      if (str == null || str.equals(""))
        return 0;
      return Integer.parseInt(str);
    } catch (Exception ex) {
      return 0;
    }
  }

  public static boolean isNumericValue(String str) {
    try {
      if (str == null || str.equals(""))
        return false;
      Integer.parseInt(str);
      return true;
    } catch (Exception ex) {
    }
    return false;
  }

  public static long defaultLong(String str) {
    try {
      if (str == null || str.equals(""))
        return 0l;
      return Long.parseLong(str);
    } catch (Exception ex) {
      return 0l;
    }
  }

  public static float defaultFloat(String str) {
      try {
          if(str==null || str.equals(""))
              return 0.0f;
          return Float.parseFloat(str);
      } catch (Exception ex) {
          return 0.0f;
      }
  }

  /**
   *
   * @param number
   * @param decimalPlaces
   * @return
   */
  public static String formatNumber(double number, int decimalPlaces){
    String places ="0.0";
    for(int i=1; i<decimalPlaces; i++) {
        places+="0";
    }
    NumberFormat formatter = new DecimalFormat(places);
    return formatter.format(number);

  }

  /**
   *
   * @param vec
   * @return
   */
  public static int[] toIntArray(Vector vec) {

    if (vec == null || vec.size() == 0)
      return new int[0];

    int ary[] = new int[vec.size()];
    for (int i = 0; i < ary.length; i++) {
      ary[i] = Default.defaultInt((String) vec.get(i));
    }

    return ary;
  }

  public static int[] toIntArray(String str[]) {

    if (str == null || str.length == 0)
      return new int[0];

    int ary[] = new int[str.length];
    for (int i = 0; i < ary.length; i++) {
      ary[i] = Default.defaultInt(str[i]);
    }

    return ary;
  }

  public static String[] toStringArray(int str[]) {

    if (str == null || str.length == 0)
      return new String[0];

    String ary[] = new String[str.length];
    for (int i = 0; i < ary.length; i++) {
      ary[i] = "" + str[i];
    }

    return ary;
  }


  /**
   *
   * @param str
   * @param rmCar
   * @return
   */
  public static String removeChar(String str, char rmCar) {

    str = Default.toDefault(str);
    char[] ary = str.toCharArray();
    str = "";
    for (int i = 0; ary != null && i < ary.length; i++) {
      if (ary[i] != rmCar)
        str += ary[i];
    }
    return str;
  }

  public static String removeQuotes(String str) {

    str = Default.toDefault(str);

    while(str.startsWith("<")) {
      str = "."+str.substring(1, str.length());
    }
    char[] ary = str.toCharArray();
    char old = ' ';
    String tmp = "";
    for(int i=0; i<ary.length; i++) {
      if(ary[i]=='\"' && old!='\\')
        tmp += ("\\"+ary[i]);
      else
        tmp += (""+ary[i]);

      old = ary[i];
    }
    return tmp;
  }

  public static String replaceChar(String str, char oldCh, char newCh) {

    str = Default.toDefault(str);
    char[] ary = str.toCharArray();
    str = "";
    for (int i = 0; ary != null && i < ary.length; i++) {
      if (ary[i] == oldCh)
        str += newCh;
      else
        str += ary[i];
    }
    return str;
  }

  /**
   *
   * @param str
   * @param oldStr
   * @param newStr
   * @return
   */
  public static String replaceString(String str, String oldStr, String newStr) {

      Vector vec = new Vector();
      while(str.indexOf(oldStr)!=-1) {
          vec.add(str.substring(0, str.indexOf(oldStr)));
          str = str.substring(str.indexOf(oldStr)+oldStr.length(), str.length());
      }
      if(str.length()>0 && vec.size()>0)  {
          vec.add(str);
          str="";
      }

      for(int i=0; i<vec.size(); i++)
          if(vec.size()==1 || i+1<vec.size())
              str += ((String)vec.get(i) + newStr);
          else
              str += (String)vec.get(i);

      return str;
  }

  /**
   *
   * @param str String
   * @param ch char
   * @return int
   */
  public static int noOfCharOccurences(String str, char ch) {

    str = Default.toDefault(str);
    char[] ary = str.toCharArray();
    int count = 0;
    for (int i = 0; ary != null && i < ary.length; i++) {
      if (ary[i] == ch)
        count++;
    }
    return count;
  }

  public static boolean isValidEmail(String email) {
      return isValidEmailLong(email);
  }

  public static boolean isValidEmailLong(String email) {
      return isValidEmailComplete(email, true);
  }
  public static boolean isValidEmailShort(String email) {
      return isValidEmailComplete(email, false);
  }

  /**
   * Checks if the given email-addr is valid.
   * Email Address can be "Suhaib Ahmad<sasuhaib@gmail.com>" if (isLong==true)
   * OR "sasuhaib@gmail.com" if (isLong==false)
   *
   * @param emailAddr Email addr for Identifing user.
   * @param errorMsg
   * @return
   */
  private static boolean isValidEmailComplete(String email, boolean isLong) {

      boolean bool = false;
      try {
          if(toDefault(email).trim().equals(""))
              return false;

          if(isLong && email.charAt(email.length()-1)=='>' && email.indexOf("<")!=-1) {
              email = email.substring(email.indexOf("<")+1, email.length()-1);
          }
          //String regex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
          String regex = "^"+EMAIL_VALIDATION_REG_EXP+"+$";
          Pattern pattern = Pattern.compile(regex);
          bool = (pattern.matcher(email).matches());
      } catch (Exception ex) {
          bool = false;
      }
      //We get a more practical implementation of RFC 2822 if we omit the syntax using double quotes and square brackets.
      //It will still match 99.99% of all email addresses in actual use today
      //http://www.regular-expressions.info/email.html
      String str = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
      return bool;
  }

  /**
   * EmailAddress can be like: "Ahmad Suhaib<ahmad.suhaib@gmail.com>" OR "Ahmad Suhaib < ahmad.suhaib@gmail.com"
   * Returned value will be like: ahmad.suhaib@gmail.com
   *
   * @param emailAddress String
   * @return String
   */
  public static String extractEmailWithoutName(String emailAddress) {

      if(emailAddress==null || emailAddress.equals(""))
          return "";

      emailAddress = emailAddress.trim();
      try {
          if(emailAddress.indexOf("<")!=-1 && emailAddress.indexOf(">")!=-1)
              emailAddress = emailAddress.substring(emailAddress.indexOf("<")+1, emailAddress.length()-1);

          return emailAddress.trim();

      } catch (Exception ex) { ; }
      return "";
  }

  /**
   * EmailAddress can be like: "Ahmad Suhaib"<ahmad.suhaib@gmail.com>
   * Returned value will be like: Ahmad Suhaib
   *
   * @param emailAddress String
   * @return String
   */
  public static String getPersonalNameInEmail(String emailAddress) {

      try {
          if(emailAddress.indexOf("<")!=-1) {
              emailAddress = emailAddress.substring(0, emailAddress.indexOf("<"));
              return Default.stripStartEndQuotes(emailAddress);
          }
      } catch (Exception ex) { ; }
      return "";
  }


  /**
   * Remove single & double quotes from start & end of the string.
   *
   * @param str String
   * @return String
   */
  public static String stripStartEndQuotes(String str) {

     char[] ch = Default.toDefault(str).toCharArray();
     if(ch.length==0)
         return str;

     if(ch[0]=='\'' || ch[0]=='\"')
         ch[0] = ' ';

     if(ch[ch.length-1]=='\'' || ch[ch.length-1]=='\"')
         ch[ch.length-1] = ' ';

     return new String(ch).trim();
  }


  /**
   * Checks for valid password: at least 8 char long, must have at least one number, one character.
   *
   * @param pass
   * @return
   */
  public static boolean isValidPassword(String pass) {

      boolean bool = false;
      try {
          String regex = "^.*(?=.{8,})(?=.*\\d)(?=.*[A-Za-z]).*$";
          Pattern pattern = Pattern.compile(regex);
          bool = (pattern.matcher(pass).matches());
      } catch (Exception ex) {
          bool = false;
      }
      return bool;
  }

  /**
   *
   * @param in InputStream
   * @return String
   */
  public static String getText(InputStream in) {
    try {
      StringBuffer resp = new StringBuffer();
      int ch = -1;
      while ((ch = in.read()) != -1) {
        resp.append((char) ch);
      }
      in.close();
      return resp.toString();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static String subString(String str, int maxChar) {
    str = Default.toDefault(str);
    if(str.length()>maxChar)
      str = str.substring(0, maxChar);
    return str;
  }

  public static String subString(String str, int maxChar, String appender) {
    str = Default.toDefault(str);
    if(str.length()>maxChar) {
        str = str.substring(0, maxChar);
        str += appender;
    }
    return str;
  }

  public static long parseDate(String date, String format) {
      if(date==null || format==null)
          return System.currentTimeMillis();

      try {
          SimpleDateFormat sdf = new SimpleDateFormat(format);
          return sdf.parse(date).getTime();
      } catch (Exception ex) {
      }
      return System.currentTimeMillis();
  }

  public static boolean isValidDate(String date, String format) {
      //date should be at least 6char long. (e.g. mmddyy)
      if(date==null || format==null || date.length()<6)
          return false;

      try {
          SimpleDateFormat sdf = new SimpleDateFormat(format);
          sdf.parse(date).getTime();
          return true;
      } catch (Exception ex) {
      }
      return false;
  }
  /**
   *
   * @param time
   * @param format
   * @return
   */
  public static String formatDate(long time, String format) {
      if(time==0l || format==null)
          return "";

      try {
          SimpleDateFormat sdf = new SimpleDateFormat(format);
          return sdf.format(new java.util.Date(time));
      } catch (Exception ex) {
      }
      return "";
  }
  /**
   *
   * @param vec
   * @param subSize
   * @return
   */
  public static Vector getSubOfVector(Vector vec, int subSize) {
      if(vec==null || vec.isEmpty())
          return new Vector();

      Vector subSizeVec = new Vector();
      for(int i=0; i<vec.size() && i<subSize; i++) {
          subSizeVec.add(vec.get(i));
      }
      return subSizeVec;
  }
  /**
   *
   * @param time
   * @return
   */
  public static String formatDateForSQL(long time) {
      return formatDate(time, "yyyy-MM-dd HH:mm:ss");
  }
  /**
   *
   * @param time
   * @return
   */
  public static String buildDateRangeSQL(long startDate, long endDate, String columnName) {

      if(startDate<=0 || endDate<=0 || columnName.equals(""))
          return "";

      try {
          return " "+columnName+">='"+Default.formatDateForSQL(startDate)+"' AND "+columnName+"<'"+Default.formatDateForSQL(endDate)+"'";
      } catch (Exception ex) {
      }
      return "";
  }

  public static int getDays(long time) {
      int days = (int)((((time/1000)/60)/60)/24);
      return days;
  }

  public static long removeHourPart(long time) {

      if(time==0l)
          return 0l;

      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(time);

      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.MILLISECOND, 0);

      return cal.getTime().getTime();
  }

  public static long fetchHourPart(long time) {

      if(time<=0l)
          return 0l;

      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(time);

      return (cal.get(Calendar.HOUR_OF_DAY)*MILLIES_FOR_ONE_HOUR) + (cal.get(Calendar.MINUTE)*MILLIES_FOR_ONE_MINUTE)
           + (cal.get(Calendar.SECOND)*MILLIES_FOR_ONE_SECOND) + cal.get(Calendar.MILLISECOND);
  }


  public static String validateField(String str) {

      if (str == null)
          return null;

      str = str.replace('\\', '/');//replace '\' character with '/'

      StringBuffer sqlString = new StringBuffer(str);
      int lengthIncrement = 0;
      if (! (str.indexOf("\"") != -1)) {
          sqlString.insert(0, "\"");
          sqlString.insert(sqlString.length(), "\"");
      }
      else {
          for (int i = 0; i < str.length(); i++) {
              if (str.charAt(i) == '"') {
                  sqlString.insert( (i + lengthIncrement), "\"");
                  lengthIncrement++;
              }
          }
          sqlString.insert(0, "\"");
          sqlString.insert(sqlString.length(), "\"");
      }
      return sqlString.toString();
  }


  public static Timestamp toTimestamp(Date dt) {
      if(dt==null)
          return null;
      return new Timestamp(dt.getTime());
  }

  /**
   *
   * @param html
   * @return
   */
  public static String escapeHtml(String html) {
      html = Default.toDefault(html);
      return StringEscapeUtils.escapeHtml(html);
  }
  /**
   * Escapes the invalid JSON charcters.
   *
   * @param json
   * @return
   */
  public static String escapeForJSON(String json) {

      json = Default.toDefault(json);
      json = json.replaceAll("&", "&amp;");
      json = json.replaceAll("\"", "&quot;");
      json = json.replaceAll("'", "&apos;");
      return json;
  }
  /**
   *
   * @param escapedHtml
   * @return
   */
  public static String unescapeHtml(String escapedHtml) {
      escapedHtml = Default.toDefault(escapedHtml);
      return StringEscapeUtils.unescapeHtml(escapedHtml);
  }

  public static String[] secureInput(String input[]) {

      if(input==null || input.length==0)
          return input;
      for(int i=0; i<input.length;i++)
          input[i] = secureInput(input[i]);

      return input;
  }

  public static String[] secureEmail(String email[]) {

      if(email==null || email.length==0)
          return email;
      for(int i=0; i<email.length;i++)
          email[i] = secureEmail(email[i]);

      return email;
  }

  public static Vector secureEmail(Vector emailVec) {

      Vector vec = new Vector();
      if(emailVec==null || emailVec.size()==0)
          return emailVec;

      for(int i=0; i<emailVec.size();i++) {
          String email = secureEmail((String)emailVec.get(i));
          vec.add(email);
      }
      return vec;
  }

  /**
   * Secure URL such a way that it gets attached to a GET request without any problem
   * @param url
   * @return
   */
  public static String secureURLForParameter(String url) {
      url = url.replaceAll("&", "~.~");
      url = Default.secureInput(url);
      return url;
  }

  public static String unSecureURLFromParameter(String url) {
      return url.replaceAll("~.~", "&");
  }

  public static String secureURL(String url) {
      return secureMe(url, 2);
  }

  public static String secureFullURL(String url) {
      return secureMe(url, 3);
  }

  public static String secureEmail(String email) {
      return secureMe(email, 1);
  }

  /**
   * Secure email addresses like: Ahmad Suhaib<ahmad.suhaib@gmail.com>
   * @param email
   * @return
   */
  public static String secureEmailWithName(String email) {

      try {
          String senderName = Default.getPersonalNameInEmail(email).trim();
          String anEmail = Default.extractEmailWithoutName(email).trim();

          if(Default.isValidEmailShort(anEmail)) {
              senderName = Default.secureInput(senderName);
              if(!senderName.equals(""))
                  return senderName+" <"+anEmail+">";
              else
                  return anEmail;
          } else
              return "";

      } catch (Exception ex) {
          ;
      }
      return "";
  }

  public static String secureInput(String input) {
      return secureMe(input, 0);
  }

  private static String secureMe(String input, int type) {

      String bracketKeywords[] = new String[]{"\"", "<", ">", "'", ")","(","=",":"};
//      String bracketKeywords[] = new String[]{"\"", "<", ">","<script>", "<script", "<script/", "<body", "<xml", "script>", " src=", "<link", "alert("
//          , "javascript:", "document.write", "<embed", "<embed>", "<img", "<style", "<style>", "<meta", " onload=", "background:url", "<a", "onmouseover="
//          , "<textarea>", "<textarea", "textarea>", "url(", " url=", "<applet","<object", "<xss", "</"};

      if(type==1) {//If input EmailAddress...use email specific keywords
          bracketKeywords = new String[]{"\"", "<", ">", "'", "="};
//          bracketKeywords = new String[]{"<script>", "<script ", "script>", "src=", "<body ", "<link ", "alert(", "onmouseover="
//          , "javascript:", "document.write", "<embed ", "<embed>", "<img ", "<style ", "<style>", "<meta ", "onload=", "background:url", "<a "
//          , "<textarea>", "<textarea ", "textarea>", "url(", "url=", "<applet ","<object ", "<xss ", "</", "\"", "/>"};

      } else if(type==2) {// if input is URL
          bracketKeywords = new String[]{"\"", "<", ">", "'", ")","("};
//          bracketKeywords = new String[]{"\"", "<", ">","<script>", "<script", "script>", "<body", "<link", "alert("
//          , "javascript:", "document.write", "<embed", "<embed>", "<img", "<style", "<style>", "<meta", "background:url", "<a"
//          , "<textarea>", "<textarea", "textarea>", "url(", "<applet","<object", "<xss", "</"};
      }

      if(input==null || input.equals(""))
          return input;

      String lowercase = input.toLowerCase();

      for(int i=0; i<bracketKeywords.length; i++) {
          if(lowercase.indexOf(bracketKeywords[i])>=0) {

              //matched with keyword, replace chars & return;
              char[] inputChars = input.toCharArray();
              StringBuffer buff = new StringBuffer();
              for(int j=0; inputChars!=null && j<inputChars.length; j++) {
                  char ch = inputChars[j];

                  if(type==3) {//full url
                      if(ch=='<')
                          buff.append("%3C");
                      else if(ch=='>')
                          buff.append("%3E");
                      else if(ch=='(')
                          buff.append("%28");
                      else if(ch==')')
                          buff.append("%29");
                      else if(ch=='\'')
                          buff.append("%27");
                      else if(ch=='\"')
                          buff.append("%22");
                      else if(ch=='=')
                          buff.append("%3D");
                      else if(ch=='/')
                          buff.append("%2F");
                      else if(ch=='%')
                          buff.append("%25");
                      else if(ch==':')
                          buff.append("%3A");
                      else if(ch=='&')
                          buff.append("%26");
                      else if(ch=='?')
                          buff.append("%3F");
                      else
                          buff.append(ch);

                  } else if(type==2) {//if input is url
                      if(ch=='<')
                          buff.append("%3C");
                      else if(ch=='>')
                          buff.append("%3E");
                      else if(ch=='(')
                          buff.append("%28");
                      else if(ch==')')
                          buff.append("%29");
                      else if(ch=='\'')
                          buff.append("%27");
                      else if(ch=='\"')
                          buff.append("%22");
                      else
                          buff.append(ch);

                  } else if(type==0) {
                      if(ch=='<')
                          buff.append("&lt;");
                      else if(ch=='>')
                          buff.append("&gt;");
                      else if(ch==':')
                          buff.append("&#58;");
                      else if(ch=='=')
                          buff.append("&#61;");
                      else if(ch=='(')
                          buff.append("&#40;");
                      else if(ch==')')
                          buff.append("&#41;");
                      else if(ch=='\'')
                          buff.append("&#39;");
                      else if(ch=='\"')
                          buff.append("&#34;");
                      else
                          buff.append(ch);

                  } else if(type==1) {//if input is email

                      String anEmail = Default.extractEmailWithoutName(input).trim();
                      if(Default.isValidEmailShort(anEmail)) {
                          return anEmail;
                      } else
                          return "";
                  }
              }
              return buff.toString();
          }
      }
      return input;
  }

  /**
   * For Validating CampaignName, ListName
   *
   * @param name
   * @return
   */
  public static boolean isSecureName(String name) {

      if(name==null || name.equals(""))
          return false;

      if(name.indexOf("\"")>=0)
          return false;

      return true;
  }
  /**
   *
   * @param date
   * @param isPastTense
   * @return
   */
  public static String elapsedDateFormat(long date, boolean isPastTense) {
      return elapsedDateFormat(date, isPastTense, false, false);
  }
  /**
   *
   * @param date
   * @param isPastTense
   * @param detailed
   * @return
   */
  public static String elapsedDateFormat(long date, boolean isPastTense, boolean detailed) {
      return elapsedDateFormat(date, isPastTense, detailed, false);
  }
  /**
   *
   * @param date
   * @param isPastTense
   * @param detailed
   * @param onePart
   * @return
   */
  public static String elapsedDateFormat(long date, boolean isPastTense, boolean detailed, boolean onePart) {

      String dayStr = (detailed)? " day":"d";
      String hourStr = (detailed)? " hour":"h";
      String minStr = (detailed)? " minute":"m";
      String secStr = (detailed)? " second":"s";

      String pastStr = (isPastTense)? " ago": "";
      if(date<=0)
          return "0s"+pastStr;

      try {
          long DAY = 24*60*60*1000l;
          long HOUR = 60*60*1000l;
          long MINUTE = 60*1000l;
          long SECOND = 1000l;

          int days = (int)(date/DAY);
          date = (days>0)? (long)(date%DAY): date;

          int hrs = (int)(date/HOUR);
          date = (hrs>0)? (long)(date%HOUR): date;

          int mins = (int)(date/MINUTE);
          date = (mins>0)? (long)(date%MINUTE): date;

          int secs = (int)(date/SECOND);

          if(detailed) {
              dayStr = (days>1)? dayStr+"s": dayStr;
              hourStr = (hrs>1)? hourStr+"s": hourStr;
              minStr = (mins>1)? minStr+"s": minStr;
              secStr = (secs>1)? secStr+"s": secStr;
          }

          if(days>0)
              return days+dayStr+" "+(onePart? "": hrs+hourStr)+pastStr;

          else if(hrs>0)
              return hrs+hourStr+" "+(onePart? "": mins+minStr)+pastStr;

          else if(mins>0)
              return mins+minStr+" "+(onePart? "": secs+secStr)+pastStr;

          else
              return secs+secStr+pastStr;

      } catch (Exception ex) {
          ;
      }
      return "";
  }
  /**
   *
   * @param totalResultSetCount
   * @param offset
   * @param bucket
   * @param pageURL
   * @param pageVisibility
   * @return
   */
  public static String getHTMLForPaging(int totalResultSetCount, int offset, int bucket, String pageURL, int pageVisibility) {
      return getHTMLForPagingInDetail(totalResultSetCount, offset, bucket, pageURL, pageVisibility, "Previous", "Next", "items");
  }

  /**
   * Call this method when:
   *
   * You are fetching records from database using SQL queries like this:
   * mySQL+" LIMIT "+offset+", "+bucket;
   *
   * And You want to show your records in form of X records per page (where X == bucket).
   *
   * @param totalCount
   * @param offset
   * @param bucket
   * @param pageURL - URL for creating A-HREF against pageNumbers e.g. "/pms/my.jsp?abc=1&offset=##offset##" OR "javascript:viewNext(##offset##)";
   * @param pageVisibility - Number of pages to be shown in one batch e.g. 10
   * @return
   */
  public static String getHTMLForPagingInDetail(int totalResultSetCount, int offset, int bucket, String pageURL, int pageVisibility
                                        , String prevLabel, String nextLabel, String itemsLeftLabel) {

      if(totalResultSetCount<=0 || bucket<=0 || (offset)>=totalResultSetCount)
          return "";

      StringBuffer buff = new StringBuffer("");
      try {
          int currentPageNumber = ((int)(offset/bucket)) + 1;
          int startPageNumber = (((int)((currentPageNumber-1)/pageVisibility)) * pageVisibility)+1;

          if(currentPageNumber>1)
              buff.append("\n<a href=\""+pageURL.replaceAll("##offset##", ""+(offset-bucket))+"\">&lt;&lt&nbsp;"+prevLabel+"</a>");
          else
              buff.append("\n&lt;&lt&nbsp;"+prevLabel);

              //Show ... if NOT showing first group of pages.
          if(currentPageNumber<=pageVisibility)
              buff.append("&nbsp;&nbsp;");
          else
              buff.append("&nbsp;&nbsp;...&nbsp;&nbsp;");


          int pageIndex = startPageNumber;
          for(; pageIndex<(startPageNumber+pageVisibility); pageIndex++) {

              int myoffset = ((pageIndex-1)*bucket);
              if(myoffset>=totalResultSetCount)
                  break;

              if(pageIndex==currentPageNumber) {
                  buff.append("\n<span style=\"padding:3px; color: #ffffff; background: #2842AC; font-size:13px; font-weight:bold;\">"+pageIndex+"</span>");
              } else {
                  buff.append("\n<a href=\""+pageURL.replaceAll("##offset##", ""+myoffset)+"\">"+pageIndex+"</a>");
              }
              buff.append("&nbsp;");
          }

          //Show ... if NOT showing last group of pages.
          if(((pageIndex-1)*bucket) >= totalResultSetCount)
              buff.append("&nbsp;&nbsp;");
          else
              buff.append("&nbsp;&nbsp;...&nbsp;&nbsp;");

          //don't create Next>> link if offset for Next Page exceeds the Total Records.
          if((currentPageNumber*bucket)>=totalResultSetCount) {
              buff.append("\n"+nextLabel+"&nbsp;&gt;&gt;");
          } else {
              buff.append("\n<a href=\""+pageURL.replaceAll("##offset##", ""+(currentPageNumber*bucket))+"\">"+nextLabel+"&nbsp;&gt;&gt;</a>");
          }

          buff.append("\n&nbsp;&nbsp;&nbsp;<span style=\"color:#aaa\">("+toHumanReadable(totalResultSetCount)+" "+itemsLeftLabel+")</span>");
          return buff.toString();

      } catch (Exception ex) { ex.printStackTrace(); }
      return "";
  }


  public static String getHTMLForPagingFlickrStyle(int totalResultSetCount, int offset, int bucket, String pageURL, int pageVisibility) {

      if(totalResultSetCount<=0 || bucket<=0 || (offset)>=totalResultSetCount)
          return "";

      StringBuffer buff = new StringBuffer("\n<div style='margin-top:3px;margin-bottom:3px;' class=\"tag_cloud\">");
      try {
          int currentPageNumber = ((int)(offset/bucket)) + 1;
          int startPageNumber = (((int)((currentPageNumber-1)/pageVisibility)) * pageVisibility)+1;

          if(currentPageNumber>1)
              buff.append("\n<a href=\""+pageURL.replaceAll("##offset##", ""+(offset-bucket))+"\">&nbsp;&lt;&nbsp;Previous&nbsp;</a>");
          else
              buff.append("\n&nbsp;&lt;&nbsp;Previous&nbsp;");

          //Show ... if NOT showing first group of pages.
          if(currentPageNumber<=pageVisibility)
              buff.append("&nbsp;&nbsp;");
          else
              buff.append("&nbsp;&nbsp;...&nbsp;&nbsp;");

          int pageIndex = startPageNumber;
          for(; pageIndex<(startPageNumber+pageVisibility); pageIndex++) {

              int myoffset = ((pageIndex-1)*bucket);
              if(myoffset>=totalResultSetCount)
                  break;

              if(pageIndex==currentPageNumber) {
                  buff.append("\n<span class=\"this-page\">"+pageIndex+"</span>");
              } else {
                  buff.append("\n<a href=\""+pageURL.replaceAll("##offset##", ""+myoffset)+"\">"+pageIndex+"</a>");
              }
              buff.append("&nbsp;");
          }

          //Show ... if NOT showing last group of pages.
          if(((pageIndex-1)*bucket) >= totalResultSetCount)
              buff.append("&nbsp;&nbsp;");
          else
              buff.append("&nbsp;&nbsp;...&nbsp;&nbsp;");

          //don't create Next>> link if offset for Next Page exceeds the Total Records.
          if((currentPageNumber*bucket)>=totalResultSetCount) {
              buff.append("\n&nbsp;Next&nbsp;&gt;&nbsp;");
          } else {
              buff.append("\n<a href=\""+pageURL.replaceAll("##offset##", ""+(currentPageNumber*bucket))+"\">&nbsp;Next&nbsp;&gt;&nbsp;</a>");
          }

          buff.append("\n&nbsp;&nbsp;&nbsp;<span class='results'>("+toHumanReadable(totalResultSetCount)+" items)</span>");
          buff.append("\n</div>");
          return buff.toString();

      } catch (Exception ex) { ex.printStackTrace(); }
      return "";
  }

  /**
   *
   * @param value
   * @return
   */
  public static String toHumanReadable(long value) {

      try {
          return new DecimalFormat().format(value);
      } catch(Exception ex) {}
      return ""+value;//return as it is, in case of exception
  }

  public static int currentDayOfMonth() {
      try {
          return Calendar.getInstance().get(Calendar.DAY_OF_MONTH); // 1-31
      } catch(Exception ex) {}
      return 1;
  }

  public static int currentMonthOfYear() {
      try {
          return Calendar.getInstance().get(Calendar.MONTH)+1; // 1-12
      } catch(Exception ex) {}
      return 1;
  }

  public static int currentYear() {
      try {
          return Calendar.getInstance().get(Calendar.YEAR); // 1971-till now
      } catch(Exception ex) {}
      return 2005;
  }
  /**
   * @param dt
   * @return
   */
  public static int getDayOfMonth(Date dt) {
      if(dt==null)
          return currentDayOfMonth();
      try {
          Calendar cal = Calendar.getInstance();
          cal.setTime(dt);
          return cal.get(Calendar.DAY_OF_MONTH); // 1-31
      } catch(Exception ex) {}
      return currentDayOfMonth();
  }
  /**
   * @param dt
   * @return
   */
  public static int getMonthOfYear(Date dt) {
      if(dt==null)
          return currentMonthOfYear();
      try {
          Calendar cal = Calendar.getInstance();
          cal.setTime(dt);
          return cal.get(Calendar.MONTH)+1; // 1-12
      } catch(Exception ex) {}
      return currentMonthOfYear();
  }
  /**
   * @param dt
   * @return
   */
  public static int getYear(Date dt) {
      if(dt==null)
          return currentYear();
      try {
          Calendar cal = Calendar.getInstance();
          cal.setTime(dt);
          return cal.get(Calendar.YEAR); // 1971-till now
      } catch(Exception ex) {}
      return currentYear();
  }

  public static long getDaysPartOfTime(long time) {
      return getPartOfTime(time, "D");//return no. of days existing in giving time.
  }
  public static long getHoursPartOfTime(long time) {
      return getPartOfTime(time, "H");//return 1-23
  }
  public static long getMinutesPartOfTime(long time) {
      return getPartOfTime(time, "M");//return 1-60
  }
  public static long getSecondsPartOfTime(long time) {
      return getPartOfTime(time, "S");//return 1-60
  }

  /**
   * Always call Default.fetchHourPart() before, for full date (e.g. '2011-03-01 15:07:00') time.
   *
   * DO NOT WORK FOR FULL DATE & TIME MILLISECONDS.
   *
   * @param date
   * @param type
   * @return
   */
  private static long getPartOfTime(long date, String type) {

      try {
          long days = (long)(date/Default.MILLIES_FOR_ONE_DAY);
          date = (days>0)? (long)(date%Default.MILLIES_FOR_ONE_DAY): date;

          long hrs = (long)(date/Default.MILLIES_FOR_ONE_HOUR);
          date = (hrs>0)? (long)(date%Default.MILLIES_FOR_ONE_HOUR): date;

          long mins = (long)(date/Default.MILLIES_FOR_ONE_MINUTE);
          date = (mins>0)? (long)(date%Default.MILLIES_FOR_ONE_MINUTE): date;

          long secs = (long)(date/Default.MILLIES_FOR_ONE_SECOND);

          if(type.equalsIgnoreCase("S"))
              return secs;
          else if(type.equalsIgnoreCase("M"))
              return mins;
          else if(type.equalsIgnoreCase("H"))
              return hrs;
          else if(type.equalsIgnoreCase("D"))
              return days;

          return date;

      } catch (Exception ex) {}
      return 0;
  }


  public static String zeroPadding(String num) {
    if(Default.defaultInt(num)<10)
      return "0"+num;
    return num;
  }

  /**
   * @param nvVec
   * @return
   */
  public static Vector fromNameValueVecToNameVec(Vector nvVec) {
      Vector nameVec = new Vector();
      for(int i=0; nvVec!=null && i<nvVec.size(); i++) {
          if(nvVec.get(i) instanceof NameValue)
              nameVec.add(((NameValue)nvVec.get(i)).getName());
      }
      return nameVec;
  }
  /**
   * @param vec Vector
   * @param name String
   * @return NameValue
   */
  public static NameValue findNameValue(Vector vec, String name) {
      int index = vec.indexOf(new NameValue(name, ""));
      if(index>=0)
          return (NameValue)vec.get(index);

      return null;
  }
  /**
   *
   * @param vec
   * @return
   */
  public static Vector cloneNameValueVector(Vector vec) {

      Vector newVec = new Vector();
      for(int i=0; i<vec.size(); i++) {
          if(vec.get(i) instanceof NameValue) {
              NameValue nv = (NameValue)vec.get(i);
              newVec.add(new NameValue(nv.getName(), nv.getValue()));
          }
      }
      return newVec;
  }
  /**
   *
   * @param url
   * @return
   */
  public static String removeInvalidLinkEndChars(String url){
      if(url.length()==0)
          return url;

      Vector charVec = new Vector();
      charVec.add("]");
      charVec.add("[");
      charVec.add(",");
      charVec.add(";");
      charVec.add("{");
      charVec.add("}");
      charVec.add("(");
      charVec.add(")");
      charVec.add(":");
      charVec.add("-");
      charVec.add("+");
      charVec.add("/");
      charVec.add("*");

      int count = 0;
      for(int i=url.length()-1;i>0;i--){
          if(charVec.contains(""+url.charAt(i))){
              count++;
          } else{
              return url.substring(0,url.length()-count);
          }
      }
      return url;
  }
  /**
   * Return valid BirthDay time, extracting from the given Time & Date.
   *
   * @param birthDate
   * @return
   */
  public static long getBirthDay(long birthDate) {

      long dispenseDate = 0;
      Calendar sysCal = Calendar.getInstance();
      Calendar birthCal = Calendar.getInstance();
      birthCal.setTimeInMillis(birthDate);


      if(sysCal.get(Calendar.MONTH)==birthCal.get(Calendar.MONTH) && sysCal.get(Calendar.DAY_OF_MONTH)==birthCal.get(Calendar.DAY_OF_MONTH)) {
          dispenseDate = System.currentTimeMillis();

      } else {
          if(birthCal.get(Calendar.MONTH)>sysCal.get(Calendar.MONTH)) {
              sysCal.set(Calendar.MONTH, birthCal.get(Calendar.MONTH));
              sysCal.set(Calendar.DAY_OF_MONTH, birthCal.get(Calendar.DAY_OF_MONTH));

          } else if(birthCal.get(Calendar.MONTH)==sysCal.get(Calendar.MONTH) && birthCal.get(Calendar.DAY_OF_MONTH)>sysCal.get(Calendar.DAY_OF_MONTH)) {
              sysCal.set(Calendar.MONTH, birthCal.get(Calendar.MONTH));
              sysCal.set(Calendar.DAY_OF_MONTH, birthCal.get(Calendar.DAY_OF_MONTH));

          } else {
              sysCal.set(Calendar.YEAR, sysCal.get(Calendar.YEAR)+1);
              sysCal.set(Calendar.MONTH, birthCal.get(Calendar.MONTH));
              sysCal.set(Calendar.DAY_OF_MONTH, birthCal.get(Calendar.DAY_OF_MONTH));
          }
          dispenseDate = sysCal.getTimeInMillis();//schedule it for the future 'birthday' according to subs. value.
      }
      return Default.removeHourPart(dispenseDate);//REMOVE hours, mins, secs, millies from the birthday time.
  }
  /**
   *
   * @param time
   * @param years
   * @return
   */
  public static long addYearsToTime(long time, int years) {

      if(time<=0 || years<=0)
          return time;

      try {
          Calendar cal = Calendar.getInstance();
          cal.setTimeInMillis(time);
          cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+years); // 1971-till now
          return cal.getTimeInMillis();

      } catch(Exception ex) {}
      return time;
  }

  public static String getValidYesORNoValue(String str) {
      return Default.toDefault(str).trim().equalsIgnoreCase("Y")? "Y": "N";
  }
  /**
   *
   * @param text
   * @return
   */
  public static String toMD5CheckSum(String text) {

      try {
          byte[] bytesOfMessage = text.getBytes("UTF-8");
          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] thedigest = md.digest(bytesOfMessage);
          BigInteger bigInt = new BigInteger(1,thedigest);
          return bigInt.toString(16);

      } catch (Exception ex) {
          ex.printStackTrace();
      }
      return "";
  }

  /**
   *
   * @param hash
   * @return
   */
  public static Vector toKeysVector(Hashtable hash) {

      Vector keyVec = new Vector();
      if(hash==null || hash.size()==0)
          return keyVec;

      Enumeration keyNum = hash.keys();
      while(keyNum.hasMoreElements())
          keyVec.add(keyNum.nextElement());

      return keyVec;
  }
}



