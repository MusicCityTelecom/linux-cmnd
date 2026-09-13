/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.core;

import java.util.LinkedHashMap;
import javax.ws.rs.core.AbstractMultivaluedMap;
import javax.ws.rs.core.MultivaluedMap;

public class Form {
    private final MultivaluedMap<String, String> parameters;

    public Form() {
        this((MultivaluedMap<String, String>)new AbstractMultivaluedMap<String, String>(new LinkedHashMap()){});
    }

    public Form(String parameterName, String parameterValue) {
        this();
        this.parameters.add(parameterName, parameterValue);
    }

    public Form(MultivaluedMap<String, String> store) {
        this.parameters = store;
    }

    public Form param(String name, String value) {
        this.parameters.add(name, value);
        return this;
    }

    public MultivaluedMap<String, String> asMap() {
        return this.parameters;
    }
}

