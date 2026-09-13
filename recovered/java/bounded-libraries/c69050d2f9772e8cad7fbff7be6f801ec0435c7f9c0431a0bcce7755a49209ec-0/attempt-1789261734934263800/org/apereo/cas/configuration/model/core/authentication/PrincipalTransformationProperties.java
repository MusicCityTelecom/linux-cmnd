/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.GroovyPrincipalTransformationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="PrincipalTransformationProperties")
public class PrincipalTransformationProperties
implements Serializable {
    private static final long serialVersionUID = 1678602647607236322L;
    private String prefix;
    private String suffix;
    private String pattern;
    private String blockingPattern;
    @NestedConfigurationProperty
    private GroovyPrincipalTransformationProperties groovy = new GroovyPrincipalTransformationProperties();
    private CaseConversion caseConversion = CaseConversion.NONE;

    @Generated
    public String getPrefix() {
        return this.prefix;
    }

    @Generated
    public String getSuffix() {
        return this.suffix;
    }

    @Generated
    public String getPattern() {
        return this.pattern;
    }

    @Generated
    public String getBlockingPattern() {
        return this.blockingPattern;
    }

    @Generated
    public GroovyPrincipalTransformationProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public CaseConversion getCaseConversion() {
        return this.caseConversion;
    }

    @Generated
    public PrincipalTransformationProperties setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Generated
    public PrincipalTransformationProperties setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    @Generated
    public PrincipalTransformationProperties setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    @Generated
    public PrincipalTransformationProperties setBlockingPattern(String blockingPattern) {
        this.blockingPattern = blockingPattern;
        return this;
    }

    @Generated
    public PrincipalTransformationProperties setGroovy(GroovyPrincipalTransformationProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public PrincipalTransformationProperties setCaseConversion(CaseConversion caseConversion) {
        this.caseConversion = caseConversion;
        return this;
    }

    public static enum CaseConversion {
        NONE,
        LOWERCASE,
        UPPERCASE;

    }
}

