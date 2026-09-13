/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.messaging.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

public class NativeMessageHeaderAccessor
extends MessageHeaderAccessor {
    public static final String NATIVE_HEADERS = "nativeHeaders";

    protected NativeMessageHeaderAccessor() {
        this((Map<String, List<String>>)null);
    }

    protected NativeMessageHeaderAccessor(@Nullable Map<String, List<String>> nativeHeaders) {
        if (!CollectionUtils.isEmpty(nativeHeaders)) {
            this.setHeader(NATIVE_HEADERS, new LinkedMultiValueMap(nativeHeaders));
        }
    }

    protected NativeMessageHeaderAccessor(@Nullable Message<?> message) {
        super(message);
        Map map;
        if (message != null && (map = (Map)this.getHeader(NATIVE_HEADERS)) != null) {
            this.setHeader(NATIVE_HEADERS, null);
            this.setHeader(NATIVE_HEADERS, new LinkedMultiValueMap(map));
        }
    }

    @Nullable
    protected Map<String, List<String>> getNativeHeaders() {
        return (Map)this.getHeader(NATIVE_HEADERS);
    }

    public Map<String, List<String>> toNativeHeaderMap() {
        Map<String, List<String>> map = this.getNativeHeaders();
        return map != null ? new LinkedMultiValueMap(map) : Collections.emptyMap();
    }

    @Override
    public void setImmutable() {
        if (this.isMutable()) {
            Map<String, List<String>> map = this.getNativeHeaders();
            if (map != null) {
                this.setHeader(NATIVE_HEADERS, null);
                this.setHeader(NATIVE_HEADERS, Collections.unmodifiableMap(map));
            }
            super.setImmutable();
        }
    }

    @Override
    public void copyHeaders(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy == null) {
            return;
        }
        Map map = (Map)headersToCopy.get(NATIVE_HEADERS);
        if (map != null && map != this.getNativeHeaders()) {
            map.forEach(this::setNativeHeaderValues);
        }
        super.copyHeaders(headersToCopy);
    }

    @Override
    public void copyHeadersIfAbsent(@Nullable Map<String, ?> headersToCopy) {
        if (headersToCopy == null) {
            return;
        }
        Map map = (Map)headersToCopy.get(NATIVE_HEADERS);
        if (map != null && this.getNativeHeaders() == null) {
            map.forEach(this::setNativeHeaderValues);
        }
        super.copyHeadersIfAbsent(headersToCopy);
    }

    public boolean containsNativeHeader(String headerName) {
        Map<String, List<String>> map = this.getNativeHeaders();
        return map != null && map.containsKey(headerName);
    }

    @Nullable
    public List<String> getNativeHeader(String headerName) {
        Map<String, List<String>> map = this.getNativeHeaders();
        return map != null ? map.get(headerName) : null;
    }

    @Nullable
    public String getFirstNativeHeader(String headerName) {
        List<String> values;
        Map<String, List<String>> map = this.getNativeHeaders();
        if (map != null && !CollectionUtils.isEmpty(values = map.get(headerName))) {
            return values.get(0);
        }
        return null;
    }

    public void setNativeHeader(String name, @Nullable String value) {
        Assert.state((boolean)this.isMutable(), (String)"Already immutable");
        LinkedMultiValueMap map = this.getNativeHeaders();
        if (value == null) {
            if (map != null && map.get(name) != null) {
                this.setModified(true);
                map.remove(name);
            }
            return;
        }
        if (map == null) {
            map = new LinkedMultiValueMap(3);
            this.setHeader(NATIVE_HEADERS, map);
        }
        ArrayList<String> values = new ArrayList<String>(1);
        values.add(value);
        if (!ObjectUtils.nullSafeEquals(values, (Object)this.getHeader(name))) {
            this.setModified(true);
            map.put(name, values);
        }
    }

    public void setNativeHeaderValues(String name, @Nullable List<String> values) {
        Assert.state((boolean)this.isMutable(), (String)"Already immutable");
        LinkedMultiValueMap map = this.getNativeHeaders();
        if (values == null) {
            if (map != null && map.get(name) != null) {
                this.setModified(true);
                map.remove(name);
            }
            return;
        }
        if (map == null) {
            map = new LinkedMultiValueMap(3);
            this.setHeader(NATIVE_HEADERS, map);
        }
        if (!ObjectUtils.nullSafeEquals(values, (Object)this.getHeader(name))) {
            this.setModified(true);
            map.put(name, new ArrayList<String>(values));
        }
    }

    public void addNativeHeader(String name, @Nullable String value) {
        Assert.state((boolean)this.isMutable(), (String)"Already immutable");
        if (value == null) {
            return;
        }
        LinkedMultiValueMap nativeHeaders = this.getNativeHeaders();
        if (nativeHeaders == null) {
            nativeHeaders = new LinkedMultiValueMap(3);
            this.setHeader(NATIVE_HEADERS, nativeHeaders);
        }
        List values = nativeHeaders.computeIfAbsent((String)name, k -> new ArrayList(1));
        values.add(value);
        this.setModified(true);
    }

    public void addNativeHeaders(@Nullable MultiValueMap<String, String> headers) {
        if (headers == null) {
            return;
        }
        headers.forEach((key, values) -> values.forEach(value -> this.addNativeHeader((String)key, (String)value)));
    }

    @Nullable
    public List<String> removeNativeHeader(String headerName) {
        Assert.state((boolean)this.isMutable(), (String)"Already immutable");
        Map<String, List<String>> nativeHeaders = this.getNativeHeaders();
        if (CollectionUtils.isEmpty(nativeHeaders)) {
            return null;
        }
        return nativeHeaders.remove(headerName);
    }

    @Nullable
    public static String getFirstNativeHeader(String headerName, Map<String, Object> headers) {
        List values;
        Map map = (Map)headers.get(NATIVE_HEADERS);
        if (map != null && !CollectionUtils.isEmpty((Collection)(values = (List)map.get(headerName)))) {
            return (String)values.get(0);
        }
        return null;
    }
}

