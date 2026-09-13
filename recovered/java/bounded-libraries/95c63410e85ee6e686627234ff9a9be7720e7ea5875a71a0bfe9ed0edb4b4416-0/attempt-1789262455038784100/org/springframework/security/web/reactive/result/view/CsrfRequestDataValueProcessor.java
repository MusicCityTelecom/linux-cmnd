/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.web.reactive.result.view.RequestDataValueProcessor
 *  org.springframework.web.server.ServerWebExchange
 */
package org.springframework.security.web.reactive.result.view;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.lang.NonNull;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.reactive.result.view.RequestDataValueProcessor;
import org.springframework.web.server.ServerWebExchange;

public class CsrfRequestDataValueProcessor
implements RequestDataValueProcessor {
    public static final String DEFAULT_CSRF_ATTR_NAME = "_csrf";
    private static final Pattern DISABLE_CSRF_TOKEN_PATTERN = Pattern.compile("(?i)^(GET|HEAD|TRACE|OPTIONS)$");
    private static final String DISABLE_CSRF_TOKEN_ATTR = "DISABLE_CSRF_TOKEN_ATTR";

    public String processAction(ServerWebExchange exchange, String action, String httpMethod) {
        if (httpMethod != null && DISABLE_CSRF_TOKEN_PATTERN.matcher(httpMethod).matches()) {
            exchange.getAttributes().put(DISABLE_CSRF_TOKEN_ATTR, Boolean.TRUE);
        } else {
            exchange.getAttributes().remove(DISABLE_CSRF_TOKEN_ATTR);
        }
        return action;
    }

    public String processFormFieldValue(ServerWebExchange exchange, String name, String value, String type) {
        return value;
    }

    @NonNull
    public Map<String, String> getExtraHiddenFields(ServerWebExchange exchange) {
        if (Boolean.TRUE.equals(exchange.getAttribute(DISABLE_CSRF_TOKEN_ATTR))) {
            exchange.getAttributes().remove(DISABLE_CSRF_TOKEN_ATTR);
            return Collections.emptyMap();
        }
        CsrfToken token = (CsrfToken)exchange.getAttribute(DEFAULT_CSRF_ATTR_NAME);
        if (token == null) {
            return Collections.emptyMap();
        }
        return Collections.singletonMap(token.getParameterName(), token.getToken());
    }

    public String processUrl(ServerWebExchange exchange, String url) {
        return url;
    }
}

