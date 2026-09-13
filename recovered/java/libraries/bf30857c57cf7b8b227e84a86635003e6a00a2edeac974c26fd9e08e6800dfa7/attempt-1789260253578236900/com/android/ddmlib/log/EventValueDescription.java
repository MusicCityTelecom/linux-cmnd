/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.log;

import com.android.ddmlib.log.EventContainer;
import com.android.ddmlib.log.InvalidValueTypeException;
import java.util.Locale;

public final class EventValueDescription {
    private String mName;
    private EventContainer.EventValueType mEventValueType;
    private ValueType mValueType;

    EventValueDescription(String name, EventContainer.EventValueType type) {
        this.mName = name;
        this.mEventValueType = type;
        this.mValueType = this.mEventValueType == EventContainer.EventValueType.INT || this.mEventValueType == EventContainer.EventValueType.LONG ? ValueType.BYTES : ValueType.NOT_APPLICABLE;
    }

    EventValueDescription(String name, EventContainer.EventValueType type, ValueType valueType) throws InvalidValueTypeException {
        this.mName = name;
        this.mEventValueType = type;
        this.mValueType = valueType;
        this.mValueType.checkType(this.mEventValueType);
    }

    public String getName() {
        return this.mName;
    }

    public EventContainer.EventValueType getEventValueType() {
        return this.mEventValueType;
    }

    public ValueType getValueType() {
        return this.mValueType;
    }

    public String toString() {
        if (this.mValueType != ValueType.NOT_APPLICABLE) {
            return String.format("%1$s (%2$s, %3$s)", this.mName, this.mEventValueType.toString(), this.mValueType.toString());
        }
        return String.format("%1$s (%2$s)", this.mName, this.mEventValueType.toString());
    }

    public boolean checkForType(Object value) {
        switch (this.mEventValueType) {
            case INT: {
                return value instanceof Integer;
            }
            case LONG: {
                return value instanceof Long;
            }
            case STRING: {
                return value instanceof String;
            }
            case LIST: {
                return value instanceof Object[];
            }
        }
        return false;
    }

    public Object getObjectFromString(String value) {
        switch (this.mEventValueType) {
            case INT: {
                try {
                    return Integer.valueOf(value);
                }
                catch (NumberFormatException e2) {
                    return null;
                }
            }
            case LONG: {
                try {
                    return Long.valueOf(value);
                }
                catch (NumberFormatException e3) {
                    return null;
                }
            }
            case STRING: {
                return value;
            }
        }
        return null;
    }

    public static enum ValueType {
        NOT_APPLICABLE(0),
        OBJECTS(1),
        BYTES(2),
        MILLISECONDS(3),
        ALLOCATIONS(4),
        ID(5),
        PERCENT(6);

        private int mValue;

        public void checkType(EventContainer.EventValueType type) throws InvalidValueTypeException {
            if (type != EventContainer.EventValueType.INT && type != EventContainer.EventValueType.LONG && this != NOT_APPLICABLE) {
                throw new InvalidValueTypeException(String.format("%1$s doesn't support type %2$s", new Object[]{type, this}));
            }
        }

        public static ValueType getValueType(int value) {
            for (ValueType type : ValueType.values()) {
                if (type.mValue != value) continue;
                return type;
            }
            return null;
        }

        public int getValue() {
            return this.mValue;
        }

        public String toString() {
            return super.toString().toLowerCase(Locale.US);
        }

        private ValueType(int value) {
            this.mValue = value;
        }
    }
}

