/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.BeanProperty
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.InjectableValues$Std
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper
 */
package org.apereo.cas.util.serialization;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.apereo.cas.util.model.TriStateBoolean;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;

public class JacksonInjectableValueSupplier
extends InjectableValues.Std {
    private static final long serialVersionUID = -7327438202032303292L;

    public JacksonInjectableValueSupplier(Supplier<? extends Map<String, Object>> valueSupplier) {
        super(valueSupplier.get());
    }

    public Object findInjectableValue(Object valueId, DeserializationContext ctxt, BeanProperty beanProperty, Object beanInstance) {
        String key = valueId.toString();
        Object valueToReturn = this._values.get(key);
        DirectFieldAccessFallbackBeanWrapper wrapper = new DirectFieldAccessFallbackBeanWrapper(beanInstance);
        if (!this._values.containsKey(key)) {
            return wrapper.getPropertyValue(key);
        }
        Class propType = Objects.requireNonNull(wrapper.getPropertyType(key));
        if (propType.equals(TriStateBoolean.class)) {
            return TriStateBoolean.valueOf((String)valueToReturn.toString().toUpperCase());
        }
        return valueToReturn;
    }
}

