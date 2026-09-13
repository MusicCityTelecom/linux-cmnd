/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core.jmx;

import java.util.ArrayList;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.quartz.JobDataMap;

public class JobDataMapSupport {
    private static final String typeName = "JobDataMap";
    private static final String[] keyValue = new String[]{"key", "value"};
    private static final OpenType[] openTypes = new OpenType[]{SimpleType.STRING, SimpleType.STRING};
    private static final CompositeType rowType;
    public static final TabularType TABULAR_TYPE;

    public static JobDataMap newJobDataMap(TabularData tabularData) {
        JobDataMap jobDataMap = new JobDataMap();
        if (tabularData != null) {
            for (CompositeData cData : tabularData.values()) {
                jobDataMap.put((String)cData.get("key"), (String)cData.get("value"));
            }
        }
        return jobDataMap;
    }

    public static JobDataMap newJobDataMap(Map<String, Object> map) {
        JobDataMap jobDataMap = new JobDataMap();
        if (map != null) {
            for (String key : map.keySet()) {
                jobDataMap.put(key, map.get(key));
            }
        }
        return jobDataMap;
    }

    public static CompositeData toCompositeData(String key, String value) {
        try {
            return new CompositeDataSupport(rowType, keyValue, new Object[]{key, value});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData toTabularData(JobDataMap jobDataMap) {
        TabularDataSupport tData = new TabularDataSupport(TABULAR_TYPE);
        ArrayList<CompositeData> list = new ArrayList<CompositeData>();
        for (String key : jobDataMap.keySet()) {
            list.add(JobDataMapSupport.toCompositeData(key, String.valueOf(jobDataMap.get(key))));
        }
        tData.putAll(list.toArray(new CompositeData[list.size()]));
        return tData;
    }

    static {
        try {
            rowType = new CompositeType(typeName, typeName, keyValue, keyValue, openTypes);
            TABULAR_TYPE = new TabularType(typeName, typeName, rowType, new String[]{"key"});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

