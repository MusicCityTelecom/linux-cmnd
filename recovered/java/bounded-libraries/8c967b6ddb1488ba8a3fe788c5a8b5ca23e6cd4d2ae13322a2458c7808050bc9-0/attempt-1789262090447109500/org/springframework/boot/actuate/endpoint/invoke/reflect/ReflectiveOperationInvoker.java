/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.style.ToStringCreator
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.actuate.endpoint.invoke.reflect;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.invoke.MissingParametersException;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.invoke.reflect.OperationMethod;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class ReflectiveOperationInvoker
implements OperationInvoker {
    private final Object target;
    private final OperationMethod operationMethod;
    private final ParameterValueMapper parameterValueMapper;

    public ReflectiveOperationInvoker(Object target, OperationMethod operationMethod, ParameterValueMapper parameterValueMapper) {
        Assert.notNull((Object)target, (String)"Target must not be null");
        Assert.notNull((Object)operationMethod, (String)"OperationMethod must not be null");
        Assert.notNull((Object)parameterValueMapper, (String)"ParameterValueMapper must not be null");
        ReflectionUtils.makeAccessible((Method)operationMethod.getMethod());
        this.target = target;
        this.operationMethod = operationMethod;
        this.parameterValueMapper = parameterValueMapper;
    }

    @Override
    public Object invoke(InvocationContext context) {
        this.validateRequiredParameters(context);
        Method method = this.operationMethod.getMethod();
        Object[] resolvedArguments = this.resolveArguments(context);
        ReflectionUtils.makeAccessible((Method)method);
        return ReflectionUtils.invokeMethod((Method)method, (Object)this.target, (Object[])resolvedArguments);
    }

    private void validateRequiredParameters(InvocationContext context) {
        Set<OperationParameter> missing = this.operationMethod.getParameters().stream().filter(parameter -> this.isMissing(context, (OperationParameter)parameter)).collect(Collectors.toSet());
        if (!missing.isEmpty()) {
            throw new MissingParametersException(missing);
        }
    }

    private boolean isMissing(InvocationContext context, OperationParameter parameter) {
        if (!parameter.isMandatory()) {
            return false;
        }
        if (context.canResolve(parameter.getType())) {
            return false;
        }
        return context.getArguments().get(parameter.getName()) == null;
    }

    private Object[] resolveArguments(InvocationContext context) {
        return this.operationMethod.getParameters().stream().map(parameter -> this.resolveArgument((OperationParameter)parameter, context)).toArray();
    }

    private Object resolveArgument(OperationParameter parameter, InvocationContext context) {
        Object resolvedByType = context.resolveArgument(parameter.getType());
        if (resolvedByType != null) {
            return resolvedByType;
        }
        Object value = context.getArguments().get(parameter.getName());
        return this.parameterValueMapper.mapParameterValue(parameter, value);
    }

    public String toString() {
        return new ToStringCreator((Object)this).append("target", this.target).append("method", (Object)this.operationMethod).toString();
    }
}

