/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Optional;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface PersistentIdGenerator
extends Serializable {
    public String generate(String var1, String var2);

    default public String generate(Principal principal, Service service) {
        return this.generate(principal, (String)Optional.ofNullable(service).map(Principal::getId).orElse(null));
    }

    public String generate(Principal var1, String var2);

    default public String generate(String principal, Service service) {
        return this.generate(principal, service.getId());
    }

    default public String generate(Principal principal) {
        return this.generate(principal, "");
    }
}

