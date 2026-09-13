/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceServiceTicketExpirationPolicy
extends Serializable {
    public long getNumberOfUses();

    public String getTimeToLive();

    public static RegisteredServiceServiceTicketExpirationPolicy undefined() {
        return new RegisteredServiceServiceTicketExpirationPolicy(){
            private static final long serialVersionUID = -6204076273553630977L;

            @Override
            public long getNumberOfUses() {
                return Long.MIN_VALUE;
            }

            @Override
            public String getTimeToLive() {
                return null;
            }
        };
    }
}

