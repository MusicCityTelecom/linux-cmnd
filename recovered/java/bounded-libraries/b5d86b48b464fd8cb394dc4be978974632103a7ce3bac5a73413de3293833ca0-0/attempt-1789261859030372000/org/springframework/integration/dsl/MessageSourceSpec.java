/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.util.Assert;

public abstract class MessageSourceSpec<S extends MessageSourceSpec<S, H>, H extends MessageSource<?>>
extends IntegrationComponentSpec<S, H> {
    public S messageHeaders(Map<String, Expression> headerExpressions) {
        Assert.state((boolean)(this.target instanceof AbstractMessageSource), () -> "'MessageSource' must be an instance of 'AbstractMessageSource', not " + ((MessageSource)this.target).getClass());
        ((AbstractMessageSource)this.target).setHeaderExpressions(headerExpressions);
        return (S)((Object)((MessageSourceSpec)((Object)this._this())));
    }
}

