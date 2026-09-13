/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.servlet.mvc.condition.MediaTypeExpression
 *  org.springframework.web.servlet.mvc.condition.NameValueExpression
 *  org.springframework.web.servlet.mvc.condition.PatternsRequestCondition
 *  org.springframework.web.servlet.mvc.method.RequestMappingInfo
 */
package org.springframework.boot.actuate.web.mappings.servlet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.MediaTypeExpression;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class RequestMappingConditionsDescription {
    private final List<MediaTypeExpressionDescription> consumes;
    private final List<NameValueExpressionDescription> headers;
    private final Set<RequestMethod> methods;
    private final List<NameValueExpressionDescription> params;
    private final Set<String> patterns;
    private final List<MediaTypeExpressionDescription> produces;

    RequestMappingConditionsDescription(RequestMappingInfo requestMapping) {
        this.consumes = requestMapping.getConsumesCondition().getExpressions().stream().map(MediaTypeExpressionDescription::new).collect(Collectors.toList());
        this.headers = requestMapping.getHeadersCondition().getExpressions().stream().map(NameValueExpressionDescription::new).collect(Collectors.toList());
        this.methods = requestMapping.getMethodsCondition().getMethods();
        this.params = requestMapping.getParamsCondition().getExpressions().stream().map(NameValueExpressionDescription::new).collect(Collectors.toList());
        this.patterns = this.extractPathPatterns(requestMapping);
        this.produces = requestMapping.getProducesCondition().getExpressions().stream().map(MediaTypeExpressionDescription::new).collect(Collectors.toList());
    }

    private Set<String> extractPathPatterns(RequestMappingInfo requestMapping) {
        PatternsRequestCondition patternsCondition = requestMapping.getPatternsCondition();
        return patternsCondition != null ? patternsCondition.getPatterns() : requestMapping.getPathPatternsCondition().getPatternValues();
    }

    public List<MediaTypeExpressionDescription> getConsumes() {
        return this.consumes;
    }

    public List<NameValueExpressionDescription> getHeaders() {
        return this.headers;
    }

    public Set<RequestMethod> getMethods() {
        return this.methods;
    }

    public List<NameValueExpressionDescription> getParams() {
        return this.params;
    }

    public Set<String> getPatterns() {
        return this.patterns;
    }

    public List<MediaTypeExpressionDescription> getProduces() {
        return this.produces;
    }

    public static class NameValueExpressionDescription {
        private final String name;
        private final Object value;
        private final boolean negated;

        NameValueExpressionDescription(NameValueExpression<?> expression) {
            this.name = expression.getName();
            this.value = expression.getValue();
            this.negated = expression.isNegated();
        }

        public String getName() {
            return this.name;
        }

        public Object getValue() {
            return this.value;
        }

        public boolean isNegated() {
            return this.negated;
        }
    }

    public static class MediaTypeExpressionDescription {
        private final String mediaType;
        private final boolean negated;

        MediaTypeExpressionDescription(MediaTypeExpression expression) {
            this.mediaType = expression.getMediaType().toString();
            this.negated = expression.isNegated();
        }

        public String getMediaType() {
            return this.mediaType;
        }

        public boolean isNegated() {
            return this.negated;
        }
    }
}

