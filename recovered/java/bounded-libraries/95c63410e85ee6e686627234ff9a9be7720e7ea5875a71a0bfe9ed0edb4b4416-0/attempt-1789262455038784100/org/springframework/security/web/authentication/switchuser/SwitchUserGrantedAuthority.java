/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.switchuser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public final class SwitchUserGrantedAuthority
implements GrantedAuthority {
    private static final long serialVersionUID = 570L;
    private final String role;
    private final Authentication source;

    public SwitchUserGrantedAuthority(String role, Authentication source) {
        Assert.notNull((Object)role, (String)"role cannot be null");
        Assert.notNull((Object)source, (String)"source cannot be null");
        this.role = role;
        this.source = source;
    }

    public Authentication getSource() {
        return this.source;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SwitchUserGrantedAuthority) {
            SwitchUserGrantedAuthority swa = (SwitchUserGrantedAuthority)obj;
            return this.role.equals(swa.role) && this.source.equals(swa.source);
        }
        return false;
    }

    public int hashCode() {
        int result = this.role.hashCode();
        result = 31 * result + this.source.hashCode();
        return result;
    }

    public String toString() {
        return "Switch User Authority [" + this.role + "," + this.source + "]";
    }
}

