/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import org.apache.groovy.util.BeanUtils;

public abstract class MetaProperty {
    public static final String PROPERTY_SET_PREFIX = "set";
    protected final String name;
    protected Class type;

    public MetaProperty(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public abstract Object getProperty(Object var1);

    public abstract void setProperty(Object var1, Object var2);

    public String getName() {
        return this.name;
    }

    public Class getType() {
        return this.type;
    }

    public int getModifiers() {
        return 1;
    }

    public static String getGetterName(String propertyName, Class type) {
        String prefix = type == Boolean.TYPE ? "is" : "get";
        return prefix + BeanUtils.capitalize(propertyName);
    }

    public static String getSetterName(String propertyName) {
        return PROPERTY_SET_PREFIX + BeanUtils.capitalize(propertyName);
    }
}

