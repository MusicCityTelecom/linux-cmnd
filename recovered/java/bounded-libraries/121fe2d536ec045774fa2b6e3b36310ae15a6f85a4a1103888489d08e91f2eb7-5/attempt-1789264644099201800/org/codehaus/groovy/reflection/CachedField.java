/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaProperty;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.codehaus.groovy.reflection.AccessPermissionChecker;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class CachedField
extends MetaProperty {
    private final Field field;
    private transient boolean madeAccessible;

    public CachedField(Field field) {
        super(field.getName(), field.getType());
        this.field = field;
    }

    public Field getCachedField() {
        this.makeAccessibleIfNecessary();
        return this.field;
    }

    public Class getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    @Override
    public int getModifiers() {
        return this.field.getModifiers();
    }

    public boolean isFinal() {
        return Modifier.isFinal(this.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.getModifiers());
    }

    @Override
    public Object getProperty(Object object) {
        this.makeAccessibleIfNecessary();
        try {
            return this.field.get(object);
        }
        catch (IllegalAccessException e) {
            throw new GroovyRuntimeException("Cannot get the property '" + this.name + "'.", e);
        }
    }

    @Override
    public void setProperty(Object object, Object newValue) {
        if (this.isFinal()) {
            throw new GroovyRuntimeException("Cannot set the property '" + this.name + "' because the backing field is final.");
        }
        this.makeAccessibleIfNecessary();
        Object goalValue = DefaultTypeTransformation.castToType(newValue, this.field.getType());
        try {
            this.field.set(object, goalValue);
        }
        catch (IllegalAccessException e) {
            throw new GroovyRuntimeException("Cannot set the property '" + this.name + "'.", e);
        }
    }

    private void makeAccessibleIfNecessary() {
        if (!this.madeAccessible) {
            ReflectionUtils.makeAccessibleInPrivilegedAction(this.field);
            this.madeAccessible = true;
        }
        AccessPermissionChecker.checkAccessPermission(this.field);
    }
}

