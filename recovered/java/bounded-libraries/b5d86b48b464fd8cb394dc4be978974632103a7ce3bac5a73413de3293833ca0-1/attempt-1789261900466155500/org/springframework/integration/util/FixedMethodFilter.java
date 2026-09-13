/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.MethodFilter
 *  org.springframework.util.Assert
 */
package org.springframework.integration.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.expression.MethodFilter;
import org.springframework.util.Assert;

public class FixedMethodFilter
implements MethodFilter {
    private final Method method;

    public FixedMethodFilter(Method method) {
        Assert.notNull((Object)method, (String)"method must not be null");
        this.method = method;
    }

    public List<Method> filter(List<Method> methods) {
        if (methods != null && methods.contains(this.method)) {
            ArrayList<Method> filteredList = new ArrayList<Method>(1);
            filteredList.add(this.method);
            return filteredList;
        }
        return Collections.emptyList();
    }
}

