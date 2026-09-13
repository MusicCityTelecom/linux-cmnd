/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodReturnValueHandler;
import org.springframework.util.Assert;

public class ReturnValueHandlerConfigurer {
    private final List<HandlerMethodReturnValueHandler> customHandlers = new ArrayList<HandlerMethodReturnValueHandler>(8);

    public void addCustomHandler(HandlerMethodReturnValueHandler ... handlers) {
        Assert.notNull((Object)handlers, (String)"'handlers' must not be null");
        this.customHandlers.addAll(Arrays.asList(handlers));
    }

    public List<HandlerMethodReturnValueHandler> getCustomHandlers() {
        return this.customHandlers;
    }
}

