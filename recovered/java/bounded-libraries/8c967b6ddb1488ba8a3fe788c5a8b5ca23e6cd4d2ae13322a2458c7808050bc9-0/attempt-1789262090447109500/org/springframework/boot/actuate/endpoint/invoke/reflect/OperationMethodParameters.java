/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.invoke.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameters;
import org.springframework.boot.actuate.endpoint.invoke.reflect.OperationMethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

class OperationMethodParameters
implements OperationParameters {
    private final List<OperationParameter> operationParameters;

    OperationMethodParameters(Method method, ParameterNameDiscoverer parameterNameDiscoverer) {
        Assert.notNull((Object)method, (String)"Method must not be null");
        Assert.notNull((Object)parameterNameDiscoverer, (String)"ParameterNameDiscoverer must not be null");
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        Assert.state((parameterNames != null ? 1 : 0) != 0, () -> "Failed to extract parameter names for " + method);
        this.operationParameters = this.getOperationParameters(parameters, parameterNames);
    }

    private List<OperationParameter> getOperationParameters(Parameter[] parameters, String[] names) {
        ArrayList<OperationMethodParameter> operationParameters = new ArrayList<OperationMethodParameter>(parameters.length);
        for (int i = 0; i < names.length; ++i) {
            operationParameters.add(new OperationMethodParameter(names[i], parameters[i]));
        }
        return Collections.unmodifiableList(operationParameters);
    }

    @Override
    public int getParameterCount() {
        return this.operationParameters.size();
    }

    @Override
    public OperationParameter get(int index) {
        return this.operationParameters.get(index);
    }

    @Override
    public Iterator<OperationParameter> iterator() {
        return this.operationParameters.iterator();
    }

    @Override
    public Stream<OperationParameter> stream() {
        return this.operationParameters.stream();
    }
}

