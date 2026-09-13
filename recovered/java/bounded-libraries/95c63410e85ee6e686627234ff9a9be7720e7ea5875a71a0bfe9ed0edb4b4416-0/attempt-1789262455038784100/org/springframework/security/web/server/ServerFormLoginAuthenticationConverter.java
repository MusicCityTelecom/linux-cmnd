/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.server;

import java.util.function.Function;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Deprecated
public class ServerFormLoginAuthenticationConverter
implements Function<ServerWebExchange, Mono<Authentication>> {
    private String usernameParameter = "username";
    private String passwordParameter = "password";

    @Override
    @Deprecated
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return exchange.getFormData().map(data -> this.createAuthentication((MultiValueMap<String, String>)data));
    }

    private UsernamePasswordAuthenticationToken createAuthentication(MultiValueMap<String, String> data) {
        String username = (String)data.getFirst((Object)this.usernameParameter);
        String password = (String)data.getFirst((Object)this.passwordParameter);
        return UsernamePasswordAuthenticationToken.unauthenticated((Object)username, (Object)password);
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.notNull((Object)usernameParameter, (String)"usernameParameter cannot be null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.notNull((Object)passwordParameter, (String)"passwordParameter cannot be null");
        this.passwordParameter = passwordParameter;
    }
}

