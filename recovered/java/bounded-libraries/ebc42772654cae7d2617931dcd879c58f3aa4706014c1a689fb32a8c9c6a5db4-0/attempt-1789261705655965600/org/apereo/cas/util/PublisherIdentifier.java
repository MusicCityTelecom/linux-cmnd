/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 */
package org.apereo.cas.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.UUID;
import lombok.Generated;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class PublisherIdentifier
implements Serializable {
    private static final long serialVersionUID = -2216572507148074902L;
    private String id = UUID.randomUUID().toString();

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public void setId(String id) {
        this.id = id;
    }

    @Generated
    public PublisherIdentifier(String id) {
        this.id = id;
    }

    @Generated
    public PublisherIdentifier() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PublisherIdentifier)) {
            return false;
        }
        PublisherIdentifier other = (PublisherIdentifier)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PublisherIdentifier;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "PublisherIdentifier(id=" + this.id + ")";
    }
}

