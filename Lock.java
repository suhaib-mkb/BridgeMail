package com.PMSystems.util;

import java.io.*;

/**
 * @version         1.0            06 Aug 2002
 * @author          Usman
 *
 * 06 Aug 2002      Usman          Initial Development of Class
 */

public class Lock implements Serializable {

    public static final int CAMPAIGN_LOCK = 1;
    public static final int LIST_LOCK = 2;

    private final int lockType;
    private final String recordID;

    public Lock() {
        this.lockType = 0;
        this.recordID = "";
    }

    public Lock(int lockType, String recordID) {
        this.lockType = lockType;
        this.recordID = recordID;
    }

    public int getLockType() {
        return lockType;
    }

    public String getRecordID() {
        return recordID;
    }
}