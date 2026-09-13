/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.util.gen.Base64RandomStringGenerator
 *  org.apereo.cas.util.gen.DefaultLongNumericGenerator
 *  org.apereo.cas.util.gen.NumericGenerator
 *  org.apereo.cas.util.gen.RandomStringGenerator
 */
package org.apereo.cas.util;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.util.gen.Base64RandomStringGenerator;
import org.apereo.cas.util.gen.DefaultLongNumericGenerator;
import org.apereo.cas.util.gen.NumericGenerator;
import org.apereo.cas.util.gen.RandomStringGenerator;

public class DefaultUniqueTicketIdGenerator
implements UniqueTicketIdGenerator {
    private NumericGenerator numericGenerator;
    private RandomStringGenerator randomStringGenerator;
    private String suffix;

    public DefaultUniqueTicketIdGenerator() {
        this(24L);
    }

    public DefaultUniqueTicketIdGenerator(long maxLength) {
        this(maxLength, null);
    }

    public DefaultUniqueTicketIdGenerator(long maxLength, String suffix) {
        this.setMaxLength(maxLength);
        this.setSuffix(suffix);
    }

    public DefaultUniqueTicketIdGenerator(NumericGenerator numericGenerator, RandomStringGenerator randomStringGenerator, String suffix) {
        this.randomStringGenerator = randomStringGenerator;
        this.numericGenerator = numericGenerator;
        this.setSuffix(suffix);
    }

    public String getNewTicketId(String prefix) {
        String number = this.numericGenerator.getNextNumberAsString();
        String ticketBody = this.randomStringGenerator.getNewString().replace('_', '-');
        String origSuffix = StringUtils.defaultString((String)this.suffix);
        Object finalizedSuffix = StringUtils.isEmpty((CharSequence)origSuffix) ? origSuffix : "-" + origSuffix;
        return prefix + "-" + number + "-" + ticketBody + (String)finalizedSuffix;
    }

    public void setMaxLength(long maxLength) {
        this.randomStringGenerator = new Base64RandomStringGenerator(maxLength);
        this.numericGenerator = new DefaultLongNumericGenerator(1L);
    }

    @Generated
    public void setNumericGenerator(NumericGenerator numericGenerator) {
        this.numericGenerator = numericGenerator;
    }

    @Generated
    public void setRandomStringGenerator(RandomStringGenerator randomStringGenerator) {
        this.randomStringGenerator = randomStringGenerator;
    }

    @Generated
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

