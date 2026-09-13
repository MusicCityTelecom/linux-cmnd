/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.MetaClass;
import groovy.transform.Internal;

public interface GroovyObject {
    @Internal
    default public Object invokeMethod(String name, Object args) {
        return this.getMetaClass().invokeMethod((Object)this, name, args);
    }

    @Internal
    default public Object getProperty(String propertyName) {
        return this.getMetaClass().getProperty(this, propertyName);
    }

    @Internal
    default public void setProperty(String propertyName, Object newValue) {
        this.getMetaClass().setProperty(this, propertyName, newValue);
    }

    public MetaClass getMetaClass();

    public void setMetaClass(MetaClass var1);
}

