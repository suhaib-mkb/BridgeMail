package com.PMSystems.util;

import java.util.*;

public class GuidGen {

    private static Properties p = null;

    static {
        Properties p = System.getProperties();
        if (p.getProperty("java.rmi.server.randomIDs")== null ||
            !p.getProperty("java.rmi.server.randomIDs").equals("true"))
            p.setProperty("java.rmi.server.randomIDs","true");
    }


    public static synchronized String generateGuid() {
        try {
            Object obj = new Object();
            long l = obj.hashCode()+Calendar.getInstance().getTime().getTime();
            System.out.println("---------- GUID: "+l);
/*
            ObjID id = new ObjID();
            String s = "" + id;
            System.out.println("1# obj.hashCode(): "+s);
            System.out.println("2# : "+s.indexOf(","));
            System.out.println("3# : "+s.substring(s.indexOf(",")+1, s.length()));

            StringTokenizer st = new StringTokenizer(s.substring(s.indexOf(",")+1, s.length())," ]");
            String tok = st.nextToken();
            System.out.println("Token : "+tok);
            Long l = new Long(tok);
            return s.substring(1,7)+s.substring(8,18)+s.substring(20,24)
                +l.toHexString(l.longValue());
*/
         return PMSEncoding.encode(""+l);

        } catch (Exception sob) {
            System.out.println("java.rmi.server.randomIDs not set to true");
            return ""+"asdfasd".hashCode();
        }
    }


    public static void main(String args[]) {

        HashSet set = new HashSet();
        set.add("");
        set.add(null);
        for (int i=0; i < 100000; i++) {
            String str = GuidGen.generateGuid();
            if (!set.add(str)) {
                System.out.println("Already Generated this GUID : "+str);
            }
            if((i%1000)==0)
                System.out.println("Successfully Generated first "+i+" GUIDs.");
        }
    }

}
