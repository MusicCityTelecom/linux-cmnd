/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.internal.HandleAndService;
import org.glassfish.hk2.internal.ImmediateLocalLocatorFilter;
import org.glassfish.hk2.utilities.ImmediateErrorHandler;

@Singleton
@Visibility(value=DescriptorVisibility.LOCAL)
public class ImmediateContext
implements Context<Immediate> {
    private final HashMap<ActiveDescriptor<?>, HandleAndService> currentImmediateServices = new HashMap();
    private final HashMap<ActiveDescriptor<?>, Long> creating = new HashMap();
    private final ServiceLocator locator;
    private final Filter validationFilter;

    @Inject
    private ImmediateContext(ServiceLocator locator) {
        this.locator = locator;
        this.validationFilter = new ImmediateLocalLocatorFilter(locator.getLocatorId());
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return Immediate.class;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <U> U findOrCreate(ActiveDescriptor<U> activeDescriptor, ServiceHandle<?> root) {
        U retVal = null;
        ImmediateContext immediateContext = this;
        synchronized (immediateContext) {
            HandleAndService has = this.currentImmediateServices.get(activeDescriptor);
            if (has != null) {
                return (U)has.getService();
            }
            while (this.creating.containsKey(activeDescriptor)) {
                long alreadyCreatingThread = this.creating.get(activeDescriptor);
                if (alreadyCreatingThread == Thread.currentThread().getId()) {
                    throw new MultiException(new IllegalStateException("A circular dependency involving Immediate service " + activeDescriptor.getImplementation() + " was found.  Full descriptor is " + activeDescriptor));
                }
                try {
                    this.wait();
                }
                catch (InterruptedException ie) {
                    throw new MultiException(ie);
                }
            }
            has = this.currentImmediateServices.get(activeDescriptor);
            if (has != null) {
                return (U)has.getService();
            }
            this.creating.put(activeDescriptor, Thread.currentThread().getId());
        }
        try {
            retVal = activeDescriptor.create(root);
        }
        finally {
            immediateContext = this;
            synchronized (immediateContext) {
                ServiceHandle<?> discoveredRoot = null;
                if (root != null && root.getActiveDescriptor().equals(activeDescriptor)) {
                    discoveredRoot = root;
                }
                if (retVal != null) {
                    this.currentImmediateServices.put(activeDescriptor, new HandleAndService(discoveredRoot, retVal));
                }
                this.creating.remove(activeDescriptor);
                this.notifyAll();
            }
        }
        return retVal;
    }

    @Override
    public synchronized boolean containsKey(ActiveDescriptor<?> descriptor) {
        return this.currentImmediateServices.containsKey(descriptor);
    }

    @Override
    public void destroyOne(ActiveDescriptor<?> descriptor) {
        this.destroyOne(descriptor, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void destroyOne(ActiveDescriptor<?> descriptor, List<ImmediateErrorHandler> errorHandlers) {
        if (errorHandlers == null) {
            errorHandlers = this.locator.getAllServices(ImmediateErrorHandler.class, new Annotation[0]);
        }
        ImmediateContext immediateContext = this;
        synchronized (immediateContext) {
            HandleAndService has = this.currentImmediateServices.remove(descriptor);
            Object instance = has.getService();
            try {
                descriptor.dispose(instance);
            }
            catch (Throwable th) {
                for (ImmediateErrorHandler ieh : errorHandlers) {
                    try {
                        ieh.preDestroyFailed(descriptor, th);
                    }
                    catch (Throwable throwable) {}
                }
            }
        }
    }

    @Override
    public boolean supportsNullCreation() {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        List<ImmediateErrorHandler> errorHandlers = this.locator.getAllServices(ImmediateErrorHandler.class, new Annotation[0]);
        ImmediateContext immediateContext = this;
        synchronized (immediateContext) {
            for (Map.Entry<ActiveDescriptor<?>, HandleAndService> entry : new HashSet(this.currentImmediateServices.entrySet())) {
                HandleAndService has = entry.getValue();
                ServiceHandle<?> handle = has.getHandle();
                if (handle != null) {
                    handle.destroy();
                    continue;
                }
                this.destroyOne(entry.getKey(), errorHandlers);
            }
        }
    }

    private List<ActiveDescriptor<?>> getImmediateServices() {
        List<ActiveDescriptor<?>> inScopeAndInThisLocator;
        try {
            inScopeAndInThisLocator = this.locator.getDescriptors(this.validationFilter);
        }
        catch (IllegalStateException ise) {
            inScopeAndInThisLocator = Collections.emptyList();
        }
        return inScopeAndInThisLocator;
    }

    public Filter getValidationFilter() {
        return this.validationFilter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doWork() {
        List<ImmediateErrorHandler> errorHandlers;
        List<ActiveDescriptor<?>> inScopeAndInThisLocator = this.getImmediateServices();
        try {
            errorHandlers = this.locator.getAllServices(ImmediateErrorHandler.class, new Annotation[0]);
        }
        catch (IllegalStateException ise) {
            return;
        }
        LinkedHashSet newFullSet = new LinkedHashSet(inScopeAndInThisLocator);
        LinkedHashSet addMe = new LinkedHashSet();
        ImmediateContext immediateContext = this;
        synchronized (immediateContext) {
            while (this.creating.size() > 0) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {
                    throw new RuntimeException(interruptedException);
                }
            }
            LinkedHashSet oldSet = new LinkedHashSet(this.currentImmediateServices.keySet());
            for (ActiveDescriptor<?> activeDescriptor : inScopeAndInThisLocator) {
                if (oldSet.contains(activeDescriptor)) continue;
                addMe.add(activeDescriptor);
            }
            oldSet.removeAll(newFullSet);
            for (ActiveDescriptor activeDescriptor : oldSet) {
                HandleAndService has = this.currentImmediateServices.get(activeDescriptor);
                ServiceHandle<?> handle = has.getHandle();
                if (handle != null) {
                    handle.destroy();
                    continue;
                }
                this.destroyOne(activeDescriptor, errorHandlers);
            }
        }
        for (ActiveDescriptor activeDescriptor : addMe) {
            try {
                this.locator.getServiceHandle(activeDescriptor).getService();
            }
            catch (Throwable throwable) {
                for (ImmediateErrorHandler ieh : errorHandlers) {
                    try {
                        ieh.postConstructFailed(activeDescriptor, throwable);
                    }
                    catch (Throwable throwable2) {}
                }
            }
        }
    }
}

