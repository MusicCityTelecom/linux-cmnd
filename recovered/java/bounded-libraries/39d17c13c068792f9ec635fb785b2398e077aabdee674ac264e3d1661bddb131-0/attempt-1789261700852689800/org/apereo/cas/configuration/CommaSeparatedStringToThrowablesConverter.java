/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class CommaSeparatedStringToThrowablesConverter
implements Converter<String, List<Class<? extends Throwable>>> {
    public List<Class<? extends Throwable>> convert(String source) {
        try {
            String[] strings = StringUtils.commaDelimitedListToStringArray((String)source);
            ArrayList<Class<? extends Throwable>> classes = new ArrayList<Class<? extends Throwable>>(strings.length);
            for (String className : strings) {
                classes.add(ClassUtils.forName((String)className.trim(), (ClassLoader)this.getClass().getClassLoader()));
            }
            return classes;
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

