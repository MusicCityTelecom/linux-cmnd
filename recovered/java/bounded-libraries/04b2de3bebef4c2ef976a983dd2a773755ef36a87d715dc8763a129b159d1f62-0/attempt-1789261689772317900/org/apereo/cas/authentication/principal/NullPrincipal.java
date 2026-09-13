/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;

public class NullPrincipal
implements Principal {
    private static final long serialVersionUID = 2309300426720915104L;
    private static final String NOBODY = "nobody";
    private static NullPrincipal INSTANCE;

    public static NullPrincipal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NullPrincipal();
        }
        return INSTANCE;
    }

    @JsonIgnore
    public String getId() {
        return NOBODY;
    }

    @Generated
    public NullPrincipal() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NullPrincipal)) {
            return false;
        }
        NullPrincipal other = (NullPrincipal)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof NullPrincipal;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }

    @Generated
    public String toString() {
        return "NullPrincipal()";
    }
}

