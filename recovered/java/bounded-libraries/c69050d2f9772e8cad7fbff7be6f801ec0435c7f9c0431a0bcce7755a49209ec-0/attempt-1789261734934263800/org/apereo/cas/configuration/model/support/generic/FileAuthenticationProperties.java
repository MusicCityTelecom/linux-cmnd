/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.generic;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-generic")
public class FileAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 4031366217090049241L;
    private transient Resource filename;
    private String separator = "::";
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private String name;

    @Generated
    public Resource getFilename() {
        return this.filename;
    }

    @Generated
    public String getSeparator() {
        return this.separator;
    }

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public FileAuthenticationProperties setFilename(Resource filename) {
        this.filename = filename;
        return this;
    }

    @Generated
    public FileAuthenticationProperties setSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    @Generated
    public FileAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public FileAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public FileAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }
}

