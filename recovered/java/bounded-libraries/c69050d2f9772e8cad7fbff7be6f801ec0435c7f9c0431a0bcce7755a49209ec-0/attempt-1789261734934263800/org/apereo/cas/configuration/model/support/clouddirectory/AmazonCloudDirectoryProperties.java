/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.clouddirectory;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-cloud-directory-authentication")
@JsonFilter(value="AmazonCloudDirectoryProperties")
public class AmazonCloudDirectoryProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = 6725526133973304269L;
    @RequiredProperty
    private String directoryArn;
    @RequiredProperty
    private String schemaArn;
    private String facetName;
    @RequiredProperty
    private String usernameAttributeName;
    @RequiredProperty
    private String passwordAttributeName;
    private String usernameIndexPath;
    private String name;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();
    private int order = Integer.MAX_VALUE;

    @Generated
    public String getDirectoryArn() {
        return this.directoryArn;
    }

    @Generated
    public String getSchemaArn() {
        return this.schemaArn;
    }

    @Generated
    public String getFacetName() {
        return this.facetName;
    }

    @Generated
    public String getUsernameAttributeName() {
        return this.usernameAttributeName;
    }

    @Generated
    public String getPasswordAttributeName() {
        return this.passwordAttributeName;
    }

    @Generated
    public String getUsernameIndexPath() {
        return this.usernameIndexPath;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public AmazonCloudDirectoryProperties setDirectoryArn(String directoryArn) {
        this.directoryArn = directoryArn;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setSchemaArn(String schemaArn) {
        this.schemaArn = schemaArn;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setFacetName(String facetName) {
        this.facetName = facetName;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setUsernameAttributeName(String usernameAttributeName) {
        this.usernameAttributeName = usernameAttributeName;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setPasswordAttributeName(String passwordAttributeName) {
        this.passwordAttributeName = passwordAttributeName;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setUsernameIndexPath(String usernameIndexPath) {
        this.usernameIndexPath = usernameIndexPath;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }

    @Generated
    public AmazonCloudDirectoryProperties setOrder(int order) {
        this.order = order;
        return this;
    }
}

