/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.RestEndpointProperties
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.hjson.JsonValue
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.SimplePrincipal;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class RestfulPrincipalFactory
extends DefaultPrincipalFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulPrincipalFactory.class);
    private static final long serialVersionUID = -1344968589212057694L;
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(false).build().toObjectMapper();
    private final RestEndpointProperties properties;

    /*
     * Loose catch block
     */
    @Override
    public Principal createPrincipal(String id, Map<String, List<Object>> attributes) {
        HttpResponse response;
        block10: {
            Principal principal;
            InputStream content;
            block11: {
                response = null;
                Principal current = super.createPrincipal(id, attributes);
                String entity = MAPPER.writeValueAsString((Object)current);
                Map headers = CollectionUtils.wrap((String)"Content-Type", (Object)"application/json");
                headers.putAll(this.properties.getHeaders());
                HttpUtils.HttpExecutionRequest exec = HttpUtils.HttpExecutionRequest.builder().basicAuthPassword(this.properties.getBasicAuthPassword()).basicAuthUsername(this.properties.getBasicAuthUsername()).method(HttpMethod.POST).url(this.properties.getUrl()).entity(entity).headers(headers).build();
                response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
                if (response == null || response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) break block10;
                content = response.getEntity().getContent();
                String result = IOUtils.toString((InputStream)content, (Charset)StandardCharsets.UTF_8);
                LOGGER.debug("Principal factory response received: [{}]", (Object)result);
                principal = (Principal)MAPPER.readValue(JsonValue.readHjson((String)result).toString(), SimplePrincipal.class);
                if (content == null) break block11;
                content.close();
            }
            HttpUtils.close((HttpResponse)response);
            return principal;
            {
                catch (Throwable throwable) {
                    try {
                        if (content != null) {
                            try {
                                content.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (Exception e) {
                        try {
                            throw new IllegalArgumentException(e.getMessage(), e);
                        }
                        catch (Throwable throwable3) {
                            HttpUtils.close(response);
                            throw throwable3;
                        }
                    }
                }
            }
        }
        HttpUtils.close((HttpResponse)response);
        LOGGER.error("Unable to create principal from REST endpoint [{}] for [{}]", (Object)this.properties.getUrl(), (Object)id);
        return null;
    }

    @Generated
    public RestfulPrincipalFactory(RestEndpointProperties properties) {
        this.properties = properties;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RestfulPrincipalFactory)) {
            return false;
        }
        RestfulPrincipalFactory other = (RestfulPrincipalFactory)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RestEndpointProperties this$properties = this.properties;
        RestEndpointProperties other$properties = other.properties;
        return !(this$properties == null ? other$properties != null : !this$properties.equals(other$properties));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RestfulPrincipalFactory;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        RestEndpointProperties $properties = this.properties;
        result = result * 59 + ($properties == null ? 43 : $properties.hashCode());
        return result;
    }
}

