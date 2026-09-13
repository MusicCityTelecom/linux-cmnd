/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.core.ResolvableType
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.PatternMatchUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.mapping;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.ResolvableType;
import org.springframework.integration.mapping.RequestReplyHeaderMapper;
import org.springframework.integration.mapping.support.JsonHeaders;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractHeaderMapper<T>
implements RequestReplyHeaderMapper<T>,
BeanClassLoaderAware {
    public static final String STANDARD_REQUEST_HEADER_NAME_PATTERN = "STANDARD_REQUEST_HEADERS";
    public static final String STANDARD_REPLY_HEADER_NAME_PATTERN = "STANDARD_REPLY_HEADERS";
    public static final String NON_STANDARD_HEADER_NAME_PATTERN = "NON_STANDARD_HEADERS";
    private static final Collection<String> TRANSIENT_HEADER_NAMES = Arrays.asList("id", "timestamp");
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final String standardHeaderPrefix;
    private final Collection<String> requestHeaderNames;
    private final Collection<String> replyHeaderNames;
    private HeaderMatcher requestHeaderMatcher;
    private HeaderMatcher replyHeaderMatcher;
    private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

    protected AbstractHeaderMapper(String standardHeaderPrefix, Collection<String> requestHeaderNames, Collection<String> replyHeaderNames) {
        this.standardHeaderPrefix = standardHeaderPrefix;
        this.requestHeaderNames = requestHeaderNames;
        this.replyHeaderNames = replyHeaderNames;
        this.requestHeaderMatcher = this.createDefaultHeaderMatcher(this.standardHeaderPrefix, this.requestHeaderNames);
        this.replyHeaderMatcher = this.createDefaultHeaderMatcher(this.standardHeaderPrefix, this.replyHeaderNames);
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void setRequestHeaderNames(String ... requestHeaderNames) {
        Assert.notNull((Object)requestHeaderNames, (String)"'requestHeaderNames' must not be null");
        this.requestHeaderMatcher = this.createHeaderMatcher(Arrays.asList(requestHeaderNames));
    }

    public void setReplyHeaderNames(String ... replyHeaderNames) {
        Assert.notNull((Object)replyHeaderNames, (String)"'replyHeaderNames' must not be null");
        this.replyHeaderMatcher = this.createHeaderMatcher(Arrays.asList(replyHeaderNames));
    }

    protected HeaderMatcher createDefaultHeaderMatcher(String standardHeaderPrefix, Collection<String> headerNames) {
        return new ContentBasedHeaderMatcher(true, headerNames);
    }

    protected HeaderMatcher createHeaderMatcher(Collection<String> patterns) {
        ArrayList<HeaderMatcher> matchers = new ArrayList<HeaderMatcher>();
        for (String pattern : patterns) {
            if (STANDARD_REQUEST_HEADER_NAME_PATTERN.equals(pattern)) {
                matchers.add(new ContentBasedHeaderMatcher(true, this.requestHeaderNames));
                continue;
            }
            if (STANDARD_REPLY_HEADER_NAME_PATTERN.equals(pattern)) {
                matchers.add(new ContentBasedHeaderMatcher(true, this.replyHeaderNames));
                continue;
            }
            if (NON_STANDARD_HEADER_NAME_PATTERN.equals(pattern)) {
                matchers.add(new PrefixBasedMatcher(false, this.standardHeaderPrefix));
                continue;
            }
            String thePattern = pattern;
            boolean negate = false;
            if (pattern.startsWith("!")) {
                thePattern = pattern.substring(1);
                negate = true;
            } else if (pattern.startsWith("\\!")) {
                thePattern = pattern.substring(1);
            }
            if (negate) {
                matchers.add(0, new SinglePatternBasedHeaderMatcher(thePattern, negate));
                continue;
            }
            matchers.add(new SinglePatternBasedHeaderMatcher(thePattern, negate));
        }
        return new CompositeHeaderMatcher(matchers);
    }

    @Override
    public void fromHeadersToRequest(MessageHeaders headers, T target) {
        this.fromHeaders(headers, target, this.requestHeaderMatcher);
    }

    @Override
    public void fromHeadersToReply(MessageHeaders headers, T target) {
        this.fromHeaders(headers, target, this.replyHeaderMatcher);
    }

    @Override
    public Map<String, Object> toHeadersFromRequest(T source) {
        return this.toHeaders(source, this.requestHeaderMatcher);
    }

    @Override
    public Map<String, Object> toHeadersFromReply(T source) {
        return this.toHeaders(source, this.replyHeaderMatcher);
    }

    private void fromHeaders(MessageHeaders headers, T target, HeaderMatcher headerMatcher) {
        try {
            HashMap<String, Object> subset = new HashMap<String, Object>();
            for (Map.Entry entry : headers.entrySet()) {
                String headerName = (String)entry.getKey();
                if (!this.shouldMapHeader(headerName, headerMatcher)) continue;
                subset.put(headerName, entry.getValue());
            }
            this.populateStandardHeaders((Map<String, Object>)headers, subset, target);
            this.populateUserDefinedHeaders(subset, target);
        }
        catch (Exception e) {
            this.logger.warn((Object)"error occurred while mapping from MessageHeaders", (Throwable)e);
        }
    }

    private void populateUserDefinedHeaders(Map<String, Object> headers, T target) {
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            Object value = entry.getValue();
            if (value == null || this.isMessageChannel(headerName, value)) continue;
            try {
                if (headerName.startsWith(this.standardHeaderPrefix)) continue;
                String key = this.createTargetPropertyName(headerName, true);
                this.populateUserDefinedHeader(key, value, target);
            }
            catch (Exception e) {
                if (!this.logger.isWarnEnabled()) continue;
                this.logger.warn((Object)("failed to map from Message header '" + headerName + "' to target"), (Throwable)e);
            }
        }
    }

    private boolean isMessageChannel(String headerName, Object headerValue) {
        if (headerValue instanceof MessageChannel) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Cannot map a MessageChannel instance in header " + headerName));
            }
            return true;
        }
        return false;
    }

    private Map<String, Object> toHeaders(T source, HeaderMatcher headerMatcher) {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        Map<String, Object> standardHeaders = this.extractStandardHeaders(source);
        this.copyHeaders(standardHeaders, headers, headerMatcher);
        Map<String, Object> userDefinedHeaders = this.extractUserDefinedHeaders(source);
        this.copyHeaders(userDefinedHeaders, headers, headerMatcher);
        return headers;
    }

    private void copyHeaders(Map<String, Object> source, Map<String, Object> target, HeaderMatcher headerMatcher) {
        if (!CollectionUtils.isEmpty(source)) {
            for (Map.Entry<String, Object> entry : source.entrySet()) {
                try {
                    ResolvableType resolvableType;
                    String headerName = this.createTargetPropertyName(entry.getKey(), false);
                    if (!this.shouldMapHeader(headerName, headerMatcher)) continue;
                    Object value = entry.getValue();
                    target.put(headerName, value);
                    if (!this.replyHeaderMatcher.equals(headerMatcher) || !"json__TypeId__".equals(headerName) || value == null || (resolvableType = this.createJsonResolvableTypHeaderInAny(value, source.get("json__ContentTypeId__"), source.get("json__KeyTypeId__"))) == null) continue;
                    target.put("json_resolvableType", resolvableType);
                }
                catch (Exception e) {
                    if (!this.logger.isWarnEnabled()) continue;
                    this.logger.warn((Object)("error occurred while mapping header '" + entry.getKey() + "' to Message header"), (Throwable)e);
                }
            }
        }
    }

    @Nullable
    private ResolvableType createJsonResolvableTypHeaderInAny(Object typeId, @Nullable Object contentId, @Nullable Object keyId) {
        try {
            return JsonHeaders.buildResolvableType(this.getClassLoader(), typeId, contentId, keyId);
        }
        catch (Exception e) {
            this.logger.debug((Object)"Cannot build a ResolvableType from 'json__TypeId__' header", (Throwable)e);
            return null;
        }
    }

    private boolean shouldMapHeader(String headerName, HeaderMatcher headerMatcher) {
        return StringUtils.hasText((String)headerName) && !this.getTransientHeaderNames().contains(headerName) && headerMatcher.matchHeader(headerName);
    }

    @Nullable
    protected <V> V getHeaderIfAvailable(Map<String, Object> headers, String name, Class<V> type) {
        Object value = headers.get(name);
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn((Object)("skipping header '" + name + "' since it is not of expected type [" + type + "], it is [" + value.getClass() + "]"));
            }
            return null;
        }
        return (V)value;
    }

    protected String createTargetPropertyName(String propertyName, boolean fromMessageHeaders) {
        return propertyName;
    }

    protected Collection<String> getTransientHeaderNames() {
        return TRANSIENT_HEADER_NAMES;
    }

    protected abstract Map<String, Object> extractStandardHeaders(T var1);

    protected abstract Map<String, Object> extractUserDefinedHeaders(T var1);

    protected abstract void populateStandardHeaders(Map<String, Object> var1, T var2);

    protected void populateStandardHeaders(@Nullable Map<String, Object> allHeaders, Map<String, Object> subset, T target) {
        this.populateStandardHeaders(subset, target);
    }

    protected abstract void populateUserDefinedHeader(String var1, Object var2, T var3);

    @FunctionalInterface
    public static interface HeaderMatcher {
        public boolean matchHeader(String var1);

        default public boolean isNegated() {
            return false;
        }
    }

    protected static class ContentBasedHeaderMatcher
    implements HeaderMatcher {
        private static final Log LOGGER = LogFactory.getLog(HeaderMatcher.class);
        private final boolean match;
        private final Collection<String> content;

        public ContentBasedHeaderMatcher(boolean match, Collection<String> content) {
            this.match = match;
            Assert.notNull(content, (String)"Content must not be null");
            this.content = content;
        }

        @Override
        public boolean matchHeader(String headerName) {
            boolean result;
            boolean bl = result = this.match == this.containsIgnoreCase(headerName);
            if (result && LOGGER.isDebugEnabled()) {
                StringBuilder message = new StringBuilder("headerName=[{0}] WILL be mapped, ");
                if (!this.match) {
                    message.append("not ");
                }
                message.append("found in {1}");
                LOGGER.debug((Object)MessageFormat.format(message.toString(), headerName, this.content));
            }
            return result;
        }

        private boolean containsIgnoreCase(String name) {
            for (String headerName : this.content) {
                if (!headerName.equalsIgnoreCase(name)) continue;
                return true;
            }
            return false;
        }
    }

    protected static class PrefixBasedMatcher
    implements HeaderMatcher {
        private static final Log LOGGER = LogFactory.getLog(HeaderMatcher.class);
        private final boolean match;
        private final String prefix;

        public PrefixBasedMatcher(boolean match, String prefix) {
            this.match = match;
            this.prefix = prefix;
        }

        @Override
        public boolean matchHeader(String headerName) {
            boolean result;
            boolean bl = result = this.match == headerName.startsWith(this.prefix);
            if (result && LOGGER.isDebugEnabled()) {
                StringBuilder message = new StringBuilder("headerName=[{0}] WILL be mapped, ");
                if (!this.match) {
                    message.append("does not ");
                }
                message.append("start with [{1}]");
                LOGGER.debug((Object)MessageFormat.format(message.toString(), headerName, this.prefix));
            }
            return result;
        }
    }

    protected static class SinglePatternBasedHeaderMatcher
    implements HeaderMatcher {
        private static final Log LOGGER = LogFactory.getLog(HeaderMatcher.class);
        private final String pattern;
        private final boolean negate;

        public SinglePatternBasedHeaderMatcher(String pattern) {
            this(pattern, false);
        }

        public SinglePatternBasedHeaderMatcher(String pattern, boolean negate) {
            Assert.notNull((Object)pattern, (String)"Pattern must no be null");
            this.pattern = pattern.toLowerCase();
            this.negate = negate;
        }

        @Override
        public boolean matchHeader(String headerName) {
            String header = headerName.toLowerCase();
            if (PatternMatchUtils.simpleMatch((String)this.pattern, (String)header)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)MessageFormat.format("headerName=[{0}] WILL be mapped, matched pattern={1}", headerName, this.pattern));
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean isNegated() {
            return this.negate;
        }
    }

    protected static class CompositeHeaderMatcher
    implements HeaderMatcher {
        private static final Log LOGGER = LogFactory.getLog(HeaderMatcher.class);
        private final Collection<HeaderMatcher> matchers;

        CompositeHeaderMatcher(Collection<HeaderMatcher> strategies) {
            this.matchers = strategies;
        }

        CompositeHeaderMatcher(HeaderMatcher ... strategies) {
            this(Arrays.asList(strategies));
        }

        @Override
        public boolean matchHeader(String headerName) {
            for (HeaderMatcher strategy : this.matchers) {
                if (!strategy.matchHeader(headerName)) continue;
                if (strategy.isNegated()) break;
                return true;
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)MessageFormat.format("headerName=[{0}] WILL NOT be mapped", headerName));
            }
            return false;
        }
    }

    protected static class PatternBasedHeaderMatcher
    implements HeaderMatcher {
        private static final Log LOGGER = LogFactory.getLog(HeaderMatcher.class);
        private final Collection<String> patterns = new ArrayList<String>();

        public PatternBasedHeaderMatcher(Collection<String> patterns) {
            Assert.notNull(patterns, (String)"Patterns must no be null");
            Assert.notEmpty(patterns, (String)"At least one pattern must be specified");
            for (String pattern : patterns) {
                this.patterns.add(pattern.toLowerCase());
            }
        }

        @Override
        public boolean matchHeader(String headerName) {
            String header = headerName.toLowerCase();
            for (String pattern : this.patterns) {
                if (!PatternMatchUtils.simpleMatch((String)pattern, (String)header)) continue;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)MessageFormat.format("headerName=[{0}] WILL be mapped, matched pattern={1}", headerName, pattern));
                }
                return true;
            }
            return false;
        }
    }
}

