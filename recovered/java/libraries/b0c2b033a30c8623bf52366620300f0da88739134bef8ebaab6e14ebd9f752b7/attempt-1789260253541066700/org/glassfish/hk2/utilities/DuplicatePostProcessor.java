/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.util.HashSet;
import java.util.Set;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.PopulatorPostProcessor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.DuplicatePostProcessorMode;

@PerLookup
public class DuplicatePostProcessor
implements PopulatorPostProcessor {
    private final DuplicatePostProcessorMode mode;
    private final HashSet<DescriptorImpl> strictDupSet = new HashSet();
    private final HashSet<ImplOnlyKey> implOnlyDupSet = new HashSet();

    public DuplicatePostProcessor() {
        this(DuplicatePostProcessorMode.STRICT);
    }

    public DuplicatePostProcessor(DuplicatePostProcessorMode mode) {
        this.mode = mode;
    }

    public DuplicatePostProcessorMode getMode() {
        return this.mode;
    }

    @Override
    public DescriptorImpl process(ServiceLocator serviceLocator, DescriptorImpl descriptorImpl) {
        switch (this.mode) {
            case STRICT: {
                return this.strict(serviceLocator, descriptorImpl);
            }
            case IMPLEMENTATION_ONLY: {
                return this.implementationOnly(serviceLocator, descriptorImpl);
            }
        }
        throw new AssertionError((Object)("UnkownMode: " + (Object)((Object)this.mode)));
    }

    private DescriptorImpl implementationOnly(ServiceLocator serviceLocator, final DescriptorImpl descriptorImpl) {
        final String impl = descriptorImpl.getImplementation();
        if (impl == null) {
            return descriptorImpl;
        }
        ImplOnlyKey key = new ImplOnlyKey(descriptorImpl);
        if (this.implOnlyDupSet.contains(key)) {
            return null;
        }
        this.implOnlyDupSet.add(key);
        if (serviceLocator.getBestDescriptor(new Filter(){

            @Override
            public boolean matches(Descriptor d) {
                return d.getImplementation().equals(impl) && d.getDescriptorType().equals((Object)descriptorImpl.getDescriptorType());
            }
        }) != null) {
            return null;
        }
        return descriptorImpl;
    }

    private DescriptorImpl strict(ServiceLocator serviceLocator, DescriptorImpl descriptorImpl) {
        String fName;
        String fContract;
        DescriptorImpl fDescriptorImpl;
        if (this.strictDupSet.contains(descriptorImpl)) {
            return null;
        }
        this.strictDupSet.add(descriptorImpl);
        Set<String> contracts = descriptorImpl.getAdvertisedContracts();
        String contract = null;
        for (String candidate : contracts) {
            if (candidate.equals(descriptorImpl.getImplementation())) {
                contract = candidate;
                break;
            }
            contract = candidate;
        }
        if (serviceLocator.getBestDescriptor(new IndexedFilter(fDescriptorImpl = descriptorImpl, fContract = contract, fName = descriptorImpl.getName()){
            final /* synthetic */ DescriptorImpl val$fDescriptorImpl;
            final /* synthetic */ String val$fContract;
            final /* synthetic */ String val$fName;
            {
                this.val$fDescriptorImpl = descriptorImpl;
                this.val$fContract = string;
                this.val$fName = string2;
            }

            @Override
            public boolean matches(Descriptor d) {
                return this.val$fDescriptorImpl.equals(d);
            }

            @Override
            public String getAdvertisedContract() {
                return this.val$fContract;
            }

            @Override
            public String getName() {
                return this.val$fName;
            }
        }) != null) {
            return null;
        }
        return descriptorImpl;
    }

    public String toString() {
        return "DuplicateCodeProcessor(" + (Object)((Object)this.mode) + "," + System.identityHashCode(this) + ")";
    }

    private static final class ImplOnlyKey {
        private final String impl;
        private final DescriptorType type;
        private final int hash;

        private ImplOnlyKey(Descriptor desc) {
            this.impl = desc.getImplementation();
            this.type = desc.getDescriptorType();
            this.hash = this.impl.hashCode() ^ this.type.hashCode();
        }

        public int hashCode() {
            return this.hash;
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof ImplOnlyKey)) {
                return false;
            }
            ImplOnlyKey other = (ImplOnlyKey)o;
            return other.impl.equals(this.impl) && other.type.equals((Object)this.type);
        }

        public String toString() {
            return "ImplOnlyKey(" + this.impl + "," + (Object)((Object)this.type) + "," + System.identityHashCode(this) + ")";
        }
    }
}

