/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Named;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.NamedImpl;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public abstract class AbstractActiveDescriptor<T>
extends DescriptorImpl
implements ActiveDescriptor<T> {
    private static final long serialVersionUID = 7080312303893604939L;
    private static final Set<Annotation> EMPTY_QUALIFIER_SET = Collections.emptySet();
    private Set<Type> advertisedContracts = new LinkedHashSet<Type>();
    private Annotation scopeAnnotation;
    private Class<? extends Annotation> scope;
    private Set<Annotation> qualifiers;
    private Long factoryServiceId;
    private Long factoryLocatorId;
    private boolean isReified = true;
    private transient boolean cacheSet = false;
    private transient T cachedValue;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rLock = this.rwLock.readLock();
    private final Lock wLock = this.rwLock.writeLock();

    public AbstractActiveDescriptor() {
        this.scope = null;
    }

    protected AbstractActiveDescriptor(Descriptor baseDescriptor) {
        super(baseDescriptor);
        this.isReified = false;
        this.scope = null;
    }

    protected AbstractActiveDescriptor(Set<Type> advertisedContracts, Class<? extends Annotation> scope, String name, Set<Annotation> qualifiers, DescriptorType descriptorType, DescriptorVisibility descriptorVisibility, int ranking, Boolean proxy, Boolean proxyForSameScope, String analyzerName, Map<String, List<String>> metadata) {
        this.scope = scope;
        this.advertisedContracts.addAll(advertisedContracts);
        if (qualifiers != null && !qualifiers.isEmpty()) {
            this.qualifiers = new LinkedHashSet<Annotation>();
            this.qualifiers.addAll(qualifiers);
        }
        this.setRanking(ranking);
        this.setDescriptorType(descriptorType);
        this.setDescriptorVisibility(descriptorVisibility);
        this.setName(name);
        this.setProxiable(proxy);
        this.setProxyForSameScope(proxyForSameScope);
        if (scope != null) {
            this.setScope(scope.getName());
        }
        for (Type type : advertisedContracts) {
            Class<?> raw = ReflectionHelper.getRawClass(type);
            if (raw == null) continue;
            this.addAdvertisedContract(raw.getName());
        }
        if (qualifiers != null) {
            for (Annotation annotation : qualifiers) {
                this.addQualifier(annotation.annotationType().getName());
            }
        }
        this.setClassAnalysisName(analyzerName);
        if (metadata == null) {
            return;
        }
        for (Map.Entry entry : metadata.entrySet()) {
            String key = (String)entry.getKey();
            List values = (List)entry.getValue();
            for (String value : values) {
                this.addMetadata(key, value);
            }
        }
    }

    private void removeNamedQualifier() {
        try {
            this.wLock.lock();
            if (this.qualifiers == null) {
                return;
            }
            for (Annotation qualifier : this.qualifiers) {
                if (!qualifier.annotationType().equals(Named.class)) continue;
                this.removeQualifierAnnotation(qualifier);
                return;
            }
        }
        finally {
            this.wLock.unlock();
        }
    }

    public void setImplementationType(Type t) {
        throw new AssertionError((Object)("Can not set type of " + this.getClass().getName() + " descriptor"));
    }

    @Override
    public void setName(String name) {
        try {
            this.wLock.lock();
            super.setName(name);
            this.removeNamedQualifier();
            if (name == null) {
                return;
            }
            this.addQualifierAnnotation(new NamedImpl(name));
        }
        finally {
            this.wLock.unlock();
        }
    }

    @Override
    public T getCache() {
        try {
            this.rLock.lock();
            T t = this.cachedValue;
            return t;
        }
        finally {
            this.rLock.unlock();
        }
    }

    @Override
    public boolean isCacheSet() {
        try {
            this.rLock.lock();
            boolean bl = this.cacheSet;
            return bl;
        }
        finally {
            this.rLock.unlock();
        }
    }

    @Override
    public void setCache(T cacheMe) {
        try {
            this.wLock.lock();
            this.cachedValue = cacheMe;
            this.cacheSet = true;
        }
        finally {
            this.wLock.unlock();
        }
    }

    @Override
    public void releaseCache() {
        try {
            this.wLock.lock();
            this.cacheSet = false;
            this.cachedValue = null;
        }
        finally {
            this.wLock.unlock();
        }
    }

    @Override
    public boolean isReified() {
        try {
            this.rLock.lock();
            boolean bl = this.isReified;
            return bl;
        }
        finally {
            this.rLock.unlock();
        }
    }

    public void setReified(boolean reified) {
        try {
            this.wLock.lock();
            this.isReified = reified;
        }
        finally {
            this.wLock.unlock();
        }
    }

    @Override
    public Set<Type> getContractTypes() {
        try {
            this.rLock.lock();
            Set<Type> set = Collections.unmodifiableSet(this.advertisedContracts);
            return set;
        }
        finally {
            this.rLock.unlock();
        }
    }

    public void addContractType(Type addMe) {
        try {
            this.wLock.lock();
            if (addMe == null) {
                return;
            }
            this.advertisedContracts.add(addMe);
            Class<?> rawClass = ReflectionHelper.getRawClass(addMe);
            if (rawClass == null) {
                return;
            }
            this.addAdvertisedContract(rawClass.getName());
        }
        finally {
            this.wLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeContractType(Type removeMe) {
        try {
            this.wLock.lock();
            if (removeMe == null) {
                boolean bl = false;
                return bl;
            }
            boolean retVal = this.advertisedContracts.remove(removeMe);
            Class<?> rawClass = ReflectionHelper.getRawClass(removeMe);
            if (rawClass == null) {
                boolean bl = retVal;
                return bl;
            }
            boolean bl = this.removeAdvertisedContract(rawClass.getName());
            return bl;
        }
        finally {
            this.wLock.unlock();
        }
    }

    @Override
    public Annotation getScopeAsAnnotation() {
        return this.scopeAnnotation;
    }

    public void setScopeAsAnnotation(Annotation scopeAnnotation) {
        this.scopeAnnotation = scopeAnnotation;
        if (scopeAnnotation != null) {
            this.setScopeAnnotation(scopeAnnotation.annotationType());
        }
    }

    @Override
    public Class<? extends Annotation> getScopeAnnotation() {
        return this.scope;
    }

    public void setScopeAnnotation(Class<? extends Annotation> scopeAnnotation) {
        this.scope = scopeAnnotation;
        this.setScope(this.scope.getName());
    }

    @Override
    public Set<Annotation> getQualifierAnnotations() {
        try {
            this.rLock.lock();
            if (this.qualifiers == null) {
                Set<Annotation> set = EMPTY_QUALIFIER_SET;
                return set;
            }
            Set<Annotation> set = Collections.unmodifiableSet(this.qualifiers);
            return set;
        }
        finally {
            this.rLock.unlock();
        }
    }

    public void addQualifierAnnotation(Annotation addMe) {
        try {
            this.wLock.lock();
            if (addMe == null) {
                return;
            }
            if (this.qualifiers == null) {
                this.qualifiers = new LinkedHashSet<Annotation>();
            }
            this.qualifiers.add(addMe);
            this.addQualifier(addMe.annotationType().getName());
        }
        finally {
            this.wLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeQualifierAnnotation(Annotation removeMe) {
        try {
            this.wLock.lock();
            if (removeMe == null) {
                boolean bl = false;
                return bl;
            }
            if (this.qualifiers == null) {
                boolean bl = false;
                return bl;
            }
            boolean retVal = this.qualifiers.remove(removeMe);
            this.removeQualifier(removeMe.annotationType().getName());
            boolean bl = retVal;
            return bl;
        }
        finally {
            this.wLock.unlock();
        }
    }

    @Override
    public Long getFactoryServiceId() {
        return this.factoryServiceId;
    }

    @Override
    public Long getFactoryLocatorId() {
        return this.factoryLocatorId;
    }

    public void setFactoryId(Long locatorId, Long serviceId) {
        if (!this.getDescriptorType().equals((Object)DescriptorType.PROVIDE_METHOD)) {
            throw new IllegalStateException("The descriptor type must be PROVIDE_METHOD");
        }
        this.factoryServiceId = serviceId;
        this.factoryLocatorId = locatorId;
    }

    @Override
    public List<Injectee> getInjectees() {
        return Collections.emptyList();
    }

    @Override
    public void dispose(T instance) {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

