/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.support.ReloadableResourceBundleMessageSource
 *  org.springframework.context.support.ReloadableResourceBundleMessageSource$PropertiesHolder
 */
package org.apereo.cas.web.view;

import java.util.Locale;
import java.util.stream.IntStream;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class CasReloadableMessageBundle
extends ReloadableResourceBundleMessageSource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasReloadableMessageBundle.class);
    private String[] basenames;

    protected String getDefaultMessage(String code) {
        String messageToReturn = super.getDefaultMessage(code);
        if (StringUtils.isNotBlank((CharSequence)messageToReturn) && messageToReturn.equals(code)) {
            LOGGER.trace("The code [{}] cannot be found in the default language bundle and will be used as the message itself.", (Object)code);
        }
        return messageToReturn;
    }

    protected String getMessageInternal(String code, Object[] args, Locale locale) {
        boolean foundCode;
        if (!locale.equals(Locale.ENGLISH) && !(foundCode = IntStream.range(0, this.basenames.length).filter(i -> {
            String filename = this.basenames[i] + "_" + locale;
            LOGGER.trace("Examining bundle [{}] for the key [{}]", (Object)filename, (Object)code);
            ReloadableResourceBundleMessageSource.PropertiesHolder holder = this.getProperties(filename);
            return holder != null && holder.getProperties() != null && holder.getProperty(code) != null;
        }).findFirst().isPresent())) {
            LOGGER.trace("The key [{}] cannot be found in the bundle for the locale [{}]", (Object)code, (Object)locale);
        }
        return super.getMessageInternal(code, args, locale);
    }

    public void setBasenames(String ... basenames) {
        this.basenames = basenames;
        super.setBasenames(basenames);
    }
}

