/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateAuthenticationTicketGrantingTicketProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateCouchDbAuthenticationProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateGroovyAuthenticationProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateJdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateJsonAuthenticationProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateLdapAuthenticationProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateRestfulAuthenticationProperties;
import org.apereo.cas.configuration.model.support.surrogate.SurrogateSimpleAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-surrogate-webflow")
@JsonFilter(value="SurrogateAuthenticationProperties")
public class SurrogateAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -2088813217398883623L;
    private String separator = "+";
    @NestedConfigurationProperty
    private SurrogateCouchDbAuthenticationProperties couchDb = new SurrogateCouchDbAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateSimpleAuthenticationProperties simple = new SurrogateSimpleAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateJsonAuthenticationProperties json = new SurrogateJsonAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateGroovyAuthenticationProperties groovy = new SurrogateGroovyAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateLdapAuthenticationProperties ldap = new SurrogateLdapAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateJdbcAuthenticationProperties jdbc = new SurrogateJdbcAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateRestfulAuthenticationProperties rest = new SurrogateRestfulAuthenticationProperties();
    @NestedConfigurationProperty
    private SurrogateAuthenticationTicketGrantingTicketProperties tgt = new SurrogateAuthenticationTicketGrantingTicketProperties();
    @NestedConfigurationProperty
    private PersonDirectoryPrincipalResolverProperties principal = new PersonDirectoryPrincipalResolverProperties();
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();

    @Generated
    public String getSeparator() {
        return this.separator;
    }

    @Generated
    public SurrogateCouchDbAuthenticationProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public SurrogateSimpleAuthenticationProperties getSimple() {
        return this.simple;
    }

    @Generated
    public SurrogateJsonAuthenticationProperties getJson() {
        return this.json;
    }

    @Generated
    public SurrogateGroovyAuthenticationProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public SurrogateLdapAuthenticationProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public SurrogateJdbcAuthenticationProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public SurrogateRestfulAuthenticationProperties getRest() {
        return this.rest;
    }

    @Generated
    public SurrogateAuthenticationTicketGrantingTicketProperties getTgt() {
        return this.tgt;
    }

    @Generated
    public PersonDirectoryPrincipalResolverProperties getPrincipal() {
        return this.principal;
    }

    @Generated
    public EmailProperties getMail() {
        return this.mail;
    }

    @Generated
    public SmsProperties getSms() {
        return this.sms;
    }

    @Generated
    public SurrogateAuthenticationProperties setSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setCouchDb(SurrogateCouchDbAuthenticationProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setSimple(SurrogateSimpleAuthenticationProperties simple) {
        this.simple = simple;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setJson(SurrogateJsonAuthenticationProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setGroovy(SurrogateGroovyAuthenticationProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setLdap(SurrogateLdapAuthenticationProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setJdbc(SurrogateJdbcAuthenticationProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setRest(SurrogateRestfulAuthenticationProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setTgt(SurrogateAuthenticationTicketGrantingTicketProperties tgt) {
        this.tgt = tgt;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setPrincipal(PersonDirectoryPrincipalResolverProperties principal) {
        this.principal = principal;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public SurrogateAuthenticationProperties setSms(SmsProperties sms) {
        this.sms = sms;
        return this;
    }
}

