/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  javax.persistence.CollectionTable
 *  javax.persistence.Column
 *  javax.persistence.ElementCollection
 *  javax.persistence.JoinColumn
 *  javax.persistence.MapKeyColumn
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest
 *  org.springframework.data.annotation.Id
 */
package org.apereo.cas.support.events.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.springframework.data.annotation.Id;

@MappedSuperclass
public class CasEvent
implements Serializable {
    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_EVENT_ID = "eventId";
    public static final String FIELD_CLIENT_IP = "clientip";
    public static final String FIELD_SERVER_IP = "serverip";
    public static final String FIELD_AGENT = "agent";
    public static final String FIELD_GEO_LATITUDE = "geoLatitude";
    public static final String FIELD_GEO_LONGITUDE = "geoLongitude";
    public static final String FIELD_GEO_ACCURACY = "geoAccuracy";
    public static final String FIELD_GEO_TIMESTAMP = "geoTimestamp";
    private static final long serialVersionUID = -4206712375316470417L;
    @Id
    @JsonProperty
    @Transient
    private long id = -1L;
    @JsonProperty(value="type")
    @Column(nullable=false)
    private String type;
    @JsonProperty(value="principalId")
    @Column(nullable=false)
    private String principalId;
    @JsonProperty(value="creationTime")
    @Column(nullable=false)
    private String creationTime;
    @JsonProperty(value="properties")
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="events_properties", joinColumns={@JoinColumn(name="eventId")})
    private Map<String, String> properties = new HashMap<String, String>(0);

    public CasEvent() {
        this.id = System.currentTimeMillis();
    }

    public void putTimestamp(Long time) {
        this.put(FIELD_TIMESTAMP, time.toString());
    }

    public void putEventId(String eventId) {
        this.put(FIELD_EVENT_ID, eventId);
    }

    public void putClientIpAddress(String loc) {
        this.put(FIELD_CLIENT_IP, loc);
    }

    public void putServerIpAddress(String loc) {
        this.put(FIELD_SERVER_IP, loc);
    }

    public void putAgent(String dev) {
        this.put(FIELD_AGENT, dev);
    }

    @JsonIgnore
    public Long getTimestamp() {
        return Long.valueOf(this.get(FIELD_TIMESTAMP));
    }

    @JsonIgnore
    public String getAgent() {
        return this.get(FIELD_AGENT);
    }

    @JsonIgnore
    public String getEventId() {
        return this.get(FIELD_EVENT_ID);
    }

    @JsonIgnore
    public String getClientIpAddress() {
        return this.get(FIELD_CLIENT_IP);
    }

    @JsonIgnore
    public String getServerIpAddress() {
        return this.get(FIELD_SERVER_IP);
    }

    public void put(String key, String value) {
        if (StringUtils.isBlank((CharSequence)value)) {
            this.properties.remove(key);
        } else {
            this.properties.put(key, value);
        }
    }

    public String get(String key) {
        return this.properties.get(key);
    }

    public void putGeoLocation(GeoLocationRequest location) {
        this.putGeoAccuracy(location.getAccuracy());
        this.putGeoLatitude(location.getLatitude());
        this.putGeoLongitude(location.getLongitude());
        this.putGeoTimestamp(location.getTimestamp());
    }

    @JsonIgnore
    public GeoLocationRequest getGeoLocation() {
        GeoLocationRequest request = new GeoLocationRequest();
        request.setAccuracy(this.get(FIELD_GEO_ACCURACY));
        request.setTimestamp(this.get(FIELD_GEO_TIMESTAMP));
        request.setLongitude(this.get(FIELD_GEO_LONGITUDE));
        request.setLatitude(this.get(FIELD_GEO_LATITUDE));
        return request;
    }

    private void putGeoLatitude(String s) {
        this.put(FIELD_GEO_LATITUDE, s);
    }

    private void putGeoLongitude(String s) {
        this.put(FIELD_GEO_LONGITUDE, s);
    }

    private void putGeoAccuracy(String s) {
        this.put(FIELD_GEO_ACCURACY, s);
    }

    private void putGeoTimestamp(String s) {
        this.put(FIELD_GEO_TIMESTAMP, s);
    }

    @Generated
    public String toString() {
        return "CasEvent(id=" + this.id + ", type=" + this.type + ", principalId=" + this.principalId + ", creationTime=" + this.creationTime + ", properties=" + this.properties + ")";
    }

    @Generated
    public long getId() {
        return this.id;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public String getPrincipalId() {
        return this.principalId;
    }

    @Generated
    public String getCreationTime() {
        return this.creationTime;
    }

    @Generated
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @JsonProperty
    @Generated
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty(value="type")
    @Generated
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(value="principalId")
    @Generated
    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    @JsonProperty(value="creationTime")
    @Generated
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @JsonProperty(value="properties")
    @Generated
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Generated
    public CasEvent(long id, String type, String principalId, String creationTime, Map<String, String> properties) {
        this.id = id;
        this.type = type;
        this.principalId = principalId;
        this.creationTime = creationTime;
        this.properties = properties;
    }
}

