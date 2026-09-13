/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.transformer;

import java.util.Map;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

public abstract class AbstractTransformer
extends IntegrationObjectSupport
implements Transformer {
    @Override
    public final Message<?> transform(Message<?> message) {
        try {
            Object result = this.doTransform(message);
            if (result == null) {
                return null;
            }
            return result instanceof Message ? (Message<Object>)result : this.getMessageBuilderFactory().withPayload(result).copyHeaders((Map<String, ?>)message.getHeaders()).build();
        }
        catch (MessageTransformationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new MessageTransformationException(message, "failed to transform message", e);
        }
    }

    protected abstract Object doTransform(Message<?> var1);
}

