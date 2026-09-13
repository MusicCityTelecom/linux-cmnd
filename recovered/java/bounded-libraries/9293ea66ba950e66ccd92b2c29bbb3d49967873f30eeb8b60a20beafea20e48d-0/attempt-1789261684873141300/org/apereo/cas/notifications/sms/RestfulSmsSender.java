/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.configuration.model.support.sms.RestfulSmsProperties
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.notifications.sms;

import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apache.http.HttpResponse;
import org.apereo.cas.configuration.model.support.sms.RestfulSmsProperties;
import org.apereo.cas.notifications.sms.SmsSender;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class RestfulSmsSender
implements SmsSender {
    private final RestfulSmsProperties restProperties;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean send(String from, String to, String message) {
        HttpResponse response;
        block3: {
            boolean bl;
            response = null;
            try {
                HashMap<String, String> parameters = new HashMap<String, String>();
                ClientInfo holder = ClientInfoHolder.getClientInfo();
                if (holder != null) {
                    parameters.put("clientIpAddress", holder.getClientIpAddress());
                    parameters.put("serverIpAddress", holder.getServerIpAddress());
                }
                parameters.put("from", from);
                parameters.put("to", to);
                Map headers = CollectionUtils.wrap((String)"Content-Type", (Object)"application/json");
                headers.putAll(this.restProperties.getHeaders());
                HttpUtils.HttpExecutionRequest exec = HttpUtils.HttpExecutionRequest.builder().basicAuthPassword(this.restProperties.getBasicAuthPassword()).basicAuthUsername(this.restProperties.getBasicAuthUsername()).method(HttpMethod.valueOf((String)this.restProperties.getMethod().toUpperCase())).url(this.restProperties.getUrl()).parameters(parameters).entity(message).headers(headers).build();
                response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
                if (response == null) break block3;
                HttpStatus status = HttpStatus.valueOf((int)response.getStatusLine().getStatusCode());
                bl = status.is2xxSuccessful();
            }
            catch (Throwable throwable) {
                HttpUtils.close(response);
                throw throwable;
            }
            HttpUtils.close((HttpResponse)response);
            return bl;
        }
        HttpUtils.close((HttpResponse)response);
        return false;
    }

    @Generated
    public RestfulSmsProperties getRestProperties() {
        return this.restProperties;
    }

    @Generated
    public RestfulSmsSender(RestfulSmsProperties restProperties) {
        this.restProperties = restProperties;
    }
}

