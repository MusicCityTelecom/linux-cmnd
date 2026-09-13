/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.core.type.filter.TypeFilter
 */
package org.springframework.boot.context;

import java.io.IOException;
import java.util.Collection;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class TypeExcludeFilter
implements TypeFilter,
BeanFactoryAware {
    private BeanFactory beanFactory;
    private Collection<TypeExcludeFilter> delegates;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if (this.beanFactory instanceof ListableBeanFactory && this.getClass() == TypeExcludeFilter.class) {
            for (TypeExcludeFilter delegate : this.getDelegates()) {
                if (!delegate.match(metadataReader, metadataReaderFactory)) continue;
                return true;
            }
        }
        return false;
    }

    private Collection<TypeExcludeFilter> getDelegates() {
        Collection<TypeExcludeFilter> delegates = this.delegates;
        if (delegates == null) {
            this.delegates = delegates = ((ListableBeanFactory)this.beanFactory).getBeansOfType(TypeExcludeFilter.class).values();
        }
        return delegates;
    }

    public boolean equals(Object obj) {
        throw new IllegalStateException("TypeExcludeFilter " + this.getClass() + " has not implemented equals");
    }

    public int hashCode() {
        throw new IllegalStateException("TypeExcludeFilter " + this.getClass() + " has not implemented hashCode");
    }
}

