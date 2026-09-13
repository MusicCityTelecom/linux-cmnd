/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.MutablePropertyValues
 *  org.springframework.beans.PropertyAccessorFactory
 *  org.springframework.beans.PropertyValue
 *  org.springframework.beans.PropertyValues
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.EmbeddedValueResolver
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotation$Adapt
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.integration.support.management.IntegrationManagedResource
 *  org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource
 *  org.springframework.jmx.export.metadata.InvalidMetadataException
 *  org.springframework.jmx.export.metadata.ManagedResource
 *  org.springframework.util.StringValueResolver
 */
package org.springframework.integration.monitor;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.jmx.export.metadata.ManagedResource;
import org.springframework.util.StringValueResolver;

public class IntegrationJmxAttributeSource
extends AnnotationJmxAttributeSource {
    private StringValueResolver valueResolver;

    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        if (beanFactory instanceof ConfigurableBeanFactory) {
            this.valueResolver = new EmbeddedValueResolver((ConfigurableBeanFactory)beanFactory);
        }
    }

    public ManagedResource getManagedResource(Class<?> beanClass) throws InvalidMetadataException {
        Class target;
        MergedAnnotation ann = MergedAnnotations.from(beanClass, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(IntegrationManagedResource.class).withNonMergedAttributes();
        if (!ann.isPresent()) {
            return null;
        }
        Class declaringClass = (Class)ann.getSource();
        Class clazz = target = declaringClass != null && !declaringClass.isInterface() ? declaringClass : beanClass;
        if (!Modifier.isPublic(target.getModifiers())) {
            throw new InvalidMetadataException("@IntegrationManagedResource class '" + target.getName() + "' must be public");
        }
        ManagedResource bean = new ManagedResource();
        Map map = ann.asMap(new MergedAnnotation.Adapt[0]);
        ArrayList list = new ArrayList(map.size());
        map.forEach((attrName, attrValue) -> {
            if (!"value".equals(attrName)) {
                Object value = attrValue;
                if (this.valueResolver != null && value instanceof String) {
                    value = this.valueResolver.resolveStringValue((String)value);
                }
                list.add(new PropertyValue(attrName, value));
            }
        });
        PropertyAccessorFactory.forBeanPropertyAccess((Object)bean).setPropertyValues((PropertyValues)new MutablePropertyValues(list));
        return bean;
    }
}

