/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.resource.spi.ActivationSpec
 *  javax.resource.spi.ResourceAdapter
 */
package org.springframework.jms.listener.endpoint;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ResourceAdapter;
import org.springframework.jms.listener.endpoint.JmsActivationSpecConfig;

public interface JmsActivationSpecFactory {
    public ActivationSpec createActivationSpec(ResourceAdapter var1, JmsActivationSpecConfig var2);
}

