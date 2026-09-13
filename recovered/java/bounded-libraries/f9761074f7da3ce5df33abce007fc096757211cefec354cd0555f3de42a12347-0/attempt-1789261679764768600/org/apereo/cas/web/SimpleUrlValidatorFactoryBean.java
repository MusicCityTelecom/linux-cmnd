/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.validator.routines.DomainValidator
 *  org.apache.commons.validator.routines.RegexValidator
 *  org.apache.commons.validator.routines.UrlValidator
 *  org.springframework.beans.factory.FactoryBean
 */
package org.apereo.cas.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.apereo.cas.web.SimpleUrlValidator;
import org.apereo.cas.web.UrlValidator;
import org.springframework.beans.factory.FactoryBean;

public class SimpleUrlValidatorFactoryBean
implements FactoryBean<UrlValidator> {
    private static final org.apache.commons.validator.routines.UrlValidator URL_VALIDATOR_ALLOW_LOCAL_URLS = new org.apache.commons.validator.routines.UrlValidator(8L);
    private final boolean allowLocalUrls;
    private final org.apache.commons.validator.routines.UrlValidator urlValidatorWithRegex;

    public SimpleUrlValidatorFactoryBean(boolean allowLocalUrls) {
        this(allowLocalUrls, null, true);
    }

    public SimpleUrlValidatorFactoryBean(boolean allowLocalUrls, String authorityValidationRegEx, boolean authorityValidationRegExCaseSensitive) {
        this.allowLocalUrls = allowLocalUrls;
        this.urlValidatorWithRegex = SimpleUrlValidatorFactoryBean.createUrlValidatorWithRegex(allowLocalUrls, authorityValidationRegEx, authorityValidationRegExCaseSensitive);
    }

    private static org.apache.commons.validator.routines.UrlValidator createUrlValidatorWithRegex(boolean allowLocalUrls, String authorityValidationRegEx, boolean authorityValidationRegExCaseSensitive) {
        if (StringUtils.isEmpty((CharSequence)authorityValidationRegEx)) {
            return null;
        }
        RegexValidator authorityValidator = new RegexValidator(authorityValidationRegEx, authorityValidationRegExCaseSensitive);
        long options = allowLocalUrls ? 8L : 0L;
        return new org.apache.commons.validator.routines.UrlValidator(authorityValidator, options);
    }

    public UrlValidator getObject() {
        return new SimpleUrlValidator(this.getUrlValidator(), this.getDomainValidator());
    }

    private org.apache.commons.validator.routines.UrlValidator getUrlValidator() {
        if (this.urlValidatorWithRegex != null) {
            return this.urlValidatorWithRegex;
        }
        if (this.allowLocalUrls) {
            return URL_VALIDATOR_ALLOW_LOCAL_URLS;
        }
        return org.apache.commons.validator.routines.UrlValidator.getInstance();
    }

    public DomainValidator getDomainValidator() {
        return DomainValidator.getInstance((boolean)this.allowLocalUrls);
    }

    public Class<?> getObjectType() {
        return SimpleUrlValidator.class;
    }

    public boolean isSingleton() {
        return true;
    }
}

