/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.ServiceMatchingStrategy
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceMatchingStrategy;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServiceMatchingStrategy
implements ServiceMatchingStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceMatchingStrategy.class);
    private static final Pattern FRAGMENT_PATTERN = Pattern.compile("#.+");
    private final ServicesManager servicesManager;

    public boolean matches(Service service, Service serviceToMatch) {
        try {
            String thisUrl = DefaultServiceMatchingStrategy.removeFragmentFrom(URLDecoder.decode(service.getId(), StandardCharsets.UTF_8));
            String serviceUrl = DefaultServiceMatchingStrategy.removeFragmentFrom(URLDecoder.decode(serviceToMatch.getId(), StandardCharsets.UTF_8));
            LOGGER.debug("Decoded urls and comparing [{}] with [{}]", (Object)thisUrl, (Object)serviceUrl);
            return thisUrl.equalsIgnoreCase(serviceUrl);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return false;
        }
    }

    private static String removeFragmentFrom(String id) {
        return FRAGMENT_PATTERN.matcher(id).replaceAll("");
    }

    @Generated
    public DefaultServiceMatchingStrategy(ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }
}

