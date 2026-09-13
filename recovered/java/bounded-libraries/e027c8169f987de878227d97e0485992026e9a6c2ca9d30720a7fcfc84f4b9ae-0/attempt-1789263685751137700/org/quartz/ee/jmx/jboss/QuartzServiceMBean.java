/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jboss.system.ServiceMBean
 */
package org.quartz.ee.jmx.jboss;

import org.jboss.system.ServiceMBean;

public interface QuartzServiceMBean
extends ServiceMBean {
    public void setJndiName(String var1) throws Exception;

    public String getJndiName();

    public void setProperties(String var1);

    public void setPropertiesFile(String var1);

    public void setStartScheduler(boolean var1);
}

