/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.authentication.adaptive.geo;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;

public class GeoLocationResponse
implements Serializable {
    private static final long serialVersionUID = -4380882448842426005L;
    private final Set<String> addresses = new ConcurrentSkipListSet<String>();
    private double latitude;
    private double longitude;

    @CanIgnoreReturnValue
    public GeoLocationResponse addAddress(String address) {
        if (StringUtils.isNotBlank((CharSequence)address)) {
            this.addresses.add(address);
        }
        return this;
    }

    public String build() {
        return String.join((CharSequence)",", this.addresses);
    }

    @Generated
    protected GeoLocationResponse(GeoLocationResponseBuilder<?, ?> b) {
        this.latitude = b.latitude;
        this.longitude = b.longitude;
    }

    @Generated
    public static GeoLocationResponseBuilder<?, ?> builder() {
        return new GeoLocationResponseBuilderImpl();
    }

    @Generated
    public String toString() {
        return "GeoLocationResponse(addresses=" + this.addresses + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ")";
    }

    @Generated
    public Set<String> getAddresses() {
        return this.addresses;
    }

    @Generated
    public double getLatitude() {
        return this.latitude;
    }

    @Generated
    public double getLongitude() {
        return this.longitude;
    }

    @Generated
    public GeoLocationResponse setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    @Generated
    public GeoLocationResponse setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Generated
    public GeoLocationResponse() {
    }

    @Generated
    private static final class GeoLocationResponseBuilderImpl
    extends GeoLocationResponseBuilder<GeoLocationResponse, GeoLocationResponseBuilderImpl> {
        @Generated
        private GeoLocationResponseBuilderImpl() {
        }

        @Override
        @Generated
        protected GeoLocationResponseBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public GeoLocationResponse build() {
            return new GeoLocationResponse(this);
        }
    }

    @Generated
    public static abstract class GeoLocationResponseBuilder<C extends GeoLocationResponse, B extends GeoLocationResponseBuilder<C, B>> {
        @Generated
        private double latitude;
        @Generated
        private double longitude;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B latitude(double latitude) {
            this.latitude = latitude;
            return this.self();
        }

        @Generated
        public B longitude(double longitude) {
            this.longitude = longitude;
            return this.self();
        }

        @Generated
        public String toString() {
            return "GeoLocationResponse.GeoLocationResponseBuilder(latitude=" + this.latitude + ", longitude=" + this.longitude + ")";
        }
    }
}

