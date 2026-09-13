/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.couchbase.BaseCouchbaseProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-couchbase")
@JsonFilter(value="CouchbaseAcceptableUsagePolicyProperties")
public class CouchbaseAcceptableUsagePolicyProperties
extends BaseCouchbaseProperties {
    private static final long serialVersionUID = 2323894615409106853L;
}

