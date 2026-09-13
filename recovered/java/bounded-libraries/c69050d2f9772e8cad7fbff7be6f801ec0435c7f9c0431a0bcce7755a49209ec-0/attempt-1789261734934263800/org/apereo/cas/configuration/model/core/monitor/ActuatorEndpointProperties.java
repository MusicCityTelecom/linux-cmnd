/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-reports")
@JsonFilter(value="ActuatorEndpointProperties")
public class ActuatorEndpointProperties
implements Serializable {
    private static final long serialVersionUID = -2463521198550485506L;
    private List<String> requiredRoles = new ArrayList<String>(0);
    private List<String> requiredAuthorities = new ArrayList<String>(0);
    private List<String> requiredIpAddresses = new ArrayList<String>(0);
    private List<EndpointAccessLevel> access = Stream.of(EndpointAccessLevel.DENY).collect(Collectors.toList());

    @Generated
    public List<String> getRequiredRoles() {
        return this.requiredRoles;
    }

    @Generated
    public List<String> getRequiredAuthorities() {
        return this.requiredAuthorities;
    }

    @Generated
    public List<String> getRequiredIpAddresses() {
        return this.requiredIpAddresses;
    }

    @Generated
    public List<EndpointAccessLevel> getAccess() {
        return this.access;
    }

    @Generated
    public ActuatorEndpointProperties setRequiredRoles(List<String> requiredRoles) {
        this.requiredRoles = requiredRoles;
        return this;
    }

    @Generated
    public ActuatorEndpointProperties setRequiredAuthorities(List<String> requiredAuthorities) {
        this.requiredAuthorities = requiredAuthorities;
        return this;
    }

    @Generated
    public ActuatorEndpointProperties setRequiredIpAddresses(List<String> requiredIpAddresses) {
        this.requiredIpAddresses = requiredIpAddresses;
        return this;
    }

    @Generated
    public ActuatorEndpointProperties setAccess(List<EndpointAccessLevel> access) {
        this.access = access;
        return this;
    }

    @Generated
    public String toString() {
        return "ActuatorEndpointProperties(requiredRoles=" + this.requiredRoles + ", requiredAuthorities=" + this.requiredAuthorities + ", requiredIpAddresses=" + this.requiredIpAddresses + ", access=" + this.access + ")";
    }

    public static enum EndpointAccessLevel {
        PERMIT,
        ANONYMOUS,
        DENY,
        AUTHENTICATED,
        ROLE,
        AUTHORITY,
        IP_ADDRESS;

    }
}

