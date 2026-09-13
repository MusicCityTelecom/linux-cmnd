/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aws;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aws-s3-service-registry")
@JsonFilter(value="AmazonS3ServiceRegistryProperties")
public class AmazonS3ServiceRegistryProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = -6790277338807046269L;
}

