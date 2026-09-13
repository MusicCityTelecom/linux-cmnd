/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.util;

import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apereo.cas.util.DateTimeUtils;
import org.jooq.lambda.Unchecked;

public final class CasVersion {
    public static String asString() {
        return CasVersion.getVersion() + " - " + CasVersion.getSpecificationVersion() + " - " + CasVersion.getDateTime().toString();
    }

    public static String getVersion() {
        return CasVersion.class.getPackage().getImplementationVersion();
    }

    public static String getSpecificationVersion() {
        return CasVersion.class.getPackage().getSpecificationVersion();
    }

    public static ZonedDateTime getDateTime() {
        return (ZonedDateTime)Unchecked.supplier(() -> {
            Class<CasVersion> clazz = CasVersion.class;
            URL resource = clazz.getResource(clazz.getSimpleName() + ".class");
            Instant time = Instant.ofEpochMilli(resource.openConnection().getLastModified());
            return DateTimeUtils.zonedDateTimeOf(time);
        }).get();
    }

    @Generated
    private CasVersion() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

