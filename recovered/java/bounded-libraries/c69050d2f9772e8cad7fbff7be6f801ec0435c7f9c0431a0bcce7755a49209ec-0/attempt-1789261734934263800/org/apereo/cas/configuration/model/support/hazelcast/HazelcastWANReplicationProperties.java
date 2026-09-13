/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastWANReplicationTargetClusterProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="HazelcastWANReplicationProperties")
public class HazelcastWANReplicationProperties
implements Serializable {
    private static final long serialVersionUID = 1726420607045775145L;
    private boolean enabled;
    private String replicationName = "apereo-cas";
    private List<HazelcastWANReplicationTargetClusterProperties> targets = new ArrayList<HazelcastWANReplicationTargetClusterProperties>(0);

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getReplicationName() {
        return this.replicationName;
    }

    @Generated
    public List<HazelcastWANReplicationTargetClusterProperties> getTargets() {
        return this.targets;
    }

    @Generated
    public HazelcastWANReplicationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public HazelcastWANReplicationProperties setReplicationName(String replicationName) {
        this.replicationName = replicationName;
        return this;
    }

    @Generated
    public HazelcastWANReplicationProperties setTargets(List<HazelcastWANReplicationTargetClusterProperties> targets) {
        this.targets = targets;
        return this;
    }
}

