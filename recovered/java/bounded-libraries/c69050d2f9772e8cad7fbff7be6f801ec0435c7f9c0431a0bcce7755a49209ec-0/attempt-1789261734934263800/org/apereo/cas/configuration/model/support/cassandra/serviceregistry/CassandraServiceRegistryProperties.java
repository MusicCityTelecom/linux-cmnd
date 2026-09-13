/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.cassandra.serviceregistry;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.cassandra.authentication.BaseCassandraProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-cassandra-service-registry")
@JsonFilter(value="CassandraServiceRegistryProperties")
public class CassandraServiceRegistryProperties
extends BaseCassandraProperties {
    private static final long serialVersionUID = -1835394847251801709L;
}

