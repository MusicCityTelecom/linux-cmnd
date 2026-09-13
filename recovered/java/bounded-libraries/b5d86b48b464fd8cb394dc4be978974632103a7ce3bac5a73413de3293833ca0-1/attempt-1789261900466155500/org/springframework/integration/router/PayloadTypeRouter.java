/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.router;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;

public class PayloadTypeRouter
extends AbstractMappingMessageRouter {
    private static final String ARRAY_SUFFIX = "[]";

    @Override
    protected List<Object> getChannelKeys(Message<?> message) {
        String closestMatch;
        if (CollectionUtils.isEmpty(this.getChannelMappings())) {
            return null;
        }
        Class<?> type = message.getPayload().getClass();
        boolean isArray = type.isArray();
        if (isArray) {
            type = type.getComponentType();
        }
        return (closestMatch = this.findClosestMatch(type, isArray)) != null ? Collections.singletonList(closestMatch) : null;
    }

    private String findClosestMatch(Class<?> type, boolean isArray) {
        int minTypeDiffWeight = Integer.MAX_VALUE;
        LinkedList<String> matches = new LinkedList<String>();
        for (String candidate : this.getChannelMappings().keySet()) {
            if (isArray) {
                if (!candidate.endsWith(ARRAY_SUFFIX)) continue;
                candidate = candidate.substring(0, candidate.length() - ARRAY_SUFFIX.length());
            } else if (candidate.endsWith(ARRAY_SUFFIX)) continue;
            int typeDiffWeight = this.determineTypeDifferenceWeight(candidate, type, 0);
            if (typeDiffWeight < minTypeDiffWeight) {
                minTypeDiffWeight = typeDiffWeight;
                matches.clear();
                matches.add(isArray ? candidate + ARRAY_SUFFIX : candidate);
                continue;
            }
            if (typeDiffWeight != minTypeDiffWeight || typeDiffWeight == Integer.MAX_VALUE) continue;
            matches.add(candidate);
        }
        if (matches.size() > 1) {
            throw new IllegalStateException("Unresolvable ambiguity while attempting to find closest match for [" + type.getName() + "]. Found: " + matches);
        }
        if (CollectionUtils.isEmpty(matches)) {
            return null;
        }
        return (String)matches.get(0);
    }

    private int determineTypeDifferenceWeight(String candidate, Class<?> type, int level) {
        if (type.getName().equals(candidate)) {
            return level;
        }
        for (Class<?> iface : type.getInterfaces()) {
            if (iface.getName().equals(candidate)) {
                return level % 2 != 0 ? level + 2 : level + 1;
            }
            for (Class<?> superInterface : iface.getInterfaces()) {
                int weight = this.determineTypeDifferenceWeight(candidate, superInterface, level + 3);
                if (weight >= Integer.MAX_VALUE) continue;
                return weight;
            }
        }
        if (type.getSuperclass() == null) {
            return Integer.MAX_VALUE;
        }
        return this.determineTypeDifferenceWeight(candidate, type.getSuperclass(), level + 2);
    }
}

