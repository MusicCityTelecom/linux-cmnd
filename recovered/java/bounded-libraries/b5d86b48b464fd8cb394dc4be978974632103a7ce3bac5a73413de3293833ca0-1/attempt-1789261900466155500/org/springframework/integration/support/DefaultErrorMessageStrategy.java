/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.support.ErrorMessage
 */
package org.springframework.integration.support;

import org.springframework.core.AttributeAccessor;
import org.springframework.integration.support.ErrorMessageStrategy;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;

public class DefaultErrorMessageStrategy
implements ErrorMessageStrategy {
    @Override
    public ErrorMessage buildErrorMessage(Throwable throwable, @Nullable AttributeAccessor attributes) {
        Object inputMessage;
        Object object = inputMessage = attributes == null ? null : attributes.getAttribute("inputMessage");
        if (inputMessage instanceof Message) {
            return new ErrorMessage(throwable, (Message)inputMessage);
        }
        return new ErrorMessage(throwable);
    }
}

