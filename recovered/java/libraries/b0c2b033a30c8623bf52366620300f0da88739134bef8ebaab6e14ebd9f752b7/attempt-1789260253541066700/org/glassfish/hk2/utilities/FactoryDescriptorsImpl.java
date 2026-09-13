/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;

public class FactoryDescriptorsImpl
implements FactoryDescriptors {
    private final Descriptor asService;
    private final Descriptor asProvideMethod;

    public FactoryDescriptorsImpl(Descriptor asService, Descriptor asProvideMethod) {
        if (asService == null || asProvideMethod == null) {
            throw new IllegalArgumentException();
        }
        if (!DescriptorType.CLASS.equals((Object)asService.getDescriptorType())) {
            throw new IllegalArgumentException("Creation of FactoryDescriptors must have first argument of type CLASS");
        }
        if (!asService.getAdvertisedContracts().contains(Factory.class.getName())) {
            throw new IllegalArgumentException("Creation of FactoryDescriptors must have Factory as a contract of the first argument");
        }
        if (!DescriptorType.PROVIDE_METHOD.equals((Object)asProvideMethod.getDescriptorType())) {
            throw new IllegalArgumentException("Creation of FactoryDescriptors must have second argument of type PROVIDE_METHOD");
        }
        this.asService = asService;
        this.asProvideMethod = asProvideMethod;
    }

    @Override
    public Descriptor getFactoryAsAService() {
        return this.asService;
    }

    @Override
    public Descriptor getFactoryAsAFactory() {
        return this.asProvideMethod;
    }

    public int hashCode() {
        return this.asService.hashCode() ^ this.asProvideMethod.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof FactoryDescriptors)) {
            return false;
        }
        FactoryDescriptors other = (FactoryDescriptors)o;
        Descriptor otherService = other.getFactoryAsAService();
        Descriptor otherFactory = other.getFactoryAsAFactory();
        if (otherService == null || otherFactory == null) {
            return false;
        }
        return this.asService.equals(otherService) && this.asProvideMethod.equals(otherFactory);
    }

    public String toString() {
        return "FactoryDescriptorsImpl(\n" + this.asService + ",\n" + this.asProvideMethod + ",\n\t" + System.identityHashCode(this) + ")";
    }
}

