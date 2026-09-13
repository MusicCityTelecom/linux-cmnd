/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.lang.reflect.Method;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JSONEnumConvertor
implements JSON.Convertor {
    private static final Logger LOG = Log.getLogger(JSONEnumConvertor.class);
    private boolean _fromJSON;
    private Method _valueOf;

    public JSONEnumConvertor() {
        this(false);
    }

    public JSONEnumConvertor(boolean fromJSON) {
        try {
            Class e = Loader.loadClass("java.lang.Enum");
            this._valueOf = e.getMethod("valueOf", Class.class, String.class);
        }
        catch (Exception e) {
            throw new RuntimeException("!Enums", e);
        }
        this._fromJSON = fromJSON;
    }

    @Override
    public Object fromJSON(Map map) {
        if (!this._fromJSON) {
            throw new UnsupportedOperationException();
        }
        try {
            Class c = Loader.loadClass((String)map.get("class"));
            return this._valueOf.invoke(null, c, map.get("value"));
        }
        catch (Exception e) {
            LOG.warn(e);
            return null;
        }
    }

    @Override
    public void toJSON(Object obj, JSON.Output out) {
        if (this._fromJSON) {
            out.addClass(obj.getClass());
            out.add("value", ((Enum)obj).name());
        } else {
            out.add(((Enum)obj).name());
        }
    }
}

