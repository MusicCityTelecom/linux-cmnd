/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIdentityInfo
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.annotation.ObjectIdGenerators$IntSequenceGenerator
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.ExpirationPolicy
 *  org.apereo.cas.ticket.TransientSessionTicket
 */
package org.apereo.cas.ticket;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicket;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.TransientSessionTicket;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class TransientSessionTicketImpl
extends AbstractTicket
implements TransientSessionTicket {
    private static final long serialVersionUID = 7839186396717950243L;
    private Service service;
    private Map<String, Object> properties = new HashMap<String, Object>(0);

    public TransientSessionTicketImpl(String id, ExpirationPolicy expirationPolicy, Service service, Map<String, Serializable> properties) {
        super(id, expirationPolicy);
        this.service = service;
        this.properties = new HashMap<String, Serializable>(properties);
    }

    public <T> T getProperty(String key, Class<T> clazz) {
        return clazz.cast(this.properties.get(key));
    }

    public String getPrefix() {
        return "TST";
    }

    public void put(String name, Serializable value) {
        this.properties.put(name, value);
    }

    public void putAll(Map<String, Serializable> props) {
        this.properties.putAll(props);
    }

    public boolean contains(String name) {
        return this.properties.containsKey(name);
    }

    public <T extends Serializable> T get(String name, Class<T> clazz) {
        if (this.contains(name)) {
            return (T)((Serializable)clazz.cast(this.properties.get(name)));
        }
        return null;
    }

    public <T extends Serializable> T get(String name, Class<T> clazz, T defaultValue) {
        if (this.contains(name)) {
            return (T)((Serializable)clazz.cast(this.properties.getOrDefault(name, defaultValue)));
        }
        return defaultValue;
    }

    @Override
    @Generated
    public String toString() {
        return "TransientSessionTicketImpl(super=" + super.toString() + ", service=" + this.service + ", properties=" + this.properties + ")";
    }

    @Generated
    public Service getService() {
        return this.service;
    }

    @Generated
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @Generated
    public TransientSessionTicketImpl() {
    }

    @Generated
    public TransientSessionTicketImpl(Service service, Map<String, Object> properties) {
        this.service = service;
        this.properties = properties;
    }
}

