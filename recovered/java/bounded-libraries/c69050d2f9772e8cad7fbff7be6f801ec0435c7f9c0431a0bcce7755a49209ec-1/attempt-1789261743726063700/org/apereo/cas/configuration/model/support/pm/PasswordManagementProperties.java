/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.model.support.captcha.GoogleRecaptchaProperties;
import org.apereo.cas.configuration.model.support.pm.ForgotUsernamePasswordManagementProperties;
import org.apereo.cas.configuration.model.support.pm.GroovyPasswordManagementProperties;
import org.apereo.cas.configuration.model.support.pm.JdbcPasswordManagementProperties;
import org.apereo.cas.configuration.model.support.pm.JsonPasswordManagementProperties;
import org.apereo.cas.configuration.model.support.pm.LdapPasswordManagementProperties;
import org.apereo.cas.configuration.model.support.pm.PasswordHistoryProperties;
import org.apereo.cas.configuration.model.support.pm.PasswordManagementCoreProperties;
import org.apereo.cas.configuration.model.support.pm.ResetPasswordManagementProperties;
import org.apereo.cas.configuration.model.support.pm.RestfulPasswordManagementProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pm-webflow")
@JsonFilter(value="PasswordManagementProperties")
public class PasswordManagementProperties
implements Serializable {
    private static final long serialVersionUID = -260644582798411176L;
    @NestedConfigurationProperty
    private PasswordManagementCoreProperties core = new PasswordManagementCoreProperties();
    @NestedConfigurationProperty
    private GoogleRecaptchaProperties googleRecaptcha = new GoogleRecaptchaProperties();
    private List<LdapPasswordManagementProperties> ldap = new ArrayList<LdapPasswordManagementProperties>();
    @NestedConfigurationProperty
    private JdbcPasswordManagementProperties jdbc = new JdbcPasswordManagementProperties();
    @NestedConfigurationProperty
    private RestfulPasswordManagementProperties rest = new RestfulPasswordManagementProperties();
    @NestedConfigurationProperty
    private JsonPasswordManagementProperties json = new JsonPasswordManagementProperties();
    @NestedConfigurationProperty
    private ResetPasswordManagementProperties reset = new ResetPasswordManagementProperties();
    @NestedConfigurationProperty
    private ForgotUsernamePasswordManagementProperties forgotUsername = new ForgotUsernamePasswordManagementProperties();
    @NestedConfigurationProperty
    private PasswordHistoryProperties history = new PasswordHistoryProperties();
    @NestedConfigurationProperty
    private GroovyPasswordManagementProperties groovy = new GroovyPasswordManagementProperties();
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties webflow = new WebflowAutoConfigurationProperties().setOrder(200);

    @Generated
    public PasswordManagementCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public GoogleRecaptchaProperties getGoogleRecaptcha() {
        return this.googleRecaptcha;
    }

    @Generated
    public List<LdapPasswordManagementProperties> getLdap() {
        return this.ldap;
    }

    @Generated
    public JdbcPasswordManagementProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public RestfulPasswordManagementProperties getRest() {
        return this.rest;
    }

    @Generated
    public JsonPasswordManagementProperties getJson() {
        return this.json;
    }

    @Generated
    public ResetPasswordManagementProperties getReset() {
        return this.reset;
    }

    @Generated
    public ForgotUsernamePasswordManagementProperties getForgotUsername() {
        return this.forgotUsername;
    }

    @Generated
    public PasswordHistoryProperties getHistory() {
        return this.history;
    }

    @Generated
    public GroovyPasswordManagementProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public PasswordManagementProperties setCore(PasswordManagementCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public PasswordManagementProperties setGoogleRecaptcha(GoogleRecaptchaProperties googleRecaptcha) {
        this.googleRecaptcha = googleRecaptcha;
        return this;
    }

    @Generated
    public PasswordManagementProperties setLdap(List<LdapPasswordManagementProperties> ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public PasswordManagementProperties setJdbc(JdbcPasswordManagementProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public PasswordManagementProperties setRest(RestfulPasswordManagementProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public PasswordManagementProperties setJson(JsonPasswordManagementProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public PasswordManagementProperties setReset(ResetPasswordManagementProperties reset) {
        this.reset = reset;
        return this;
    }

    @Generated
    public PasswordManagementProperties setForgotUsername(ForgotUsernamePasswordManagementProperties forgotUsername) {
        this.forgotUsername = forgotUsername;
        return this;
    }

    @Generated
    public PasswordManagementProperties setHistory(PasswordHistoryProperties history) {
        this.history = history;
        return this;
    }

    @Generated
    public PasswordManagementProperties setGroovy(GroovyPasswordManagementProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public PasswordManagementProperties setWebflow(WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }

    @Generated
    public PasswordManagementProperties() {
    }
}

