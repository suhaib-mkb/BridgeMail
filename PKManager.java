/**
 * PKManager.java         1.0  28 March 2005
 *
 * Copyright (c) 2005 Workflow Mobility Inc.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of Workflow Mobility Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information.
 */
package com.PMSystems.util;

import com.PMSystems.*;
import com.PMSystems.logger.*;


/**
 * This class is used to get the PrimaryKeys for different tables
 * in the database. This class insures that there will be atleast
 * one PrimaryKey in it, even if you set bucketSize=0 and estimatedKeys=0
 *
 * BucketSize is DEFAULT to value(normally 50) greater than zero. Its
 * value remains to DEFAULT if you give bucketSize greater than
 * estimatedKeys or less than zero.
 *
 * Bucket refills automatically when it is out of primary keys.
 *
 * @version         1.0       28 March 2005
 * @author          Syed Ahmad Suhaib
 *
 * History
 * 28 March 2005    Syed Ahmad Suhaib        Initial Development of Class.
 */
public class PKManager implements java.io.Serializable {

    private String tableName="";
    /**
     * The amount by which the PKManager automatically generates new PrimaryKeys.
     * If not given it DEFAULT to value greater than zero(normally 50).
     */
    private int bucketSize=PMSDefinitions.DEFAULT_BUCKET_SIZE;
    // The first PrimaryKey
    private long firstKey;
    // The current PrimaryKey
    private long currentKey;
    // The last PrimaryKey
    private long lastKey;

    //Estimated required PrimaryKeys.
    private int estimatedKeysRequired;

    //Thread Monitor.
    private static final Object monitor = new Object();

    /**
     * Constructor
     */
    public PKManager(String tableName, int estimatedKeysRequired, int bucketSize) {
        this.tableName = tableName;
        this.estimatedKeysRequired = (estimatedKeysRequired>0)? estimatedKeysRequired: 1;

        //Bucket size can't be Zero or Greater than the estimated required Keys.
        if(bucketSize>0 && bucketSize<=estimatedKeysRequired)
            this.bucketSize = bucketSize;

        //Bucket size can't be greater than the estimated required Keys.
        else if(bucketSize>0)
            this.bucketSize = (estimatedKeysRequired<PMSDefinitions.DEFAULT_BUCKET_SIZE)?
                estimatedKeysRequired: PMSDefinitions.DEFAULT_BUCKET_SIZE;

        System.out.println("PKManager instanciated with tableName:"+ this.tableName
                           + ", estimatedKeys: "+this.estimatedKeysRequired+", BucketSize: "
                           + this.bucketSize);

        //Getting ready to take requests for PKs.
        reFillBucket();
    }

    /**
     *
     * @param tableName
     * @param estimatedKeysRequired
     */
    public PKManager(String tableName, int estimatedKeysRequired) {
        this(tableName, estimatedKeysRequired, ((estimatedKeysRequired>PMSDefinitions.DEFAULT_BUCKET_SIZE)?
                                                PMSDefinitions.DEFAULT_BUCKET_SIZE: estimatedKeysRequired));
    }

    /**
     *
     * @param tableName
     */
    public PKManager(String tableName) {
        this(tableName, 1, 1);
    }

    /**
     * Re-fills the bucket according to data given.
     */
    private synchronized void reFillBucket() {

        synchronized(monitor) {
            //First valid PK.
            firstKey = AutoNumberFactory.getNextNumber(this.tableName, this.bucketSize).longValue();
        }
        currentKey = firstKey;

        //LastKey is excluded.
        lastKey = (firstKey+this.bucketSize);
    }

    /**
     *
     * @return
     */
    public Long getPrimaryKey() {

        if(this.currentKey>0) {

            //Checking if we are out of PKs or not. LastKey is excluded.
            if(this.currentKey >= this.lastKey) {
                reFillBucket();
            }

            Long pk = new Long(this.currentKey);
            this.currentKey = this.currentKey+1;
            return pk;
        }
        WebServerLogger.getLogger().log(new LogEntry("PKManager","getPrimaryKey()","Returning null, [DATA] tableName: "
            + this.tableName+", estimatedPKs: "+this.estimatedKeysRequired
            + ", bucketSize: "+this.bucketSize));

        System.out.println("=== Returning null from PKManager.getPrimaryKey() ===");
        return null;
    }

    /**
     *
     * @return
     */
    public static String getHashDataKey(){
        return ""+(new PKManager("HashedData")).getPrimaryKey().longValue();
    }

    /**
     *
     * @return
     */
    public static String getLogHashDataKey(){
        return ""+(new PKManager("LogHashedData")).getPrimaryKey().longValue();
    }

}//class