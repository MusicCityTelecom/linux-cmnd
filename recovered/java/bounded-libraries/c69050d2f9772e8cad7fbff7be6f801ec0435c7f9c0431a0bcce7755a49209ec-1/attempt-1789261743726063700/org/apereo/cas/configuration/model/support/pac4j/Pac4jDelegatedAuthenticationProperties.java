/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.flow.WebflowAutoConfigurationProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationBitBucketProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationCookieProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationCoreProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationDropboxProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationFacebookProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationFoursquareProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationGitHubProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationGoogleProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationHiOrgServerProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationLinkedInProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationPayPalProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationProfileSelectionProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationProvisioningProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationRestfulProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationTwitterProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationWindowsLiveProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationWordpressProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationYahooProperties;
import org.apereo.cas.configuration.model.support.pac4j.cas.Pac4jCasClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oauth.Pac4jOAuth20ClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.oidc.Pac4jOidcClientProperties;
import org.apereo.cas.configuration.model.support.pac4j.saml.Pac4jSamlClientProperties;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPDiscoveryProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jDelegatedAuthenticationProperties")
public class Pac4jDelegatedAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 4388567744591488495L;
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationCoreProperties core = new Pac4jDelegatedAuthenticationCoreProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationProvisioningProperties provisioning = new Pac4jDelegatedAuthenticationProvisioningProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationProfileSelectionProperties profileSelection = new Pac4jDelegatedAuthenticationProfileSelectionProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationFacebookProperties facebook = new Pac4jDelegatedAuthenticationFacebookProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationTwitterProperties twitter = new Pac4jDelegatedAuthenticationTwitterProperties();
    private List<Pac4jSamlClientProperties> saml = new ArrayList<Pac4jSamlClientProperties>(0);
    private List<Pac4jOidcClientProperties> oidc = new ArrayList<Pac4jOidcClientProperties>(0);
    private List<Pac4jOAuth20ClientProperties> oauth2 = new ArrayList<Pac4jOAuth20ClientProperties>(0);
    private List<Pac4jCasClientProperties> cas = new ArrayList<Pac4jCasClientProperties>(0);
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationLinkedInProperties linkedIn = new Pac4jDelegatedAuthenticationLinkedInProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationDropboxProperties dropbox = new Pac4jDelegatedAuthenticationDropboxProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationGitHubProperties github = new Pac4jDelegatedAuthenticationGitHubProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationGoogleProperties google = new Pac4jDelegatedAuthenticationGoogleProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationYahooProperties yahoo = new Pac4jDelegatedAuthenticationYahooProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationFoursquareProperties foursquare = new Pac4jDelegatedAuthenticationFoursquareProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationWindowsLiveProperties windowsLive = new Pac4jDelegatedAuthenticationWindowsLiveProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationPayPalProperties paypal = new Pac4jDelegatedAuthenticationPayPalProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationWordpressProperties wordpress = new Pac4jDelegatedAuthenticationWordpressProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationBitBucketProperties bitbucket = new Pac4jDelegatedAuthenticationBitBucketProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationHiOrgServerProperties hiOrgServer = new Pac4jDelegatedAuthenticationHiOrgServerProperties();
    @NestedConfigurationProperty
    private SamlIdPDiscoveryProperties samlDiscovery = new SamlIdPDiscoveryProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationRestfulProperties rest = new Pac4jDelegatedAuthenticationRestfulProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationCookieProperties cookie = new Pac4jDelegatedAuthenticationCookieProperties();
    @NestedConfigurationProperty
    private WebflowAutoConfigurationProperties webflow = new WebflowAutoConfigurationProperties();

    @Generated
    public Pac4jDelegatedAuthenticationCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProvisioningProperties getProvisioning() {
        return this.provisioning;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProfileSelectionProperties getProfileSelection() {
        return this.profileSelection;
    }

    @Generated
    public Pac4jDelegatedAuthenticationFacebookProperties getFacebook() {
        return this.facebook;
    }

    @Generated
    public Pac4jDelegatedAuthenticationTwitterProperties getTwitter() {
        return this.twitter;
    }

    @Generated
    public List<Pac4jSamlClientProperties> getSaml() {
        return this.saml;
    }

    @Generated
    public List<Pac4jOidcClientProperties> getOidc() {
        return this.oidc;
    }

    @Generated
    public List<Pac4jOAuth20ClientProperties> getOauth2() {
        return this.oauth2;
    }

    @Generated
    public List<Pac4jCasClientProperties> getCas() {
        return this.cas;
    }

    @Generated
    public Pac4jDelegatedAuthenticationLinkedInProperties getLinkedIn() {
        return this.linkedIn;
    }

    @Generated
    public Pac4jDelegatedAuthenticationDropboxProperties getDropbox() {
        return this.dropbox;
    }

    @Generated
    public Pac4jDelegatedAuthenticationGitHubProperties getGithub() {
        return this.github;
    }

    @Generated
    public Pac4jDelegatedAuthenticationGoogleProperties getGoogle() {
        return this.google;
    }

    @Generated
    public Pac4jDelegatedAuthenticationYahooProperties getYahoo() {
        return this.yahoo;
    }

    @Generated
    public Pac4jDelegatedAuthenticationFoursquareProperties getFoursquare() {
        return this.foursquare;
    }

    @Generated
    public Pac4jDelegatedAuthenticationWindowsLiveProperties getWindowsLive() {
        return this.windowsLive;
    }

    @Generated
    public Pac4jDelegatedAuthenticationPayPalProperties getPaypal() {
        return this.paypal;
    }

    @Generated
    public Pac4jDelegatedAuthenticationWordpressProperties getWordpress() {
        return this.wordpress;
    }

    @Generated
    public Pac4jDelegatedAuthenticationBitBucketProperties getBitbucket() {
        return this.bitbucket;
    }

    @Generated
    public Pac4jDelegatedAuthenticationHiOrgServerProperties getHiOrgServer() {
        return this.hiOrgServer;
    }

    @Generated
    public SamlIdPDiscoveryProperties getSamlDiscovery() {
        return this.samlDiscovery;
    }

    @Generated
    public Pac4jDelegatedAuthenticationRestfulProperties getRest() {
        return this.rest;
    }

    @Generated
    public Pac4jDelegatedAuthenticationCookieProperties getCookie() {
        return this.cookie;
    }

    @Generated
    public WebflowAutoConfigurationProperties getWebflow() {
        return this.webflow;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setCore(Pac4jDelegatedAuthenticationCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setProvisioning(Pac4jDelegatedAuthenticationProvisioningProperties provisioning) {
        this.provisioning = provisioning;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setProfileSelection(Pac4jDelegatedAuthenticationProfileSelectionProperties profileSelection) {
        this.profileSelection = profileSelection;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setFacebook(Pac4jDelegatedAuthenticationFacebookProperties facebook) {
        this.facebook = facebook;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setTwitter(Pac4jDelegatedAuthenticationTwitterProperties twitter) {
        this.twitter = twitter;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setSaml(List<Pac4jSamlClientProperties> saml) {
        this.saml = saml;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setOidc(List<Pac4jOidcClientProperties> oidc) {
        this.oidc = oidc;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setOauth2(List<Pac4jOAuth20ClientProperties> oauth2) {
        this.oauth2 = oauth2;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setCas(List<Pac4jCasClientProperties> cas) {
        this.cas = cas;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setLinkedIn(Pac4jDelegatedAuthenticationLinkedInProperties linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setDropbox(Pac4jDelegatedAuthenticationDropboxProperties dropbox) {
        this.dropbox = dropbox;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setGithub(Pac4jDelegatedAuthenticationGitHubProperties github) {
        this.github = github;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setGoogle(Pac4jDelegatedAuthenticationGoogleProperties google) {
        this.google = google;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setYahoo(Pac4jDelegatedAuthenticationYahooProperties yahoo) {
        this.yahoo = yahoo;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setFoursquare(Pac4jDelegatedAuthenticationFoursquareProperties foursquare) {
        this.foursquare = foursquare;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setWindowsLive(Pac4jDelegatedAuthenticationWindowsLiveProperties windowsLive) {
        this.windowsLive = windowsLive;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setPaypal(Pac4jDelegatedAuthenticationPayPalProperties paypal) {
        this.paypal = paypal;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setWordpress(Pac4jDelegatedAuthenticationWordpressProperties wordpress) {
        this.wordpress = wordpress;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setBitbucket(Pac4jDelegatedAuthenticationBitBucketProperties bitbucket) {
        this.bitbucket = bitbucket;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setHiOrgServer(Pac4jDelegatedAuthenticationHiOrgServerProperties hiOrgServer) {
        this.hiOrgServer = hiOrgServer;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setSamlDiscovery(SamlIdPDiscoveryProperties samlDiscovery) {
        this.samlDiscovery = samlDiscovery;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setRest(Pac4jDelegatedAuthenticationRestfulProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setCookie(Pac4jDelegatedAuthenticationCookieProperties cookie) {
        this.cookie = cookie;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProperties setWebflow(WebflowAutoConfigurationProperties webflow) {
        this.webflow = webflow;
        return this;
    }
}

