/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.SimpleTypeConverter
 *  org.springframework.beans.TypeConverter
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.expression.TypeConverter
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.util;

import java.beans.PropertyEditor;
import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.TypeConverter;
import org.springframework.integration.history.MessageHistory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.ClassUtils;

public class BeanFactoryTypeConverter
implements TypeConverter,
BeanFactoryAware {
    private SimpleTypeConverter delegate = new SimpleTypeConverter();
    private ConversionService conversionService;
    private volatile boolean haveCalledDelegateGetDefaultEditor;

    public BeanFactoryTypeConverter() {
        this.conversionService = DefaultConversionService.getSharedInstance();
    }

    public BeanFactoryTypeConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        org.springframework.beans.TypeConverter typeConverter;
        if (beanFactory instanceof ConfigurableBeanFactory && (typeConverter = ((ConfigurableBeanFactory)beanFactory).getTypeConverter()) instanceof SimpleTypeConverter) {
            this.delegate = (SimpleTypeConverter)typeConverter;
        }
    }

    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        if (this.conversionService.canConvert(sourceType, targetType)) {
            return true;
        }
        if (!String.class.isAssignableFrom(sourceType) && !String.class.isAssignableFrom(targetType)) {
            return false;
        }
        if (!String.class.isAssignableFrom(sourceType)) {
            return this.delegate.findCustomEditor(sourceType, null) != null || this.getDefaultEditor(sourceType) != null;
        }
        return this.delegate.findCustomEditor(targetType, null) != null || this.getDefaultEditor(targetType) != null;
    }

    public boolean canConvert(TypeDescriptor sourceTypeDescriptor, TypeDescriptor targetTypeDescriptor) {
        if (this.conversionService.canConvert(sourceTypeDescriptor, targetTypeDescriptor)) {
            return true;
        }
        Class sourceType = sourceTypeDescriptor.getObjectType();
        Class targetType = targetTypeDescriptor.getObjectType();
        return this.canConvert(sourceType, targetType);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object convertValue(Object value, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if ((targetType.getType() == Void.class || targetType.getType() == Void.TYPE) && value == null) {
            return null;
        }
        if (sourceType != null) {
            Class sourceClass = sourceType.getType();
            Class targetClass = targetType.getType();
            if (sourceClass == MessageHeaders.class && targetClass == MessageHeaders.class || sourceClass == MessageHistory.class && targetClass == MessageHistory.class || sourceType.isAssignableTo(targetType) && ClassUtils.isPrimitiveArray((Class)sourceClass)) {
                return value;
            }
        }
        if (this.conversionService.canConvert(sourceType, targetType)) {
            return this.conversionService.convert(value, sourceType, targetType);
        }
        Object editorResult = this.valueFromEditorIfAny(value, sourceType.getType(), targetType);
        if (editorResult == null) {
            SimpleTypeConverter simpleTypeConverter = this.delegate;
            synchronized (simpleTypeConverter) {
                return this.delegate.convertIfNecessary(value, targetType.getType());
            }
        }
        return editorResult;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertyEditor getDefaultEditor(Class<?> sourceType) {
        PropertyEditor defaultEditor;
        if (this.haveCalledDelegateGetDefaultEditor) {
            defaultEditor = this.delegate.getDefaultEditor(sourceType);
        } else {
            BeanFactoryTypeConverter beanFactoryTypeConverter = this;
            synchronized (beanFactoryTypeConverter) {
                defaultEditor = this.delegate.getDefaultEditor(sourceType);
            }
            this.haveCalledDelegateGetDefaultEditor = true;
        }
        return defaultEditor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    private Object valueFromEditorIfAny(Object value, Class<?> sourceClass, TypeDescriptor targetType) {
        if (!String.class.isAssignableFrom(sourceClass)) {
            PropertyEditor editor = this.delegate.findCustomEditor(sourceClass, null);
            if (editor == null) {
                editor = this.getDefaultEditor(sourceClass);
            }
            if (editor != null) {
                String text;
                PropertyEditor propertyEditor = editor;
                synchronized (propertyEditor) {
                    editor.setValue(value);
                    text = editor.getAsText();
                }
                if (String.class.isAssignableFrom(targetType.getType())) {
                    return text;
                }
                return this.convertValue(text, TypeDescriptor.valueOf(String.class), targetType);
            }
        }
        return null;
    }
}

