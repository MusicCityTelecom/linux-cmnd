/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.x509;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-x509-webflow")
@JsonFilter(value="SubjectDnPrincipalResolverProperties")
public class SubjectDnPrincipalResolverProperties
implements Serializable {
    private static final long serialVersionUID = -1833042842488884318L;
    private SubjectDnFormat format = SubjectDnFormat.DEFAULT;

    @Generated
    public SubjectDnFormat getFormat() {
        return this.format;
    }

    @Generated
    public SubjectDnPrincipalResolverProperties setFormat(SubjectDnFormat format) {
        this.format = format;
        return this;
    }

    public static enum SubjectDnFormat {
        DEFAULT,
        RFC1779,
        RFC2253,
        CANONICAL;

    }
}

