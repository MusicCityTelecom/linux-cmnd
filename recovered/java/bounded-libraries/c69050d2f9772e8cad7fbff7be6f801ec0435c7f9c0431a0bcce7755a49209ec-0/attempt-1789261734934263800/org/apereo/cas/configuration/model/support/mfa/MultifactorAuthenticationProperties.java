/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.mfa.AccepttoMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.AuthyMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.DuoSecurityMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.InweboMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationCoreProperties;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationTriggersProperties;
import org.apereo.cas.configuration.model.support.mfa.RadiusMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.SwivelMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.gauth.GoogleAuthenticatorMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.simple.CasSimpleMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.TrustedDevicesMultifactorProperties;
import org.apereo.cas.configuration.model.support.mfa.u2f.U2FMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.webauthn.WebAuthnMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.model.support.mfa.yubikey.YubiKeyMultifactorAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="MultifactorAuthenticationProperties")
public class MultifactorAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 7416521468929733907L;
    @NestedConfigurationProperty
    private MultifactorAuthenticationCoreProperties core = new MultifactorAuthenticationCoreProperties();
    @NestedConfigurationProperty
    private MultifactorAuthenticationTriggersProperties triggers = new MultifactorAuthenticationTriggersProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovyScript = new SpringResourceProperties();
    @NestedConfigurationProperty
    private U2FMultifactorAuthenticationProperties u2f = new U2FMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private TrustedDevicesMultifactorProperties trusted = new TrustedDevicesMultifactorProperties();
    @NestedConfigurationProperty
    private YubiKeyMultifactorAuthenticationProperties yubikey = new YubiKeyMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private WebAuthnMultifactorAuthenticationProperties webAuthn = new WebAuthnMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private RadiusMultifactorAuthenticationProperties radius = new RadiusMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private GoogleAuthenticatorMultifactorProperties gauth = new GoogleAuthenticatorMultifactorProperties();
    @NestedConfigurationProperty
    private CasSimpleMultifactorAuthenticationProperties simple = new CasSimpleMultifactorAuthenticationProperties();
    private List<DuoSecurityMultifactorAuthenticationProperties> duo = new ArrayList<DuoSecurityMultifactorAuthenticationProperties>(0);
    @NestedConfigurationProperty
    private AuthyMultifactorAuthenticationProperties authy = new AuthyMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private SwivelMultifactorAuthenticationProperties swivel = new SwivelMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private AccepttoMultifactorAuthenticationProperties acceptto = new AccepttoMultifactorAuthenticationProperties();
    @NestedConfigurationProperty
    private InweboMultifactorAuthenticationProperties inwebo = new InweboMultifactorAuthenticationProperties();

    @Generated
    public MultifactorAuthenticationCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public MultifactorAuthenticationTriggersProperties getTriggers() {
        return this.triggers;
    }

    @Generated
    public SpringResourceProperties getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public U2FMultifactorAuthenticationProperties getU2f() {
        return this.u2f;
    }

    @Generated
    public TrustedDevicesMultifactorProperties getTrusted() {
        return this.trusted;
    }

    @Generated
    public YubiKeyMultifactorAuthenticationProperties getYubikey() {
        return this.yubikey;
    }

    @Generated
    public WebAuthnMultifactorAuthenticationProperties getWebAuthn() {
        return this.webAuthn;
    }

    @Generated
    public RadiusMultifactorAuthenticationProperties getRadius() {
        return this.radius;
    }

    @Generated
    public GoogleAuthenticatorMultifactorProperties getGauth() {
        return this.gauth;
    }

    @Generated
    public CasSimpleMultifactorAuthenticationProperties getSimple() {
        return this.simple;
    }

    @Generated
    public List<DuoSecurityMultifactorAuthenticationProperties> getDuo() {
        return this.duo;
    }

    @Generated
    public AuthyMultifactorAuthenticationProperties getAuthy() {
        return this.authy;
    }

    @Generated
    public SwivelMultifactorAuthenticationProperties getSwivel() {
        return this.swivel;
    }

    @Generated
    public AccepttoMultifactorAuthenticationProperties getAcceptto() {
        return this.acceptto;
    }

    @Generated
    public InweboMultifactorAuthenticationProperties getInwebo() {
        return this.inwebo;
    }

    @Generated
    public MultifactorAuthenticationProperties setCore(MultifactorAuthenticationCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setTriggers(MultifactorAuthenticationTriggersProperties triggers) {
        this.triggers = triggers;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setGroovyScript(SpringResourceProperties groovyScript) {
        this.groovyScript = groovyScript;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setU2f(U2FMultifactorAuthenticationProperties u2f) {
        this.u2f = u2f;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setTrusted(TrustedDevicesMultifactorProperties trusted) {
        this.trusted = trusted;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setYubikey(YubiKeyMultifactorAuthenticationProperties yubikey) {
        this.yubikey = yubikey;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setWebAuthn(WebAuthnMultifactorAuthenticationProperties webAuthn) {
        this.webAuthn = webAuthn;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setRadius(RadiusMultifactorAuthenticationProperties radius) {
        this.radius = radius;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setGauth(GoogleAuthenticatorMultifactorProperties gauth) {
        this.gauth = gauth;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setSimple(CasSimpleMultifactorAuthenticationProperties simple) {
        this.simple = simple;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setDuo(List<DuoSecurityMultifactorAuthenticationProperties> duo) {
        this.duo = duo;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setAuthy(AuthyMultifactorAuthenticationProperties authy) {
        this.authy = authy;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setSwivel(SwivelMultifactorAuthenticationProperties swivel) {
        this.swivel = swivel;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setAcceptto(AccepttoMultifactorAuthenticationProperties acceptto) {
        this.acceptto = acceptto;
        return this;
    }

    @Generated
    public MultifactorAuthenticationProperties setInwebo(InweboMultifactorAuthenticationProperties inwebo) {
        this.inwebo = inwebo;
        return this;
    }
}

