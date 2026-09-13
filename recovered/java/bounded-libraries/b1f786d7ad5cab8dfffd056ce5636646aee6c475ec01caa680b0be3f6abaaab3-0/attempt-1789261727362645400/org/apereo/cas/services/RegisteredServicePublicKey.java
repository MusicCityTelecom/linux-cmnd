/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.security.PublicKey;
import javax.crypto.Cipher;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServicePublicKey
extends Serializable {
    public String getLocation();

    public String getAlgorithm();

    @JsonIgnore
    public PublicKey createInstance();

    @JsonIgnore
    public Cipher toCipher();
}

