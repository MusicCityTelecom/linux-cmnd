/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.internal;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.utilities.DescriptorBuilder;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.FactoryDescriptorsImpl;

public class DescriptorBuilderImpl
implements DescriptorBuilder {
    private String name;
    private final HashSet<String> contracts = new HashSet();
    private String scope;
    private final HashSet<String> qualifiers = new HashSet();
    private final HashMap<String, List<String>> metadatas = new HashMap();
    private String implementation;
    private HK2Loader loader = null;
    private int rank = 0;
    private Boolean proxy = null;
    private Boolean proxyForSameScope = null;
    private DescriptorVisibility visibility = DescriptorVisibility.NORMAL;
    private String analysisName = null;

    public DescriptorBuilderImpl() {
    }

    public DescriptorBuilderImpl(String implementation, boolean addToContracts) {
        this.implementation = implementation;
        if (addToContracts) {
            this.contracts.add(implementation);
        }
    }

    @Override
    public DescriptorBuilder named(String name) throws IllegalArgumentException {
        if (this.name != null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.qualifiers.add(Named.class.getName());
        return this;
    }

    @Override
    public DescriptorBuilder to(Class<?> contract) throws IllegalArgumentException {
        if (contract == null) {
            throw new IllegalArgumentException();
        }
        return this.to(contract.getName());
    }

    @Override
    public DescriptorBuilder to(String contract) throws IllegalArgumentException {
        if (contract == null) {
            throw new IllegalArgumentException();
        }
        this.contracts.add(contract);
        return this;
    }

    @Override
    public DescriptorBuilder in(Class<? extends Annotation> scope) throws IllegalArgumentException {
        if (scope == null) {
            throw new IllegalArgumentException();
        }
        return this.in(scope.getName());
    }

    @Override
    public DescriptorBuilder in(String scope) throws IllegalArgumentException {
        if (scope == null) {
            throw new IllegalArgumentException();
        }
        this.scope = scope;
        return this;
    }

    @Override
    public DescriptorBuilder qualifiedBy(Annotation annotation) throws IllegalArgumentException {
        if (annotation == null) {
            throw new IllegalArgumentException();
        }
        if (Named.class.equals(annotation.annotationType())) {
            this.name = ((Named)annotation).value();
        }
        return this.qualifiedBy(annotation.annotationType().getName());
    }

    @Override
    public DescriptorBuilder qualifiedBy(String annotation) throws IllegalArgumentException {
        if (annotation == null) {
            throw new IllegalArgumentException();
        }
        this.qualifiers.add(annotation);
        return this;
    }

    @Override
    public DescriptorBuilder has(String key, String value) throws IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        LinkedList<String> values = new LinkedList<String>();
        values.add(value);
        return this.has(key, values);
    }

    @Override
    public DescriptorBuilder has(String key, List<String> values) throws IllegalArgumentException {
        if (key == null || values == null || values.size() <= 0) {
            throw new IllegalArgumentException();
        }
        this.metadatas.put(key, values);
        return this;
    }

    @Override
    public DescriptorBuilder ofRank(int rank) {
        this.rank = rank;
        return this;
    }

    @Override
    public DescriptorBuilder proxy() {
        return this.proxy(true);
    }

    @Override
    public DescriptorBuilder proxy(boolean forceProxy) {
        this.proxy = forceProxy ? Boolean.TRUE : Boolean.FALSE;
        return this;
    }

    @Override
    public DescriptorBuilder proxyForSameScope() {
        return this.proxyForSameScope(true);
    }

    @Override
    public DescriptorBuilder proxyForSameScope(boolean proxyForSameScope) {
        this.proxyForSameScope = proxyForSameScope ? Boolean.TRUE : Boolean.FALSE;
        return this;
    }

    @Override
    public DescriptorBuilder localOnly() {
        this.visibility = DescriptorVisibility.LOCAL;
        return this;
    }

    @Override
    public DescriptorBuilder visibility(DescriptorVisibility visibility) {
        if (visibility == null) {
            throw new IllegalArgumentException();
        }
        this.visibility = visibility;
        return this;
    }

    @Override
    public DescriptorBuilder andLoadWith(HK2Loader loader) throws IllegalArgumentException {
        if (this.loader != null) {
            throw new IllegalArgumentException();
        }
        this.loader = loader;
        return this;
    }

    @Override
    public DescriptorBuilder analyzeWith(String serviceName) {
        this.analysisName = serviceName;
        return this;
    }

    @Override
    public DescriptorImpl build() throws IllegalArgumentException {
        return new DescriptorImpl(this.contracts, this.name, this.scope, this.implementation, this.metadatas, this.qualifiers, DescriptorType.CLASS, this.visibility, this.loader, this.rank, this.proxy, this.proxyForSameScope, this.analysisName, null, null);
    }

    @Override
    public FactoryDescriptors buildFactory(String factoryScope) throws IllegalArgumentException {
        HashSet<String> factoryContracts = new HashSet<String>();
        factoryContracts.add(this.implementation);
        factoryContracts.add(Factory.class.getName());
        Set<String> factoryQualifiers = Collections.emptySet();
        Map<String, List<String>> factoryMetadata = Collections.emptyMap();
        DescriptorImpl asService = new DescriptorImpl(factoryContracts, null, factoryScope, this.implementation, factoryMetadata, factoryQualifiers, DescriptorType.CLASS, DescriptorVisibility.NORMAL, this.loader, this.rank, null, null, this.analysisName, null, null);
        HashSet<String> serviceContracts = new HashSet<String>(this.contracts);
        if (this.implementation != null) {
            serviceContracts.remove(this.implementation);
        }
        DescriptorImpl asFactory = new DescriptorImpl(serviceContracts, this.name, this.scope, this.implementation, this.metadatas, this.qualifiers, DescriptorType.PROVIDE_METHOD, this.visibility, this.loader, this.rank, this.proxy, this.proxyForSameScope, null, null, null);
        return new FactoryDescriptorsImpl(asService, asFactory);
    }

    @Override
    public FactoryDescriptors buildFactory() throws IllegalArgumentException {
        return this.buildFactory(PerLookup.class.getName());
    }

    @Override
    public FactoryDescriptors buildFactory(Class<? extends Annotation> factoryScope) throws IllegalArgumentException {
        if (factoryScope == null) {
            factoryScope = PerLookup.class;
        }
        return this.buildFactory(factoryScope.getName());
    }
}

