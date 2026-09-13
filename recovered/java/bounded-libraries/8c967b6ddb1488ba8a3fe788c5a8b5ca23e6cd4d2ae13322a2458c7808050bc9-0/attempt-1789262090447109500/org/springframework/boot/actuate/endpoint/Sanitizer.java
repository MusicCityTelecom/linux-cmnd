/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.endpoint.SanitizableData;
import org.springframework.boot.actuate.endpoint.SanitizingFunction;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class Sanitizer {
    private static final String[] REGEX_PARTS = new String[]{"*", "$", "^", "+"};
    private static final Set<String> DEFAULT_KEYS_TO_SANITIZE = new LinkedHashSet<String>(Arrays.asList("password", "secret", "key", "token", ".*credentials.*", "vcap_services", "^vcap\\.services.*$", "sun.java.command", "^spring[._]application[._]json$"));
    private static final Set<String> URI_USERINFO_KEYS = new LinkedHashSet<String>(Arrays.asList("uri", "uris", "url", "urls", "address", "addresses"));
    private static final Pattern URI_USERINFO_PATTERN = Pattern.compile("^\\[?[A-Za-z][A-Za-z0-9\\+\\.\\-]+://.+:(.*)@.+$");
    private Pattern[] keysToSanitize;
    private final List<SanitizingFunction> sanitizingFunctions = new ArrayList<SanitizingFunction>();

    public Sanitizer() {
        this(DEFAULT_KEYS_TO_SANITIZE.toArray(new String[0]));
    }

    public Sanitizer(String ... keysToSanitize) {
        this(Collections.emptyList(), keysToSanitize);
    }

    public Sanitizer(Iterable<SanitizingFunction> sanitizingFunctions) {
        this(sanitizingFunctions, DEFAULT_KEYS_TO_SANITIZE.toArray(new String[0]));
    }

    public Sanitizer(Iterable<SanitizingFunction> sanitizingFunctions, String ... keysToSanitize) {
        sanitizingFunctions.forEach(this.sanitizingFunctions::add);
        this.sanitizingFunctions.add(this.getDefaultSanitizingFunction());
        this.setKeysToSanitize(keysToSanitize);
    }

    private SanitizingFunction getDefaultSanitizingFunction() {
        return data -> {
            Object sanitizedValue = this.sanitize(data.getKey(), data.getValue());
            return data.withValue(sanitizedValue);
        };
    }

    public void setKeysToSanitize(String ... keysToSanitize) {
        Assert.notNull((Object)keysToSanitize, (String)"KeysToSanitize must not be null");
        this.keysToSanitize = new Pattern[keysToSanitize.length];
        for (int i = 0; i < keysToSanitize.length; ++i) {
            this.keysToSanitize[i] = this.getPattern(keysToSanitize[i]);
        }
    }

    public void keysToSanitize(String ... keysToSanitize) {
        Assert.notNull((Object)keysToSanitize, (String)"KeysToSanitize must not be null");
        int existingKeys = this.keysToSanitize.length;
        this.keysToSanitize = Arrays.copyOf(this.keysToSanitize, this.keysToSanitize.length + keysToSanitize.length);
        for (int i = 0; i < keysToSanitize.length; ++i) {
            this.keysToSanitize[i + existingKeys] = this.getPattern(keysToSanitize[i]);
        }
    }

    private Pattern getPattern(String value) {
        if (this.isRegex(value)) {
            return Pattern.compile(value, 2);
        }
        return Pattern.compile(".*" + value + "$", 2);
    }

    private boolean isRegex(String value) {
        for (String part : REGEX_PARTS) {
            if (!value.contains(part)) continue;
            return true;
        }
        return false;
    }

    public Object sanitize(String key, Object value) {
        if (value == null) {
            return null;
        }
        for (Pattern pattern : this.keysToSanitize) {
            if (!pattern.matcher(key).matches()) continue;
            if (this.keyIsUriWithUserInfo(pattern)) {
                return this.sanitizeUris(value.toString());
            }
            return "******";
        }
        return value;
    }

    public Object sanitize(SanitizableData data) {
        Object value = data.getValue();
        if (value == null) {
            return null;
        }
        for (SanitizingFunction sanitizingFunction : this.sanitizingFunctions) {
            data = sanitizingFunction.apply(data);
            Object sanitizedValue = data.getValue();
            if (value.equals(sanitizedValue)) continue;
            return sanitizedValue;
        }
        return value;
    }

    private boolean keyIsUriWithUserInfo(Pattern pattern) {
        for (String uriKey : URI_USERINFO_KEYS) {
            if (!pattern.matcher(uriKey).matches()) continue;
            return true;
        }
        return false;
    }

    private Object sanitizeUris(String value) {
        return Arrays.stream(value.split(",")).map(this::sanitizeUri).collect(Collectors.joining(","));
    }

    private String sanitizeUri(String value) {
        String password;
        Matcher matcher = URI_USERINFO_PATTERN.matcher(value);
        String string = password = matcher.matches() ? matcher.group(1) : null;
        if (password != null) {
            return StringUtils.replace((String)value, (String)(":" + password + "@"), (String)":******@");
        }
        return value;
    }

    static {
        DEFAULT_KEYS_TO_SANITIZE.addAll(URI_USERINFO_KEYS);
    }
}

