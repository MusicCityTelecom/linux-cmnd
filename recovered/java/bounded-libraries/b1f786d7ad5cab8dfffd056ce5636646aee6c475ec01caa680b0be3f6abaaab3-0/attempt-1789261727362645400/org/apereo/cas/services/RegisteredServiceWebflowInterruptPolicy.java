/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.util.model.TriStateBoolean;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceWebflowInterruptPolicy
extends Serializable {
    public boolean isEnabled();

    public TriStateBoolean getForceExecution();
}

