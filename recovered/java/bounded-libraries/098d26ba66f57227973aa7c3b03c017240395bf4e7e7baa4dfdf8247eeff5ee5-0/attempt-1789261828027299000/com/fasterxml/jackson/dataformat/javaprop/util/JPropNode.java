/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.dataformat.javaprop.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class JPropNode {
    protected String _value;
    protected TreeMap<Integer, JPropNode> _byIndex;
    protected Map<String, JPropNode> _byName;
    protected boolean _hasContents = false;

    public JPropNode setValue(String v) {
        this._value = v;
        return this;
    }

    public JPropNode addByIndex(int index) {
        Integer key;
        JPropNode n;
        if (this._byName != null) {
            return this.addByName(String.valueOf(index));
        }
        this._hasContents = true;
        if (this._byIndex == null) {
            this._byIndex = new TreeMap();
        }
        if ((n = this._byIndex.get(key = Integer.valueOf(index))) == null) {
            n = new JPropNode();
            this._byIndex.put(key, n);
        }
        return n;
    }

    public JPropNode addByName(String name) {
        this._hasContents = true;
        if (this._byIndex != null) {
            if (this._byName == null) {
                this._byName = new LinkedHashMap<String, JPropNode>();
            }
            for (Map.Entry<Integer, JPropNode> entry : this._byIndex.entrySet()) {
                this._byName.put(entry.getKey().toString(), entry.getValue());
            }
            this._byIndex = null;
        }
        if (this._byName == null) {
            this._byName = new LinkedHashMap<String, JPropNode>();
        } else {
            JPropNode old = this._byName.get(name);
            if (old != null) {
                return old;
            }
        }
        JPropNode result = new JPropNode();
        this._byName.put(name, result);
        return result;
    }

    public boolean isLeaf() {
        return !this._hasContents && this._value != null;
    }

    public boolean isArray() {
        return this._byIndex != null;
    }

    public String getValue() {
        return this._value;
    }

    public Iterator<JPropNode> arrayContents() {
        return this._byIndex.values().iterator();
    }

    public Iterator<Map.Entry<String, JPropNode>> objectContents() {
        if (this._byName == null) {
            return Collections.emptyIterator();
        }
        return this._byName.entrySet().iterator();
    }

    public Object asRaw() {
        if (this.isArray()) {
            ArrayList<Object> result = new ArrayList<Object>();
            if (this._value != null) {
                result.add(this._value);
            }
            for (JPropNode v : this._byIndex.values()) {
                result.add(v.asRaw());
            }
            return result;
        }
        if (this._byName != null) {
            LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
            if (this._value != null) {
                result.put("", this._value);
            }
            for (Map.Entry<String, JPropNode> entry : this._byName.entrySet()) {
                result.put(entry.getKey(), entry.getValue().asRaw());
            }
            return result;
        }
        return this._value;
    }
}

