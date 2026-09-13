/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.util.serialization.ComponentSerializationPlan
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.util.serialization.ComponentSerializationPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultComponentSerializationPlan
implements ComponentSerializationPlan {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultComponentSerializationPlan.class);
    private final List<Pair<Class, Integer>> registeredClasses = new ArrayList<Pair<Class, Integer>>(0);

    public void registerSerializableClass(Class clazz) {
        this.registerSerializableClass(clazz, Integer.MAX_VALUE);
    }

    public void registerSerializableClass(Class clazz, Integer order) {
        LOGGER.trace("Registering serializable class [{}] with order [{}]", (Object)clazz.getName(), (Object)order);
        this.registeredClasses.add((Pair<Class, Integer>)Pair.of((Object)clazz, (Object)order));
    }

    public Collection<Class> getRegisteredClasses() {
        return this.registeredClasses.stream().sorted(Comparator.comparingInt(Pair::getValue)).map(Pair::getKey).collect(Collectors.toSet());
    }
}

