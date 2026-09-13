/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.module.paramnames;

import java.io.Serializable;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

class ParameterExtractor
implements Serializable {
    private static final long serialVersionUID = 1L;

    ParameterExtractor() {
    }

    public Parameter[] getParameters(Executable executable) {
        return executable.getParameters();
    }
}

