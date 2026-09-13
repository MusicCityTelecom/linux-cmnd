/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse$IPAddressIntelligenceStatus
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.hjson.JsonValue
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive.intel;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apereo.cas.authentication.adaptive.intel.BaseIPAddressIntelligenceService;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.apereo.cas.util.HttpUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.hjson.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.webflow.execution.RequestContext;

public class BlackDotIPAddressIntelligenceService
extends BaseIPAddressIntelligenceService {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BlackDotIPAddressIntelligenceService.class);
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(false).build().toObjectMapper();

    public BlackDotIPAddressIntelligenceService(AdaptiveAuthenticationProperties adaptiveAuthenticationProperties) {
        super(adaptiveAuthenticationProperties);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public IPAddressIntelligenceResponse examineInternal(RequestContext context, String clientIpAddress) {
        bannedResponse = IPAddressIntelligenceResponse.banned();
        response = null;
        properties = this.adaptiveAuthenticationProperties.getIpIntel().getBlackDot();
        builder = new StringBuilder(String.format(properties.getUrl(), new Object[]{clientIpAddress}));
        builder.append("&format=json");
        if (StringUtils.isNotBlank((CharSequence)properties.getEmailAddress())) {
            builder.append("&contact=");
            builder.append(properties.getEmailAddress());
        }
        var7_8 = properties.getMode().toUpperCase();
        var8_9 = -1;
        switch (var7_8.hashCode()) {
            case 300254581: {
                if (!var7_8.equals("DYNA_LIST")) break;
                var8_9 = 0;
                break;
            }
            case 709602033: {
                if (!var7_8.equals("DYNA_CHECK")) break;
                var8_9 = 1;
                break;
            }
        }
        switch (var8_9) {
            case 0: {
                builder.append("&flags=m");
                break;
            }
            case 1: {
                builder.append("&flags=b");
                break;
            }
            default: {
                builder.append("&flags=f");
            }
        }
        url = builder.toString();
        BlackDotIPAddressIntelligenceService.LOGGER.debug("Sending IP check request to [{}]", (Object)url);
        exec = HttpUtils.HttpExecutionRequest.builder().method(HttpMethod.GET).url(url).build();
        response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
        if (response.getStatusLine().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
            BlackDotIPAddressIntelligenceService.LOGGER.error("Exceeded the number of allowed queries");
            var9_11 = bannedResponse;
        }
        ** GOTO lbl54
        {
            block32: {
                block26: {
                    block31: {
                        block29: {
                            block30: {
                                block27: {
                                    block28: {
                                        catch (Exception e) {
                                            LoggingUtils.error((Logger)BlackDotIPAddressIntelligenceService.LOGGER, (Throwable)e);
                                            HttpUtils.close(response);
                                            return bannedResponse;
                                        }
                                        catch (Throwable var16_24) {
                                            HttpUtils.close(response);
                                            throw var16_24;
                                        }
                                        HttpUtils.close((HttpResponse)response);
                                        return var9_11;
lbl54:
                                        // 1 sources

                                        content = response.getEntity().getContent();
                                        result = IOUtils.toString((InputStream)content, (Charset)StandardCharsets.UTF_8);
                                        BlackDotIPAddressIntelligenceService.LOGGER.debug("Received payload result after examining IP address [{}] as [{}]", (Object)clientIpAddress, (Object)result);
                                        json = (Map)BlackDotIPAddressIntelligenceService.MAPPER.readValue(JsonValue.readHjson((String)result).toString(), Map.class);
                                        status = json.getOrDefault("status", "error").toString();
                                        if (!"success".equalsIgnoreCase(status)) break block26;
                                        rank = Double.parseDouble(json.getOrDefault("result", 1).toString());
                                        if (rank != 1.0) break block27;
                                        var15_20 = bannedResponse;
                                        if (content == null) break block28;
                                        content.close();
                                    }
                                    HttpUtils.close((HttpResponse)response);
                                    return var15_20;
                                }
                                if (rank != 0.0) break block29;
                                var15_21 = IPAddressIntelligenceResponse.allowed();
                                if (content == null) break block30;
                                content.close();
                            }
                            HttpUtils.close((HttpResponse)response);
                            return var15_21;
                        }
                        var15_22 = IPAddressIntelligenceResponse.builder().score(rank).status(IPAddressIntelligenceResponse.IPAddressIntelligenceStatus.RANKED).build();
                        if (content == null) break block31;
                        content.close();
                    }
                    HttpUtils.close((HttpResponse)response);
                    return var15_22;
                }
                try {
                    message = json.getOrDefault("message", "Invalid IP address").toString();
                    BlackDotIPAddressIntelligenceService.LOGGER.error(message);
                    var14_23 = bannedResponse;
                    if (content == null) break block32;
                }
                catch (Throwable var10_14) {}
                {
                    if (content == null) throw var10_14;
                    try {
                        content.close();
                        throw var10_14;
                    }
                    catch (Throwable var11_16) {
                        var10_14.addSuppressed(var11_16);
                    }
                    throw var10_14;
                }
                content.close();
            }
            HttpUtils.close((HttpResponse)response);
            return var14_23;
        }
    }
}

