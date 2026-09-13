/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.beans.Transient;
import java.util.Optional;
import org.codehaus.groovy.runtime.InvokerHelper;

public abstract class GroovyObjectSupport
implements GroovyObject {
    private transient MetaClass metaClass = this.getDefaultMetaClass();

    @Override
    @Transient
    public MetaClass getMetaClass() {
        return this.metaClass;
    }

    @Override
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = Optional.ofNullable(metaClass).orElseGet(this::getDefaultMetaClass);
    }

    private MetaClass getDefaultMetaClass() {
        return InvokerHelper.getMetaClass(this.getClass());
    }
}

