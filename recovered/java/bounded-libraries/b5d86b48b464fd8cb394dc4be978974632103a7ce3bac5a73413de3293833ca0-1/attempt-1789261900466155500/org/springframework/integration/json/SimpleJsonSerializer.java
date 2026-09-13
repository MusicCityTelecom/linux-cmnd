/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeanUtils
 */
package org.springframework.integration.json;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

public final class SimpleJsonSerializer {
    private static final Log LOGGER = LogFactory.getLog(SimpleJsonSerializer.class);

    private SimpleJsonSerializer() {
    }

    public static String toJson(Object bean, String ... propertiesToExclude) {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(bean.getClass());
        HashSet<String> excluded = new HashSet<String>(Arrays.asList(propertiesToExclude));
        excluded.add("class");
        StringBuilder stringBuilder = new StringBuilder("{");
        Object[] emptyArgs = new Object[]{};
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            Object result;
            String propertyName = descriptor.getName();
            Method readMethod = descriptor.getReadMethod();
            if (excluded.contains(propertyName) || readMethod == null) continue;
            stringBuilder.append(SimpleJsonSerializer.toElement(propertyName)).append(":");
            try {
                result = readMethod.invoke(bean, emptyArgs);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                Throwable exception = e;
                if (e instanceof InvocationTargetException) {
                    exception = e.getCause();
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Failed to serialize property " + propertyName), exception);
                }
                result = exception.getMessage() != null ? exception.getMessage() : exception.toString();
            }
            stringBuilder.append(SimpleJsonSerializer.toElement(result)).append(",");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append("}");
        if (stringBuilder.length() == 1) {
            return null;
        }
        return stringBuilder.toString();
    }

    private static String toElement(Object result) {
        if (result instanceof Number || result instanceof Boolean) {
            return result.toString();
        }
        return "\"" + (result == null ? "null" : Matcher.quoteReplacement(result.toString())) + "\"";
    }
}

