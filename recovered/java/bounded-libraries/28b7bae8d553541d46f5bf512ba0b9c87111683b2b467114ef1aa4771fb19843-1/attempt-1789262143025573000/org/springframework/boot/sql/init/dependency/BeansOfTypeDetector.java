/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 */
package org.springframework.boot.sql.init.dependency;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;

class BeansOfTypeDetector {
    private final Set<Class<?>> types;

    BeansOfTypeDetector(Set<Class<?>> types) {
        this.types = types;
    }

    Set<String> detect(ListableBeanFactory beanFactory) {
        HashSet<String> beanNames = new HashSet<String>();
        for (Class<?> type : this.types) {
            try {
                String[] names = beanFactory.getBeanNamesForType(type, true, false);
                Arrays.stream(names).map(BeanFactoryUtils::transformedBeanName).forEach(beanNames::add);
            }
            catch (Throwable throwable) {}
        }
        return beanNames;
    }
}

