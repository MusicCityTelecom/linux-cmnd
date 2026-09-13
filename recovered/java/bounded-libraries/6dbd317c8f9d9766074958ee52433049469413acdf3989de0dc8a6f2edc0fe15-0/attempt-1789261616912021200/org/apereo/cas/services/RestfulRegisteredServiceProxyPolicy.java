/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.PrettyPrinter
 *  com.fasterxml.jackson.core.util.MinimalPrettyPrinter
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  lombok.Generated
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceProxyPolicy
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;
import org.apache.http.HttpResponse;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceProxyPolicy;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class RestfulRegisteredServiceProxyPolicy
implements RegisteredServiceProxyPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulRegisteredServiceProxyPolicy.class);
    private static final long serialVersionUID = -222069319543047324L;
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().singleValueAsArray(true).defaultTypingEnabled(true).build().toObjectMapper();
    private String endpoint;
    private Map<String, String> headers = new TreeMap<String, String>();

    @JsonIgnore
    public boolean isAllowedToProxy() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public boolean isAllowedProxyCallbackUrl(RegisteredService registeredService, URL pgtUrl) {
        HttpResponse response = null;
        StringWriter writer = new StringWriter();
        MAPPER.writer((PrettyPrinter)new MinimalPrettyPrinter()).writeValue((Writer)writer, (Object)registeredService);
        HttpUtils.HttpExecutionRequest exec = HttpUtils.HttpExecutionRequest.builder().method(HttpMethod.GET).headers(this.headers).url(SpringExpressionLanguageValueResolver.getInstance().resolve(this.endpoint)).entity(writer.toString()).parameters(CollectionUtils.wrap((String)"pgtUrl", (Object)pgtUrl.toExternalForm())).headers(CollectionUtils.wrap((String)"Content-Type", (Object)"application/json")).build();
        response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
        boolean bl = HttpStatus.valueOf((int)response.getStatusLine().getStatusCode()).is2xxSuccessful();
        writer.close();
        HttpUtils.close((HttpResponse)response);
        return bl;
        {
            catch (Throwable throwable) {
                try {
                    try {
                        try {
                            writer.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                    catch (Exception e) {
                        LoggingUtils.error((Logger)LOGGER, (Throwable)e);
                        HttpUtils.close(response);
                    }
                }
                catch (Throwable throwable3) {
                    HttpUtils.close(response);
                    throw throwable3;
                }
            }
        }
        return false;
    }

    @Generated
    public String toString() {
        return "RestfulRegisteredServiceProxyPolicy(endpoint=" + this.endpoint + ", headers=" + this.headers + ")";
    }

    @Generated
    public String getEndpoint() {
        return this.endpoint;
    }

    @Generated
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Generated
    public RestfulRegisteredServiceProxyPolicy setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Generated
    public RestfulRegisteredServiceProxyPolicy setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Generated
    public RestfulRegisteredServiceProxyPolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RestfulRegisteredServiceProxyPolicy)) {
            return false;
        }
        RestfulRegisteredServiceProxyPolicy other = (RestfulRegisteredServiceProxyPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$endpoint = this.endpoint;
        String other$endpoint = other.endpoint;
        if (this$endpoint == null ? other$endpoint != null : !this$endpoint.equals(other$endpoint)) {
            return false;
        }
        Map<String, String> this$headers = this.headers;
        Map<String, String> other$headers = other.headers;
        return !(this$headers == null ? other$headers != null : !((Object)this$headers).equals(other$headers));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RestfulRegisteredServiceProxyPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $endpoint = this.endpoint;
        result = result * 59 + ($endpoint == null ? 43 : $endpoint.hashCode());
        Map<String, String> $headers = this.headers;
        result = result * 59 + ($headers == null ? 43 : ((Object)$headers).hashCode());
        return result;
    }
}

