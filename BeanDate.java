package com.PMSystems.util;

import com.PMSystems.*;


public class BeanDate extends PMSDate {

    /**
      * BeanDate()              constructor of BeanDate()
      *
      * @param                   year as int
      * @param                   month as int
      * @param                   date as int
      * @param                   hour as int
      * @param                   minute as int
      * @param                   second as int
      */
     public BeanDate(int year, int month, int date, int hour, int minute, int second) {
         super(year, month, date, hour, minute, second);
     }


     /**
      * BeanDate()              constructor of BeanDate()
      *
      * @param                   year as int
      * @param                   month as int
      * @param                   date as int
      */
     public BeanDate(int year, int month, int date) {
         super(year, month, date);
     }


     /**
      * BeanDate()              constructor of BeanDate()
      *
      * @param                   year as int
      * @param                   month as int
      * @param                   date as int
      */
     public BeanDate(String date) {

         super(0, 0, 0);
     }

     /**
      * BeanDate()              constructor of BeanDate()
      *
      * @param                   date as Date
      */
     public BeanDate(java.util.Date date) {
         super(date);
     }

     /**
      * BeanDate(boolean empty) constructor of BeanDate()
      *
      * @param                empty as boolean
      */
     public BeanDate(boolean empty) {
         super(empty);
     }

     /**
      * BeanDate()              constructor of BeanDate()
      *
      * @param                   date as Date
      * @param                   time as Date
      */
     public BeanDate(java.util.Date date, java.util.Date time) {
         super(date, time);
     }

     /**
      * BeanDate()              constructor of BeanDate()
      *
      * @param                   date as long
      */
     public BeanDate(long date) {
         super(date);
     }

     /**
      * BeanDate()              constructor of BeanDate()
      *
      */
     public BeanDate() {
         super();
     }

     /**
      * toString()               returns a string representing in the format:
      *                          yyyy-mm-yy hour:minute:second
      *
      * @return                  String representing a date
      */
     public String toString() {
         StringBuffer datetime = new StringBuffer();
         datetime.append(getYear()).append((getMonth()<10) ? "-0" : "-");
         datetime.append(getMonth()).append((getDate()<10) ? "-0" : "-");
         datetime.append(getDate()).append((getHour()<10) ? " 0" : " ");
         datetime.append(getHour()).append((getMinute()<10) ? ":0" : ":");
         datetime.append(getMinute()).append((getSecond()<10) ? ":0" : ":");
         datetime.append(getSecond());
         return datetime.toString();
         /*return ""
              + getYear()   + ((getMonth()<10) ? "-0" : "-")
              + getMonth()  + ((getDate()<10) ? "-0" : "-")
              + getDate()   + ((getHour()<10) ? " 0" : " ")
              + getHour()   + ((getMinute()<10) ? ":0" : ":")
              + getMinute() + ((getSecond()<10) ? ":0" : ":")
              + getSecond()
         ;*/
     }

     /**
      * date()
      *
      * @return                  String containing date without time
      */
     public String date() {
         //if(getYear() == 0 || getMonth() == 0 || getDate() == 0) {
         //    return "";
         //}
         StringBuffer date = new StringBuffer();
         date.append((getYear() < 10) ? "000" : ((getYear() < 100) ? "00": ((getYear() < 1000) ? "0" : "")));
         date.append(getYear()).append((getMonth()<10) ? "-0" : "-");
         date.append(getMonth()).append((getDate()<10) ? "-0" : "-");
         date.append(getDate());
         return date.toString();
     }

     /**
      * time()
      *
      * @return                  String containing time without date
      */
     public String time() {
         //if(getHour() == 0 && getMinute() == 0 && getSecond() == 0) {
         //    return "";
         //}
         StringBuffer time = new StringBuffer();
         time.append((getHour()<10) ? "0" : "");
         time.append(getHour()).append((getMinute()<10) ? ":0" : ":");
         time.append(getMinute()).append((getSecond()<10) ? ":0" : ":");
         time.append(getSecond());
         return time.toString();
     }
}