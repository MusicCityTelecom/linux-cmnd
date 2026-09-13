/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;

public class ObjectContext<T>
implements JexlContext,
JexlContext.NamespaceResolver {
    private final JexlEngine jexl;
    private final T object;

    protected JexlEngine getJexl() {
        return this.jexl;
    }

    protected T getObject() {
        return this.object;
    }

    public ObjectContext(JexlEngine engine, T wrapped) {
        this.jexl = engine;
        this.object = wrapped;
    }

    @Override
    public Object get(String name) {
        block3: {
            JexlPropertyGet jget = this.jexl.getUberspect().getPropertyGet(this.object, name);
            if (jget != null) {
                try {
                    return jget.invoke(this.object);
                }
                catch (Exception xany) {
                    if (!this.jexl.isStrict()) break block3;
                    throw new JexlException.Property(null, name, true, xany);
                }
            }
        }
        return null;
    }

    @Override
    public void set(String name, Object value) {
        block3: {
            JexlPropertySet jset = this.jexl.getUberspect().getPropertySet(this.object, name, value);
            if (jset != null) {
                try {
                    jset.invoke(this.object, value);
                }
                catch (Exception xany) {
                    if (!this.jexl.isStrict()) break block3;
                    throw new JexlException.Property(null, name, true, xany);
                }
            }
        }
    }

    @Override
    public boolean has(String name) {
        JexlPropertyGet jget = this.jexl.getUberspect().getPropertyGet(this.object, name);
        try {
            return jget != null && jget.invoke(this.object) != null;
        }
        catch (Exception xany) {
            return false;
        }
    }

    @Override
    public Object resolveNamespace(String name) {
        if (name == null || name.isEmpty()) {
            return this.object;
        }
        return null;
    }
}

