/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.credential.UsernamePasswordCredential
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.rest.factory;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

public class UsernamePasswordRestHttpRequestCredentialFactory
implements RestHttpRequestCredentialFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(UsernamePasswordRestHttpRequestCredentialFactory.class);
    private int order = Integer.MIN_VALUE;

    @Override
    public List<Credential> fromRequest(HttpServletRequest request, MultiValueMap<String, String> requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            LOGGER.debug("Skipping [{}] because the requestBody is null or empty", (Object)this.getClass().getSimpleName());
            return new ArrayList<Credential>(0);
        }
        String username = (String)requestBody.getFirst((Object)"username");
        String password = (String)requestBody.getFirst((Object)"password");
        if (StringUtils.isBlank((CharSequence)username) || StringUtils.isBlank((CharSequence)password)) {
            LOGGER.debug("Invalid payload; missing required fields.");
            return new ArrayList<Credential>(0);
        }
        UsernamePasswordCredential c = new UsernamePasswordCredential(username, password);
        return CollectionUtils.wrap((Object)c);
    }

    @Override
    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

