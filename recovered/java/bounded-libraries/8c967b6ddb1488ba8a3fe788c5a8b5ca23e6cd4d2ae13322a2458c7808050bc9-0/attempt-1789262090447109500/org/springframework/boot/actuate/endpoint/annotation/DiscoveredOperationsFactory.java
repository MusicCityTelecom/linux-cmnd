/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodIntrospector
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotation$Adapt
 *  org.springframework.core.annotation.MergedAnnotations
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.invoke.reflect.OperationMethod;
import org.springframework.boot.actuate.endpoint.invoke.reflect.ReflectiveOperationInvoker;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;

abstract class DiscoveredOperationsFactory<O extends Operation> {
    private static final Map<OperationType, Class<? extends Annotation>> OPERATION_TYPES;
    private final ParameterValueMapper parameterValueMapper;
    private final Collection<OperationInvokerAdvisor> invokerAdvisors;

    DiscoveredOperationsFactory(ParameterValueMapper parameterValueMapper, Collection<OperationInvokerAdvisor> invokerAdvisors) {
        this.parameterValueMapper = parameterValueMapper;
        this.invokerAdvisors = invokerAdvisors;
    }

    Collection<O> createOperations(EndpointId id, Object target) {
        return MethodIntrospector.selectMethods(target.getClass(), method -> this.createOperation(id, target, method)).values();
    }

    private O createOperation(EndpointId endpointId, Object target, Method method) {
        return (O)((Operation)OPERATION_TYPES.entrySet().stream().map(entry -> this.createOperation(endpointId, target, method, (OperationType)((Object)((Object)entry.getKey())), (Class)entry.getValue())).filter(Objects::nonNull).findFirst().orElse(null));
    }

    private O createOperation(EndpointId endpointId, Object target, Method method, OperationType operationType, Class<? extends Annotation> annotationType) {
        MergedAnnotation annotation = MergedAnnotations.from((AnnotatedElement)method).get(annotationType);
        if (!annotation.isPresent()) {
            return null;
        }
        DiscoveredOperationMethod operationMethod = new DiscoveredOperationMethod(method, operationType, annotation.asAnnotationAttributes(new MergedAnnotation.Adapt[0]));
        OperationInvoker invoker = new ReflectiveOperationInvoker(target, operationMethod, this.parameterValueMapper);
        invoker = this.applyAdvisors(endpointId, operationMethod, invoker);
        return this.createOperation(endpointId, operationMethod, invoker);
    }

    private OperationInvoker applyAdvisors(EndpointId endpointId, OperationMethod operationMethod, OperationInvoker invoker) {
        if (this.invokerAdvisors != null) {
            for (OperationInvokerAdvisor advisor : this.invokerAdvisors) {
                invoker = advisor.apply(endpointId, operationMethod.getOperationType(), operationMethod.getParameters(), invoker);
            }
        }
        return invoker;
    }

    protected abstract O createOperation(EndpointId var1, DiscoveredOperationMethod var2, OperationInvoker var3);

    static {
        EnumMap<OperationType, Class<DeleteOperation>> operationTypes = new EnumMap<OperationType, Class<DeleteOperation>>(OperationType.class);
        operationTypes.put(OperationType.READ, ReadOperation.class);
        operationTypes.put(OperationType.WRITE, WriteOperation.class);
        operationTypes.put(OperationType.DELETE, DeleteOperation.class);
        OPERATION_TYPES = Collections.unmodifiableMap(operationTypes);
    }
}

