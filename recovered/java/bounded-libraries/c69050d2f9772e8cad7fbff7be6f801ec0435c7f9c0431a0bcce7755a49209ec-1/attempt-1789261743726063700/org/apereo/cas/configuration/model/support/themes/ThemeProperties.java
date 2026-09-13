/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.themes;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-themes", automated=true)
@JsonFilter(value="ThemeProperties")
public class ThemeProperties
implements Serializable {
    private static final long serialVersionUID = 2248773823196496599L;
    private String defaultThemeName = "cas-theme-default";
    private String paramName = "theme";

    @Generated
    public String getDefaultThemeName() {
        return this.defaultThemeName;
    }

    @Generated
    public String getParamName() {
        return this.paramName;
    }

    @Generated
    public ThemeProperties setDefaultThemeName(String defaultThemeName) {
        this.defaultThemeName = defaultThemeName;
        return this;
    }

    @Generated
    public ThemeProperties setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }
}

