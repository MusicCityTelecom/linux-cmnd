/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.autoconfigure.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

abstract class FilteringSpringBootCondition
extends SpringBootCondition
implements AutoConfigurationImportFilter,
BeanFactoryAware,
BeanClassLoaderAware {
    private BeanFactory beanFactory;
    private ClassLoader beanClassLoader;

    FilteringSpringBootCondition() {
    }

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        ConditionEvaluationReport report = ConditionEvaluationReport.find(this.beanFactory);
        ConditionOutcome[] outcomes = this.getOutcomes(autoConfigurationClasses, autoConfigurationMetadata);
        boolean[] match = new boolean[outcomes.length];
        for (int i = 0; i < outcomes.length; ++i) {
            boolean bl = match[i] = outcomes[i] == null || outcomes[i].isMatch();
            if (match[i] || outcomes[i] == null) continue;
            this.logOutcome(autoConfigurationClasses[i], outcomes[i]);
            if (report == null) continue;
            report.recordConditionEvaluation(autoConfigurationClasses[i], this, outcomes[i]);
        }
        return match;
    }

    protected abstract ConditionOutcome[] getOutcomes(String[] var1, AutoConfigurationMetadata var2);

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected final BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    protected final ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    protected final List<String> filter(Collection<String> classNames, ClassNameFilter classNameFilter, ClassLoader classLoader) {
        if (CollectionUtils.isEmpty(classNames)) {
            return Collections.emptyList();
        }
        ArrayList<String> matches = new ArrayList<String>(classNames.size());
        for (String candidate : classNames) {
            if (!classNameFilter.matches(candidate, classLoader)) continue;
            matches.add(candidate);
        }
        return matches;
    }

    protected static Class<?> resolve(String className, ClassLoader classLoader) throws ClassNotFoundException {
        if (classLoader != null) {
            return Class.forName(className, false, classLoader);
        }
        return Class.forName(className);
    }

    protected static enum ClassNameFilter {
        PRESENT{

            @Override
            public boolean matches(String className, ClassLoader classLoader) {
                return 1.isPresent(className, classLoader);
            }
        }
        ,
        MISSING{

            @Override
            public boolean matches(String className, ClassLoader classLoader) {
                return !2.isPresent(className, classLoader);
            }
        };


        abstract boolean matches(String var1, ClassLoader var2);

        static boolean isPresent(String className, ClassLoader classLoader) {
            if (classLoader == null) {
                classLoader = ClassUtils.getDefaultClassLoader();
            }
            try {
                FilteringSpringBootCondition.resolve(className, classLoader);
                return true;
            }
            catch (Throwable ex) {
                return false;
            }
        }
    }
}

