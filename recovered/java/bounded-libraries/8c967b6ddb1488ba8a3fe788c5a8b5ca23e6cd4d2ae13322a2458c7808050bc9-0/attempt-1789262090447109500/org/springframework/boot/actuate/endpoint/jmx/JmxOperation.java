/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx;

import java.util.List;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationParameter;

public interface JmxOperation
extends Operation {
    public String getName();

    public Class<?> getOutputType();

    public String getDescription();

    public List<JmxOperationParameter> getParameters();
}

