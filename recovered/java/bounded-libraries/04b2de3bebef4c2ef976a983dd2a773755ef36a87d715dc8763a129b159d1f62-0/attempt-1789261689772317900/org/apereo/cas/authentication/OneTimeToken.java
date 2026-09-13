/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  javax.persistence.Column
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apache.commons.lang3.builder.CompareToBuilder
 *  org.springframework.data.annotation.Id
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Generated;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.data.annotation.Id;

@MappedSuperclass
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class OneTimeToken
implements Serializable,
Comparable<OneTimeToken> {
    private static final long serialVersionUID = -1329938047176583075L;
    @Id
    @JsonProperty(value="id")
    @Transient
    private long id;
    @Column(nullable=false)
    private Integer token;
    @Column(nullable=false)
    private String userId;
    @Column(nullable=false)
    private LocalDateTime issuedDateTime = LocalDateTime.now(ZoneId.systemDefault());

    public OneTimeToken() {
        this.setId(System.currentTimeMillis());
    }

    public OneTimeToken(Integer token, String userId) {
        this();
        this.token = token;
        this.userId = userId;
    }

    @Override
    public int compareTo(OneTimeToken o) {
        return new CompareToBuilder().append((Object)this.token, (Object)o.getToken()).append((Object)this.userId, (Object)o.getUserId()).append((Object)this.issuedDateTime, (Object)o.getIssuedDateTime()).append(this.id, o.id).build();
    }

    @Generated
    public String toString() {
        return "OneTimeToken(id=" + this.id + ", token=" + this.token + ", userId=" + this.userId + ", issuedDateTime=" + this.issuedDateTime + ")";
    }

    @Generated
    public long getId() {
        return this.id;
    }

    @Generated
    public Integer getToken() {
        return this.token;
    }

    @Generated
    public String getUserId() {
        return this.userId;
    }

    @Generated
    public LocalDateTime getIssuedDateTime() {
        return this.issuedDateTime;
    }

    @JsonProperty(value="id")
    @Generated
    public void setId(long id) {
        this.id = id;
    }

    @Generated
    public void setToken(Integer token) {
        this.token = token;
    }

    @Generated
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Generated
    public void setIssuedDateTime(LocalDateTime issuedDateTime) {
        this.issuedDateTime = issuedDateTime;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OneTimeToken)) {
            return false;
        }
        OneTimeToken other = (OneTimeToken)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        Integer this$token = this.token;
        Integer other$token = other.token;
        if (this$token == null ? other$token != null : !((Object)this$token).equals(other$token)) {
            return false;
        }
        String this$userId = this.userId;
        String other$userId = other.userId;
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) {
            return false;
        }
        LocalDateTime this$issuedDateTime = this.issuedDateTime;
        LocalDateTime other$issuedDateTime = other.issuedDateTime;
        return !(this$issuedDateTime == null ? other$issuedDateTime != null : !((Object)this$issuedDateTime).equals(other$issuedDateTime));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof OneTimeToken;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $id = this.id;
        result = result * 59 + (int)($id >>> 32 ^ $id);
        Integer $token = this.token;
        result = result * 59 + ($token == null ? 43 : ((Object)$token).hashCode());
        String $userId = this.userId;
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        LocalDateTime $issuedDateTime = this.issuedDateTime;
        result = result * 59 + ($issuedDateTime == null ? 43 : ((Object)$issuedDateTime).hashCode());
        return result;
    }
}

