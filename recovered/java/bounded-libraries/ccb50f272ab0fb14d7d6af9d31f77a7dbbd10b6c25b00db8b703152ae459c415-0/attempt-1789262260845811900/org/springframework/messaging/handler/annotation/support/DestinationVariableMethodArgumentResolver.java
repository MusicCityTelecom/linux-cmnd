/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.handler.annotation.support;

import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.support.AbstractNamedValueMethodArgumentResolver;
import org.springframework.util.Assert;

public class DestinationVariableMethodArgumentResolver
extends AbstractNamedValueMethodArgumentResolver {
    public static final String DESTINATION_TEMPLATE_VARIABLES_HEADER = DestinationVariableMethodArgumentResolver.class.getSimpleName() + ".templateVariables";

    public DestinationVariableMethodArgumentResolver(ConversionService conversionService) {
        super(conversionService, null);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(DestinationVariable.class);
    }

    @Override
    protected AbstractNamedValueMethodArgumentResolver.NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        DestinationVariable annot = (DestinationVariable)parameter.getParameterAnnotation(DestinationVariable.class);
        Assert.state((annot != null ? 1 : 0) != 0, (String)"No DestinationVariable annotation");
        return new DestinationVariableNamedValueInfo(annot);
    }

    @Override
    @Nullable
    protected Object resolveArgumentInternal(MethodParameter parameter, Message<?> message, String name) {
        MessageHeaders headers = message.getHeaders();
        Map vars = (Map)headers.get(DESTINATION_TEMPLATE_VARIABLES_HEADER);
        return vars != null ? vars.get(name) : null;
    }

    @Override
    protected void handleMissingValue(String name, MethodParameter parameter, Message<?> message) {
        throw new MessageHandlingException(message, "Missing path template variable '" + name + "' for method parameter type [" + parameter.getParameterType() + "]");
    }

    private static final class DestinationVariableNamedValueInfo
    extends AbstractNamedValueMethodArgumentResolver.NamedValueInfo {
        private DestinationVariableNamedValueInfo(DestinationVariable annotation) {
            super(annotation.value(), true, "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n");
        }
    }
}

