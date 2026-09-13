/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilterChain
 */
package org.springframework.security.web.server;

import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

public class WebFilterExchange {
    private final ServerWebExchange exchange;
    private final WebFilterChain chain;

    public WebFilterExchange(ServerWebExchange exchange, WebFilterChain chain) {
        Assert.notNull((Object)exchange, (String)"exchange cannot be null");
        Assert.notNull((Object)chain, (String)"chain cannot be null");
        this.exchange = exchange;
        this.chain = chain;
    }

    public ServerWebExchange getExchange() {
        return this.exchange;
    }

    public WebFilterChain getChain() {
        return this.chain;
    }
}

