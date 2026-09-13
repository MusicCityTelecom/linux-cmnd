/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 */
package org.apereo.cas.authentication.credential;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.credential.AbstractCredential;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class BasicIdentifiableCredential
extends AbstractCredential {
    private static final long serialVersionUID = -700605020472810939L;
    @JsonProperty(value="id")
    private String id;

    @Override
    @Generated
    public String toString() {
        return "BasicIdentifiableCredential(id=" + this.id + ")";
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @JsonProperty(value="id")
    @Generated
    public void setId(String id) {
        this.id = id;
    }

    @Generated
    public BasicIdentifiableCredential(String id) {
        this.id = id;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BasicIdentifiableCredential)) {
            return false;
        }
        BasicIdentifiableCredential other = (BasicIdentifiableCredential)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BasicIdentifiableCredential;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    @Generated
    public BasicIdentifiableCredential() {
    }
}

