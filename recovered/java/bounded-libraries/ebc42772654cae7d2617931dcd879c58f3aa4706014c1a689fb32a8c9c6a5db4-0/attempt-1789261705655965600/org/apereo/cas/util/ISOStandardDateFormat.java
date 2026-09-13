/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.time.FastDateFormat
 */
package org.apereo.cas.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apereo.cas.util.DateTimeUtils;

public class ISOStandardDateFormat
extends FastDateFormat {
    private static final long serialVersionUID = 9196017562782775535L;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public ISOStandardDateFormat() {
        super(DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());
    }

    public String getCurrentDateAndTime() {
        return this.format(ZonedDateTime.now(ZoneOffset.UTC));
    }

    public String format(ZonedDateTime dt) {
        return this.format(DateTimeUtils.dateOf(dt));
    }

    public String format(Instant dt) {
        return this.format(DateTimeUtils.dateOf(dt));
    }
}

