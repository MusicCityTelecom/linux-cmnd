/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.security;

import java.security.Principal;
import java.util.Base64;

public class SpnegoUserPrincipal
implements Principal {
    private final String _name;
    private byte[] _token;
    private String _encodedToken;

    public SpnegoUserPrincipal(String name, String encodedToken) {
        this._name = name;
        this._encodedToken = encodedToken;
    }

    public SpnegoUserPrincipal(String name, byte[] token) {
        this._name = name;
        this._token = token;
    }

    @Override
    public String getName() {
        return this._name;
    }

    public byte[] getToken() {
        if (this._token == null) {
            this._token = Base64.getDecoder().decode(this._encodedToken);
        }
        return this._token;
    }

    public String getEncodedToken() {
        if (this._encodedToken == null) {
            this._encodedToken = new String(Base64.getEncoder().encode(this._token));
        }
        return this._encodedToken;
    }
}

