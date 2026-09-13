/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.support.ConversionServiceFactoryBean
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.GenericConversionService
 */
package org.springframework.integration.config;

import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

class CustomConversionServiceFactoryBean
extends ConversionServiceFactoryBean {
    CustomConversionServiceFactoryBean() {
    }

    public ConversionService getObject() {
        ConversionService service = super.getObject();
        if (service instanceof GenericConversionService) {
            ((GenericConversionService)service).removeConvertible(Object.class, Object.class);
        }
        return service;
    }
}

