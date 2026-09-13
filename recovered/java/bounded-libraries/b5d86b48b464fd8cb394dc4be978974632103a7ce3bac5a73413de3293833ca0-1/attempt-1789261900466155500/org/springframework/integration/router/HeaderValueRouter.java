/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.router;

import java.util.Collections;
import java.util.List;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class HeaderValueRouter
extends AbstractMappingMessageRouter {
    private final String headerName;

    public HeaderValueRouter(String headerName) {
        Assert.notNull((Object)headerName, (String)"'headerName' must not be null");
        this.headerName = headerName;
    }

    @Override
    protected List<Object> getChannelKeys(Message<?> message) {
        String[] value = message.getHeaders().get((Object)this.headerName);
        if (value instanceof String && ((String)value).indexOf(44) != -1) {
            value = StringUtils.tokenizeToStringArray((String)((String)value), (String)",");
        }
        return Collections.singletonList(value);
    }
}

