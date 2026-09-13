/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.log;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.log.EventContainer;
import com.android.ddmlib.log.EventValueDescription;
import com.android.ddmlib.log.GcEventContainer;
import com.android.ddmlib.log.InvalidValueTypeException;
import com.android.ddmlib.log.LogReceiver;
import com.android.ddmlib.utils.ArrayHelper;
import com.google.common.base.Charsets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EventLogParser {
    private static final String EVENT_TAG_MAP_FILE = "/system/etc/event-log-tags";
    private static final int EVENT_TYPE_INT = 0;
    private static final int EVENT_TYPE_LONG = 1;
    private static final int EVENT_TYPE_STRING = 2;
    private static final int EVENT_TYPE_LIST = 3;
    private static final Pattern PATTERN_SIMPLE_TAG = Pattern.compile("^(\\d+)\\s+([A-Za-z0-9_]+)\\s*$");
    private static final Pattern PATTERN_TAG_WITH_DESC = Pattern.compile("^(\\d+)\\s+([A-Za-z0-9_]+)\\s*(.*)\\s*$");
    private static final Pattern PATTERN_DESCRIPTION = Pattern.compile("\\(([A-Za-z0-9_\\s]+)\\|(\\d+)(\\|\\d+){0,1}\\)");
    private static final Pattern TEXT_LOG_LINE = Pattern.compile("(\\d\\d)-(\\d\\d)\\s(\\d\\d):(\\d\\d):(\\d\\d).(\\d{3})\\s+I/([a-zA-Z0-9_]+)\\s*\\(\\s*(\\d+)\\):\\s+(.*)");
    private final TreeMap<Integer, String> mTagMap = new TreeMap();
    private final TreeMap<Integer, EventValueDescription[]> mValueDescriptionMap = new TreeMap();

    public boolean init(IDevice device) {
        try {
            device.executeShellCommand("cat /system/etc/event-log-tags", new MultiLineReceiver(){

                @Override
                public void processNewLines(String[] lines) {
                    for (String line : lines) {
                        EventLogParser.this.processTagLine(line);
                    }
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }
            });
        }
        catch (Exception e2) {
            return false;
        }
        return true;
    }

    public boolean init(String[] tagFileContent) {
        for (String line : tagFileContent) {
            this.processTagLine(line);
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean init(String filePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            do {
                if ((line = reader.readLine()) == null) continue;
                this.processTagLine(line);
            } while (line != null);
            boolean bl = true;
            return bl;
        }
        catch (IOException e2) {
            boolean bl = false;
            return bl;
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException iOException) {}
        }
    }

    private void processTagLine(String line) {
        if (!line.isEmpty() && line.charAt(0) != '#') {
            Matcher m3 = PATTERN_TAG_WITH_DESC.matcher(line);
            if (m3.matches()) {
                try {
                    int value = Integer.parseInt(m3.group(1));
                    String name = m3.group(2);
                    if (name != null && this.mTagMap.get(value) == null) {
                        this.mTagMap.put(value, name);
                    }
                    if (value == 20001) {
                        this.mValueDescriptionMap.put(value, GcEventContainer.getValueDescriptions());
                    } else {
                        EventValueDescription[] desc;
                        String description = m3.group(3);
                        if (description != null && !description.isEmpty() && (desc = this.processDescription(description)) != null) {
                            this.mValueDescriptionMap.put(value, desc);
                        }
                    }
                }
                catch (NumberFormatException value) {}
            } else {
                m3 = PATTERN_SIMPLE_TAG.matcher(line);
                if (m3.matches()) {
                    int value = Integer.parseInt(m3.group(1));
                    String name = m3.group(2);
                    if (name != null && this.mTagMap.get(value) == null) {
                        this.mTagMap.put(value, name);
                    }
                }
            }
        }
    }

    private EventValueDescription[] processDescription(String description) {
        String[] descriptions = description.split("\\s*,\\s*");
        ArrayList<EventValueDescription> list = new ArrayList<EventValueDescription>();
        for (String desc : descriptions) {
            Matcher m3 = PATTERN_DESCRIPTION.matcher(desc);
            if (m3.matches()) {
                try {
                    String name = m3.group(1);
                    String typeString = m3.group(2);
                    int typeValue = Integer.parseInt(typeString);
                    EventContainer.EventValueType eventValueType = EventContainer.EventValueType.getEventValueType(typeValue);
                    if (eventValueType == null) {
                        // empty if block
                    }
                    if ((typeString = m3.group(3)) != null && !typeString.isEmpty()) {
                        typeString = typeString.substring(1);
                        typeValue = Integer.parseInt(typeString);
                        EventValueDescription.ValueType valueType = EventValueDescription.ValueType.getValueType(typeValue);
                        list.add(new EventValueDescription(name, eventValueType, valueType));
                        continue;
                    }
                    list.add(new EventValueDescription(name, eventValueType));
                }
                catch (NumberFormatException numberFormatException) {
                }
                catch (InvalidValueTypeException invalidValueTypeException) {}
                continue;
            }
            Log.e("EventLogParser", String.format("Can't parse %1$s", description));
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new EventValueDescription[list.size()]);
    }

    public EventContainer parse(LogReceiver.LogEntry entry) {
        ArrayList<Object> list;
        if (entry.len < 4) {
            return null;
        }
        int inOffset = 0;
        int tagValue = ArrayHelper.swap32bitFromArray(entry.data, inOffset);
        inOffset += 4;
        String tag = this.mTagMap.get(tagValue);
        if (tag == null) {
            Log.e("EventLogParser", String.format("unknown tag number: %1$d", tagValue));
        }
        if (EventLogParser.parseBinaryEvent(entry.data, inOffset, list = new ArrayList<Object>()) == -1) {
            return null;
        }
        Object data = list.size() == 1 ? list.get(0) : list.toArray();
        EventContainer event = null;
        event = tagValue == 20001 ? new GcEventContainer(entry, tagValue, data) : new EventContainer(entry, tagValue, data);
        return event;
    }

    public EventContainer parse(String textLogLine) {
        if (textLogLine.isEmpty()) {
            return null;
        }
        Matcher m3 = TEXT_LOG_LINE.matcher(textLogLine);
        if (m3.matches()) {
            try {
                int month = Integer.parseInt(m3.group(1));
                int day = Integer.parseInt(m3.group(2));
                int hours = Integer.parseInt(m3.group(3));
                int minutes = Integer.parseInt(m3.group(4));
                int seconds = Integer.parseInt(m3.group(5));
                int milliseconds = Integer.parseInt(m3.group(6));
                Calendar cal = Calendar.getInstance();
                cal.set(cal.get(1), month - 1, day, hours, minutes, seconds);
                int sec = (int)Math.floor(cal.getTimeInMillis() / 1000L);
                int nsec = milliseconds * 1000000;
                String tag = m3.group(7);
                int tagValue = -1;
                Set<Map.Entry<Integer, String>> tagSet = this.mTagMap.entrySet();
                for (Map.Entry<Integer, String> entry : tagSet) {
                    if (!tag.equals(entry.getValue())) continue;
                    tagValue = entry.getKey();
                    break;
                }
                if (tagValue == -1) {
                    return null;
                }
                int pid = Integer.parseInt(m3.group(8));
                Object data = this.parseTextData(m3.group(9), tagValue);
                if (data == null) {
                    return null;
                }
                EventContainer event = null;
                event = tagValue == 20001 ? new GcEventContainer(tagValue, pid, -1, sec, nsec, data) : new EventContainer(tagValue, pid, -1, sec, nsec, data);
                return event;
            }
            catch (NumberFormatException e2) {
                return null;
            }
        }
        return null;
    }

    public Map<Integer, String> getTagMap() {
        return this.mTagMap;
    }

    public Map<Integer, EventValueDescription[]> getEventInfoMap() {
        return this.mValueDescriptionMap;
    }

    private static int parseBinaryEvent(byte[] eventData, int dataOffset, ArrayList<Object> list) {
        if (eventData.length - dataOffset < 1) {
            return -1;
        }
        int offset = dataOffset;
        byte type = eventData[offset++];
        switch (type) {
            case 0: {
                if (eventData.length - offset < 4) {
                    return -1;
                }
                int ival = ArrayHelper.swap32bitFromArray(eventData, offset);
                offset += 4;
                list.add(ival);
                break;
            }
            case 1: {
                if (eventData.length - offset < 8) {
                    return -1;
                }
                long lval = ArrayHelper.swap64bitFromArray(eventData, offset);
                offset += 8;
                list.add(lval);
                break;
            }
            case 2: {
                int strLen;
                if (eventData.length - offset < 4) {
                    return -1;
                }
                if (eventData.length - (offset += 4) < (strLen = ArrayHelper.swap32bitFromArray(eventData, offset))) {
                    return -1;
                }
                String str = new String(eventData, offset, strLen, Charsets.UTF_8);
                list.add(str);
                offset += strLen;
                break;
            }
            case 3: {
                if (eventData.length - offset < 1) {
                    return -1;
                }
                int count = eventData[offset++];
                ArrayList<Object> subList = new ArrayList<Object>();
                for (int i2 = 0; i2 < count; ++i2) {
                    int result = EventLogParser.parseBinaryEvent(eventData, offset, subList);
                    if (result == -1) {
                        return result;
                    }
                    offset += result;
                }
                list.add(subList.toArray());
                break;
            }
            default: {
                Log.e("EventLogParser", String.format("Unknown binary event type %1$d", type));
                return -1;
            }
        }
        return offset - dataOffset;
    }

    private Object parseTextData(String data, int tagValue) {
        EventValueDescription[] desc = this.mValueDescriptionMap.get(tagValue);
        if (desc == null) {
            return null;
        }
        if (desc.length == 1) {
            return this.getObjectFromString(data, desc[0].getEventValueType());
        }
        if (data.startsWith("[") && data.endsWith("]")) {
            data = data.substring(1, data.length() - 1);
            String[] values2 = data.split(",");
            if (tagValue == 20001) {
                Object[] objects = new Object[]{this.getObjectFromString(values2[0], EventContainer.EventValueType.LONG), this.getObjectFromString(values2[1], EventContainer.EventValueType.LONG)};
                return objects;
            }
            if (values2.length != desc.length) {
                return null;
            }
            Object[] objects = new Object[values2.length];
            for (int i2 = 0; i2 < desc.length; ++i2) {
                Object obj = this.getObjectFromString(values2[i2], desc[i2].getEventValueType());
                if (obj == null) {
                    return null;
                }
                objects[i2] = obj;
            }
            return objects;
        }
        return null;
    }

    private Object getObjectFromString(String value, EventContainer.EventValueType type) {
        try {
            switch (type) {
                case INT: {
                    return Integer.valueOf(value);
                }
                case LONG: {
                    return Long.valueOf(value);
                }
                case STRING: {
                    return value;
                }
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveTags(String filePath) throws IOException {
        File destFile = new File(filePath);
        destFile.createNewFile();
        try (FileOutputStream fos = null;){
            fos = new FileOutputStream(destFile);
            for (Integer key : this.mTagMap.keySet()) {
                String tagName = this.mTagMap.get(key);
                EventValueDescription[] descriptors = this.mValueDescriptionMap.get(key);
                String line = null;
                if (descriptors != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.format("%1$d %2$s", key, tagName));
                    boolean first = true;
                    for (EventValueDescription evd : descriptors) {
                        if (first) {
                            sb.append(" (");
                            first = false;
                        } else {
                            sb.append(",(");
                        }
                        sb.append(evd.getName());
                        sb.append("|");
                        sb.append(evd.getEventValueType().getValue());
                        sb.append("|");
                        sb.append(evd.getValueType().getValue());
                        sb.append("|)");
                    }
                    sb.append("\n");
                    line = sb.toString();
                } else {
                    line = String.format("%1$d %2$s\n", key, tagName);
                }
                byte[] buffer = line.getBytes();
                fos.write(buffer);
            }
        }
    }
}

