/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegexUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtils.class);
    public static final Pattern MATCH_NOTHING_PATTERN = Pattern.compile("a^");

    public static boolean isValidRegex(String pattern) {
        try {
            if (StringUtils.isNotBlank((CharSequence)pattern)) {
                Pattern.compile(pattern);
                return true;
            }
        }
        catch (PatternSyntaxException exception) {
            LOGGER.debug("Pattern [{}] is not a valid regex.", (Object)pattern);
        }
        return false;
    }

    public static Pattern concatenate(Collection<?> requiredValues, boolean caseInsensitive) {
        String pattern = requiredValues.stream().map(Object::toString).collect(Collectors.joining("|", "(", ")"));
        return RegexUtils.createPattern(pattern, caseInsensitive ? 2 : 0);
    }

    public static Pattern createPattern(String pattern) {
        return RegexUtils.createPattern(pattern, 2);
    }

    public static Pattern createPattern(String pattern, int flags) {
        if (StringUtils.isBlank((CharSequence)pattern)) {
            LOGGER.warn("Pattern cannot be null/blank");
            return MATCH_NOTHING_PATTERN;
        }
        try {
            return Pattern.compile(pattern, flags);
        }
        catch (PatternSyntaxException exception) {
            LOGGER.debug("Pattern [{}] is not a valid regex.", (Object)pattern);
            return MATCH_NOTHING_PATTERN;
        }
    }

    public static boolean matches(Pattern pattern, String string) {
        return pattern.matcher(string).matches();
    }

    public static boolean matches(Pattern pattern, String value, boolean completeMatch) {
        Matcher matcher = pattern.matcher(value);
        LOGGER.debug("Matching value [{}] against pattern [{}]", (Object)value, (Object)pattern.pattern());
        if (completeMatch) {
            return matcher.matches();
        }
        return matcher.find();
    }

    public static boolean find(Pattern pattern, String string) {
        return pattern.matcher(string).find();
    }

    public static boolean find(String pattern, String string) {
        return StringUtils.isNotBlank((CharSequence)string) && RegexUtils.createPattern(pattern, 2).matcher(string).find();
    }

    @Generated
    private RegexUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

