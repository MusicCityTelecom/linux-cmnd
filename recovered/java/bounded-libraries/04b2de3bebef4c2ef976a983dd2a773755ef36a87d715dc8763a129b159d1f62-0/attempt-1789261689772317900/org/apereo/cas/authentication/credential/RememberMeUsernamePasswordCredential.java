/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.RememberMeCredential
 */
package org.apereo.cas.authentication.credential;

import lombok.Generated;
import org.apereo.cas.authentication.RememberMeCredential;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;

public class RememberMeUsernamePasswordCredential
extends UsernamePasswordCredential
implements RememberMeCredential {
    private static final long serialVersionUID = -6710007659431302397L;
    private boolean rememberMe;

    @Generated
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Generated
    public boolean isRememberMe() {
        return this.rememberMe;
    }

    @Generated
    public RememberMeUsernamePasswordCredential() {
    }

    @Generated
    public RememberMeUsernamePasswordCredential(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RememberMeUsernamePasswordCredential)) {
            return false;
        }
        RememberMeUsernamePasswordCredential other = (RememberMeUsernamePasswordCredential)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return this.rememberMe == other.rememberMe;
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RememberMeUsernamePasswordCredential;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        result = result * 59 + (this.rememberMe ? 79 : 97);
        return result;
    }

    @Override
    @Generated
    public String toString() {
        return "RememberMeUsernamePasswordCredential(super=" + super.toString() + ", rememberMe=" + this.rememberMe + ")";
    }
}

