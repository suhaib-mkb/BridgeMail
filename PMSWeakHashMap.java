package com.PMSystems.util;

import java.util.*;

public class PMSWeakHashMap {
    private HashMap hash;
    private boolean run = false;
    private Thread thread = null;
    private long inactivityTime;
    private long pollingTime = 10000;

    /**
     * Constructs a new, empty <code>WeakHashMap</code> with the default
     * initial capacity and the default load factor, which is
     * <code>0.75</code>.
     */
    public PMSWeakHashMap() {
        hash = new HashMap();
        inactivityTime = 60l*1000l;
    }

    public PMSWeakHashMap(int inactivityTime) {
        hash = new HashMap();
        this.inactivityTime = ((long)inactivityTime) * 1000l;
    }

    public void setInactivityTime(int inactivityTime) {
        this.inactivityTime = ((long)inactivityTime) * 1000l;
    }

    public void setPollingTime(long pollingTime) {
        this.pollingTime = pollingTime;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * <strong>Note:</strong> <em>In contrast with most implementations of the
     * <code>Map</code> interface, the time required by this operation is
     * linear in the size of the map.</em>
     *
     * @return int
     */
    public int size() {
        return hash.size();
    }

    /**
     * Returns <code>true</code> if this map contains no key-value mappings.
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return hash.isEmpty();
    }

    /**
     * Returns <code>true</code> if this map contains a mapping for the
     * specified key.
     *
     * @param   key   The key whose presence in this map is to be tested
     * @return  boolean
     */
    public boolean containsKey(Object key) {
        return hash.containsKey(key);
    }


    /* -- Lookup and modification operations -- */

    /**
     * Returns the value to which this map maps the specified <code>key</code>.
     * If this map does not contain a value for this key, then return
     * <code>null</code>.
     *
     * @param  key  The key whose associated value, if any, is to be returned
     * @return object
     */
    public Object get(Object key) {
        WeakHashEntry entry = (WeakHashEntry)hash.get(key);
        if(entry==null)
            return null;

        entry.setInsertedTime(System.currentTimeMillis());
        return entry.getObject();
    }

    /**
     * Updates this map so that the given <code>key</code> maps to the given
     * <code>value</code>.  If the map previously contained a mapping for
     * <code>key</code> then that mapping is replaced and the previous value is
     * returned.
     *
     * @param  key    The key that is to be mapped to the given
     *                <code>value</code>
     * @param  value  The value to which the given <code>key</code> is to be
     *                mapped
     *
     * @return  The previous value to which this key was mapped, or
     *          <code>null</code> if if there was no mapping for the key
     */
    public Object put(Object key, Object value) {
        if(!run) {
            run = true;
            thread = new Thread(new WeakHashThread());
            thread.start();
        }
        return hash.put(key, new WeakHashEntry(System.currentTimeMillis(), value));
    }

    /**
     * Removes the mapping for the given <code>key</code> from this map, if
     * present.
     *
     * @param  key  The key whose mapping is to be removed
     *
     * @return  The value to which this key was mapped, or <code>null</code> if
     *          there was no mapping for the key
     */
    public Object remove(Object key) {
        WeakHashEntry entry = (WeakHashEntry)hash.remove(key);
        if(entry == null) {
            return null;
        }
        if(hash.size()==0) {
            run = false;
        }
        return entry.getObject();
    }

    /**
     * Inner Class WeakHashThread
     */
    private class WeakHashThread implements Runnable {

        public void run() {
            Set set = null;
            WeakHashEntry entry = null;
            System.out.println("Runing...");
            while(run) {
                try {
                    set = hash.keySet();
                    Object[] keys = set.toArray();
                    for(int i=0; i < keys.length; i++) {
                        entry = (WeakHashEntry)hash.get(keys[i]);
                        if(System.currentTimeMillis() - entry.getInsertedTime() > inactivityTime) {
                            System.out.println(keys[i]+" removal time "+new Date(System.currentTimeMillis()));
                            remove(keys[i]);
                        }
                    }//end while

                    Thread.sleep(pollingTime);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Stopped...");
        } // end run
    } // end class

    /**
     * Inner Class WeakHashEntry
     */
    private class WeakHashEntry {
        private long insertedTime;
        private Object object;

        WeakHashEntry(long insertedTime, Object aObject) {
            setInsertedTime(insertedTime);
            object = aObject;
        }

        public long getInsertedTime() {
            return insertedTime;
        }

        public Object getObject() {
            return object;
        }

        public void setInsertedTime(long insertedTime) {
            this.insertedTime = insertedTime;
        }

        public void setObject(Object aObject) {
            object = aObject;
        }
    }

    public static void main(String[] args) {
        PMSWeakHashMap map = new PMSWeakHashMap(1);
        map.setPollingTime(100);
        try {
            map.put("zee", "Zeeshan");
            Thread.sleep(100);
            map.put("manee", "Imran");
            Thread.sleep(100);
            map.put("nomee", "Naeem");
            Thread.sleep(100);
            map.put("fedo", "Farid");
            Thread.sleep(100);
            map.put("czar", "Tariq");
            Thread.sleep(4000);
            map.get("zee");
            Thread.sleep(1000);
            map.remove("fedo");

            Thread.sleep(10000);
            map.put("something", "a thing");
            Thread.sleep(10000);
            map.remove("something");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}