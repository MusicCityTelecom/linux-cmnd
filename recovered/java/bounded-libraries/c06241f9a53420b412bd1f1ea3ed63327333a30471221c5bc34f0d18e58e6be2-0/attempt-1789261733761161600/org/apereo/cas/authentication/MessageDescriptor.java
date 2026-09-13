/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface MessageDescriptor
extends Serializable {
    public String getCode();

    public String getDefaultMessage();

    public Serializable[] getParams();
}

