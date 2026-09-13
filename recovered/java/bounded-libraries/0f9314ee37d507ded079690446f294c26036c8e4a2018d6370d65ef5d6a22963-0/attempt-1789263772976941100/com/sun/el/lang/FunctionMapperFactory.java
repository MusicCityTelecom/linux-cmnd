/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.FunctionMapper
 */
package com.sun.el.lang;

import com.sun.el.lang.FunctionMapperImpl;
import java.lang.reflect.Method;
import javax.el.FunctionMapper;

public class FunctionMapperFactory
extends FunctionMapper {
    protected FunctionMapperImpl memento = null;
    protected FunctionMapper target;

    public FunctionMapperFactory(FunctionMapper mapper) {
        if (mapper == null) {
            throw new NullPointerException("FunctionMapper target cannot be null");
        }
        this.target = mapper;
    }

    public Method resolveFunction(String prefix, String localName) {
        Method m;
        if (this.memento == null) {
            this.memento = new FunctionMapperImpl();
        }
        if ((m = this.target.resolveFunction(prefix, localName)) != null) {
            this.memento.addFunction(prefix, localName, m);
        }
        return m;
    }

    public FunctionMapper create() {
        return this.memento;
    }
}

