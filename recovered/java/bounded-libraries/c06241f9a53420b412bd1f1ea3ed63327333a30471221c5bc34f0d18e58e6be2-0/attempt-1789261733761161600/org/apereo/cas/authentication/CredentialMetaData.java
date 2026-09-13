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
import org.apereo.cas.authentication.Credential;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface CredentialMetaData
extends Serializable {
    public String getId();

    public Class<? extends Credential> getCredentialClass();
}

