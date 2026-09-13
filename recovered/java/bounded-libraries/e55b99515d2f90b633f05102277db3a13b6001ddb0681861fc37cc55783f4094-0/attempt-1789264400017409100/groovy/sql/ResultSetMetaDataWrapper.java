/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObjectSupport
 *  groovy.lang.MissingPropertyException
 *  groovy.lang.ReadOnlyPropertyException
 *  org.codehaus.groovy.runtime.InvokerHelper
 */
package groovy.sql;

import groovy.lang.GroovyObjectSupport;
import groovy.lang.MissingPropertyException;
import groovy.lang.ReadOnlyPropertyException;
import java.sql.ResultSetMetaData;
import org.codehaus.groovy.runtime.InvokerHelper;

public class ResultSetMetaDataWrapper
extends GroovyObjectSupport {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private final ResultSetMetaData target;
    private final int index;

    public ResultSetMetaDataWrapper(ResultSetMetaData target, int index) {
        this.target = target;
        this.index = index;
    }

    private Object[] getIndexedArgs(Object[] originalArgs) {
        Object[] result = new Object[originalArgs.length + 1];
        result[0] = this.index;
        for (Object result[i + 1] : originalArgs) {
        }
        return result;
    }

    public Object invokeMethod(String name, Object args) {
        Object[] indexedArgs = this.getIndexedArgs((Object[])args);
        return InvokerHelper.invokeMethod((Object)this.target, (String)name, (Object)indexedArgs);
    }

    private String getPropertyGetterName(String prop) {
        if (prop == null || prop.length() < 1) {
            throw new MissingPropertyException(prop, this.target.getClass());
        }
        return "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
    }

    public Object getProperty(String property) {
        return this.invokeMethod(this.getPropertyGetterName(property), EMPTY_OBJECT_ARRAY);
    }

    public void setProperty(String property, Object newValue) {
        throw new ReadOnlyPropertyException(property, this.target.getClass());
    }
}

