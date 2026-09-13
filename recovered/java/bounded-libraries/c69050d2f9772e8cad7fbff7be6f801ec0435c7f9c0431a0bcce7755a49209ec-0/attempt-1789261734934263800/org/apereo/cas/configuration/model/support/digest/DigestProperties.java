/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.digest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-digest-authentication")
@Deprecated(since="6.6")
public class DigestProperties
implements Serializable {
    private static final long serialVersionUID = -7920128284733546444L;
    private String realm = "CAS";
    private String authenticationMethod = "auth";
    private Map<String, String> users = new HashMap<String, String>(0);
    private String name;
    private Integer order;

    @Generated
    public String getRealm() {
        return this.realm;
    }

    @Generated
    public String getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Generated
    public Map<String, String> getUsers() {
        return this.users;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public DigestProperties setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    @Generated
    public DigestProperties setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
        return this;
    }

    @Generated
    public DigestProperties setUsers(Map<String, String> users) {
        this.users = users;
        return this;
    }

    @Generated
    public DigestProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public DigestProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }
}

