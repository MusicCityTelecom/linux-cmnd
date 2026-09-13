/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.HK2Loader;

public interface Descriptor {
    public String getImplementation();

    public Set<String> getAdvertisedContracts();

    public String getScope();

    public String getName();

    public Set<String> getQualifiers();

    public DescriptorType getDescriptorType();

    public DescriptorVisibility getDescriptorVisibility();

    public Map<String, List<String>> getMetadata();

    public HK2Loader getLoader();

    public int getRanking();

    public int setRanking(int var1);

    public Boolean isProxiable();

    public Boolean isProxyForSameScope();

    public String getClassAnalysisName();

    public Long getServiceId();

    public Long getLocatorId();
}

