/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.web.embedded.netty;

import java.util.function.Function;
import reactor.netty.http.server.HttpServer;

@FunctionalInterface
public interface NettyServerCustomizer
extends Function<HttpServer, HttpServer> {
}

