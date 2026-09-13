/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.git.services;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.File;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apereo.cas.configuration.model.support.git.services.BaseGitProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-git-service-registry")
@JsonFilter(value="GitServiceRegistryProperties")
public class GitServiceRegistryProperties
extends BaseGitProperties {
    public static final String DEFAULT_CAS_SERVICE_REGISTRY_NAME = "cas-service-registry";
    private static final long serialVersionUID = 4194689836396653458L;
    private String rootDirectory;
    private boolean groupByType = true;

    public GitServiceRegistryProperties() {
        FileSystemResource location = new FileSystemResource(new File(FileUtils.getTempDirectory(), DEFAULT_CAS_SERVICE_REGISTRY_NAME));
        this.getCloneDirectory().setLocation((Resource)location);
    }

    @Generated
    public String getRootDirectory() {
        return this.rootDirectory;
    }

    @Generated
    public boolean isGroupByType() {
        return this.groupByType;
    }

    @Generated
    public GitServiceRegistryProperties setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        return this;
    }

    @Generated
    public GitServiceRegistryProperties setGroupByType(boolean groupByType) {
        this.groupByType = groupByType;
        return this;
    }
}

