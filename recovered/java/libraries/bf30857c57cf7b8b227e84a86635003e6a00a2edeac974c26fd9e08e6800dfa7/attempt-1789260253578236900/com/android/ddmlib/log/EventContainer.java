/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.log;

import com.android.ddmlib.log.InvalidTypeException;
import com.android.ddmlib.log.LogReceiver;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventContainer {
    public int mTag;
    public int pid;
    public int tid;
    public int sec;
    public int nsec;
    private Object mData;

    EventContainer(LogReceiver.LogEntry entry, int tag, Object data) {
        this.getType(data);
        this.mTag = tag;
        this.mData = data;
        this.pid = entry.pid;
        this.tid = entry.tid;
        this.sec = entry.sec;
        this.nsec = entry.nsec;
    }

    EventContainer(int tag, int pid, int tid, int sec, int nsec, Object data) {
        this.getType(data);
        this.mTag = tag;
        this.mData = data;
        this.pid = pid;
        this.tid = tid;
        this.sec = sec;
        this.nsec = nsec;
    }

    public final Integer getInt() throws InvalidTypeException {
        if (this.getType(this.mData) == EventValueType.INT) {
            return (Integer)this.mData;
        }
        throw new InvalidTypeException();
    }

    public final Long getLong() throws InvalidTypeException {
        if (this.getType(this.mData) == EventValueType.LONG) {
            return (Long)this.mData;
        }
        throw new InvalidTypeException();
    }

    public final String getString() throws InvalidTypeException {
        if (this.getType(this.mData) == EventValueType.STRING) {
            return (String)this.mData;
        }
        throw new InvalidTypeException();
    }

    public Object getValue(int valueIndex) {
        return this.getValue(this.mData, valueIndex, true);
    }

    public double getValueAsDouble(int valueIndex) throws InvalidTypeException {
        return this.getValueAsDouble(this.mData, valueIndex, true);
    }

    public String getValueAsString(int valueIndex) throws InvalidTypeException {
        return this.getValueAsString(this.mData, valueIndex, true);
    }

    public EventValueType getType() {
        return this.getType(this.mData);
    }

    public final EventValueType getType(Object data) {
        if (data instanceof Integer) {
            return EventValueType.INT;
        }
        if (data instanceof Long) {
            return EventValueType.LONG;
        }
        if (data instanceof String) {
            return EventValueType.STRING;
        }
        if (data instanceof Object[]) {
            Object[] objects;
            for (Object obj : objects = (Object[])data) {
                EventValueType type = this.getType(obj);
                if (type != EventValueType.LIST && type != EventValueType.TREE) continue;
                return EventValueType.TREE;
            }
            return EventValueType.LIST;
        }
        return EventValueType.UNKNOWN;
    }

    public boolean testValue(int index, Object value, CompareMethod compareMethod) throws InvalidTypeException {
        EventValueType type = this.getType(this.mData);
        if (index > 0 && type != EventValueType.LIST) {
            throw new InvalidTypeException();
        }
        Object data = this.mData;
        if (type == EventValueType.LIST) {
            data = ((Object[])this.mData)[index];
        }
        if (!data.getClass().equals(value.getClass())) {
            throw new InvalidTypeException();
        }
        switch (compareMethod) {
            case EQUAL_TO: {
                return data.equals(value);
            }
            case LESSER_THAN: {
                if (data instanceof Integer) {
                    return ((Integer)data).compareTo((Integer)value) <= 0;
                }
                if (data instanceof Long) {
                    return ((Long)data).compareTo((Long)value) <= 0;
                }
                throw new InvalidTypeException();
            }
            case LESSER_THAN_STRICT: {
                if (data instanceof Integer) {
                    return ((Integer)data).compareTo((Integer)value) < 0;
                }
                if (data instanceof Long) {
                    return ((Long)data).compareTo((Long)value) < 0;
                }
                throw new InvalidTypeException();
            }
            case GREATER_THAN: {
                if (data instanceof Integer) {
                    return ((Integer)data).compareTo((Integer)value) >= 0;
                }
                if (data instanceof Long) {
                    return ((Long)data).compareTo((Long)value) >= 0;
                }
                throw new InvalidTypeException();
            }
            case GREATER_THAN_STRICT: {
                if (data instanceof Integer) {
                    return ((Integer)data).compareTo((Integer)value) > 0;
                }
                if (data instanceof Long) {
                    return ((Long)data).compareTo((Long)value) > 0;
                }
                throw new InvalidTypeException();
            }
            case BIT_CHECK: {
                if (data instanceof Integer) {
                    return ((Integer)data & (Integer)value) != 0;
                }
                if (data instanceof Long) {
                    return ((Long)data & (Long)value) != 0L;
                }
                throw new InvalidTypeException();
            }
        }
        throw new InvalidTypeException();
    }

    private Object getValue(Object data, int valueIndex, boolean recursive) {
        EventValueType type = this.getType(data);
        switch (type) {
            case STRING: 
            case INT: 
            case LONG: {
                return data;
            }
            case LIST: {
                if (!recursive) break;
                Object[] list = (Object[])data;
                if (valueIndex < 0 || valueIndex >= list.length) break;
                return this.getValue(list[valueIndex], valueIndex, false);
            }
        }
        return null;
    }

    private double getValueAsDouble(Object data, int valueIndex, boolean recursive) throws InvalidTypeException {
        EventValueType type = this.getType(data);
        switch (type) {
            case INT: {
                return ((Integer)data).doubleValue();
            }
            case LONG: {
                return ((Long)data).doubleValue();
            }
            case STRING: {
                throw new InvalidTypeException();
            }
            case LIST: {
                if (!recursive) break;
                Object[] list = (Object[])data;
                if (valueIndex < 0 || valueIndex >= list.length) break;
                return this.getValueAsDouble(list[valueIndex], valueIndex, false);
            }
        }
        throw new InvalidTypeException();
    }

    private String getValueAsString(Object data, int valueIndex, boolean recursive) throws InvalidTypeException {
        EventValueType type = this.getType(data);
        switch (type) {
            case INT: {
                return data.toString();
            }
            case LONG: {
                return data.toString();
            }
            case STRING: {
                return (String)data;
            }
            case LIST: {
                if (recursive) {
                    Object[] list = (Object[])data;
                    if (valueIndex < 0 || valueIndex >= list.length) break;
                    return this.getValueAsString(list[valueIndex], valueIndex, false);
                }
                throw new InvalidTypeException("getValueAsString() doesn't support EventValueType.TREE");
            }
        }
        throw new InvalidTypeException("getValueAsString() unsupported type:" + (Object)((Object)type));
    }

    public static enum EventValueType {
        UNKNOWN(0),
        INT(1),
        LONG(2),
        STRING(3),
        LIST(4),
        TREE(5);

        private static final Pattern STORAGE_PATTERN;
        private int mValue;

        static EventValueType getEventValueType(int value) {
            for (EventValueType type : EventValueType.values()) {
                if (type.mValue != value) continue;
                return type;
            }
            return null;
        }

        public static String getStorageString(Object object) {
            if (object instanceof String) {
                return EventValueType.STRING.mValue + "@" + object;
            }
            if (object instanceof Integer) {
                return EventValueType.INT.mValue + "@" + object.toString();
            }
            if (object instanceof Long) {
                return EventValueType.LONG.mValue + "@" + object.toString();
            }
            return null;
        }

        public static Object getObjectFromStorageString(String value) {
            Matcher m3 = STORAGE_PATTERN.matcher(value);
            if (m3.matches()) {
                try {
                    EventValueType type = EventValueType.getEventValueType(Integer.parseInt(m3.group(1)));
                    if (type == null) {
                        return null;
                    }
                    switch (type) {
                        case STRING: {
                            return m3.group(2);
                        }
                        case INT: {
                            return Integer.valueOf(m3.group(2));
                        }
                        case LONG: {
                            return Long.valueOf(m3.group(2));
                        }
                    }
                }
                catch (NumberFormatException nfe) {
                    return null;
                }
            }
            return null;
        }

        public int getValue() {
            return this.mValue;
        }

        public String toString() {
            return super.toString().toLowerCase(Locale.US);
        }

        private EventValueType(int value) {
            this.mValue = value;
        }

        static {
            STORAGE_PATTERN = Pattern.compile("^(\\d+)@(.*)$");
        }
    }

    public static enum CompareMethod {
        EQUAL_TO("equals", "=="),
        LESSER_THAN("less than or equals to", "<="),
        LESSER_THAN_STRICT("less than", "<"),
        GREATER_THAN("greater than or equals to", ">="),
        GREATER_THAN_STRICT("greater than", ">"),
        BIT_CHECK("bit check", "&");

        private final String mName;
        private final String mTestString;

        private CompareMethod(String name, String testString) {
            this.mName = name;
            this.mTestString = testString;
        }

        public String toString() {
            return this.mName;
        }

        public String testString() {
            return this.mTestString;
        }
    }
}

