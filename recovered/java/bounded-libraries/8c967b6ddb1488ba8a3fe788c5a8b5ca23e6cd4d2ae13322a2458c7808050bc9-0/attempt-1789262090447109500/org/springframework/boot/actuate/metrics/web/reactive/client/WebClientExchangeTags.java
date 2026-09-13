/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.web.reactive.function.client.ClientRequest
 *  org.springframework.web.reactive.function.client.ClientResponse
 *  org.springframework.web.reactive.function.client.WebClient
 */
package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.Tag;
import java.io.IOException;
import java.util.regex.Pattern;
import org.springframework.boot.actuate.metrics.http.Outcome;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

public final class WebClientExchangeTags {
    private static final String URI_TEMPLATE_ATTRIBUTE = WebClient.class.getName() + ".uriTemplate";
    private static final Tag IO_ERROR = Tag.of((String)"status", (String)"IO_ERROR");
    private static final Tag CLIENT_ERROR = Tag.of((String)"status", (String)"CLIENT_ERROR");
    private static final Pattern PATTERN_BEFORE_PATH = Pattern.compile("^https?://[^/]+/");
    private static final Tag CLIENT_NAME_NONE = Tag.of((String)"client.name", (String)"none");

    private WebClientExchangeTags() {
    }

    public static Tag method(ClientRequest request) {
        return Tag.of((String)"method", (String)request.method().name());
    }

    public static Tag uri(ClientRequest request) {
        String uri = (String)request.attribute(URI_TEMPLATE_ATTRIBUTE).orElseGet(() -> request.url().toString());
        return Tag.of((String)"uri", (String)WebClientExchangeTags.extractPath(uri));
    }

    private static String extractPath(String url) {
        String path = PATTERN_BEFORE_PATH.matcher(url).replaceFirst("");
        return path.startsWith("/") ? path : "/" + path;
    }

    public static Tag status(ClientResponse response, Throwable throwable) {
        if (response != null) {
            return Tag.of((String)"status", (String)String.valueOf(response.rawStatusCode()));
        }
        if (throwable != null) {
            return throwable instanceof IOException ? IO_ERROR : CLIENT_ERROR;
        }
        return CLIENT_ERROR;
    }

    public static Tag clientName(ClientRequest request) {
        String host = request.url().getHost();
        if (host == null) {
            return CLIENT_NAME_NONE;
        }
        return Tag.of((String)"client.name", (String)host);
    }

    public static Tag outcome(ClientResponse response) {
        Outcome outcome = response != null ? Outcome.forStatus(response.rawStatusCode()) : Outcome.UNKNOWN;
        return outcome.asTag();
    }
}

