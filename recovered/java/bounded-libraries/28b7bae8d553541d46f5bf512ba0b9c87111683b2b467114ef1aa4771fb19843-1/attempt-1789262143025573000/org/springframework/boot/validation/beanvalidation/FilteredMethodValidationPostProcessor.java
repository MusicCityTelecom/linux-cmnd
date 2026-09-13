/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.ClassFilter
 *  org.springframework.aop.MethodMatcher
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.support.ComposablePointcut
 *  org.springframework.aop.support.DefaultPointcutAdvisor
 *  org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
package org.springframework.boot.validation.beanvalidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

public class FilteredMethodValidationPostProcessor
extends MethodValidationPostProcessor {
    private final Collection<MethodValidationExcludeFilter> excludeFilters;

    public FilteredMethodValidationPostProcessor(Stream<? extends MethodValidationExcludeFilter> excludeFilters) {
        this.excludeFilters = excludeFilters.collect(Collectors.toList());
    }

    public FilteredMethodValidationPostProcessor(Collection<? extends MethodValidationExcludeFilter> excludeFilters) {
        this.excludeFilters = new ArrayList<MethodValidationExcludeFilter>(excludeFilters);
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        DefaultPointcutAdvisor advisor = (DefaultPointcutAdvisor)this.advisor;
        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        MethodMatcher methodMatcher = advisor.getPointcut().getMethodMatcher();
        advisor.setPointcut((Pointcut)new ComposablePointcut(classFilter, methodMatcher).intersection(this::isIncluded));
    }

    private boolean isIncluded(Class<?> candidate) {
        for (MethodValidationExcludeFilter exclusionFilter : this.excludeFilters) {
            if (!exclusionFilter.isExcluded(candidate)) continue;
            return false;
        }
        return true;
    }
}

