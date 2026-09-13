/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.convert.ApplicationConversionService
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.invoke.convert;

import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.ParameterMappingException;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;

public class ConversionServiceParameterValueMapper
implements ParameterValueMapper {
    private final ConversionService conversionService;

    public ConversionServiceParameterValueMapper() {
        this(ApplicationConversionService.getSharedInstance());
    }

    public ConversionServiceParameterValueMapper(ConversionService conversionService) {
        Assert.notNull((Object)conversionService, (String)"ConversionService must not be null");
        this.conversionService = conversionService;
    }

    @Override
    public Object mapParameterValue(OperationParameter parameter, Object value) throws ParameterMappingException {
        try {
            return this.conversionService.convert(value, parameter.getType());
        }
        catch (Exception ex) {
            throw new ParameterMappingException(parameter, value, (Throwable)ex);
        }
    }
}

