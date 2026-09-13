/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoke;

import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;

public final class ParameterMappingException
extends InvalidEndpointRequestException {
    private final OperationParameter parameter;
    private final Object value;

    public ParameterMappingException(OperationParameter parameter, Object value, Throwable cause) {
        super("Failed to map " + value + " of type " + value.getClass() + " to " + parameter, "Parameter mapping failure", cause);
        this.parameter = parameter;
        this.value = value;
    }

    public OperationParameter getParameter() {
        return this.parameter;
    }

    public Object getValue() {
        return this.value;
    }
}

