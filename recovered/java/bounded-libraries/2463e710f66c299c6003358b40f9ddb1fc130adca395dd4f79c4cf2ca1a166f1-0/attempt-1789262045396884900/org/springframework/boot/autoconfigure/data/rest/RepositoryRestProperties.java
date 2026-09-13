/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.data.rest.core.config.RepositoryRestConfiguration
 *  org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy$RepositoryDetectionStrategies
 *  org.springframework.http.MediaType
 */
package org.springframework.boot.autoconfigure.data.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.http.MediaType;

@ConfigurationProperties(prefix="spring.data.rest")
public class RepositoryRestProperties {
    private String basePath;
    private Integer defaultPageSize;
    private Integer maxPageSize;
    private String pageParamName;
    private String limitParamName;
    private String sortParamName;
    private RepositoryDetectionStrategy.RepositoryDetectionStrategies detectionStrategy = RepositoryDetectionStrategy.RepositoryDetectionStrategies.DEFAULT;
    private MediaType defaultMediaType;
    private Boolean returnBodyOnCreate;
    private Boolean returnBodyOnUpdate;
    private Boolean enableEnumTranslation;

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Integer getDefaultPageSize() {
        return this.defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public Integer getMaxPageSize() {
        return this.maxPageSize;
    }

    public void setMaxPageSize(Integer maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public String getPageParamName() {
        return this.pageParamName;
    }

    public void setPageParamName(String pageParamName) {
        this.pageParamName = pageParamName;
    }

    public String getLimitParamName() {
        return this.limitParamName;
    }

    public void setLimitParamName(String limitParamName) {
        this.limitParamName = limitParamName;
    }

    public String getSortParamName() {
        return this.sortParamName;
    }

    public void setSortParamName(String sortParamName) {
        this.sortParamName = sortParamName;
    }

    public RepositoryDetectionStrategy.RepositoryDetectionStrategies getDetectionStrategy() {
        return this.detectionStrategy;
    }

    public void setDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies detectionStrategy) {
        this.detectionStrategy = detectionStrategy;
    }

    public MediaType getDefaultMediaType() {
        return this.defaultMediaType;
    }

    public void setDefaultMediaType(MediaType defaultMediaType) {
        this.defaultMediaType = defaultMediaType;
    }

    public Boolean getReturnBodyOnCreate() {
        return this.returnBodyOnCreate;
    }

    public void setReturnBodyOnCreate(Boolean returnBodyOnCreate) {
        this.returnBodyOnCreate = returnBodyOnCreate;
    }

    public Boolean getReturnBodyOnUpdate() {
        return this.returnBodyOnUpdate;
    }

    public void setReturnBodyOnUpdate(Boolean returnBodyOnUpdate) {
        this.returnBodyOnUpdate = returnBodyOnUpdate;
    }

    public Boolean getEnableEnumTranslation() {
        return this.enableEnumTranslation;
    }

    public void setEnableEnumTranslation(Boolean enableEnumTranslation) {
        this.enableEnumTranslation = enableEnumTranslation;
    }

    public void applyTo(RepositoryRestConfiguration rest) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this::getBasePath).to(arg_0 -> ((RepositoryRestConfiguration)rest).setBasePath(arg_0));
        map.from(this::getDefaultPageSize).to(arg_0 -> ((RepositoryRestConfiguration)rest).setDefaultPageSize(arg_0));
        map.from(this::getMaxPageSize).to(arg_0 -> ((RepositoryRestConfiguration)rest).setMaxPageSize(arg_0));
        map.from(this::getPageParamName).to(arg_0 -> ((RepositoryRestConfiguration)rest).setPageParamName(arg_0));
        map.from(this::getLimitParamName).to(arg_0 -> ((RepositoryRestConfiguration)rest).setLimitParamName(arg_0));
        map.from(this::getSortParamName).to(arg_0 -> ((RepositoryRestConfiguration)rest).setSortParamName(arg_0));
        map.from(this::getDetectionStrategy).to(arg_0 -> ((RepositoryRestConfiguration)rest).setRepositoryDetectionStrategy(arg_0));
        map.from(this::getDefaultMediaType).to(arg_0 -> ((RepositoryRestConfiguration)rest).setDefaultMediaType(arg_0));
        map.from(this::getReturnBodyOnCreate).to(arg_0 -> ((RepositoryRestConfiguration)rest).setReturnBodyOnCreate(arg_0));
        map.from(this::getReturnBodyOnUpdate).to(arg_0 -> ((RepositoryRestConfiguration)rest).setReturnBodyOnUpdate(arg_0));
        map.from(this::getEnableEnumTranslation).to(arg_0 -> ((RepositoryRestConfiguration)rest).setEnableEnumTranslation(arg_0));
    }
}

