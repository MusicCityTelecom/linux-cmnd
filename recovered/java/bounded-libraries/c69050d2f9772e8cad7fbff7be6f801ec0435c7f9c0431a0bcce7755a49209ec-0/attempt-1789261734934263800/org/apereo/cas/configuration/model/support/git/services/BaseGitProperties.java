/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.git.services;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-git-core")
public abstract class BaseGitProperties
implements Serializable {
    private static final long serialVersionUID = 4194689836396653458L;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String repositoryUrl;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String activeBranch = "master";
    @RequiredProperty
    private String branchesToClone = "*";
    private String username;
    private String password;
    private boolean pushChanges;
    private boolean signCommits;
    private boolean rebase;
    private String privateKeyPassphrase;
    @NestedConfigurationProperty
    private SpringResourceProperties privateKey = new SpringResourceProperties();
    private String sshSessionPassword;
    private boolean strictHostKeyChecking = true;
    private boolean clearExistingIdentities;
    @DurationCapable
    private String timeout = "PT10S";
    @NestedConfigurationProperty
    @RequiredProperty
    private SpringResourceProperties cloneDirectory = new SpringResourceProperties();
    private HttpClientTypes httpClientType = HttpClientTypes.JDK;

    @Generated
    public String getRepositoryUrl() {
        return this.repositoryUrl;
    }

    @Generated
    public String getActiveBranch() {
        return this.activeBranch;
    }

    @Generated
    public String getBranchesToClone() {
        return this.branchesToClone;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public boolean isPushChanges() {
        return this.pushChanges;
    }

    @Generated
    public boolean isSignCommits() {
        return this.signCommits;
    }

    @Generated
    public boolean isRebase() {
        return this.rebase;
    }

    @Generated
    public String getPrivateKeyPassphrase() {
        return this.privateKeyPassphrase;
    }

    @Generated
    public SpringResourceProperties getPrivateKey() {
        return this.privateKey;
    }

    @Generated
    public String getSshSessionPassword() {
        return this.sshSessionPassword;
    }

    @Generated
    public boolean isStrictHostKeyChecking() {
        return this.strictHostKeyChecking;
    }

    @Generated
    public boolean isClearExistingIdentities() {
        return this.clearExistingIdentities;
    }

    @Generated
    public String getTimeout() {
        return this.timeout;
    }

    @Generated
    public SpringResourceProperties getCloneDirectory() {
        return this.cloneDirectory;
    }

    @Generated
    public HttpClientTypes getHttpClientType() {
        return this.httpClientType;
    }

    @Generated
    public BaseGitProperties setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
        return this;
    }

    @Generated
    public BaseGitProperties setActiveBranch(String activeBranch) {
        this.activeBranch = activeBranch;
        return this;
    }

    @Generated
    public BaseGitProperties setBranchesToClone(String branchesToClone) {
        this.branchesToClone = branchesToClone;
        return this;
    }

    @Generated
    public BaseGitProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    @Generated
    public BaseGitProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public BaseGitProperties setPushChanges(boolean pushChanges) {
        this.pushChanges = pushChanges;
        return this;
    }

    @Generated
    public BaseGitProperties setSignCommits(boolean signCommits) {
        this.signCommits = signCommits;
        return this;
    }

    @Generated
    public BaseGitProperties setRebase(boolean rebase) {
        this.rebase = rebase;
        return this;
    }

    @Generated
    public BaseGitProperties setPrivateKeyPassphrase(String privateKeyPassphrase) {
        this.privateKeyPassphrase = privateKeyPassphrase;
        return this;
    }

    @Generated
    public BaseGitProperties setPrivateKey(SpringResourceProperties privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    @Generated
    public BaseGitProperties setSshSessionPassword(String sshSessionPassword) {
        this.sshSessionPassword = sshSessionPassword;
        return this;
    }

    @Generated
    public BaseGitProperties setStrictHostKeyChecking(boolean strictHostKeyChecking) {
        this.strictHostKeyChecking = strictHostKeyChecking;
        return this;
    }

    @Generated
    public BaseGitProperties setClearExistingIdentities(boolean clearExistingIdentities) {
        this.clearExistingIdentities = clearExistingIdentities;
        return this;
    }

    @Generated
    public BaseGitProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public BaseGitProperties setCloneDirectory(SpringResourceProperties cloneDirectory) {
        this.cloneDirectory = cloneDirectory;
        return this;
    }

    @Generated
    public BaseGitProperties setHttpClientType(HttpClientTypes httpClientType) {
        this.httpClientType = httpClientType;
        return this;
    }

    public static enum HttpClientTypes {
        JDK,
        HTTP_CLIENT;

    }
}

