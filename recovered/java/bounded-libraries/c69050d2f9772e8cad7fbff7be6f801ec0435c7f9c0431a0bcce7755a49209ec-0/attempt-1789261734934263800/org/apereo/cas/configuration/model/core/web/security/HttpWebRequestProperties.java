/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.security;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="HttpWebRequestProperties")
public class HttpWebRequestProperties
implements Serializable {
    private static final long serialVersionUID = -4711604991237695091L;
    private String encoding = StandardCharsets.UTF_8.name();
    private boolean forceEncoding = true;

    @Generated
    public String getEncoding() {
        return this.encoding;
    }

    @Generated
    public boolean isForceEncoding() {
        return this.forceEncoding;
    }

    @Generated
    public HttpWebRequestProperties setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    @Generated
    public HttpWebRequestProperties setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
        return this;
    }
}

