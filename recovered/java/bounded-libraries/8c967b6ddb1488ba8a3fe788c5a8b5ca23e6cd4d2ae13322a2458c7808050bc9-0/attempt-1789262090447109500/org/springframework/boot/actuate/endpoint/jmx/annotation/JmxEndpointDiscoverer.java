/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 */
package org.springframework.boot.actuate.endpoint.jmx.annotation;

import java.util.Collection;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.JmxEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperation;
import org.springframework.boot.actuate.endpoint.jmx.annotation.DiscoveredJmxEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.annotation.DiscoveredJmxOperation;
import org.springframework.context.ApplicationContext;

public class JmxEndpointDiscoverer
extends EndpointDiscoverer<ExposableJmxEndpoint, JmxOperation>
implements JmxEndpointsSupplier {
    public JmxEndpointDiscoverer(ApplicationContext applicationContext, ParameterValueMapper parameterValueMapper, Collection<OperationInvokerAdvisor> invokerAdvisors, Collection<EndpointFilter<ExposableJmxEndpoint>> filters) {
        super(applicationContext, parameterValueMapper, invokerAdvisors, filters);
    }

    @Override
    protected ExposableJmxEndpoint createEndpoint(Object endpointBean, EndpointId id, boolean enabledByDefault, Collection<JmxOperation> operations) {
        return new DiscoveredJmxEndpoint((EndpointDiscoverer<?, ?>)this, endpointBean, id, enabledByDefault, operations);
    }

    @Override
    protected JmxOperation createOperation(EndpointId endpointId, DiscoveredOperationMethod operationMethod, OperationInvoker invoker) {
        return new DiscoveredJmxOperation(endpointId, operationMethod, invoker);
    }

    @Override
    protected EndpointDiscoverer.OperationKey createOperationKey(JmxOperation operation) {
        return new EndpointDiscoverer.OperationKey(operation.getName(), () -> "MBean call '" + operation.getName() + "'");
    }
}

