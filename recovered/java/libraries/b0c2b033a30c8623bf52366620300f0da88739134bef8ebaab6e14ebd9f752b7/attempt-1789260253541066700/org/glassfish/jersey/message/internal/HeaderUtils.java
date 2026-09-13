/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.core.AbstractMultivaluedMap;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.RuntimeDelegateDecorator;
import org.glassfish.jersey.internal.util.collection.ImmutableMultivaluedMap;
import org.glassfish.jersey.internal.util.collection.StringKeyIgnoreCaseMultivaluedMap;
import org.glassfish.jersey.internal.util.collection.Views;

public final class HeaderUtils {
    private static final Logger LOGGER = Logger.getLogger(HeaderUtils.class.getName());

    public static AbstractMultivaluedMap<String, String> createInbound() {
        return new StringKeyIgnoreCaseMultivaluedMap<String>();
    }

    public static <V> MultivaluedMap<String, V> empty() {
        return ImmutableMultivaluedMap.empty();
    }

    public static AbstractMultivaluedMap<String, Object> createOutbound() {
        return new StringKeyIgnoreCaseMultivaluedMap<Object>();
    }

    private static String asString(Object headerValue, RuntimeDelegate rd) {
        RuntimeDelegate.HeaderDelegate<?> hp;
        if (headerValue == null) {
            return null;
        }
        if (headerValue instanceof String) {
            return (String)headerValue;
        }
        if (rd == null) {
            rd = RuntimeDelegate.getInstance();
        }
        return (hp = rd.createHeaderDelegate(headerValue.getClass())) != null ? hp.toString(headerValue) : headerValue.toString();
    }

    public static String asString(Object headerValue, Configuration configuration) {
        return HeaderUtils.asString(headerValue, RuntimeDelegateDecorator.configured(configuration));
    }

    private static List<String> asStringList(List<Object> headerValues, RuntimeDelegate rd) {
        if (headerValues == null || headerValues.isEmpty()) {
            return Collections.emptyList();
        }
        return Views.listView(headerValues, input -> input == null ? "[null]" : HeaderUtils.asString(input, rd));
    }

    public static List<String> asStringList(List<Object> headerValues, Configuration configuration) {
        return HeaderUtils.asStringList(headerValues, RuntimeDelegateDecorator.configured(configuration));
    }

    public static MultivaluedMap<String, String> asStringHeaders(MultivaluedMap<String, Object> headers, Configuration configuration) {
        if (headers == null) {
            return null;
        }
        RuntimeDelegate rd = RuntimeDelegateDecorator.configured(configuration);
        return new AbstractMultivaluedMap<String, String>(Views.mapView(headers, input -> HeaderUtils.asStringList((List<Object>)input, rd))){};
    }

    public static Map<String, String> asStringHeadersSingleValue(MultivaluedMap<String, Object> headers, Configuration configuration) {
        if (headers == null) {
            return null;
        }
        RuntimeDelegate rd = RuntimeDelegateDecorator.configured(configuration);
        return Collections.unmodifiableMap(headers.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> HeaderUtils.asHeaderString((List)entry.getValue(), rd))));
    }

    public static String asHeaderString(List<Object> values, RuntimeDelegate rd) {
        if (values == null) {
            return null;
        }
        Iterator<String> stringValues = HeaderUtils.asStringList(values, rd).iterator();
        if (!stringValues.hasNext()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder(stringValues.next());
        while (stringValues.hasNext()) {
            buffer.append(',').append(stringValues.next());
        }
        return buffer.toString();
    }

    public static void checkHeaderChanges(Map<String, String> headersSnapshot, MultivaluedMap<String, Object> currentHeaders, String connectorName, Configuration configuration) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            RuntimeDelegate rd = RuntimeDelegateDecorator.configured(configuration);
            HashSet changedHeaderNames = new HashSet();
            for (Map.Entry entry : currentHeaders.entrySet()) {
                String newValue;
                if (!headersSnapshot.containsKey(entry.getKey())) {
                    changedHeaderNames.add(entry.getKey());
                    continue;
                }
                String prevValue = headersSnapshot.get(entry.getKey());
                if (prevValue.equals(newValue = HeaderUtils.asHeaderString((List)currentHeaders.get(entry.getKey()), rd))) continue;
                changedHeaderNames.add(entry.getKey());
            }
            if (!changedHeaderNames.isEmpty() && LOGGER.isLoggable(Level.WARNING)) {
                LOGGER.warning(LocalizationMessages.SOME_HEADERS_NOT_SENT(connectorName, ((Object)changedHeaderNames).toString()));
            }
        }
    }

    public static NewCookie getPreferredCookie(NewCookie first, NewCookie second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        if (first.getMaxAge() != second.getMaxAge()) {
            return Comparator.comparing(NewCookie::getMaxAge).compare(first, second) > 0 ? first : second;
        }
        if (first.getExpiry() != null && second.getExpiry() != null && !first.getExpiry().equals(second.getExpiry())) {
            return Comparator.comparing(NewCookie::getExpiry).compare(first, second) > 0 ? first : second;
        }
        return first.getPath().length() > second.getPath().length() ? first : second;
    }

    @Deprecated
    public static String asString(Object headerValue) {
        return HeaderUtils.asString(headerValue, (Configuration)null);
    }

    @Deprecated
    public static List<String> asStringList(List<Object> headerValues) {
        return HeaderUtils.asStringList(headerValues, (Configuration)null);
    }

    @Deprecated
    public static MultivaluedMap<String, String> asStringHeaders(MultivaluedMap<String, Object> headers) {
        return HeaderUtils.asStringHeaders(headers, null);
    }

    @Deprecated
    public static Map<String, String> asStringHeadersSingleValue(MultivaluedMap<String, Object> headers) {
        return HeaderUtils.asStringHeadersSingleValue(headers, null);
    }

    @Deprecated
    public static void checkHeaderChanges(Map<String, String> headersSnapshot, MultivaluedMap<String, Object> currentHeaders, String connectorName) {
        HeaderUtils.checkHeaderChanges(headersSnapshot, currentHeaders, connectorName, null);
    }

    private HeaderUtils() {
        throw new AssertionError((Object)"No instances allowed.");
    }
}

