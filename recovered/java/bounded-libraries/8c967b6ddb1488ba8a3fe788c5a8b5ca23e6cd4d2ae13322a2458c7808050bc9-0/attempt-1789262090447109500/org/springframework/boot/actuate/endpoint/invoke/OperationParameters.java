/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.invoke;

import java.util.stream.Stream;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;

public interface OperationParameters
extends Iterable<OperationParameter> {
    default public boolean hasParameters() {
        return this.getParameterCount() > 0;
    }

    public int getParameterCount();

    default public boolean hasMandatoryParameter() {
        return this.stream().anyMatch(OperationParameter::isMandatory);
    }

    public OperationParameter get(int var1);

    public Stream<OperationParameter> stream();
}

