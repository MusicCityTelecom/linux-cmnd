/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.ehcache.util.counter.Counter
 *  net.sf.ehcache.util.counter.sampled.SampledCounter
 *  org.slf4j.Logger
 */
package net.sf.ehcache.management.service.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.sf.ehcache.management.service.AccessorPrefix;
import net.sf.ehcache.util.counter.Counter;
import net.sf.ehcache.util.counter.sampled.SampledCounter;
import org.slf4j.Logger;

abstract class ConstrainableEntityBuilderSupport<SAMPLER> {
    private static final Set<String> SIZE_ATTRIBUTE_NAMES = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("Size", "SizeSample", "RemoteSizeSample")));
    private Set<String> constraints;

    ConstrainableEntityBuilderSupport() {
    }

    abstract Logger getLog();

    protected void addConstraints(Set<String> constraints) {
        if (constraints == null) {
            throw new IllegalArgumentException("constraints == null");
        }
        if (this.constraints == null) {
            this.constraints = constraints;
        } else {
            this.constraints.addAll(constraints);
        }
    }

    protected Set<String> getAttributeConstraints() {
        return this.constraints;
    }

    protected void buildAttributeMapByAttribute(Class<?> api, SAMPLER sampler, Map<String, Object> attributeMap, Collection<String> attributes, String nameAccessor) {
        Set<String> excludedNames = this.getExcludedAttributeNames(sampler);
        for (String attribute : attributes) {
            Method method = null;
            for (AccessorPrefix prefix : AccessorPrefix.values()) {
                try {
                    method = api.getMethod((Object)((Object)prefix) + attribute, new Class[0]);
                    break;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                }
            }
            if (method == null || nameAccessor.equals(method.getName())) continue;
            if (excludedNames.contains(attribute)) {
                attributeMap.put(attribute, 0);
                continue;
            }
            this.addAttribute(sampler, attributeMap, attribute, method);
        }
    }

    protected void buildAttributeMapByApi(Class<?> api, SAMPLER sampler, Map<String, Object> attributeMap, Collection<String> attributes, String nameAccessor) {
        Set<String> excludedNames = this.getExcludedAttributeNames(sampler);
        for (Method method : api.getMethods()) {
            String name = method.getName();
            String trimmedName = AccessorPrefix.trimPrefix(name);
            if (nameAccessor.equals(name) || !AccessorPrefix.isAccessor(name) || attributes != null && !attributes.contains(trimmedName)) continue;
            if (excludedNames.contains(trimmedName)) {
                attributeMap.put(trimmedName, 0);
                continue;
            }
            this.addAttribute(sampler, attributeMap, trimmedName, method);
        }
    }

    protected abstract Set<String> getExcludedAttributeNames(SAMPLER var1);

    protected Set<String> getUnsignedIntAttributeNames(SAMPLER sampler) {
        return SIZE_ATTRIBUTE_NAMES;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addAttribute(SAMPLER sampler, Map<String, Object> attributeMap, String attribute, Method method) {
        Object value = null;
        try {
            value = method.invoke(sampler, new Object[0]);
            if (value instanceof SampledCounter) {
                value = ((SampledCounter)value).getMostRecentSample().getCounterValue();
            } else if (value instanceof Counter) {
                value = ((Counter)value).getValue();
            }
            if (this.getUnsignedIntAttributeNames(sampler).contains(attribute) && value instanceof Number) {
                value = ConstrainableEntityBuilderSupport.coerceUnsignedIntToLong(((Number)value).intValue());
            }
        }
        catch (Exception e) {
            value = null;
            String msg = String.format("Failed to invoke method %s while constructing entity.", method.getName());
            this.getLog().warn(msg);
            this.getLog().debug(msg, (Throwable)e);
        }
        finally {
            attributeMap.put(attribute, value);
        }
    }

    private static long coerceUnsignedIntToLong(int value) {
        return value < 0 ? Integer.MAX_VALUE + (long)(value - Integer.MIN_VALUE + 1) : (long)value;
    }
}

