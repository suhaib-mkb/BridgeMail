
package com.PMSystems.util;

import java.util.*;
import com.PMSystems.*;
import com.PMSystems.dbbeans.*;


public class UserInfo extends UserDetailBean implements java.io.Serializable {

    private String userID;
    private String userKey="";

    private long customerNumber;
    private String customerLogo;
    private String customerName;
    private String firstName;
    private String lastName;
    private String phone;
    private String address1;
    private String address2;
    private String email;
    private String fromEmail;
    private String userRole;
    private String url;
    private String userLayout;
    private String formLayout;
    private Vector locks;
    private Vector roles = null;
    private String subscriberProfileLayout;
    private String sendAutoReplyEmail;
    private String autoReplyEmailFromAddress;
    private String isSupressShared="N";
    private String replyToAddress = "";
    private String alertEmail;
    private String senderName="";

    private String webAddress="";
    private String webTrack = "Y";
    private String emailCategory = PMSDefinitions.EMAIL_CATEGORY_GOOD;

    private Vector userAppsVec = new Vector();

    /**
     * Constructor
     */
    public UserInfo() {

        userID = "";
        firstName = "";
        lastName = "";
        phone = "";
        address1 = "";
        address2 = "";
        email = "";
        fromEmail = "";
        userRole = "";
        url = "";
        userLayout = "";
        formLayout = "";
        locks = new Vector();
        customerLogo = PMSDefinitions.DEFAULT_LOGO;
    }//end Costructor


    public String getUserID() {
        return userID;
    }//end getFirstName()

    public long getCustomerNumber() {
        return customerNumber;
    }//end getCustomerNumber()

    public String getCustomerLogo() {
        return customerLogo;
    }//end getCustomerLogo()

    public String getFirstName() {
        return firstName;
    }//end getFirstName()

    public String getLastName() {
        return lastName;
    }//end getLastName()

    public String getPhone() {
        return phone;
    }//end getPhone()

    public String getAddress1() {
        return address1;
    }//end getAddress1()

    public String getAddress2() {
        return address2;
    }//end getAddress2()

    public String getEmail() {
        return email;
    }//end getEmail()

    public String getFromEmail() {
        return fromEmail;
    }//end getEmail()

    public String getUserRole() {
        return userRole;
    }//end getUserRole()

    public String getURL() {
        return url;
    }//end getURL()

    public String getUserLayout() {
        return userLayout;
    }//end getUserLayout()

    public String getFormLayout() {
        return formLayout;
    }//end getFormLayout()

    public Vector getRoles() {
        return roles;
    }//end getUserRole()

    public String getAlertEmail() {//Adeel
        return alertEmail;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }//end setFirstName()

    public void setCustomerNumber(long aCustomerNumber) {
        customerNumber = aCustomerNumber;
    }//end setCustomerNumber()

    public String getSenderName() {return senderName;}
    public void setSenderName(String str) { senderName = Default.toDefault(str).trim(); }

    public String getWebAddress() {return webAddress;}
    public void setWebAddress(String str) { webAddress = Default.toDefault(str).trim(); }

    public void setCustomerLogo(String aCustomerLogo) {
        customerLogo = aCustomerLogo;
    }//end setCustomerLogo()

    public void setFirstName(String aFirstName) {
        firstName = aFirstName;
    }//end setFirstName()

    public void setLastName(String aLastName) {
        lastName = aLastName;
    }//end setLastName()

    public void setPhone(String aPhone) {
        phone = aPhone;
    }//end setPhone()

    public void setAddress1(String aAddress1) {
        address1 = aAddress1;
    }//end setAddress1()

    public void setAddress2(String aAddress2) {
        address2 = aAddress2;
    }//end setAddress2()

    public void setEmail(String aEmail) {
        email = aEmail;
    }//end setEmail()

    public void setFromEmail(String aFromEmail) {
        fromEmail = aFromEmail;
    }//end setEmail()

    public void setUserRole(String aUserRole) {
        userRole = aUserRole;
    }//end setUserRole()

    public void setURL(String aURL) {
        url = aURL;
    }//end setURL()

    public void setUserLayout(String aUserLayout) {
        userLayout = aUserLayout;
    }//end setUserLayout()

    public void setFormLayout(String aFormLayout) {
        formLayout = aFormLayout;
    }//end setFormLayout()

    public void setRoles(Vector roles) {
        this.roles = roles;
    }//end setUserRole()

    public void setAlertEmail(String alertEmail) {//Adeel
        this.alertEmail = Default.toDefault(alertEmail);
    }

    public void addLock(Lock lock) {
        if(! hasLock(lock.getLockType(), lock.getRecordID())) {
            locks.addElement(lock);
        }
    }

    public boolean hasLock(int lockType, String recordID) {
        for (int i = 0; i < locks.size(); i++) {
            Lock lock = (Lock)locks.elementAt(i);
            if(lock.getLockType() == lockType && recordID.equals(lock.getRecordID())) {
                return true;
            }
        }
        return false;
    }

    public void removeLock(int lockType, String recordID) {
        for (int i = 0; i < locks.size(); i++) {
            Lock lock = (Lock)locks.elementAt(i);
            if(lock.getLockType() == lockType && recordID.equals(lock.getRecordID())) {
                locks.remove(i);
                i--;
            }
        }
    }

    public void removeAllLocks() {
        locks.clear();
    }

    public Lock[] getLocks() {
        Lock[] lks = new Lock[locks.size()];
        for (int i = 0; i < lks.length; i++) {
            lks[i] = (Lock)locks.elementAt(i);
        }
        return lks;
    }
    public void setSubscriberProfileLayout(String subscriberProfileLayout) {
        this.subscriberProfileLayout = subscriberProfileLayout;
    }
    public String getSubscriberProfileLayout() {
        return subscriberProfileLayout;
    }
    public void setSendAutoReplyEmail(String sendAutoReplyEmail) {
        if(sendAutoReplyEmail != null )
            this.sendAutoReplyEmail = sendAutoReplyEmail;
        else
            this.sendAutoReplyEmail = "N";
    }
    public String getSendAutoReplyEmail() {
        return sendAutoReplyEmail;
    }
    public void setAutoReplyEmailFromAddress(String autoReplyEmailFromAddress) {
        this.autoReplyEmailFromAddress = autoReplyEmailFromAddress;
    }
    public String getAutoReplyEmailFromAddress() {
        return autoReplyEmailFromAddress;
    }
    public void setIsSupressShared(String isSupressShared) {
        this.isSupressShared = (isSupressShared==null||isSupressShared.equals(""))? "N":isSupressShared;
    }
    public String getIsSupressShared() {
        return this.isSupressShared;
    }

    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }
    public String getReplyToAddress() {
        return this.replyToAddress;
    }

    public void setUserAppSubs(Vector vec) { this.userAppsVec=vec; }
    public Vector getUserAppSubs() { return this.userAppsVec; }

    public void setCustomerName(String str) { this.customerName=Default.toDefault(str); }
    public String getCustomerName() { return this.customerName; }

    public void setWebTrack(String str) {
        this.webTrack = Default.toDefault(str);
        webTrack = webTrack.equals("Y")? "Y": "N";
    }

    public String getWebTrack() { return this.webTrack; }
    public boolean isWebTrack() { return this.webTrack.equalsIgnoreCase("Y"); }

    public void setUserKey(String key) { this.userKey = Default.toDefault(key); }
    public String getUserKey() { return this.userKey; }

    public String getEmailCategory() {
        return emailCategory;
    }
    public void setEmailCategory(String category){
        emailCategory = category;
    }

}//class

