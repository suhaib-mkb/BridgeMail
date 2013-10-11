/**
 * EJBHomesFactory.java         1.0  7 September 2003
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

import com.PMSystems.*;
import com.PMSystems.logger.*;
import com.PMSystems.sejbs.*;

import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.sql.Timestamp;

/**
 * This bean will be use to populate the AddSubscribers jsp.
 *
 * @version         1.0       11 September 2003
 * @author          Muhammad Bilal Fazal
 *
 * History
 * 11 September 2003     Muhammad Bilal Fazal        Initial Development of Class.
 */
public class EJBHomesFactory {

    public static String CLASS_NAME="[EJBHomesFactory]";
    private static Map factoryMap;
    private static Context ctx;

    static {
        try {
            ctx = PMSUtils.getContext();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        factoryMap = Collections.synchronizedMap(new HashMap());
    }
    /**
     * Lookup and cache an EJBHome object if not found in the cache.
     */
    public static EJBHome lookUpHome(String jndiName) {
        EJBHome ejbHome=null;
        try {
            ejbHome = (EJBHome) factoryMap.get(jndiName);
            if (ejbHome == null) {
                ejbHome = (EJBHome) ctx.lookup(jndiName);
                synchronized(factoryMap) {
                    factoryMap.put(jndiName, ejbHome);
                }
            }

        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
            ex.printStackTrace();
        }
        return ejbHome;
    }

    /**
     *
     */
    public EJBHomesFactory() {
    }

    /**
     *
     * @return ComplexQueryServer2Home
     */
    private static ComplexQueryServer2Home getComplexQueryServer2Home(){
        try{
            Object ref = lookUpHome(PMSDefinitions.JNDI_NAME_COMPLEX_QUERY_SERVER2);
            //cast to Home interface
            ComplexQueryServer2Home complex2Home = (ComplexQueryServer2Home)PortableRemoteObject.
                    narrow(ref, ComplexQueryServer2Home.class);
            return complex2Home;
        }catch(Exception e){
            WebServerLogger.getLogger().log(e);
            System.out.println(" --EXCEPTION-- "+ CLASS_NAME + " Exception in getComplexQueryServer2Home():-"+e);
            System.out.println(" --EXCEPTION-- "+ CLASS_NAME + " Exception Message:=" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }//getComplexQueryServer2Home()

    /**
     *
     * @return ReportQueryServerHome
     */
    private static com.PMSystems.sejbs.ReportsQueryServerHome getReportsQueryServerHome(){
        try{
            Object ref = lookUpHome(PMSDefinitions.JNDI_NAME_REPORTS_QUERY_SERVER);
            //cast to Home interface
            ReportsQueryServerHome reportHome = (ReportsQueryServerHome)PortableRemoteObject.
                    narrow(ref, ReportsQueryServerHome.class);
            return reportHome;

        }catch(Exception e){
            WebServerLogger.getLogger().log(e);
            System.out.println(" --EXCEPTION-- "+ CLASS_NAME + " Exception in getReportsQueryServerHome():-"+e);
            System.out.println(" --EXCEPTION-- "+ CLASS_NAME + " Exception Message:=" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }//getReportsQueryServer2Home()

    /**
     *
     * @return ComplexQueryServerHome
     */
    private static ComplexQueryServerHome getComplexQueryServerHome(){
        try{
            Object ref = lookUpHome(PMSDefinitions.JNDI_NAME_COMPLEX_QUERY_SERVER);
            //cast to Home interface
            ComplexQueryServerHome complexHome = (ComplexQueryServerHome)PortableRemoteObject.
                    narrow(ref, ComplexQueryServerHome.class);
            return complexHome;
        }catch(Exception e){
            WebServerLogger.getLogger().log(e);
            System.out.println(" --EXCEPTION-- "+ CLASS_NAME + " Exception in getComplexQueryServerHome():-"+e);
            System.out.println(" --EXCEPTION-- "+ CLASS_NAME + " Exception Message:=" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }//getComplexQueryServerHome()


    private static DataQueryServerHome getDataQueryServerHome() {

        try {
            //System.out.println("DQSB Name is:"+PMSDefinitions.JNDI_NAME_DATA_QUERY_SERVER);
            Object ref = lookUpHome(PMSDefinitions.JNDI_NAME_DATA_QUERY_SERVER);
            DataQueryServerHome dataQueryServerHome = (DataQueryServerHome)PortableRemoteObject.
                narrow(ref, DataQueryServerHome.class);

            return dataQueryServerHome;

        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    private static AlphaQueryServerHome getAlphaQueryServerHome() {

        try {
            Object ref = lookUpHome(PMSDefinitions.JNDI_NAME_ALPHA_QUERY_SERVER);
            AlphaQueryServerHome alphaQueryServerHome = (AlphaQueryServerHome)PortableRemoteObject.narrow(ref, AlphaQueryServerHome.class);

            return alphaQueryServerHome;

        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    private static BMSQueryServerHome getBMSQueryServerHome() {

        try {
            Object ref = lookUpHome(PMSDefinitions.JNDI_NAME_BMS_QUERY_SERVER);
            BMSQueryServerHome bmsQueryServerHome = (BMSQueryServerHome)PortableRemoteObject.
                narrow(ref, BMSQueryServerHome.class);

            return bmsQueryServerHome;

        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    public static DataQueryServer getDataQueryServerRemote() {
        try {
            return getDataQueryServerHome().create();
        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    public static AlphaQueryServer getAlphaQueryServerRemote() {

        try {
            return getAlphaQueryServerHome().create();
        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    public static BMSQueryServer getBMSQueryServerRemote() {
        try {
            return getBMSQueryServerHome().create();
        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    public static ComplexQueryServer getComplexQueryServerRemote() {
        try {
            return getComplexQueryServerHome().create();
        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    public static ComplexQueryServer2 getComplexQueryServer2Remote() {
        try {
            return getComplexQueryServer2Home().create();
        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }

    public static ReportsQueryServer getReportsQueryServerRemote() {
        try {
            return getReportsQueryServerHome().create();
        } catch (Exception ex) {
            WebServerLogger.getLogger().log(ex);
        }
        return null;
    }


}//class EJBHomesFactory

