/*
 * Decompiled with CFR 0.152.
 */
package com.terracotta.management.resource.services.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

public class UriInfoUtils {
    private static final Set<String> PRODUCT_IDS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("TMS", "WAN", "USER")));

    public static Set<String> extractProductIds(UriInfo info) {
        List ids = (List)info.getQueryParameters().get("productIds");
        if (ids == null) {
            return null;
        }
        HashSet<String> result = new HashSet<String>();
        for (String idsString : ids) {
            List<String> idNames = Arrays.asList(idsString.split(","));
            for (String idName : idNames) {
                if (idName.equals("*")) {
                    result.addAll(PRODUCT_IDS);
                    continue;
                }
                result.add(idName);
            }
        }
        return result;
    }

    public static Set<String> extractAgentIds(UriInfo info) {
        PathSegment agentsPathSegment = null;
        List<PathSegment> pathSegments = info.getPathSegments();
        for (PathSegment pathSegment : pathSegments) {
            if (!pathSegment.getPath().equals("agents")) continue;
            agentsPathSegment = pathSegment;
            break;
        }
        if (agentsPathSegment == null) {
            throw new IllegalArgumentException("path does not contain /agents segment");
        }
        String value = agentsPathSegment.getMatrixParameters().getFirst("ids");
        Set<String> values = value == null ? Collections.emptySet() : new HashSet<String>(Arrays.asList(value.split(",")));
        return values;
    }

    public static Set<String> extractSegmentMatrixParameterAsSet(UriInfo info, String pathName, String parameterName) {
        List<PathSegment> pathSegments = info.getPathSegments();
        for (PathSegment pathSegment : pathSegments) {
            if (!pathSegment.getPath().equals(pathName)) continue;
            List values = (List)pathSegment.getMatrixParameters().get(parameterName);
            return UriInfoUtils.toSet(values);
        }
        return null;
    }

    public static Set<String> extractLastSegmentMatrixParameterAsSet(UriInfo info, String parameterName) {
        List values = (List)info.getPathSegments().get(info.getPathSegments().size() - 1).getMatrixParameters().get(parameterName);
        return UriInfoUtils.toSet(values);
    }

    private static Set<String> toSet(List<String> values) {
        if (values == null) {
            return null;
        }
        HashSet<String> result = new HashSet<String>();
        for (String value : values) {
            result.addAll(Arrays.asList(value.split(",")));
        }
        return result;
    }
}

