/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.view;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.view.Cas10ViewProperties;
import org.apereo.cas.configuration.model.core.web.view.Cas20ViewProperties;
import org.apereo.cas.configuration.model.core.web.view.Cas30ViewProperties;
import org.apereo.cas.configuration.model.core.web.view.CustomLoginFieldViewProperties;
import org.apereo.cas.configuration.model.core.web.view.RestfulViewProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="ViewProperties")
public class ViewProperties
implements Serializable {
    private static final long serialVersionUID = 2719748442042197738L;
    private boolean authorizedServicesOnSuccessfulLogin;
    private String defaultRedirectUrl;
    private Map<String, CustomLoginFieldViewProperties> customLoginFormFields = new LinkedHashMap<String, CustomLoginFieldViewProperties>(0);
    private List<String> templatePrefixes = new ArrayList<String>(1);
    private ThemeSourceTypes themeSourceType = ThemeSourceTypes.DEFAULT;
    @NestedConfigurationProperty
    private Cas10ViewProperties cas1 = new Cas10ViewProperties();
    @NestedConfigurationProperty
    private Cas20ViewProperties cas2 = new Cas20ViewProperties();
    @NestedConfigurationProperty
    private Cas30ViewProperties cas3 = new Cas30ViewProperties();
    @NestedConfigurationProperty
    private RestfulViewProperties rest = new RestfulViewProperties();

    @Generated
    public boolean isAuthorizedServicesOnSuccessfulLogin() {
        return this.authorizedServicesOnSuccessfulLogin;
    }

    @Generated
    public String getDefaultRedirectUrl() {
        return this.defaultRedirectUrl;
    }

    @Generated
    public Map<String, CustomLoginFieldViewProperties> getCustomLoginFormFields() {
        return this.customLoginFormFields;
    }

    @Generated
    public List<String> getTemplatePrefixes() {
        return this.templatePrefixes;
    }

    @Generated
    public ThemeSourceTypes getThemeSourceType() {
        return this.themeSourceType;
    }

    @Generated
    public Cas10ViewProperties getCas1() {
        return this.cas1;
    }

    @Generated
    public Cas20ViewProperties getCas2() {
        return this.cas2;
    }

    @Generated
    public Cas30ViewProperties getCas3() {
        return this.cas3;
    }

    @Generated
    public RestfulViewProperties getRest() {
        return this.rest;
    }

    @Generated
    public ViewProperties setAuthorizedServicesOnSuccessfulLogin(boolean authorizedServicesOnSuccessfulLogin) {
        this.authorizedServicesOnSuccessfulLogin = authorizedServicesOnSuccessfulLogin;
        return this;
    }

    @Generated
    public ViewProperties setDefaultRedirectUrl(String defaultRedirectUrl) {
        this.defaultRedirectUrl = defaultRedirectUrl;
        return this;
    }

    @Generated
    public ViewProperties setCustomLoginFormFields(Map<String, CustomLoginFieldViewProperties> customLoginFormFields) {
        this.customLoginFormFields = customLoginFormFields;
        return this;
    }

    @Generated
    public ViewProperties setTemplatePrefixes(List<String> templatePrefixes) {
        this.templatePrefixes = templatePrefixes;
        return this;
    }

    @Generated
    public ViewProperties setThemeSourceType(ThemeSourceTypes themeSourceType) {
        this.themeSourceType = themeSourceType;
        return this;
    }

    @Generated
    public ViewProperties setCas1(Cas10ViewProperties cas1) {
        this.cas1 = cas1;
        return this;
    }

    @Generated
    public ViewProperties setCas2(Cas20ViewProperties cas2) {
        this.cas2 = cas2;
        return this;
    }

    @Generated
    public ViewProperties setCas3(Cas30ViewProperties cas3) {
        this.cas3 = cas3;
        return this;
    }

    @Generated
    public ViewProperties setRest(RestfulViewProperties rest) {
        this.rest = rest;
        return this;
    }

    public static enum ThemeSourceTypes {
        DEFAULT,
        AGGREGATE;

    }
}

