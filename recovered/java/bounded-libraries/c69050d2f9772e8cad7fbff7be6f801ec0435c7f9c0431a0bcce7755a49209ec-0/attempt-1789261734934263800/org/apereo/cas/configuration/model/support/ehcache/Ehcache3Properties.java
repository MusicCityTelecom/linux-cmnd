/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.ehcache;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.ehcache.Ehcache3TerracottaProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ehcache3-ticket-registry")
@JsonFilter(value="Ehcache3Properties")
public class Ehcache3Properties
implements Serializable {
    private static final long serialVersionUID = 7772510035918976450L;
    @RequiredProperty
    private boolean enabled = true;
    private int maxElementsInMemory = 10000;
    private String perCacheSizeOnDisk = "20MB";
    private boolean eternal;
    private boolean enableStatistics = true;
    private boolean enableManagement = true;
    private String rootDirectory = "/tmp/cas/ehcache3";
    private boolean persistOnDisk = true;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();
    @NestedConfigurationProperty
    private Ehcache3TerracottaProperties terracotta = new Ehcache3TerracottaProperties();

    public Ehcache3Properties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public int getMaxElementsInMemory() {
        return this.maxElementsInMemory;
    }

    @Generated
    public String getPerCacheSizeOnDisk() {
        return this.perCacheSizeOnDisk;
    }

    @Generated
    public boolean isEternal() {
        return this.eternal;
    }

    @Generated
    public boolean isEnableStatistics() {
        return this.enableStatistics;
    }

    @Generated
    public boolean isEnableManagement() {
        return this.enableManagement;
    }

    @Generated
    public String getRootDirectory() {
        return this.rootDirectory;
    }

    @Generated
    public boolean isPersistOnDisk() {
        return this.persistOnDisk;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public Ehcache3TerracottaProperties getTerracotta() {
        return this.terracotta;
    }

    @Generated
    public Ehcache3Properties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public Ehcache3Properties setMaxElementsInMemory(int maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
        return this;
    }

    @Generated
    public Ehcache3Properties setPerCacheSizeOnDisk(String perCacheSizeOnDisk) {
        this.perCacheSizeOnDisk = perCacheSizeOnDisk;
        return this;
    }

    @Generated
    public Ehcache3Properties setEternal(boolean eternal) {
        this.eternal = eternal;
        return this;
    }

    @Generated
    public Ehcache3Properties setEnableStatistics(boolean enableStatistics) {
        this.enableStatistics = enableStatistics;
        return this;
    }

    @Generated
    public Ehcache3Properties setEnableManagement(boolean enableManagement) {
        this.enableManagement = enableManagement;
        return this;
    }

    @Generated
    public Ehcache3Properties setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        return this;
    }

    @Generated
    public Ehcache3Properties setPersistOnDisk(boolean persistOnDisk) {
        this.persistOnDisk = persistOnDisk;
        return this;
    }

    @Generated
    public Ehcache3Properties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }

    @Generated
    public Ehcache3Properties setTerracotta(Ehcache3TerracottaProperties terracotta) {
        this.terracotta = terracotta;
        return this;
    }
}

