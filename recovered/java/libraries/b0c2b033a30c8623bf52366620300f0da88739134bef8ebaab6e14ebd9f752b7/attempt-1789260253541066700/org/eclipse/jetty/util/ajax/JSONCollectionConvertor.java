/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.ajax.JSON;

public class JSONCollectionConvertor
implements JSON.Convertor {
    @Override
    public void toJSON(Object obj, JSON.Output out) {
        out.addClass(obj.getClass());
        out.add("list", ((Collection)obj).toArray());
    }

    @Override
    public Object fromJSON(Map object) {
        try {
            Collection result = (Collection)Loader.loadClass((String)object.get("class")).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Collections.addAll(result, (Object[])object.get("list"));
            return result;
        }
        catch (Exception x) {
            if (x instanceof RuntimeException) {
                throw (RuntimeException)x;
            }
            throw new RuntimeException(x);
        }
    }
}

