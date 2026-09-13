/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.beans;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

@Endpoint(id="beans")
public class BeansEndpoint {
    private final ConfigurableApplicationContext context;

    public BeansEndpoint(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @ReadOperation
    public ApplicationBeans beans() {
        HashMap<String, ContextBeans> contexts = new HashMap<String, ContextBeans>();
        ConfigurableApplicationContext context = this.context;
        while (context != null) {
            contexts.put(context.getId(), ContextBeans.describing(context));
            context = BeansEndpoint.getConfigurableParent(context);
        }
        return new ApplicationBeans(contexts);
    }

    private static ConfigurableApplicationContext getConfigurableParent(ConfigurableApplicationContext context) {
        ApplicationContext parent = context.getParent();
        if (parent instanceof ConfigurableApplicationContext) {
            return (ConfigurableApplicationContext)parent;
        }
        return null;
    }

    public static final class BeanDescriptor {
        private final String[] aliases;
        private final String scope;
        private final Class<?> type;
        private final String resource;
        private final String[] dependencies;

        private BeanDescriptor(String[] aliases, String scope, Class<?> type, String resource, String[] dependencies) {
            this.aliases = aliases;
            this.scope = StringUtils.hasText((String)scope) ? scope : "singleton";
            this.type = type;
            this.resource = resource;
            this.dependencies = dependencies;
        }

        public String[] getAliases() {
            return this.aliases;
        }

        public String getScope() {
            return this.scope;
        }

        public Class<?> getType() {
            return this.type;
        }

        public String getResource() {
            return this.resource;
        }

        public String[] getDependencies() {
            return this.dependencies;
        }
    }

    public static final class ContextBeans {
        private final Map<String, BeanDescriptor> beans;
        private final String parentId;

        private ContextBeans(Map<String, BeanDescriptor> beans, String parentId) {
            this.beans = beans;
            this.parentId = parentId;
        }

        public String getParentId() {
            return this.parentId;
        }

        public Map<String, BeanDescriptor> getBeans() {
            return this.beans;
        }

        private static ContextBeans describing(ConfigurableApplicationContext context) {
            if (context == null) {
                return null;
            }
            ConfigurableApplicationContext parent = BeansEndpoint.getConfigurableParent(context);
            return new ContextBeans(ContextBeans.describeBeans(context.getBeanFactory()), parent != null ? parent.getId() : null);
        }

        private static Map<String, BeanDescriptor> describeBeans(ConfigurableListableBeanFactory beanFactory) {
            HashMap<String, BeanDescriptor> beans = new HashMap<String, BeanDescriptor>();
            for (String beanName : beanFactory.getBeanDefinitionNames()) {
                BeanDefinition definition = beanFactory.getBeanDefinition(beanName);
                if (!ContextBeans.isBeanEligible(beanName, definition, (ConfigurableBeanFactory)beanFactory)) continue;
                beans.put(beanName, ContextBeans.describeBean(beanName, definition, beanFactory));
            }
            return beans;
        }

        private static BeanDescriptor describeBean(String name, BeanDefinition definition, ConfigurableListableBeanFactory factory) {
            return new BeanDescriptor(factory.getAliases(name), definition.getScope(), factory.getType(name), definition.getResourceDescription(), factory.getDependenciesForBean(name));
        }

        private static boolean isBeanEligible(String beanName, BeanDefinition bd, ConfigurableBeanFactory bf) {
            return bd.getRole() != 2 && (!bd.isLazyInit() || bf.containsSingleton(beanName));
        }
    }

    public static final class ApplicationBeans {
        private final Map<String, ContextBeans> contexts;

        private ApplicationBeans(Map<String, ContextBeans> contexts) {
            this.contexts = contexts;
        }

        public Map<String, ContextBeans> getContexts() {
            return this.contexts;
        }
    }
}

