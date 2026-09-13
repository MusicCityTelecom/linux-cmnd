/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.jmx;

import java.util.List;
import javax.management.MBeanInfo;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.jmx.EndpointMBean;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperation;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationParameter;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationResponseMapper;

class MBeanInfoFactory {
    private static final ModelMBeanAttributeInfo[] NO_ATTRIBUTES = new ModelMBeanAttributeInfo[0];
    private static final ModelMBeanConstructorInfo[] NO_CONSTRUCTORS = new ModelMBeanConstructorInfo[0];
    private static final ModelMBeanNotificationInfo[] NO_NOTIFICATIONS = new ModelMBeanNotificationInfo[0];
    private final JmxOperationResponseMapper responseMapper;

    MBeanInfoFactory(JmxOperationResponseMapper responseMapper) {
        this.responseMapper = responseMapper;
    }

    MBeanInfo getMBeanInfo(ExposableJmxEndpoint endpoint) {
        String className = EndpointMBean.class.getName();
        String description = this.getDescription(endpoint);
        ModelMBeanOperationInfo[] operations = this.getMBeanOperations(endpoint);
        return new ModelMBeanInfoSupport(className, description, NO_ATTRIBUTES, NO_CONSTRUCTORS, operations, NO_NOTIFICATIONS);
    }

    private String getDescription(ExposableJmxEndpoint endpoint) {
        return "MBean operations for endpoint " + endpoint.getEndpointId();
    }

    private ModelMBeanOperationInfo[] getMBeanOperations(ExposableJmxEndpoint endpoint) {
        return (ModelMBeanOperationInfo[])endpoint.getOperations().stream().map(this::getMBeanOperation).toArray(ModelMBeanOperationInfo[]::new);
    }

    private ModelMBeanOperationInfo getMBeanOperation(JmxOperation operation) {
        String name = operation.getName();
        String description = operation.getDescription();
        MBeanParameterInfo[] signature = this.getSignature(operation.getParameters());
        String type = this.getType(operation.getOutputType());
        int impact = this.getImpact(operation.getType());
        return new ModelMBeanOperationInfo(name, description, signature, type, impact);
    }

    private MBeanParameterInfo[] getSignature(List<JmxOperationParameter> parameters) {
        return (MBeanParameterInfo[])parameters.stream().map(this::getMBeanParameter).toArray(MBeanParameterInfo[]::new);
    }

    private MBeanParameterInfo getMBeanParameter(JmxOperationParameter parameter) {
        return new MBeanParameterInfo(parameter.getName(), parameter.getType().getName(), parameter.getDescription());
    }

    private int getImpact(OperationType operationType) {
        if (operationType == OperationType.READ) {
            return 0;
        }
        if (operationType == OperationType.WRITE || operationType == OperationType.DELETE) {
            return 1;
        }
        return 3;
    }

    private String getType(Class<?> outputType) {
        return this.responseMapper.mapResponseType(outputType).getName();
    }
}

