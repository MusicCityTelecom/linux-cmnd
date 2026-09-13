/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication.credential;

import lombok.Generated;
import org.apereo.cas.authentication.credential.AbstractCredential;

public class OneTimeTokenCredential
extends AbstractCredential {
    private static final long serialVersionUID = -7570600701132111037L;
    private String token;

    public String getId() {
        return this.token;
    }

    @Override
    @Generated
    public String toString() {
        return "OneTimeTokenCredential(token=" + this.token + ")";
    }

    @Generated
    public void setToken(String token) {
        this.token = token;
    }

    @Generated
    public String getToken() {
        return this.token;
    }

    @Generated
    public OneTimeTokenCredential() {
    }

    @Generated
    public OneTimeTokenCredential(String token) {
        this.token = token;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OneTimeTokenCredential)) {
            return false;
        }
        OneTimeTokenCredential other = (OneTimeTokenCredential)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$token = this.token;
        String other$token = other.token;
        return !(this$token == null ? other$token != null : !this$token.equals(other$token));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof OneTimeTokenCredential;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $token = this.token;
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        return result;
    }
}

