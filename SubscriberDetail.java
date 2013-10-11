package com.PMSystems.util;

public class SubscriberDetail implements java.io.Serializable {

    private String name = "";
    private String value= "";

    public SubscriberDetail(String n, String v) {
        name = Default.toDefault(n);
        value = Default.toDefault(v);
    }

    public String getName() { return name; }
    public String getValue() { return value; }
    public void setName(String str) { name = Default.toDefault(str); }
    public void setValue(String str) { value = Default.toDefault(str); }
}
