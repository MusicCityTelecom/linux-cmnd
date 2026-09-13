/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.boot.actuate.endpoint.web.WebEndpointHttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class WebOperationRequestPredicate {
    private static final Pattern PATH_VAR_PATTERN = Pattern.compile("(\\{\\*?).+?}");
    private static final Pattern ALL_REMAINING_PATH_SEGMENTS_VAR_PATTERN = Pattern.compile("^.*\\{\\*(.+?)}$");
    private final String path;
    private final String matchAllRemainingPathSegmentsVariable;
    private final String canonicalPath;
    private final WebEndpointHttpMethod httpMethod;
    private final Collection<String> consumes;
    private final Collection<String> produces;

    public WebOperationRequestPredicate(String path, WebEndpointHttpMethod httpMethod, Collection<String> consumes, Collection<String> produces) {
        this.path = path;
        this.canonicalPath = this.extractCanonicalPath(path);
        this.matchAllRemainingPathSegmentsVariable = this.extractMatchAllRemainingPathSegmentsVariable(path);
        this.httpMethod = httpMethod;
        this.consumes = consumes;
        this.produces = produces;
    }

    private String extractCanonicalPath(String path) {
        Matcher matcher = PATH_VAR_PATTERN.matcher(path);
        return matcher.replaceAll("$1*}");
    }

    private String extractMatchAllRemainingPathSegmentsVariable(String path) {
        Matcher matcher = ALL_REMAINING_PATH_SEGMENTS_VAR_PATTERN.matcher(path);
        return matcher.matches() ? matcher.group(1) : null;
    }

    public String getPath() {
        return this.path;
    }

    public String getMatchAllRemainingPathSegmentsVariable() {
        return this.matchAllRemainingPathSegmentsVariable;
    }

    public WebEndpointHttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public Collection<String> getConsumes() {
        return Collections.unmodifiableCollection(this.consumes);
    }

    public Collection<String> getProduces() {
        return Collections.unmodifiableCollection(this.produces);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        WebOperationRequestPredicate other = (WebOperationRequestPredicate)obj;
        boolean result = true;
        result = result && this.consumes.equals(other.consumes);
        result = result && this.httpMethod == other.httpMethod;
        result = result && this.canonicalPath.equals(other.canonicalPath);
        result = result && this.produces.equals(other.produces);
        return result;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.consumes.hashCode();
        result = 31 * result + this.httpMethod.hashCode();
        result = 31 * result + this.canonicalPath.hashCode();
        result = 31 * result + this.produces.hashCode();
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder((Object)((Object)this.httpMethod) + " to path '" + this.path + "'");
        if (!CollectionUtils.isEmpty(this.consumes)) {
            result.append(" consumes: ").append(StringUtils.collectionToCommaDelimitedString(this.consumes));
        }
        if (!CollectionUtils.isEmpty(this.produces)) {
            result.append(" produces: ").append(StringUtils.collectionToCommaDelimitedString(this.produces));
        }
        return result.toString();
    }
}

