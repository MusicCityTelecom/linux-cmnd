/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;
import org.apache.commons.jexl3.introspection.JexlSandbox;
import org.apache.commons.jexl3.introspection.JexlUberspect;

public final class SandboxUberspect
implements JexlUberspect {
    private final JexlUberspect uberspect;
    private final JexlSandbox sandbox;

    public SandboxUberspect(JexlUberspect theUberspect, JexlSandbox theSandbox) {
        if (theSandbox == null) {
            throw new NullPointerException("sandbox can not be null");
        }
        if (theUberspect == null) {
            throw new NullPointerException("uberspect can not be null");
        }
        this.uberspect = theUberspect;
        this.sandbox = theSandbox.copy();
    }

    @Override
    public void setClassLoader(ClassLoader loader) {
        this.uberspect.setClassLoader(loader);
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.uberspect.getClassLoader();
    }

    @Override
    public int getVersion() {
        return this.uberspect.getVersion();
    }

    @Override
    public JexlMethod getConstructor(Object ctorHandle, Object ... args) {
        String className = ctorHandle instanceof Class ? this.sandbox.execute((Class)ctorHandle, "") : (ctorHandle != null ? this.sandbox.execute(ctorHandle.toString(), "") : null);
        return className != null && className != "?" ? this.uberspect.getConstructor(className, args) : null;
    }

    @Override
    public JexlMethod getMethod(Object obj, String method, Object ... args) {
        Class<?> clazz;
        String actual;
        if (obj != null && method != null && (actual = this.sandbox.execute(clazz = obj instanceof Class ? (Class<?>)obj : obj.getClass(), method)) != null && actual != "?") {
            return this.uberspect.getMethod(obj, actual, args);
        }
        return null;
    }

    @Override
    public List<JexlUberspect.PropertyResolver> getResolvers(JexlOperator op, Object obj) {
        return this.uberspect.getResolvers(op, obj);
    }

    @Override
    public JexlPropertyGet getPropertyGet(Object obj, Object identifier) {
        return this.getPropertyGet(null, obj, identifier);
    }

    @Override
    public JexlPropertyGet getPropertyGet(List<JexlUberspect.PropertyResolver> resolvers, Object obj, Object identifier) {
        if (obj != null) {
            if (identifier != null) {
                String property = identifier.toString();
                String actual = this.sandbox.read(obj.getClass(), property);
                if (actual != null) {
                    Object pty = actual == property ? identifier : actual;
                    return this.uberspect.getPropertyGet(resolvers, obj, pty);
                }
            } else {
                String actual = this.sandbox.read(obj.getClass(), null);
                if (actual != "?") {
                    return this.uberspect.getPropertyGet(resolvers, obj, null);
                }
            }
        }
        return null;
    }

    @Override
    public JexlPropertySet getPropertySet(Object obj, Object identifier, Object arg) {
        return this.getPropertySet(null, obj, identifier, arg);
    }

    @Override
    public JexlPropertySet getPropertySet(List<JexlUberspect.PropertyResolver> resolvers, Object obj, Object identifier, Object arg) {
        if (obj != null) {
            if (identifier != null) {
                String property = identifier.toString();
                String actual = this.sandbox.write(obj.getClass(), property);
                if (actual != null) {
                    Object pty = actual == property ? identifier : actual;
                    return this.uberspect.getPropertySet(resolvers, obj, pty, arg);
                }
            } else {
                String actual = this.sandbox.write(obj.getClass(), null);
                if (actual != "?") {
                    return this.uberspect.getPropertySet(resolvers, obj, null, arg);
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<?> getIterator(Object obj) {
        return this.uberspect.getIterator(obj);
    }

    @Override
    public JexlArithmetic.Uberspect getArithmetic(JexlArithmetic arithmetic) {
        return this.uberspect.getArithmetic(arithmetic);
    }
}

