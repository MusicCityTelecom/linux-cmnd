/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import java.io.Serializable;
import org.quartz.utils.DirtyFlagMap;

public class StringKeyDirtyFlagMap
extends DirtyFlagMap<String, Object> {
    static final long serialVersionUID = -9076749120524952280L;
    private boolean allowsTransientData = false;

    public StringKeyDirtyFlagMap() {
    }

    public StringKeyDirtyFlagMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringKeyDirtyFlagMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.getWrappedMap().hashCode();
    }

    public String[] getKeys() {
        return this.keySet().toArray(new String[this.size()]);
    }

    public void setAllowsTransientData(boolean allowsTransientData) {
        if (this.containsTransientData() && !allowsTransientData) {
            throw new IllegalStateException("Cannot set property 'allowsTransientData' to 'false' when data map contains non-serializable objects.");
        }
        this.allowsTransientData = allowsTransientData;
    }

    public boolean getAllowsTransientData() {
        return this.allowsTransientData;
    }

    public boolean containsTransientData() {
        if (!this.getAllowsTransientData()) {
            return false;
        }
        String[] keys = this.getKeys();
        for (int i = 0; i < keys.length; ++i) {
            Object o = super.get(keys[i]);
            if (o instanceof Serializable) continue;
            return true;
        }
        return false;
    }

    public void removeTransientData() {
        if (!this.getAllowsTransientData()) {
            return;
        }
        String[] keys = this.getKeys();
        for (int i = 0; i < keys.length; ++i) {
            Object o = super.get(keys[i]);
            if (o instanceof Serializable) continue;
            this.remove(keys[i]);
        }
    }

    @Override
    public void put(String key, int value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, long value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, float value) {
        super.put(key, Float.valueOf(value));
    }

    @Override
    public void put(String key, double value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, boolean value) {
        super.put(key, value);
    }

    @Override
    public void put(String key, char value) {
        super.put(key, Character.valueOf(value));
    }

    @Override
    public void put(String key, String value) {
        super.put(key, value);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    public int getInt(String key) {
        Object obj = this.get(key);
        try {
            if (obj instanceof Integer) {
                return (Integer)obj;
            }
            return Integer.parseInt((String)obj);
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not an Integer.");
        }
    }

    public long getLong(String key) {
        Object obj = this.get(key);
        try {
            if (obj instanceof Long) {
                return (Long)obj;
            }
            return Long.parseLong((String)obj);
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not a Long.");
        }
    }

    public float getFloat(String key) {
        Object obj = this.get(key);
        try {
            if (obj instanceof Float) {
                return ((Float)obj).floatValue();
            }
            return Float.parseFloat((String)obj);
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not a Float.");
        }
    }

    public double getDouble(String key) {
        Object obj = this.get(key);
        try {
            if (obj instanceof Double) {
                return (Double)obj;
            }
            return Double.parseDouble((String)obj);
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not a Double.");
        }
    }

    public boolean getBoolean(String key) {
        Object obj = this.get(key);
        try {
            if (obj instanceof Boolean) {
                return (Boolean)obj;
            }
            return Boolean.parseBoolean((String)obj);
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not a Boolean.");
        }
    }

    public char getChar(String key) {
        Object obj = this.get(key);
        try {
            if (obj instanceof Character) {
                return ((Character)obj).charValue();
            }
            return ((String)obj).charAt(0);
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not a Character.");
        }
    }

    public String getString(String key) {
        Object obj = this.get(key);
        try {
            return (String)obj;
        }
        catch (Exception e) {
            throw new ClassCastException("Identified object is not a String.");
        }
    }
}

