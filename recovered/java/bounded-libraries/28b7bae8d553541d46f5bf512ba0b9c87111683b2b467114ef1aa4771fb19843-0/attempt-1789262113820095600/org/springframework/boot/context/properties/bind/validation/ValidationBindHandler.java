/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.NotReadablePropertyException
 *  org.springframework.core.ResolvableType
 *  org.springframework.util.ObjectUtils
 *  org.springframework.validation.BeanPropertyBindingResult
 *  org.springframework.validation.Errors
 *  org.springframework.validation.Validator
 */
package org.springframework.boot.context.properties.bind.validation;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.boot.context.properties.bind.AbstractBindHandler;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.DataObjectPropertyName;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.core.ResolvableType;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidationBindHandler
extends AbstractBindHandler {
    private final Validator[] validators;
    private final Map<ConfigurationPropertyName, ResolvableType> boundTypes = new LinkedHashMap<ConfigurationPropertyName, ResolvableType>();
    private final Map<ConfigurationPropertyName, Object> boundResults = new LinkedHashMap<ConfigurationPropertyName, Object>();
    private final Set<ConfigurationProperty> boundProperties = new LinkedHashSet<ConfigurationProperty>();
    private BindValidationException exception;

    public ValidationBindHandler(Validator ... validators) {
        this.validators = validators;
    }

    public ValidationBindHandler(BindHandler parent, Validator ... validators) {
        super(parent);
        this.validators = validators;
    }

    @Override
    public <T> Bindable<T> onStart(ConfigurationPropertyName name, Bindable<T> target, BindContext context) {
        this.boundTypes.put(name, target.getType());
        return super.onStart(name, target, context);
    }

    @Override
    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        this.boundResults.put(name, result);
        if (context.getConfigurationProperty() != null) {
            this.boundProperties.add(context.getConfigurationProperty());
        }
        return super.onSuccess(name, target, context, result);
    }

    @Override
    public Object onFailure(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Exception error) throws Exception {
        Object result = super.onFailure(name, target, context, error);
        if (result != null) {
            this.clear();
            this.boundResults.put(name, result);
        }
        this.validate(name, target, context, result);
        return result;
    }

    private void clear() {
        this.boundTypes.clear();
        this.boundResults.clear();
        this.boundProperties.clear();
        this.exception = null;
    }

    @Override
    public void onFinish(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) throws Exception {
        this.validate(name, target, context, result);
        super.onFinish(name, target, context, result);
    }

    private void validate(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        if (this.exception == null) {
            Object validationTarget = this.getValidationTarget(target, context, result);
            Class validationType = target.getBoxedType().resolve();
            if (validationTarget != null) {
                this.validateAndPush(name, validationTarget, validationType);
            }
        }
        if (context.getDepth() == 0 && this.exception != null) {
            throw this.exception;
        }
    }

    private Object getValidationTarget(Bindable<?> target, BindContext context, Object result) {
        if (result != null) {
            return result;
        }
        if (context.getDepth() == 0 && target.getValue() != null) {
            return target.getValue().get();
        }
        return null;
    }

    private void validateAndPush(ConfigurationPropertyName name, Object target, Class<?> type) {
        ValidationResult result = null;
        for (Validator validator : this.validators) {
            if (!validator.supports(type)) continue;
            result = result != null ? result : new ValidationResult(name, target);
            validator.validate(target, (Errors)result);
        }
        if (result != null && result.hasErrors()) {
            this.exception = new BindValidationException(result.getValidationErrors());
        }
    }

    private class ValidationResult
    extends BeanPropertyBindingResult {
        private final ConfigurationPropertyName name;

        protected ValidationResult(ConfigurationPropertyName name, Object target) {
            super(target, null);
            this.name = name;
        }

        public String getObjectName() {
            return this.name.toString();
        }

        public Class<?> getFieldType(String field) {
            Class resolved;
            ResolvableType type = (ResolvableType)this.getBoundField(ValidationBindHandler.this.boundTypes, field);
            Class clazz = resolved = type != null ? type.resolve() : null;
            if (resolved != null) {
                return resolved;
            }
            return super.getFieldType(field);
        }

        protected Object getActualFieldValue(String field) {
            Object boundField = this.getBoundField(ValidationBindHandler.this.boundResults, field);
            if (boundField != null) {
                return boundField;
            }
            try {
                return super.getActualFieldValue(field);
            }
            catch (Exception ex) {
                if (this.isPropertyNotReadable(ex)) {
                    return null;
                }
                throw ex;
            }
        }

        private boolean isPropertyNotReadable(Throwable ex) {
            while (ex != null) {
                if (ex instanceof NotReadablePropertyException) {
                    return true;
                }
                ex = ex.getCause();
            }
            return false;
        }

        private <T> T getBoundField(Map<ConfigurationPropertyName, T> boundFields, String field) {
            try {
                ConfigurationPropertyName name = this.getName(field);
                T bound = boundFields.get(name);
                if (bound != null) {
                    return bound;
                }
                if (name.hasIndexedElement()) {
                    for (Map.Entry<ConfigurationPropertyName, T> entry : boundFields.entrySet()) {
                        if (!this.isFieldNameMatch(entry.getKey(), name)) continue;
                        return entry.getValue();
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return null;
        }

        private boolean isFieldNameMatch(ConfigurationPropertyName name, ConfigurationPropertyName fieldName) {
            if (name.getNumberOfElements() != fieldName.getNumberOfElements()) {
                return false;
            }
            for (int i = 0; i < name.getNumberOfElements(); ++i) {
                String fieldElement;
                String element = name.getElement(i, ConfigurationPropertyName.Form.ORIGINAL);
                if (ObjectUtils.nullSafeEquals((Object)element, (Object)(fieldElement = fieldName.getElement(i, ConfigurationPropertyName.Form.ORIGINAL)))) continue;
                return false;
            }
            return true;
        }

        private ConfigurationPropertyName getName(String field) {
            return this.name.append(DataObjectPropertyName.toDashedForm(field));
        }

        ValidationErrors getValidationErrors() {
            Set boundProperties = ValidationBindHandler.this.boundProperties.stream().filter(property -> this.name.isAncestorOf(property.getName())).collect(Collectors.toCollection(LinkedHashSet::new));
            return new ValidationErrors(this.name, boundProperties, this.getAllErrors());
        }
    }
}

