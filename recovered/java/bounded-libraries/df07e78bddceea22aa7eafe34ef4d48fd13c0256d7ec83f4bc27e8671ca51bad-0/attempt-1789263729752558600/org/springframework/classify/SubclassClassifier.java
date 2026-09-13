/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.classify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.classify.Classifier;

public class SubclassClassifier<T, C>
implements Classifier<T, C> {
    private ConcurrentMap<Class<? extends T>, C> classified = new ConcurrentHashMap<Class<? extends T>, C>();
    private C defaultValue = null;

    public SubclassClassifier() {
        this(null);
    }

    public SubclassClassifier(C defaultValue) {
        this(new HashMap(), defaultValue);
    }

    public SubclassClassifier(Map<Class<? extends T>, C> typeMap, C defaultValue) {
        this.classified = new ConcurrentHashMap<Class<T>, C>(typeMap);
        this.defaultValue = defaultValue;
    }

    public void setDefaultValue(C defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setTypeMap(Map<Class<? extends T>, C> map) {
        this.classified = new ConcurrentHashMap<Class<T>, C>(map);
    }

    public void add(Class<? extends T> type, C target) {
        this.classified.put(type, target);
    }

    @Override
    public C classify(T classifiable) {
        if (classifiable == null) {
            return this.defaultValue;
        }
        Class<?> exceptionClass = classifiable.getClass();
        if (this.classified.containsKey(exceptionClass)) {
            return (C)this.classified.get(exceptionClass);
        }
        Object value = null;
        Class<?> cls = exceptionClass;
        while (!cls.equals(Object.class) && value == null) {
            value = this.classified.get(cls);
            cls = cls.getSuperclass();
        }
        if (value == null) {
            cls = exceptionClass;
            while (!cls.equals(Object.class) && value == null) {
                Class<?> ifc;
                Class<?>[] classArray = cls.getInterfaces();
                int n = classArray.length;
                for (int i = 0; i < n && (value = (Object)this.classified.get(ifc = classArray[i])) == null; ++i) {
                }
                cls = cls.getSuperclass();
            }
        }
        if (value != null) {
            this.classified.put(exceptionClass, value);
        }
        if (value == null) {
            value = this.defaultValue;
        }
        return (C)value;
    }

    public final C getDefault() {
        return this.defaultValue;
    }

    protected Map<Class<? extends T>, C> getClassified() {
        return this.classified;
    }
}

