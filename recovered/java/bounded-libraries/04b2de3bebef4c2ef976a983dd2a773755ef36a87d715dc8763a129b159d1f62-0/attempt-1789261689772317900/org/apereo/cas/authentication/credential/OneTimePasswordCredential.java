/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 */
package org.apereo.cas.authentication.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;
import org.apereo.cas.authentication.credential.BasicIdentifiableCredential;

public class OneTimePasswordCredential
extends BasicIdentifiableCredential {
    private static final long serialVersionUID = 1892587671827699709L;
    private String password;

    @JsonCreator
    public OneTimePasswordCredential(@JsonProperty(value="id") String id, @JsonProperty(value="password") String password) {
        super(id);
        this.password = password;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OneTimePasswordCredential)) {
            return false;
        }
        OneTimePasswordCredential other = (OneTimePasswordCredential)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$password = this.password;
        String other$password = other.password;
        return !(this$password == null ? other$password != null : !this$password.equals(other$password));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof OneTimePasswordCredential;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $password = this.password;
        result = result * 59 + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "OneTimePasswordCredential(super=" + super.toString() + ")";
    }
}

