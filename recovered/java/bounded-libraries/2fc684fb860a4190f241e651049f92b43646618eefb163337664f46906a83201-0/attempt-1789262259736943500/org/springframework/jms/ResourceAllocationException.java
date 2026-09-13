/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ResourceAllocationException
 */
package org.springframework.jms;

import org.springframework.jms.JmsException;

public class ResourceAllocationException
extends JmsException {
    public ResourceAllocationException(javax.jms.ResourceAllocationException cause) {
        super((Throwable)cause);
    }
}

