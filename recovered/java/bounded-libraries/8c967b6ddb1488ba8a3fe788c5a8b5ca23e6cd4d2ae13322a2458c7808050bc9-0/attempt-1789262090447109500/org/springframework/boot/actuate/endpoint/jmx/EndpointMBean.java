/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.endpoint.jmx;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.OperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperation;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationParameter;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationResponseMapper;
import org.springframework.boot.actuate.endpoint.jmx.MBeanInfoFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EndpointMBean
implements DynamicMBean {
    private static final boolean REACTOR_PRESENT = ClassUtils.isPresent((String)"reactor.core.publisher.Mono", (ClassLoader)EndpointMBean.class.getClassLoader());
    private final JmxOperationResponseMapper responseMapper;
    private final ClassLoader classLoader;
    private final ExposableJmxEndpoint endpoint;
    private final MBeanInfo info;
    private final Map<String, JmxOperation> operations;

    EndpointMBean(JmxOperationResponseMapper responseMapper, ClassLoader classLoader, ExposableJmxEndpoint endpoint) {
        Assert.notNull((Object)responseMapper, (String)"ResponseMapper must not be null");
        Assert.notNull((Object)endpoint, (String)"Endpoint must not be null");
        this.responseMapper = responseMapper;
        this.classLoader = classLoader;
        this.endpoint = endpoint;
        this.info = new MBeanInfoFactory(responseMapper).getMBeanInfo(endpoint);
        this.operations = this.getOperations(endpoint);
    }

    private Map<String, JmxOperation> getOperations(ExposableJmxEndpoint endpoint) {
        HashMap operations = new HashMap();
        endpoint.getOperations().forEach(operation -> operations.put(operation.getName(), operation));
        return Collections.unmodifiableMap(operations);
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return this.info;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        JmxOperation operation = this.operations.get(actionName);
        if (operation == null) {
            String message = "Endpoint with id '" + this.endpoint.getEndpointId() + "' has no operation named " + actionName;
            throw new ReflectionException(new IllegalArgumentException(message), message);
        }
        ClassLoader previousClassLoader = this.overrideThreadContextClassLoader(this.classLoader);
        try {
            Object object = this.invoke(operation, params);
            return object;
        }
        finally {
            this.overrideThreadContextClassLoader(previousClassLoader);
        }
    }

    private ClassLoader overrideThreadContextClassLoader(ClassLoader classLoader) {
        if (classLoader != null) {
            try {
                return ClassUtils.overrideThreadContextClassLoader((ClassLoader)classLoader);
            }
            catch (SecurityException securityException) {
                // empty catch block
            }
        }
        return null;
    }

    private Object invoke(JmxOperation operation, Object[] params) throws MBeanException, ReflectionException {
        try {
            String[] parameterNames = (String[])operation.getParameters().stream().map(JmxOperationParameter::getName).toArray(String[]::new);
            Map<String, Object> arguments = this.getArguments(parameterNames, params);
            InvocationContext context = new InvocationContext(SecurityContext.NONE, arguments, new OperationArgumentResolver[0]);
            Object result = operation.invoke(context);
            if (REACTOR_PRESENT) {
                result = ReactiveHandler.handle(result);
            }
            return this.responseMapper.mapResponse(result);
        }
        catch (InvalidEndpointRequestException ex) {
            throw new ReflectionException(new IllegalArgumentException(ex.getMessage()), ex.getMessage());
        }
        catch (Exception ex) {
            throw new MBeanException(this.translateIfNecessary(ex), ex.getMessage());
        }
    }

    private Exception translateIfNecessary(Exception exception) {
        if (exception.getClass().getName().startsWith("java.")) {
            return exception;
        }
        return new IllegalStateException(exception.getMessage());
    }

    private Map<String, Object> getArguments(String[] parameterNames, Object[] params) {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        for (int i = 0; i < params.length; ++i) {
            arguments.put(parameterNames[i], params[i]);
        }
        return arguments;
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        throw new AttributeNotFoundException("EndpointMBeans do not support attributes");
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        throw new AttributeNotFoundException("EndpointMBeans do not support attributes");
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return new AttributeList();
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return new AttributeList();
    }

    private static class ReactiveHandler {
        private ReactiveHandler() {
        }

        static Object handle(Object result) {
            if (result instanceof Flux) {
                result = ((Flux)result).collectList();
            }
            if (result instanceof Mono) {
                return ((Mono)result).block();
            }
            return result;
        }
    }
}

