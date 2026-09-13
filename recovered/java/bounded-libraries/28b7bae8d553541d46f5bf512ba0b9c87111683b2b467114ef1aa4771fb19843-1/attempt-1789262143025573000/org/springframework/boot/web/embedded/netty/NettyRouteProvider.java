/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.netty.http.server.HttpServerRoutes
 */
package org.springframework.boot.web.embedded.netty;

import java.util.function.Function;
import reactor.netty.http.server.HttpServerRoutes;

@FunctionalInterface
public interface NettyRouteProvider
extends Function<HttpServerRoutes, HttpServerRoutes> {
}

