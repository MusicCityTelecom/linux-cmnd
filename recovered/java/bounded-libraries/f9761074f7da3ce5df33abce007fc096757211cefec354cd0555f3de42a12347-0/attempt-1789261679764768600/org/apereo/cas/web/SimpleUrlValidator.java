/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.validator.routines.DomainValidator
 *  org.apache.commons.validator.routines.UrlValidator
 */
package org.apereo.cas.web;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apereo.cas.web.UrlValidator;

public class SimpleUrlValidator
implements UrlValidator {
    private static UrlValidator INSTANCE;
    private final org.apache.commons.validator.routines.UrlValidator urlValidator;
    private final DomainValidator domainValidator;

    public static UrlValidator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleUrlValidator(org.apache.commons.validator.routines.UrlValidator.getInstance(), DomainValidator.getInstance());
        }
        return INSTANCE;
    }

    @Override
    public boolean isValid(String value) {
        return StringUtils.isNotBlank((CharSequence)value) && this.urlValidator.isValid(value);
    }

    @Override
    public boolean isValidDomain(String value) {
        return StringUtils.isNotBlank((CharSequence)value) && this.domainValidator.isValid(value);
    }

    @Generated
    public SimpleUrlValidator(org.apache.commons.validator.routines.UrlValidator urlValidator, DomainValidator domainValidator) {
        this.urlValidator = urlValidator;
        this.domainValidator = domainValidator;
    }
}

