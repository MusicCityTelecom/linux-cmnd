/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoke;

import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.ParameterMappingException;

@FunctionalInterface
public interface ParameterValueMapper {
    public static final ParameterValueMapper NONE = (parameter, value) -> value;

    public Object mapParameterValue(OperationParameter var1, Object var2) throws ParameterMappingException;
}

