/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.atomikos.icatch.jta.UserTransactionManager
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.core.Ordered
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jta.atomikos;

import com.atomikos.icatch.jta.UserTransactionManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

public class AtomikosDependsOnBeanFactoryPostProcessor
implements BeanFactoryPostProcessor,
Ordered {
    private static final String[] NO_BEANS = new String[0];
    private int order = Integer.MAX_VALUE;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] transactionManagers;
        for (String transactionManager : transactionManagers = beanFactory.getBeanNamesForType(UserTransactionManager.class, true, false)) {
            this.addTransactionManagerDependencies(beanFactory, transactionManager);
        }
        this.addMessageDrivenContainerDependencies(beanFactory, transactionManagers);
    }

    private void addTransactionManagerDependencies(ConfigurableListableBeanFactory beanFactory, String transactionManager) {
        BeanDefinition bean = beanFactory.getBeanDefinition(transactionManager);
        LinkedHashSet<String> dependsOn = new LinkedHashSet<String>(this.asList(bean.getDependsOn()));
        int initialSize = dependsOn.size();
        this.addDependencies(beanFactory, "javax.jms.ConnectionFactory", dependsOn);
        this.addDependencies(beanFactory, "javax.sql.DataSource", dependsOn);
        if (dependsOn.size() != initialSize) {
            bean.setDependsOn(StringUtils.toStringArray(dependsOn));
        }
    }

    private void addMessageDrivenContainerDependencies(ConfigurableListableBeanFactory beanFactory, String[] transactionManagers) {
        String[] messageDrivenContainers;
        for (String messageDrivenContainer : messageDrivenContainers = this.getBeanNamesForType(beanFactory, "com.atomikos.jms.extra.MessageDrivenContainer")) {
            BeanDefinition bean = beanFactory.getBeanDefinition(messageDrivenContainer);
            LinkedHashSet<String> dependsOn = new LinkedHashSet<String>(this.asList(bean.getDependsOn()));
            dependsOn.addAll(this.asList(transactionManagers));
            bean.setDependsOn(StringUtils.toStringArray(dependsOn));
        }
    }

    private void addDependencies(ConfigurableListableBeanFactory beanFactory, String type, Set<String> dependsOn) {
        dependsOn.addAll(this.asList(this.getBeanNamesForType(beanFactory, type)));
    }

    private String[] getBeanNamesForType(ConfigurableListableBeanFactory beanFactory, String type) {
        try {
            return beanFactory.getBeanNamesForType(Class.forName(type), true, false);
        }
        catch (ClassNotFoundException | NoClassDefFoundError throwable) {
            return NO_BEANS;
        }
    }

    private List<String> asList(String[] array) {
        return array != null ? Arrays.asList(array) : Collections.emptyList();
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

