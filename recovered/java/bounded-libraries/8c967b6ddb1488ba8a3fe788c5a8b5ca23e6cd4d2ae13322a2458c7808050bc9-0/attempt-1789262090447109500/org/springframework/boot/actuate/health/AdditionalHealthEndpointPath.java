/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class AdditionalHealthEndpointPath {
    private final WebServerNamespace namespace;
    private final String value;
    private final String canonicalValue;

    private AdditionalHealthEndpointPath(WebServerNamespace namespace, String value) {
        this.namespace = namespace;
        this.value = value;
        this.canonicalValue = !value.startsWith("/") ? "/" + value : value;
    }

    public WebServerNamespace getNamespace() {
        return this.namespace;
    }

    public String getValue() {
        return this.value;
    }

    public boolean hasNamespace(WebServerNamespace webServerNamespace) {
        return this.namespace.equals(webServerNamespace);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AdditionalHealthEndpointPath other = (AdditionalHealthEndpointPath)obj;
        boolean result = true;
        result = result && this.namespace.equals(other.namespace);
        result = result && this.canonicalValue.equals(other.canonicalValue);
        return result;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.namespace.hashCode();
        result = 31 * result + this.canonicalValue.hashCode();
        return result;
    }

    public String toString() {
        return this.namespace.getValue() + ":" + this.value;
    }

    public static AdditionalHealthEndpointPath from(String value) {
        Assert.hasText((String)value, (String)"Value must not be null");
        String[] values = value.split(":");
        Assert.isTrue((values.length == 2 ? 1 : 0) != 0, (String)"Value must contain a valid namespace and value separated by ':'.");
        Assert.isTrue((boolean)StringUtils.hasText((String)values[0]), (String)"Value must contain a valid namespace.");
        WebServerNamespace namespace = WebServerNamespace.from(values[0]);
        AdditionalHealthEndpointPath.validateValue(values[1]);
        return new AdditionalHealthEndpointPath(namespace, values[1]);
    }

    public static AdditionalHealthEndpointPath of(WebServerNamespace webServerNamespace, String value) {
        Assert.notNull((Object)webServerNamespace, (String)"The server namespace must not be null.");
        Assert.notNull((Object)value, (String)"The value must not be null.");
        AdditionalHealthEndpointPath.validateValue(value);
        return new AdditionalHealthEndpointPath(webServerNamespace, value);
    }

    private static void validateValue(String value) {
        Assert.isTrue((StringUtils.countOccurrencesOf((String)value, (String)"/") <= 1 && value.indexOf("/") <= 0 ? 1 : 0) != 0, (String)"Value must contain only one segment.");
    }
}

