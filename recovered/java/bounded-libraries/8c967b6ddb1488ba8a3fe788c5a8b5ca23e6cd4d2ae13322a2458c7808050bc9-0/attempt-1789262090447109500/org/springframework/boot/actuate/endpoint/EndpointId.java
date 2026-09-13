/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.env.Environment
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

public final class EndpointId {
    private static final Log logger = LogFactory.getLog(EndpointId.class);
    private static final Set<String> loggedWarnings = new HashSet<String>();
    private static final Pattern VALID_PATTERN = Pattern.compile("[a-zA-Z0-9.-]+");
    private static final Pattern WARNING_PATTERN = Pattern.compile("[.-]+");
    private static final String MIGRATE_LEGACY_NAMES_PROPERTY = "management.endpoints.migrate-legacy-ids";
    private final String value;
    private final String lowerCaseValue;
    private final String lowerCaseAlphaNumeric;

    private EndpointId(String value) {
        Assert.hasText((String)value, (String)"Value must not be empty");
        Assert.isTrue((boolean)VALID_PATTERN.matcher(value).matches(), (String)"Value must only contain valid chars");
        Assert.isTrue((!Character.isDigit(value.charAt(0)) ? 1 : 0) != 0, (String)"Value must not start with a number");
        Assert.isTrue((!Character.isUpperCase(value.charAt(0)) ? 1 : 0) != 0, (String)"Value must not start with an uppercase letter");
        if (WARNING_PATTERN.matcher(value).find()) {
            EndpointId.logWarning(value);
        }
        this.value = value;
        this.lowerCaseValue = value.toLowerCase(Locale.ENGLISH);
        this.lowerCaseAlphaNumeric = this.getAlphaNumerics(this.lowerCaseValue);
    }

    private String getAlphaNumerics(String value) {
        StringBuilder result = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); ++i) {
            char ch = value.charAt(i);
            if ((ch < 'a' || ch > 'z') && (ch < '0' || ch > '9')) continue;
            result.append(ch);
        }
        return result.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.lowerCaseAlphaNumeric.equals(((EndpointId)obj).lowerCaseAlphaNumeric);
    }

    public int hashCode() {
        return this.lowerCaseAlphaNumeric.hashCode();
    }

    public String toLowerCaseString() {
        return this.lowerCaseValue;
    }

    public String toString() {
        return this.value;
    }

    public static EndpointId of(String value) {
        return new EndpointId(value);
    }

    public static EndpointId of(Environment environment, String value) {
        Assert.notNull((Object)environment, (String)"Environment must not be null");
        return new EndpointId(EndpointId.migrateLegacyId(environment, value));
    }

    private static String migrateLegacyId(Environment environment, String value) {
        if (((Boolean)environment.getProperty(MIGRATE_LEGACY_NAMES_PROPERTY, Boolean.class, (Object)false)).booleanValue()) {
            return value.replaceAll("[-.]+", "");
        }
        return value;
    }

    public static EndpointId fromPropertyValue(String value) {
        return new EndpointId(value.replace("-", ""));
    }

    static void resetLoggedWarnings() {
        loggedWarnings.clear();
    }

    private static void logWarning(String value) {
        if (logger.isWarnEnabled() && loggedWarnings.add(value)) {
            logger.warn((Object)("Endpoint ID '" + value + "' contains invalid characters, please migrate to a valid format."));
        }
    }
}

