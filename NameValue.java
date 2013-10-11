package com.PMSystems.util;

public class NameValue implements Comparable, java.io.Serializable {

    private String name = "";
    private String value= "";

    public NameValue(String n, String v) {
        name = n;
        value = v;
    }

    public int compareTo(Object o) {

      if(o instanceof NameValue) {
          if(toInt(this.name)!=Integer.MIN_VALUE && toInt(((NameValue)o).getName())!=Integer.MIN_VALUE) {

              if (Integer.parseInt(this.name) ==
                  Integer.parseInt( ( (NameValue) o).getName()))
                  return 0;
              else if (Integer.parseInt(this.name) >
                       Integer.parseInt( ( (NameValue) o).getName()))
                  return 1;
              else if (Integer.parseInt(this.name) <
                       Integer.parseInt( ( (NameValue) o).getName()))
                  return -1;
          } else {
              return this.name.compareToIgnoreCase(((NameValue)o).getName());
          }
      }
      return 0;
    }

    public boolean equals(Object obj) {
        if(obj instanceof NameValue) {
            return this.name.equalsIgnoreCase(((NameValue)obj).getName());
        } else
            return false;
    }

    public int tmpCompareTo(Object o) {

        if(o instanceof NameValue) {
            return this.name.compareTo(((NameValue)o).getName());
        }
        return 0;
    }

    public String getName() { return name; }
    public String getValue() { return value; }
    public void setName(String str) { name = Default.toDefault(str); }
    public void setValue(String str) { value = Default.toDefault(str); }

    private int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return Integer.MIN_VALUE;
        }
    }

}