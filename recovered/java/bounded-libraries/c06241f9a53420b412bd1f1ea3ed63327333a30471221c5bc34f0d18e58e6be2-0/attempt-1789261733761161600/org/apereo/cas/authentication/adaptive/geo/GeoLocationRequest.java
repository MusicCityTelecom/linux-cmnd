/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.authentication.adaptive.geo;

import java.io.Serializable;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;

public class GeoLocationRequest
implements Serializable {
    private static final long serialVersionUID = -3330957747025206526L;
    private String latitude;
    private String longitude;
    private String accuracy;
    private String timestamp;

    public GeoLocationRequest(double latitude, double longitude) {
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank((CharSequence)this.latitude) && StringUtils.isNotBlank((CharSequence)this.longitude) && StringUtils.isNotBlank((CharSequence)this.accuracy) && StringUtils.isNotBlank((CharSequence)this.timestamp);
    }

    @Generated
    public String toString() {
        return "GeoLocationRequest(latitude=" + this.latitude + ", longitude=" + this.longitude + ", accuracy=" + this.accuracy + ", timestamp=" + this.timestamp + ")";
    }

    @Generated
    public String getLatitude() {
        return this.latitude;
    }

    @Generated
    public String getLongitude() {
        return this.longitude;
    }

    @Generated
    public String getAccuracy() {
        return this.accuracy;
    }

    @Generated
    public String getTimestamp() {
        return this.timestamp;
    }

    @Generated
    public GeoLocationRequest() {
    }

    @Generated
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Generated
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Generated
    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    @Generated
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GeoLocationRequest)) {
            return false;
        }
        GeoLocationRequest other = (GeoLocationRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$latitude = this.latitude;
        String other$latitude = other.latitude;
        if (this$latitude == null ? other$latitude != null : !this$latitude.equals(other$latitude)) {
            return false;
        }
        String this$longitude = this.longitude;
        String other$longitude = other.longitude;
        return !(this$longitude == null ? other$longitude != null : !this$longitude.equals(other$longitude));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GeoLocationRequest;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $latitude = this.latitude;
        result = result * 59 + ($latitude == null ? 43 : $latitude.hashCode());
        String $longitude = this.longitude;
        result = result * 59 + ($longitude == null ? 43 : $longitude.hashCode());
        return result;
    }
}

