/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.jmx.export.metadata.JmxAttributeSource
 *  org.springframework.jmx.export.naming.MetadataNamingStrategy
 *  org.springframework.jmx.support.JmxUtils
 *  org.springframework.jmx.support.ObjectNameManager
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.jmx;

import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jmx.export.metadata.JmxAttributeSource;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;
import org.springframework.jmx.support.JmxUtils;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.util.ObjectUtils;

public class ParentAwareNamingStrategy
extends MetadataNamingStrategy
implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private boolean ensureUniqueRuntimeObjectNames;

    public ParentAwareNamingStrategy(JmxAttributeSource attributeSource) {
        super(attributeSource);
    }

    public void setEnsureUniqueRuntimeObjectNames(boolean ensureUniqueRuntimeObjectNames) {
        this.ensureUniqueRuntimeObjectNames = ensureUniqueRuntimeObjectNames;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ObjectName getObjectName(Object managedBean, String beanKey) throws MalformedObjectNameException {
        ObjectName name = super.getObjectName(managedBean, beanKey);
        if (this.ensureUniqueRuntimeObjectNames) {
            return JmxUtils.appendIdentityToObjectName((ObjectName)name, (Object)managedBean);
        }
        if (this.parentContextContainsSameBean(this.applicationContext, beanKey)) {
            return this.appendToObjectName(name, "context", ObjectUtils.getIdentityHexString((Object)this.applicationContext));
        }
        return name;
    }

    private boolean parentContextContainsSameBean(ApplicationContext context, String beanKey) {
        if (context.getParent() == null) {
            return false;
        }
        try {
            this.applicationContext.getParent().getBean(beanKey);
            return true;
        }
        catch (BeansException ex) {
            return this.parentContextContainsSameBean(context.getParent(), beanKey);
        }
    }

    private ObjectName appendToObjectName(ObjectName name, String key, String value) throws MalformedObjectNameException {
        Hashtable<String, String> keyProperties = name.getKeyPropertyList();
        keyProperties.put(key, value);
        return ObjectNameManager.getInstance((String)name.getDomain(), keyProperties);
    }
}

