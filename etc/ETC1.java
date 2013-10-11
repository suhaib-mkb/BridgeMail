package com.PMSystems.util;

import com.PMSystems.dbbeans.*;

import java.sql.*;
import java.util.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Makesbridge </p>
 * @author not attributable
 * @version 1.0
 */
public class SubscriberInfo implements java.io.Serializable {

    private long subscriberNumber;
    private String userId="";

    private String email="";
    private String emailType="H";
    private String firstName="";
    private String middleName="";
    private String lastName="";

    private Timestamp subscribeDate;
    private Timestamp unsubscribedDate;
    private Timestamp birthDate;

    private String gender="";
    private String maritalStatus="";
    private String occupation="";
    private String jobStatus="";
    private String householdIncome="";
    private String educationLevel="";
    private String addressLine1="";
    private String addressLine2="";
    private String city="";
    private String stateCode="";
    private String zip="";
    private String countryCode="";
    private String telephone="";
    private String company="";
    private String industry="";
    private String source="";
    private String salesRep="";
    private String salesStatus="";
    private String areaCode="";
    private String title="";
    private String ipAddress="";

    private Vector customFields=new Vector();

    private String linkID="";

    /*for bounce subsciber data*/
    private String bounceCategory="";
    private long campaignNumber;
    private String campaignName="";

    private String status = "S";//default subscribed
    private String supress = "U";//default un-supress
    private String active = "Y";

    private Timestamp hotListDate;
    private int clickCount=0;
    private int tellFriendCount;

    private String listName="";
    private long listNumber=0;
    private long articleNumber;
    private int webVisitCount;

    //Added by Shahbaz
    private String conLeadId="";

    private SubscriberSummaryBean  subSummaryBean = new SubscriberSummaryBean();
    private Vector crmDataBeanVector = new Vector();
    private String hot="N";
    /**
     * Constructor
     */
    public SubscriberInfo() {
        emailType = "H";
    }//end Constructor

    public void setUserID(String str) { this.userId = Default.toDefault(str); }
    public String getUserID() { return this.userId; }

    public void setStatus(String str) { status = Default.toDefault(str); }
    public void setSupress(String str) { supress = Default.toDefault(str); }
    public String getStatus() { return status; }
    public String getSupress() { return supress; }

    public void setListName(String listName) {
        this.listName = Default.toDefault(listName);
    }

    public void setSubscribeDate(Timestamp subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    public void setEmail(String email) {
        this.email = Default.toDefault(email);
    }

    public void setEmailType(String emailType) {
        this.emailType = Default.toDefault(emailType);
    }

    public void setFirstName(String firstName) {
        this.firstName = Default.toDefault(firstName);
    }

    public void setMiddleName(String middleName) {
        this.middleName = Default.toDefault(middleName);
    }

    public void setLastName(String lastName) {
        this.lastName = Default.toDefault(lastName);
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = Default.toDefault(gender);
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = Default.toDefault(maritalStatus);
    }

    public void setOccupation(String occupation) {
        this.occupation = Default.toDefault(occupation);
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = Default.toDefault(jobStatus);
    }

    public void setHouseholdIncome(String householdIncome) {
        this.householdIncome = Default.toDefault(householdIncome);
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = Default.toDefault(educationLevel);
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = Default.toDefault(addressLine1);
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = Default.toDefault(addressLine2);
    }

    public void setCity(String city) {
        this.city = Default.toDefault(city);
    }

    public void setStateCode(String stateCode) {
        this.stateCode = Default.toDefault(stateCode);
    }

    public void setZip(String zip) {
        this.zip = Default.toDefault(zip);
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = Default.toDefault(countryCode);
    }

    public void setTelephone(String telephone) {
        this.telephone = Default.toDefault(telephone);
    }

    public void setCompany(String company) {
        this.company = Default.toDefault(company);
    }

    public void setIndustry(String industry) {
        this.industry = Default.toDefault(industry);
    }

    public void setSource(String source) {
        this.source = Default.toDefault(source);
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = Default.toDefault(salesRep);
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = Default.toDefault(salesStatus);
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = Default.toDefault(areaCode);
    }

    public void setLinkID(String linkID){
        this.linkID = Default.toDefault(linkID);
    }

    public void setCustomFields(Vector customFields) {
        this.customFields = customFields;
    }

    public void setHotListDate(Timestamp addedDate){
        this.hotListDate = addedDate;
    }

    public void setClickCount(int clickCount){
        this.clickCount = clickCount;
    }

    public void setCRMDataBeanVector(Vector vec){
        this.crmDataBeanVector = vec;
    }
    //getter methods

    public String getListName() {
        return listName;
    }

    public Timestamp getSubscribeDate() {
        return subscribeDate;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailType() {
        return emailType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public String getHouseholdIncome() {
        return householdIncome;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getZip() {
        return zip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCompany() {
        return company;
    }

    public String getIndustry() {
        return industry;
    }

    public String getSource() {
        return source;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public String getSalesStatus() {
        return salesStatus;
    }
    public String getAreaCode(){
        return areaCode;
    }
    public String getLinkID(){
        return linkID;
    }

    public Vector getCustomFields() {
        return customFields;
    }
    public String getBounceCategory() {
         return bounceCategory;
     }
     public void setBounceCategory(String str) {
         this.bounceCategory = Default.toDefault(str);
     }
     public long getCampaignNumber() {
         return campaignNumber;
     }
     public void setCampaignNumber(long campaignNumber) {
         this.campaignNumber = campaignNumber;
     }
     public String getCampaignName() {
         return campaignName;
     }
     public Timestamp getHotListDate() {
         return this.hotListDate;
     }

     public int getClickCount(){
         return this.clickCount;
     }

     public void setCampaignName(String str) {
         this.campaignName = Default.toDefault(str);
     }

     public void setConLeadId(String str) {
         this.conLeadId = Default.toDefault(str);
     }

     public String getConLeadId() {
         return conLeadId;
     }

     public void setActive(String str){
         this.active = str;
     }
     public String getActive(){
         return this.active;
     }

     public void setSubscriberSummaryBean(SubscriberSummaryBean subSummaryBean){
         this.subSummaryBean = subSummaryBean;
     }

     public SubscriberSummaryBean getSubscriberSummaryBean(){
         return this.subSummaryBean;
     }

     public void setHot(String hot){
         this.hot = hot;
     }

     public String getHot(){
         return this.hot;
     }

     public long getArticleNumber() { return this.articleNumber; }
     public void setArticleNumber(long art) { this.articleNumber = art; }

     public int getWebVisitCount() { return this.webVisitCount; }
     public void setWebVisitCount(int c) { this.webVisitCount = c; }

     public void setSubscriberNumber(long l) {this.subscriberNumber=l;}
     public long getSubscriberNumber() {return this.subscriberNumber;}

     public void setListNumber(long listNumber) {
         this.listNumber=listNumber;
     }
     public long getListNumber() {
         return listNumber;
     }
     public void setTitle(String title) {
         this.title=Default.toDefault(title);
     }
     public String getTitle() {
         return title;
     }
     public void setUnsubscribedDate(Timestamp unsubscribedDate) {
         this.unsubscribedDate=unsubscribedDate;
     }
     public Timestamp getUnsubscribedDate() {
         return unsubscribedDate;
     }
     public void setIpAddress(String ipAddress) {
         this.ipAddress=Default.toDefault(ipAddress);
     }
     public String getIpAddress() {
         return ipAddress;
     }
     public void setTellFriendCount(int tellFriendCount) {
         this.tellFriendCount=tellFriendCount;
     }
     public int getTellFriendCount() {
         return tellFriendCount;
     }
     public Vector getCRMDataBeanVector(){
         return this.crmDataBeanVector;
     }
     /**
      *
      * @param name
      * @return
      */
     public String getCustomFieldValue(String name) {

         name = Default.toDefault(name);
         for(int i=0; i<customFields.size(); i++) {
             SubscriberDetail detail = (SubscriberDetail)customFields.get(i);
             if(detail.getName().equalsIgnoreCase(name))
                 return detail.getValue();
         }
         return null;
     }
 }