/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.eclipse.jetty.util.ajax.JSON;

public class JSONObjectConvertor
implements JSON.Convertor {
    private boolean _fromJSON;
    private Set _excluded = null;

    public JSONObjectConvertor() {
        this._fromJSON = false;
    }

    public JSONObjectConvertor(boolean fromJSON) {
        this._fromJSON = fromJSON;
    }

    public JSONObjectConvertor(boolean fromJSON, String[] excluded) {
        this._fromJSON = fromJSON;
        if (excluded != null) {
            this._excluded = new HashSet<String>(Arrays.asList(excluded));
        }
    }

    @Override
    public Object fromJSON(Map map) {
        if (this._fromJSON) {
            throw new UnsupportedOperationException();
        }
        return map;
    }

    @Override
    public void toJSON(Object obj, JSON.Output out) {
        try {
            Class<?> c = obj.getClass();
            if (this._fromJSON) {
                out.addClass(obj.getClass());
            }
            Method[] methods = obj.getClass().getMethods();
            for (int i = 0; i < methods.length; ++i) {
                Method m = methods[i];
                if (Modifier.isStatic(m.getModifiers()) || m.getParameterCount() != 0 || m.getReturnType() == null || m.getDeclaringClass() == Object.class) continue;
                String name = m.getName();
                if (name.startsWith("is")) {
                    name = name.substring(2, 3).toLowerCase(Locale.ENGLISH) + name.substring(3);
                } else {
                    if (!name.startsWith("get")) continue;
                    name = name.substring(3, 4).toLowerCase(Locale.ENGLISH) + name.substring(4);
                }
                if (!this.includeField(name, obj, m)) continue;
                out.add(name, m.invoke(obj, null));
            }
        }
        catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected boolean includeField(String name, Object o, Method m) {
        return this._excluded == null || !this._excluded.contains(name);
    }
}

