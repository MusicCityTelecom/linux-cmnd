/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.support.mfa.RestfulMultifactorAuthenticationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.authentication.mfa.trigger;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.mfa.RestfulMultifactorAuthenticationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class RestEndpointMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RestEndpointMultifactorAuthenticationTrigger.class);
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(false).build().toObjectMapper();
    private final CasConfigurationProperties casProperties;
    private final MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        RestfulMultifactorAuthenticationProperties restEndpoint = this.casProperties.getAuthn().getMfa().getTriggers().getRest();
        if (service == null || authentication == null) {
            LOGGER.trace("No service or authentication is available to determine event for principal");
            return Optional.empty();
        }
        Principal principal = authentication.getPrincipal();
        if (StringUtils.isBlank((CharSequence)restEndpoint.getUrl())) {
            LOGGER.trace("Rest endpoint to determine event is not configured for [{}]", (Object)principal.getId());
            return Optional.empty();
        }
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            return Optional.empty();
        }
        LOGGER.debug("Contacting [{}] to inquire about [{}]", (Object)restEndpoint, (Object)principal.getId());
        String results = (String)FunctionUtils.doUnchecked(() -> this.callRestEndpointForMultifactor(principal, service));
        if (StringUtils.isNotBlank((CharSequence)results)) {
            return MultifactorAuthenticationUtils.getMultifactorAuthenticationProviderById(results, this.applicationContext);
        }
        return Optional.empty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    protected String callRestEndpointForMultifactor(Principal principal, Service resolvedService) throws Exception {
        HttpResponse response;
        block8: {
            String string;
            InputStream content;
            block9: {
                response = null;
                RestfulMultifactorAuthenticationProperties rest = this.casProperties.getAuthn().getMfa().getTriggers().getRest();
                RestEndpointEntity entity = new RestEndpointEntity(principal.getId(), resolvedService.getId());
                Map headers = CollectionUtils.wrap((String)"Content-Type", (Object)"application/json");
                headers.putAll(rest.getHeaders());
                HttpUtils.HttpExecutionRequest exec = HttpUtils.HttpExecutionRequest.builder().basicAuthPassword(rest.getBasicAuthPassword()).basicAuthUsername(rest.getBasicAuthUsername()).method(HttpMethod.valueOf((String)rest.getMethod().toUpperCase().trim())).url(rest.getUrl()).headers(headers).entity(MAPPER.writeValueAsString((Object)entity)).build();
                response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
                HttpStatus status = HttpStatus.valueOf((int)response.getStatusLine().getStatusCode());
                if (!status.is2xxSuccessful()) break block8;
                content = response.getEntity().getContent();
                string = IOUtils.toString((InputStream)content, (Charset)StandardCharsets.UTF_8);
                if (content == null) break block9;
                content.close();
            }
            HttpUtils.close((HttpResponse)response);
            return string;
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
                    catch (Throwable throwable3) {
                        HttpUtils.close(response);
                        throw throwable3;
                    }
                }
            }
        }
        HttpUtils.close((HttpResponse)response);
        return null;
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }

    @Generated
    public MultifactorAuthenticationProviderResolver getMultifactorAuthenticationProviderResolver() {
        return this.multifactorAuthenticationProviderResolver;
    }

    @Generated
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public RestEndpointMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.multifactorAuthenticationProviderResolver = multifactorAuthenticationProviderResolver;
        this.applicationContext = applicationContext;
    }

    public static class RestEndpointEntity {
        private final String principalId;
        private final String serviceId;

        @Generated
        public String getPrincipalId() {
            return this.principalId;
        }

        @Generated
        public String getServiceId() {
            return this.serviceId;
        }

        @Generated
        public RestEndpointEntity(String principalId, String serviceId) {
            this.principalId = principalId;
            this.serviceId = serviceId;
        }

        @Generated
        public String toString() {
            return "RestEndpointMultifactorAuthenticationTrigger.RestEndpointEntity(principalId=" + this.principalId + ", serviceId=" + this.serviceId + ")";
        }

        @Generated
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof RestEndpointEntity)) {
                return false;
            }
            RestEndpointEntity other = (RestEndpointEntity)o;
            if (!other.canEqual(this)) {
                return false;
            }
            String this$principalId = this.principalId;
            String other$principalId = other.principalId;
            if (this$principalId == null ? other$principalId != null : !this$principalId.equals(other$principalId)) {
                return false;
            }
            String this$serviceId = this.serviceId;
            String other$serviceId = other.serviceId;
            return !(this$serviceId == null ? other$serviceId != null : !this$serviceId.equals(other$serviceId));
        }

        @Generated
        protected boolean canEqual(Object other) {
            return other instanceof RestEndpointEntity;
        }

        @Generated
        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            String $principalId = this.principalId;
            result = result * 59 + ($principalId == null ? 43 : $principalId.hashCode());
            String $serviceId = this.serviceId;
            result = result * 59 + ($serviceId == null ? 43 : $serviceId.hashCode());
            return result;
        }
    }
}

