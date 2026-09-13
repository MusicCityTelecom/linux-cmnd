/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package org.apereo.cas.util;

import com.google.common.base.Splitter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class HttpRequestUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtils.class);
    public static final String USER_AGENT_HEADER = "user-agent";
    private static final int GEO_LOC_LAT_INDEX = 0;
    private static final int GEO_LOC_LONG_INDEX = 1;
    private static final int GEO_LOC_ACCURACY_INDEX = 2;
    private static final int GEO_LOC_TIME_INDEX = 3;
    private static final int PING_URL_TIMEOUT = 5000;

    public static HttpServletRequest getHttpServletRequestFromRequestAttributes() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(requestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    public static HttpServletResponse getHttpServletResponseFromRequestAttributes() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(requestAttributes).map(ServletRequestAttributes::getResponse).orElse(null);
    }

    public static GeoLocationRequest getHttpServletRequestGeoLocation(HttpServletRequest request) {
        GeoLocationRequest loc = new GeoLocationRequest();
        if (request != null) {
            String geoLocationParam = request.getParameter("geolocation");
            return HttpRequestUtils.getHttpServletRequestGeoLocation(geoLocationParam);
        }
        return loc;
    }

    public static GeoLocationRequest getHttpServletRequestGeoLocation(String geoLocationParam) {
        GeoLocationRequest loc = new GeoLocationRequest();
        if (StringUtils.isNotBlank((CharSequence)geoLocationParam) && !StringUtils.equalsIgnoreCase((CharSequence)geoLocationParam, (CharSequence)"unknown")) {
            List geoLocation = Splitter.on((String)",").splitToList((CharSequence)geoLocationParam);
            loc.setLatitude((String)geoLocation.get(0));
            loc.setLongitude((String)geoLocation.get(1));
            loc.setAccuracy((String)geoLocation.get(2));
            loc.setTimestamp((String)geoLocation.get(3));
        }
        return loc;
    }

    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = (String)headerNames.nextElement();
                String headerValue = StringUtils.stripToEmpty((String)request.getHeader(headerName));
                headers.put(headerName, headerValue);
            }
        }
        return headers;
    }

    public static String getHttpServletRequestUserAgent(HttpServletRequest request) {
        if (request != null) {
            return request.getHeader(USER_AGENT_HEADER);
        }
        return null;
    }

    public static WebApplicationService getService(List<ArgumentExtractor> argumentExtractors, HttpServletRequest request) {
        return argumentExtractors.stream().map(argumentExtractor -> argumentExtractor.extractService(request)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static boolean doesParameterExist(HttpServletRequest request, String name) {
        String parameter = request.getParameter(name);
        if (StringUtils.isBlank((CharSequence)parameter)) {
            LOGGER.error("Missing request parameter: [{}]", (Object)name);
            return false;
        }
        LOGGER.debug("Found provided request parameter [{}]", (Object)name);
        return true;
    }

    public static HttpStatus pingUrl(String location) {
        try {
            URL url = new URL(location);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod(HttpMethod.HEAD.name());
            return HttpStatus.valueOf((int)connection.getResponseCode());
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    @Generated
    private HttpRequestUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

