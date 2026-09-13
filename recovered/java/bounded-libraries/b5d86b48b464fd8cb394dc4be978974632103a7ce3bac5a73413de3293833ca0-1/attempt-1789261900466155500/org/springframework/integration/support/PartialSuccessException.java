/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support;

import java.util.Collection;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

public class PartialSuccessException
extends MessagingException {
    private static final long serialVersionUID = 8810900575763284993L;
    private final Collection<?> partialResults;
    private final Collection<?> derivedInput;

    public PartialSuccessException(Message<?> message, String description, Throwable cause, Collection<?> partialResults, Collection<?> derivedInput) {
        super(message, description, cause);
        Assert.notNull((Object)cause, (String)"Cause is required");
        this.partialResults = partialResults;
        this.derivedInput = derivedInput;
    }

    public Collection<?> getPartialResults() {
        return this.partialResults;
    }

    public Collection<?> getDerivedInput() {
        return this.derivedInput;
    }

    public <T> Collection<T> getPartialResults(Class<T> clazz) {
        return this.partialResults;
    }

    public <T> Collection<T> getDerivedInput(Class<T> clazz) {
        return this.derivedInput;
    }

    public String toString() {
        return "PartialSuccessException [" + this.getMessage() + ":" + this.getCause().getMessage() + ", partialResults=" + this.partialResults + ", derivedInput=" + this.derivedInput + ", failedMessage=" + this.getFailedMessage() + "]";
    }
}

