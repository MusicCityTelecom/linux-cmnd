/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.RuntimeMBeanException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.jmx.DefaultMBeanAttributeFilter;
import org.springframework.integration.jmx.MBeanAttributeFilter;
import org.springframework.integration.jmx.MBeanObjectConverter;
import org.springframework.util.Assert;

public class DefaultMBeanObjectConverter
implements MBeanObjectConverter {
    private static final Log LOGGER = LogFactory.getLog(DefaultMBeanObjectConverter.class);
    private final MBeanAttributeFilter filter;

    public DefaultMBeanObjectConverter() {
        this(new DefaultMBeanAttributeFilter());
    }

    public DefaultMBeanObjectConverter(MBeanAttributeFilter filter) {
        Assert.notNull((Object)filter, (String)"'filter' must not be null.");
        this.filter = filter;
    }

    @Override
    public Object convert(MBeanServerConnection connection, ObjectInstance instance) {
        HashMap<String, Object> attributeMap = new HashMap<String, Object>();
        try {
            MBeanAttributeInfo[] attributeInfos;
            ObjectName objName = instance.getObjectName();
            if (!connection.isRegistered(objName)) {
                return attributeMap;
            }
            MBeanInfo info = connection.getMBeanInfo(objName);
            for (MBeanAttributeInfo attrInfo : attributeInfos = info.getAttributes()) {
                Object value;
                if ("ObjectName".equals(attrInfo.getName()) || !this.filter.accept(objName, attrInfo.getName())) continue;
                try {
                    value = connection.getAttribute(objName, attrInfo.getName());
                }
                catch (RuntimeMBeanException e) {
                    if (LOGGER.isTraceEnabled()) {
                        LOGGER.trace((Object)("Error getting attribute '" + attrInfo.getName() + "' on '" + objName + "'"), (Throwable)e);
                    }
                    Throwable t = e;
                    while (t.getCause() != null) {
                        t = t.getCause();
                    }
                    value = String.format("%s[%s]", t.getClass().getName(), t.getMessage());
                }
                attributeMap.put(attrInfo.getName(), this.checkAndConvert(value));
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return attributeMap;
    }

    private Object checkAndConvert(Object input) {
        Object converted = null;
        if (input instanceof CompositeData) {
            converted = this.convertFromCompositeData((CompositeData)input);
        } else if (input instanceof TabularData) {
            converted = this.convertFromTabularData((TabularData)input);
        } else if (input != null && input.getClass().isArray()) {
            converted = this.convertFromArray(input);
        }
        if (converted != null) {
            return converted;
        }
        return input;
    }

    private Object convertFromArray(Object input) {
        if (CompositeData.class.isAssignableFrom(input.getClass().getComponentType())) {
            ArrayList<Object> converted = new ArrayList<Object>();
            int length = Array.getLength(input);
            for (int i = 0; i < length; ++i) {
                Object value = this.checkAndConvert(Array.get(input, i));
                converted.add(value);
            }
            return converted;
        }
        if (TabularData.class.isAssignableFrom(input.getClass().getComponentType())) {
            LOGGER.warn((Object)("TabularData.isAssignableFrom(getComponentType) for " + input.toString()));
        }
        return null;
    }

    private Object convertFromCompositeData(CompositeData data) {
        if (data.getCompositeType().isArray()) {
            LOGGER.warn((Object)("(data.getCompositeType().isArray for " + data.toString()));
            return null;
        }
        HashMap<String, Object> returnable = new HashMap<String, Object>();
        Set<String> keys = data.getCompositeType().keySet();
        for (String key : keys) {
            if ("ObjectName".equals(key)) continue;
            Object value = this.checkAndConvert(data.get(key));
            returnable.put(key, value);
        }
        return returnable;
    }

    private Object convertFromTabularData(TabularData data) {
        if (data.getTabularType().isArray()) {
            LOGGER.warn((Object)("TabularData.isArray for " + data.toString()));
            return null;
        }
        HashMap<Object, Object> returnable = new HashMap<Object, Object>();
        Set<?> keySet = data.keySet();
        for (List keys : keySet) {
            CompositeData cd = data.get(keys.toArray());
            Object value = this.checkAndConvert(cd);
            if (keys.size() == 1 && value instanceof Map && ((Map)value).size() == 2) {
                Object actualKey = keys.get(0);
                Map valueMap = (Map)value;
                if (valueMap.containsKey("key") && valueMap.containsKey("value") && actualKey.equals(valueMap.get("key"))) {
                    returnable.put(valueMap.get("key"), valueMap.get("value"));
                    continue;
                }
                returnable.put(actualKey, value);
                continue;
            }
            returnable.put(keys, value);
        }
        return returnable;
    }
}

