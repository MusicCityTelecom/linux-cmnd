/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;

public class PropertiesParser {
    Properties props = null;

    public PropertiesParser(Properties props) {
        this.props = props;
    }

    public Properties getUnderlyingProperties() {
        return this.props;
    }

    public String getStringProperty(String name) {
        return this.getStringProperty(name, null);
    }

    public String getStringProperty(String name, String def) {
        String val = this.props.getProperty(name, def);
        if (val == null) {
            return def;
        }
        return (val = val.trim()).length() == 0 ? def : val;
    }

    public String[] getStringArrayProperty(String name) {
        return this.getStringArrayProperty(name, null);
    }

    public String[] getStringArrayProperty(String name, String[] def) {
        String vals = this.getStringProperty(name);
        if (vals == null) {
            return def;
        }
        StringTokenizer stok = new StringTokenizer(vals, ",");
        ArrayList<String> strs = new ArrayList<String>();
        try {
            while (stok.hasMoreTokens()) {
                strs.add(stok.nextToken().trim());
            }
            return strs.toArray(new String[strs.size()]);
        }
        catch (Exception e) {
            return def;
        }
    }

    public boolean getBooleanProperty(String name) {
        return this.getBooleanProperty(name, false);
    }

    public boolean getBooleanProperty(String name, boolean def) {
        String val = this.getStringProperty(name);
        return val == null ? def : Boolean.valueOf(val);
    }

    public byte getByteProperty(String name) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }
        try {
            return Byte.parseByte(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public byte getByteProperty(String name, byte def) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            return def;
        }
        try {
            return Byte.parseByte(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public char getCharProperty(String name) {
        return this.getCharProperty(name, '\u0000');
    }

    public char getCharProperty(String name, char def) {
        String param = this.getStringProperty(name);
        return param == null ? def : param.charAt(0);
    }

    public double getDoubleProperty(String name) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }
        try {
            return Double.parseDouble(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public double getDoubleProperty(String name, double def) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            return def;
        }
        try {
            return Double.parseDouble(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public float getFloatProperty(String name) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }
        try {
            return Float.parseFloat(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public float getFloatProperty(String name, float def) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            return def;
        }
        try {
            return Float.parseFloat(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int getIntProperty(String name) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }
        try {
            return Integer.parseInt(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int getIntProperty(String name, int def) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            return def;
        }
        try {
            return Integer.parseInt(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int[] getIntArrayProperty(String name) throws NumberFormatException {
        return this.getIntArrayProperty(name, null);
    }

    public int[] getIntArrayProperty(String name, int[] def) throws NumberFormatException {
        String vals = this.getStringProperty(name);
        if (vals == null) {
            return def;
        }
        StringTokenizer stok = new StringTokenizer(vals, ",");
        ArrayList<Integer> ints = new ArrayList<Integer>();
        try {
            while (stok.hasMoreTokens()) {
                try {
                    ints.add(new Integer(stok.nextToken().trim()));
                }
                catch (NumberFormatException nfe) {
                    throw new NumberFormatException(" '" + vals + "'");
                }
            }
            int[] outInts = new int[ints.size()];
            for (int i = 0; i < ints.size(); ++i) {
                outInts[i] = (Integer)ints.get(i);
            }
            return outInts;
        }
        catch (Exception e) {
            return def;
        }
    }

    public long getLongProperty(String name) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }
        try {
            return Long.parseLong(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public long getLongProperty(String name, long def) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            return def;
        }
        try {
            return Long.parseLong(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public short getShortProperty(String name) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }
        try {
            return Short.parseShort(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public short getShortProperty(String name, short def) throws NumberFormatException {
        String val = this.getStringProperty(name);
        if (val == null) {
            return def;
        }
        try {
            return Short.parseShort(val);
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public String[] getPropertyGroups(String prefix) {
        Enumeration<?> keys = this.props.propertyNames();
        HashSet<String> groups = new HashSet<String>(10);
        if (!prefix.endsWith(".")) {
            prefix = prefix + ".";
        }
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            if (!key.startsWith(prefix)) continue;
            String groupName = key.substring(prefix.length(), key.indexOf(46, prefix.length()));
            groups.add(groupName);
        }
        return groups.toArray(new String[groups.size()]);
    }

    public Properties getPropertyGroup(String prefix) {
        return this.getPropertyGroup(prefix, false, null);
    }

    public Properties getPropertyGroup(String prefix, boolean stripPrefix) {
        return this.getPropertyGroup(prefix, stripPrefix, null);
    }

    public Properties getPropertyGroup(String prefix, boolean stripPrefix, String[] excludedPrefixes) {
        Enumeration<?> keys = this.props.propertyNames();
        Properties group = new Properties();
        if (!prefix.endsWith(".")) {
            prefix = prefix + ".";
        }
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            if (!key.startsWith(prefix)) continue;
            boolean exclude = false;
            if (excludedPrefixes != null) {
                for (int i = 0; i < excludedPrefixes.length && !exclude; ++i) {
                    exclude = key.startsWith(excludedPrefixes[i]);
                }
            }
            if (exclude) continue;
            String value = this.getStringProperty(key, "");
            if (stripPrefix) {
                group.put(key.substring(prefix.length()), value);
                continue;
            }
            group.put(key, value);
        }
        return group;
    }
}

