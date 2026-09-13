/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.atomikos.jms.AtomikosConnectionFactoryBean
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jta.atomikos;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix="spring.jta.atomikos.connectionfactory")
public class AtomikosConnectionFactoryBean
extends com.atomikos.jms.AtomikosConnectionFactoryBean
implements BeanNameAware,
InitializingBean,
DisposableBean {
    private String beanName;

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasLength((String)this.getUniqueResourceName())) {
            this.setUniqueResourceName(this.beanName);
        }
        this.init();
    }

    public void destroy() throws Exception {
        this.close();
    }
}

