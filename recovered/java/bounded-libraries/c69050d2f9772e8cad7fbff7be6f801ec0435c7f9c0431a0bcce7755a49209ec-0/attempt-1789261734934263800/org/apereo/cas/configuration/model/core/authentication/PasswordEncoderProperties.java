/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="PasswordEncoderProperties")
public class PasswordEncoderProperties
implements Serializable {
    private static final long serialVersionUID = -2396781005262069816L;
    @RequiredProperty
    private String type = "NONE";
    @RequiredProperty
    private String encodingAlgorithm;
    private String characterEncoding = "UTF-8";
    private String secret;
    private int strength = 16;
    private int hashLength = 16;

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public String getEncodingAlgorithm() {
        return this.encodingAlgorithm;
    }

    @Generated
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Generated
    public String getSecret() {
        return this.secret;
    }

    @Generated
    public int getStrength() {
        return this.strength;
    }

    @Generated
    public int getHashLength() {
        return this.hashLength;
    }

    @Generated
    public PasswordEncoderProperties setType(String type) {
        this.type = type;
        return this;
    }

    @Generated
    public PasswordEncoderProperties setEncodingAlgorithm(String encodingAlgorithm) {
        this.encodingAlgorithm = encodingAlgorithm;
        return this;
    }

    @Generated
    public PasswordEncoderProperties setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
        return this;
    }

    @Generated
    public PasswordEncoderProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Generated
    public PasswordEncoderProperties setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    @Generated
    public PasswordEncoderProperties setHashLength(int hashLength) {
        this.hashLength = hashLength;
        return this;
    }

    public static enum PasswordEncoderTypes {
        ARGON2,
        NONE,
        DEFAULT,
        STANDARD,
        BCRYPT,
        SCRYPT,
        PBKDF2,
        GLIBC_CRYPT,
        SSHA;

    }
}

