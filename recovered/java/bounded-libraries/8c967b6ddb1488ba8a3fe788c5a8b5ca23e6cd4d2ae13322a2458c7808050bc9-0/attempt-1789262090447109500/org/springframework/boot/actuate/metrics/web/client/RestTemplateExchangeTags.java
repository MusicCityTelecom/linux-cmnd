/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.http.HttpRequest
 *  org.springframework.http.client.ClientHttpResponse
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.metrics.web.client;

import io.micrometer.core.instrument.Tag;
import java.io.IOException;
import java.util.regex.Pattern;
import org.springframework.boot.actuate.metrics.http.Outcome;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public final class RestTemplateExchangeTags {
    private static final Pattern STRIP_URI_PATTERN = Pattern.compile("^https?://[^/]+/");

    private RestTemplateExchangeTags() {
    }

    public static Tag method(HttpRequest request) {
        return Tag.of((String)"method", (String)request.getMethod().name());
    }

    public static Tag uri(HttpRequest request) {
        return Tag.of((String)"uri", (String)RestTemplateExchangeTags.ensureLeadingSlash(RestTemplateExchangeTags.stripUri(request.getURI().toString())));
    }

    public static Tag uri(String uriTemplate) {
        String uri = StringUtils.hasText((String)uriTemplate) ? uriTemplate : "none";
        return Tag.of((String)"uri", (String)RestTemplateExchangeTags.ensureLeadingSlash(RestTemplateExchangeTags.stripUri(uri)));
    }

    private static String stripUri(String uri) {
        return STRIP_URI_PATTERN.matcher(uri).replaceAll("");
    }

    private static String ensureLeadingSlash(String url) {
        return url == null || url.startsWith("/") ? url : "/" + url;
    }

    public static Tag status(ClientHttpResponse response) {
        return Tag.of((String)"status", (String)RestTemplateExchangeTags.getStatusMessage(response));
    }

    private static String getStatusMessage(ClientHttpResponse response) {
        try {
            if (response == null) {
                return "CLIENT_ERROR";
            }
            return String.valueOf(response.getRawStatusCode());
        }
        catch (IOException ex) {
            return "IO_ERROR";
        }
    }

    public static Tag clientName(HttpRequest request) {
        String host = request.getURI().getHost();
        if (host == null) {
            host = "none";
        }
        return Tag.of((String)"client.name", (String)host);
    }

    public static Tag outcome(ClientHttpResponse response) {
        try {
            if (response != null) {
                return Outcome.forStatus(response.getRawStatusCode()).asTag();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return Outcome.UNKNOWN.asTag();
    }
}

