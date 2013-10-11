package com.PMSystems.util;

import com.PMSystems.*;
import java.util.*;
import java.util.regex.*;

/**
 * This class contains validation functions to verify the data entered by the user.
 *
 * @version         1.0            29 Aug 2002
 * @author          Naaman Musawwir
 *
 * 29 Aug 2002      Naaman Musawwir          Initial Development of Class
 */
public class ValidationChecks {
    public long k;
//    private static final String SUPPRESSED_EMAILS_FILE=PMSDefinitions.RESOURCE_DIR+"SuppressedEmails.txt";// for www
//    private static String filePath="E:\\PMSProject\\PMS\\SuppressedEmails.txt";//for local
//    private static FileInputStream fis=null;

    /**
     * validateDate() checks if the passed date is a valid date and gives
     * respective error messages.
     *
     * @param year as int
     * @param month as int
     * @param date as int
     * @param dateTimeHeading as String
     * @param errorMessage as ErrorMessage
     * @return true if validated
     */
    public static boolean validateDate(int year, int month, int date,
                                       String dateTimeHeading, ErrorMessage errorMessage) {


        if(month > 11){
            errorMessage.addError("Invalid "
                                     + dateTimeHeading
                                     + "; "
                                     + " Invalid Month, months can't be more than 12 ");
             return false;
        }

        if(date > 31){
            errorMessage.addError("Invalid "
                                     + dateTimeHeading
                                     + "; "
                                     + " Invalid Date, date can't be greater than 31st ");
             return false;
        }


        // check if year is between 1900 and 9999
        if(year>9999 || year<1900) {
            errorMessage.addError("Invalid "+dateTimeHeading
                                +" Year; year value must be between 1900 and 9999");
            return false;
        }
        else {
            // checks for the month of February
            // if Leap year
            if(year%4==0) {
                if(month==1) {
                    if(date>29) {
                        errorMessage.addError("Invalid "
                                            + dateTimeHeading
                                            + "; February can't have "+date
                                            + (date<31?"th":"st")+" day");
                        return false;
                    }
                }
            }
            // if not Leap year
            else {
                if(month==1) {
                    if(date>28) {
                        errorMessage.addError("Invalid "
                                            + dateTimeHeading
                                            + "; February can't have "+date
                                            + (date<31?"th":"st")+" day");
                        return false;
                    }
                }
            }
            // checks for other 30 dayed months
            if (month==3 || month==5 || month == 8 || month==10){
                if (date > 30) {
                    errorMessage.addError("Invalid "
                                            + dateTimeHeading
                                            + "; "+PMSUtils.monthNames[month]
                                            + " can't have "+date+"st day");
                    return false;
                }
            }
        }

        return true;
    } // end validateDate()

    /**
     * validateDate() checks if the passed date is a valid date and gives
     * respective error messages.
     *
     * @param year as int
     * @param month as int
     * @param date as int
     * @param error as StringBuffer
     * @return true if validated
     */
    public static boolean validateDate(int year, int month, int date, StringBuffer error) {
        String dateTimeHeading = "Date";

        if(month > 11){
            error.append("Invalid "
                                     + dateTimeHeading
                                     + "; "
                                     + " Invalid Month, months can't be more than 12 ");
             return false;
        }

        if(date > 31){
            error.append("Invalid "
                                     + dateTimeHeading
                                     + "; "
                                     + " Invalid Date, date can't be greater than 31st ");
             return false;
        }


        // check if year is between 1900 and 9999
        if(year>9999 || year<1900) {
            error.append("Invalid "+dateTimeHeading
                                +" Year; year value must be between 1900 and 9999");
            return false;
        }
        else {
            // checks for the month of February
            // if Leap year
            if(year%4==0) {
                if(month==1) {
                    if(date>29) {
                        error.append("Invalid "
                                            + dateTimeHeading
                                            + "; February can't have "+date
                                            + (date<31?"th":"st")+" day");
                        return false;
                    }
                }
            }
            // if not Leap year
            else {
                if(month==1) {
                    if(date>28) {
                        error.append("Invalid "
                                            + dateTimeHeading
                                            + "; February can't have "+date
                                            + (date<31?"th":"st")+" day");
                        return false;
                    }
                }
            }
            // checks for other 30 dayed months
            if (month==3 || month==5 || month == 8 || month==10){
                if (date > 30) {
                    error.append("Invalid "
                                            + dateTimeHeading
                                            + "; "+PMSUtils.monthNames[month]
                                            + " can't have "+date+"st day");
                    return false;
                }
            }
        }

        return true;
    } // end validateDate()

    /**
     * validateDate() checks if the passed date is a valid date and gives
     * respective error messages
     *
     * @param strDate as String
     * @param dateTimeHeading as String
     * @param errorMessage as ErrorMessage
     * @return date as Calendar
     */
    public static java.util.Calendar validateDate(String strDate,
            String dateTimeHeading, ErrorMessage errorMessage) {
        if(strDate.length() < 8 || strDate.length() >10) {
            errorMessage.addError("Invalid "+dateTimeHeading+"; Looks like an invalid format, allowed format is yyyy-m-d");
            return null;
        }
        else {
            StringTokenizer tokens = new StringTokenizer(strDate,"-");
            if(tokens.countTokens() > 3 || tokens.countTokens() < 3){
                errorMessage.addError("Invalid "+dateTimeHeading+"; Looks like an invalid format, allowed format is yyyy-m-d");
                return null;
            }
            if(tokens.hasMoreTokens()) {
                try {
                    int year = Integer.parseInt(tokens.nextToken());
                    int month = Integer.parseInt(tokens.nextToken());
                    int date = Integer.parseInt(tokens.nextToken());
                    if(validateDate(year,month-1,date,dateTimeHeading,errorMessage)){
                        Calendar calDate = Calendar.getInstance();
                        calDate.set(year, month-1, date, 0, 0, 0);
                        return calDate;
                    }
                    else {
                        return null;
                    }
                }
                catch (NumberFormatException e) {
                    errorMessage.addError("Invalid "+dateTimeHeading+"; Year, month and date must be Integer values");
                    return null;
                }
            }
        }
        return null;
    } // end validateDate()

    /**
     * validateDate() checks if the passed date is a valid date and gives
     * respective error messages
     *
     * @param strDate as String
     * @param error as StringBuffer
     * @return date as Calendar
     */
    public static java.util.Calendar validateDate(String strDate, StringBuffer error) {
        String dateTimeHeading = "Date";
        if(strDate.length() < 8 || strDate.length() >10) {
            error.append("Invalid "+dateTimeHeading+"; Looks like an invalid format, allowed format is yyyy-m-d");
            return null;
        }
        else {
            StringTokenizer tokens = new StringTokenizer(strDate,"-");
            if(tokens.countTokens() > 3 || tokens.countTokens() < 3){
                error.append("Invalid "+dateTimeHeading+"; Looks like an invalid format, allowed format is yyyy-m-d");
                return null;
            }
            if(tokens.hasMoreTokens()) {
                try {
                    int year = Integer.parseInt(tokens.nextToken());
                    int month = Integer.parseInt(tokens.nextToken());
                    int date = Integer.parseInt(tokens.nextToken());
                    if(validateDate(year, month-1, date, error)){
                        Calendar calDate = Calendar.getInstance();
                        calDate.set(year, month-1, date, 0, 0, 0);
                        return calDate;
                    }
                    else {
                        return null;
                    }
                }
                catch (NumberFormatException e) {
                    error.append("Invalid "+dateTimeHeading+"; Year, month and date must be Integer values");
                    return null;
                }
            }
        }
        return null;
    } // end validateDate()

    /**
     * Valid Email is in right format, & not included 'spam' as subtext in it, & does not belong to UniversalSupress List.
     *
     * @param email as String
     * @return   boolean
     */
    public static boolean validateEmail(String email) {

        PMSResources pmsResources=null;
        try {
            pmsResources = PMSResources.getInstance();
        } catch(com.PMSystems.ResourceException re) { re.printStackTrace(); }


        try {
            email = email.trim().toLowerCase();
            if(email.equals("") || !Default.isValidEmailLong(email) || email.indexOf("spam")!=-1 || !pmsResources.isValidEmail(email)) {
                return false;
            } else
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   /**
    * This method converts a java.util.Date object into a String according to MySQL
    * date standard.
    *
    * @param   date    as a java.util.Date object to be converted
    * @return  date    as a String object
    */
   public static String getValidatedValue(Date date) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       return "'"+(c.get(c.YEAR))
                 + ((c.get(c.MONTH)<10) ? "-0" : "-")
                 + c.get(c.MONTH)  + ((c.get(c.DATE)<10) ? "-0" : "-")
                 + c.get(c.DATE)   + ((c.get(c.HOUR)<10) ? " 0" : " ")
                 + c.get(c.HOUR)   + ((c.get(c.MINUTE)<10) ? ":0" : ":")
                 + c.get(c.MINUTE) + ((c.get(c.SECOND)<10) ? ":0" : ":")
                 + c.get(c.SECOND)+"'";
   }

   /**
    * This method looks for single quote(') within a vlaue and converts it
    * into double single quotes ('') to make it valid for MySQL. ' works as
    * an escape sequence in MySQL.
    *
    * @param   value       as a String object to be converted
    * @return  converted   as a String object
    */
   public static String getValidatedValue(String value) {
       return "'"+value+"'";
   }

   /**
    * This method converts a boolean into 1 (for yrue)
    * and 0 (for false).
    *
    * @param   value       as a boolean to be converted
    * @return  converted   as an int
    */
   public static int getValidatedValue(boolean value) {
       return value?1:0;
   }

   /**
    * validateNumber() checks if the passed String is a valid number
    * according to the specified type
    *
    * @param   number as a String object holding the number to be validated
    * @param   type as an int specifying the expected type of the passed number
    * @return  boolean representing the validity of the number
    */
   public static boolean validateNumber(String number, int type) {
       if(number==null) {
           return false;
       }
       String typeName = "";
       try {
           switch (type) {
               case PMSDefinitions.NUMTYPE_INTEGER :
                   Integer.parseInt(number);
                   break;
               case PMSDefinitions.NUMTYPE_LONG :
                   Long.parseLong(number);
                   break;
               case PMSDefinitions.NUMTYPE_FLOAT :
                   Float.parseFloat(number);
                   break;
               case PMSDefinitions.NUMTYPE_DOUBLE :
                   Double.parseDouble(number);
                   break;
           }
       }
       catch(NumberFormatException nfe) {
           return false;
       }
       return true;
   } // end validateNumber()

   /**
    * validateNumber() checks if the passed String is a valid number
    * according to the specified type
    *
    * @param   number as a String object holding the number to be validated
    * @param   type as an int specifying the expected type of the passed number
    * @param   errorMessage an ErrorMessage object to append error messages to
    * @param   fieldHeading a String object holding the screen name of the field
    * @return  boolean representing the validity of the number
    */
   public static boolean validateNumber(String number, int type,
                                        ErrorMessage errorMessage,
                                        String fieldHeading) {
       if(number==null) {
           errorMessage.addError("Passed Number is 'null'; Cannot be validated");
           return false;
       }
       String typeName = "";
       try {
           switch (type) {
               case PMSDefinitions.NUMTYPE_INTEGER :
                   typeName = "numbers";
                   Integer.parseInt(number);
                   break;
               case PMSDefinitions.NUMTYPE_LONG :
                   typeName = "numbers";
                   Long.parseLong(number);
                   break;
               case PMSDefinitions.NUMTYPE_FLOAT :
                   typeName = "numbers and decimals";
                   Float.parseFloat(number);
                   break;
               case PMSDefinitions.NUMTYPE_DOUBLE :
                   typeName = "numbers and decimals";
                   Double.parseDouble(number);
                   break;
           }
       }
       catch(NumberFormatException nfe) {
           errorMessage.addError("'"+fieldHeading+"' must contain only "
                                                      + typeName);
           return false;
       }
       return true;
   } // end validateNumber()

   /**
    * validateNumber() checks if the passed String is a valid number
    * according to the specified type. Also checks if the number is
    * less than zero(negative).
    *
    * @param   number as a String object holding the number to be validated
    * @param   type as an int specifying the expected type of the passed number
    * @param   errorMessage a StringBuffer object to append error messages to
    * @param   fieldHeading a String object holding the screen name of the field
    * @return  boolean representing the validity of the number
    */
   public static boolean validatePositiveNumber(String number, int type,
                                        ErrorMessage errorMessage,
                                        String fieldHeading) {
       double num = 0;
       if(number==null) {
           errorMessage.addError("Passed Number is 'null'; Cannot be validated");
           return false;
       }
       String typeName = "";
       try {
           switch (type) {
               case PMSDefinitions.NUMTYPE_INTEGER :
                   typeName = "numbers";
                   num = Integer.parseInt(number);
                   break;
               case PMSDefinitions.NUMTYPE_LONG :
                   typeName = "numbers";
                   num = Long.parseLong(number);
                   break;
               case PMSDefinitions.NUMTYPE_FLOAT :
                   typeName = "numbers and decimals";
                   num = Float.parseFloat(number);
                   break;
               case PMSDefinitions.NUMTYPE_DOUBLE :
                   typeName = "numbers and decimals";
                   num = Double.parseDouble(number);
                   break;
           }
       }
       catch(NumberFormatException nfe) {
           errorMessage.addError("'"+fieldHeading+"' must contain only "
                                                      + typeName);
           return false;
       }
       if(num < 0) {
           errorMessage.addError("'"+fieldHeading+"' must contain only "
                                                      + "positive " + typeName);
           return false;
       }
       return true;
   } // end validateNumber()

}
