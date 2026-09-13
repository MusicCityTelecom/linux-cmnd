/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.oer.its.Duration
 *  org.bouncycastle.oer.its.ValidityPeriod
 */
package org.bouncycastle.its;

import java.util.Date;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.Duration;
import org.bouncycastle.oer.its.ValidityPeriod;

public class ITSValidityPeriod {
    private final long startDate;
    private final int duration;
    private final Unit timeUnit;

    public static Builder from(Date date) {
        return new Builder(date);
    }

    public ITSValidityPeriod(ValidityPeriod validityPeriod) {
        this.startDate = validityPeriod.getTime32().longValueExact();
        Duration duration = validityPeriod.getDuration();
        this.duration = duration.getValue();
        this.timeUnit = Unit.values()[duration.getTag()];
    }

    ITSValidityPeriod(long l, int n, Unit unit) {
        this.startDate = l;
        this.duration = n;
        this.timeUnit = unit;
    }

    public Date getStartDate() {
        return new Date(this.startDate);
    }

    public ValidityPeriod toASN1Structure() {
        return ValidityPeriod.builder().setTime32(new ASN1Integer(this.startDate / 1000L)).setDuration(new Duration(this.duration, this.timeUnit.unitTag)).createValidityPeriod();
    }

    public static class Builder {
        private final long startDate;

        Builder(Date date) {
            this.startDate = date.getTime();
        }

        public ITSValidityPeriod plusYears(int n) {
            return new ITSValidityPeriod(this.startDate, n, Unit.years);
        }

        public ITSValidityPeriod plusSixtyHours(int n) {
            return new ITSValidityPeriod(this.startDate, n, Unit.sixtyHours);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Unit {
        microseconds(0),
        milliseconds(1),
        seconds(2),
        minutes(3),
        hours(4),
        sixtyHours(5),
        years(6);

        private final int unitTag;

        private Unit(int n2) {
            this.unitTag = n2;
        }
    }
}

