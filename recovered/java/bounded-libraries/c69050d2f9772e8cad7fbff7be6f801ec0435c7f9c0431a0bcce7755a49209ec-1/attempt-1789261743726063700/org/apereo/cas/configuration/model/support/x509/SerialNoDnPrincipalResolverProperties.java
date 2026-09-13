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
@JsonFilter(value="SerialNoDnPrincipalResolverProperties")
public class SerialNoDnPrincipalResolverProperties
implements Serializable {
    private static final long serialVersionUID = 1259126639860604739L;
    private String serialNumberPrefix = "SERIALNUMBER=";
    private String valueDelimiter = ", ";

    @Generated
    public String getSerialNumberPrefix() {
        return this.serialNumberPrefix;
    }

    @Generated
    public String getValueDelimiter() {
        return this.valueDelimiter;
    }

    @Generated
    public SerialNoDnPrincipalResolverProperties setSerialNumberPrefix(String serialNumberPrefix) {
        this.serialNumberPrefix = serialNumberPrefix;
        return this;
    }

    @Generated
    public SerialNoDnPrincipalResolverProperties setValueDelimiter(String valueDelimiter) {
        this.valueDelimiter = valueDelimiter;
        return this;
    }
}

