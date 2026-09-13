/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.web.flow;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.webflow.execution.RequestContext;

public class SingleSignOnParticipationRequest {
    private final RequestContext requestContext;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final Map<String, Object> attributes;

    public Optional<RequestContext> getRequestContext() {
        return Optional.ofNullable(this.requestContext);
    }

    public Optional<HttpServletRequest> getHttpServletRequest() {
        return Optional.ofNullable(this.httpServletRequest);
    }

    public Optional<HttpServletResponse> getHttpServletResponse() {
        return Optional.ofNullable(this.httpServletResponse);
    }

    public <T> T getAttributeValue(String key, Class<T> clazz) {
        return clazz.cast(this.attributes.get(key));
    }

    public boolean containsAttribute(String key) {
        return this.attributes.containsKey(key);
    }

    @CanIgnoreReturnValue
    public SingleSignOnParticipationRequest attribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public boolean isRequestingRenewAuthentication() {
        return this.getRequestParameter("renew").isPresent();
    }

    public Optional<String> getRequestParameter(String key) {
        String result = this.getHttpServletRequest().map(request -> request.getParameter(key)).filter(StringUtils::isNotBlank).orElseGet(() -> this.getRequestContext().map(context -> context.getRequestParameters().get(key)).orElse(null));
        return Optional.ofNullable(result);
    }

    @Generated
    private static Map<String, Object> $default$attributes() {
        return new LinkedHashMap<String, Object>();
    }

    @Generated
    protected SingleSignOnParticipationRequest(SingleSignOnParticipationRequestBuilder<?, ?> b) {
        this.requestContext = b.requestContext;
        this.httpServletRequest = b.httpServletRequest;
        this.httpServletResponse = b.httpServletResponse;
        this.attributes = b.attributes$set ? b.attributes$value : SingleSignOnParticipationRequest.$default$attributes();
    }

    @Generated
    public static SingleSignOnParticipationRequestBuilder<?, ?> builder() {
        return new SingleSignOnParticipationRequestBuilderImpl();
    }

    @Generated
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Generated
    private static final class SingleSignOnParticipationRequestBuilderImpl
    extends SingleSignOnParticipationRequestBuilder<SingleSignOnParticipationRequest, SingleSignOnParticipationRequestBuilderImpl> {
        @Generated
        private SingleSignOnParticipationRequestBuilderImpl() {
        }

        @Override
        @Generated
        protected SingleSignOnParticipationRequestBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public SingleSignOnParticipationRequest build() {
            return new SingleSignOnParticipationRequest(this);
        }
    }

    @Generated
    public static abstract class SingleSignOnParticipationRequestBuilder<C extends SingleSignOnParticipationRequest, B extends SingleSignOnParticipationRequestBuilder<C, B>> {
        @Generated
        private RequestContext requestContext;
        @Generated
        private HttpServletRequest httpServletRequest;
        @Generated
        private HttpServletResponse httpServletResponse;
        @Generated
        private boolean attributes$set;
        @Generated
        private Map<String, Object> attributes$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B requestContext(RequestContext requestContext) {
            this.requestContext = requestContext;
            return this.self();
        }

        @Generated
        public B httpServletRequest(HttpServletRequest httpServletRequest) {
            this.httpServletRequest = httpServletRequest;
            return this.self();
        }

        @Generated
        public B httpServletResponse(HttpServletResponse httpServletResponse) {
            this.httpServletResponse = httpServletResponse;
            return this.self();
        }

        @Generated
        public B attributes(Map<String, Object> attributes) {
            this.attributes$value = attributes;
            this.attributes$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "SingleSignOnParticipationRequest.SingleSignOnParticipationRequestBuilder(requestContext=" + this.requestContext + ", httpServletRequest=" + this.httpServletRequest + ", httpServletResponse=" + this.httpServletResponse + ", attributes$value=" + this.attributes$value + ")";
        }
    }
}

