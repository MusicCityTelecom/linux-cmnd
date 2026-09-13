/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  javax.persistence.DiscriminatorValue
 *  javax.persistence.Entity
 *  lombok.Generated
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Generated;
import org.apereo.cas.authentication.principal.AbstractWebApplicationService;

@Entity
@DiscriminatorValue(value="simple")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SimpleWebApplicationServiceImpl
extends AbstractWebApplicationService {
    private static final long serialVersionUID = 8334068957483758042L;

    @JsonCreator
    protected SimpleWebApplicationServiceImpl(@JsonProperty(value="id") String id, @JsonProperty(value="originalUrl") String originalUrl, @JsonProperty(value="artifactId") String artifactId) {
        super(id, originalUrl, artifactId);
    }

    @Generated
    public SimpleWebApplicationServiceImpl() {
    }
}

