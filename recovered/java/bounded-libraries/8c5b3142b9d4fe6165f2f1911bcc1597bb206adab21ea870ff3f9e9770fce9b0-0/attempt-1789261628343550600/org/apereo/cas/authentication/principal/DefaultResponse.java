/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Response
 *  org.apereo.cas.authentication.principal.Response$ResponseType
 *  org.apereo.cas.util.EncodingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Response;
import org.apereo.cas.util.EncodingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResponse
implements Response {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResponse.class);
    private static final Pattern NON_PRINTABLE = Pattern.compile("[\\x00-\\x1F\\x7F]+");
    private static final int RESPONSE_INITIAL_CAPACITY = 200;
    private static final long serialVersionUID = -8251042088720603062L;
    private final Response.ResponseType responseType;
    private final String url;
    private final Map<String, String> attributes;

    public static Response getPostResponse(String url, Map<String, String> attributes) {
        return new DefaultResponse(Response.ResponseType.POST, url, attributes);
    }

    public static Response getHeaderResponse(String url, Map<String, String> attributes) {
        return new DefaultResponse(Response.ResponseType.HEADER, url, attributes);
    }

    public static Response getRedirectResponse(String url, Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder(parameters.size() * 200);
        String sanitizedUrl = DefaultResponse.sanitizeUrl(url);
        LOGGER.trace("Sanitized URL for redirect response is [{}]", (Object)sanitizedUrl);
        List fragmentSplit = Splitter.on((String)"#").splitToList((CharSequence)sanitizedUrl);
        builder.append((String)fragmentSplit.get(0));
        String params = parameters.entrySet().stream().filter(entry -> entry.getValue() != null).map(entry -> String.join((CharSequence)"=", (CharSequence)entry.getKey(), EncodingUtils.urlEncode((String)((String)entry.getValue())))).collect(Collectors.joining("&"));
        if (!params.isEmpty()) {
            builder.append(((String)fragmentSplit.get(0)).contains("?") ? "&" : "?");
            builder.append(params);
        }
        if (fragmentSplit.size() > 1) {
            builder.append('#');
            builder.append((String)fragmentSplit.get(1));
        }
        String urlRedirect = builder.toString();
        LOGGER.debug("Final redirect response is [{}]", (Object)urlRedirect);
        return new DefaultResponse(Response.ResponseType.REDIRECT, urlRedirect, parameters);
    }

    private static String sanitizeUrl(String url) {
        Matcher m = NON_PRINTABLE.matcher(url);
        StringBuilder sb = new StringBuilder(url.length());
        boolean hasNonPrintable = false;
        while (m.find()) {
            m.appendReplacement(sb, " ");
            hasNonPrintable = true;
        }
        m.appendTail(sb);
        if (hasNonPrintable) {
            LOGGER.warn("The following redirect URL has been sanitized and may be sign of attack:\n[{}]", (Object)url);
        }
        return sb.toString();
    }

    @Generated
    public Response.ResponseType getResponseType() {
        return this.responseType;
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    @Generated
    public DefaultResponse(Response.ResponseType responseType, String url, Map<String, String> attributes) {
        this.responseType = responseType;
        this.url = url;
        this.attributes = attributes;
    }
}

