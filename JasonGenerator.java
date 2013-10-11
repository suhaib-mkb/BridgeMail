package com.PMSystems.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JasonGenerator {

    /**
     *
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2011</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class Container {
        private String    key;
        private Hashtable hashtable;

        /**
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * @param key the key to set
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * @return the hashtable
         */
        public Hashtable getHashtable() {
            return hashtable;
        }

        /**
         * @param hashtable the hashtable to set
         */
        public void setHashtable(Hashtable hashtable) {
            this.hashtable = hashtable;
        }
    }

    /**
     *
     * @param hashtable
     * @return
     */
    public static String getJSONText(Hashtable hashtable) {

        List hashTableList = new ArrayList();
        StringBuffer jsonTextBuffer = new StringBuffer("{");

        boolean hashTableFlag = false;
        if(hashtable!=null) {

            Iterator iterator = hashtable.keySet().iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Object valueObject = hashtable.get(key);

                if ( valueObject instanceof Hashtable ) {
                    Container container = new JasonGenerator().new Container();
                    container.setKey(key);
                    container.setHashtable((Hashtable)valueObject);
                    hashTableList.add(container);
                    hashTableFlag = true;
                } else {
                    String text = "\"" + key + "\": \"" + valueObject + "\",";
                    jsonTextBuffer.append(text);
                }
            }
        }

        if(hashTableFlag==false && jsonTextBuffer.length()>0 && jsonTextBuffer.charAt(jsonTextBuffer.length()-1)==',') {
            jsonTextBuffer.deleteCharAt(jsonTextBuffer.length()-1);
        }

        for (int index=0; index<hashTableList.size(); index++) {

            Container container = (Container)hashTableList.get(index);
            String text = traverseHashTable(container.getKey(),container.getHashtable());
            if(index < hashTableList.size()-1) {
                text = text+",";
            }
            jsonTextBuffer.append(text);
        }
        jsonTextBuffer.append("}");
        return jsonTextBuffer.toString();
    }

    /**
     *
     * @param parentKey
     * @param hashtable
     * @return
     */
    private static String traverseHashTable(String parentKey, Hashtable hashtable) {

        List hashTableList = new ArrayList();
        StringBuffer jsonTextBuffer = new StringBuffer("");

        if (hashtable!=null) {

            jsonTextBuffer.append("\""+parentKey+"\"");
            jsonTextBuffer.append(":[{");
            Iterator iterator = hashtable.keySet().iterator();
            boolean hashTableFlag = false;

            while(iterator.hasNext()) {

                String key = (String) iterator.next();
                Object valueObject = hashtable.get(key);

                if (valueObject instanceof Hashtable) {
                    Container container = new JasonGenerator().new Container();
                    container.setKey(key);
                    container.setHashtable((Hashtable)valueObject);
                    hashTableList.add(container);
                    hashTableFlag = true;

                } else {
                    String text = "\"" + key + "\": \"" + valueObject + "\",";
                    jsonTextBuffer.append(text);
                }
            }
            if(hashTableFlag==false && jsonTextBuffer.length()>0 && jsonTextBuffer.charAt(jsonTextBuffer.length()-1)==',') {
                jsonTextBuffer.deleteCharAt(jsonTextBuffer.length()-1);
            }

            for (int index=0; index<hashTableList.size(); index++) {
                Container container = (Container)hashTableList.get(index);
                String text = traverseHashTable(container.getKey(),container.getHashtable());
                if(index < hashTableList.size()-1) {
                    text = text+",";
                }
                jsonTextBuffer.append(text);
            }
            jsonTextBuffer.append("}]");

        }
        return jsonTextBuffer.toString();
    }

/*    public static void main(String [] args) {

        Hashtable wfTable = new Hashtable();
        Hashtable stepTable = new Hashtable();
        Hashtable optionTable = new Hashtable();
        optionTable.put("Sent", "400");
        optionTable.put("Pending", "225");
        stepTable.put("optionId22", optionTable);
        optionTable = new Hashtable();
        optionTable.put("Sent", "600");
        optionTable.put("Opened", "300");
        stepTable.put("optionId33", optionTable);

        wfTable.put("stepID_1485", stepTable);
        wfTable.put("workflowId", "3");
        wfTable.put("workflowName", "ChartSummaryWorkflow");

        String jsonText = getJSONText(wfTable);
        System.out.println(jsonText);
    }
*/
}
