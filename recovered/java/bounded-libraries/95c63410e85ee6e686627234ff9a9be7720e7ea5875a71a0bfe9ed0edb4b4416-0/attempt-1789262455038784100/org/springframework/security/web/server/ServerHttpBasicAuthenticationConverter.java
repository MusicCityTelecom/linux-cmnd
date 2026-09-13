/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Deprecated
public class ServerHttpBasicAuthenticationConverter
implements Function<ServerWebExchange, Mono<Authentication>> {
    public static final String BASIC = "Basic ";
    private Charset credentialsCharset = StandardCharsets.UTF_8;

    @Override
    @Deprecated
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst("Authorization");
        if (!StringUtils.startsWithIgnoreCase((String)authorization, (String)"basic ")) {
            return Mono.empty();
        }
        String credentials = authorization.length() <= BASIC.length() ? "" : authorization.substring(BASIC.length());
        String decoded = new String(this.base64Decode(credentials), this.credentialsCharset);
        String[] parts = decoded.split(":", 2);
        if (parts.length != 2) {
            return Mono.empty();
        }
        return Mono.just((Object)UsernamePasswordAuthenticationToken.unauthenticated((Object)parts[0], (Object)parts[1]));
    }

    private byte[] base64Decode(String value) {
        try {
            return Base64.getDecoder().decode(value);
        }
        catch (Exception ex) {
            return new byte[0];
        }
    }

    public final void setCredentialsCharset(Charset credentialsCharset) {
        Assert.notNull((Object)credentialsCharset, (String)"credentialsCharset cannot be null");
        this.credentialsCharset = credentialsCharset;
    }
}

