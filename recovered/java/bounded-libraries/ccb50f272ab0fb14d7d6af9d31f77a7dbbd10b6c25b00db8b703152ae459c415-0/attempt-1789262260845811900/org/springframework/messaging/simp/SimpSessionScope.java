/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectFactory
 *  org.springframework.beans.factory.config.Scope
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.SimpAttributes;
import org.springframework.messaging.simp.SimpAttributesContextHolder;

public class SimpSessionScope
implements Scope {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object get(String name, ObjectFactory<?> objectFactory) {
        SimpAttributes simpAttributes = SimpAttributesContextHolder.currentAttributes();
        Object scopedObject = simpAttributes.getAttribute(name);
        if (scopedObject != null) {
            return scopedObject;
        }
        Object object = simpAttributes.getSessionMutex();
        synchronized (object) {
            scopedObject = simpAttributes.getAttribute(name);
            if (scopedObject == null) {
                scopedObject = objectFactory.getObject();
                simpAttributes.setAttribute(name, scopedObject);
            }
            return scopedObject;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public Object remove(String name) {
        SimpAttributes simpAttributes = SimpAttributesContextHolder.currentAttributes();
        Object object = simpAttributes.getSessionMutex();
        synchronized (object) {
            Object value = simpAttributes.getAttribute(name);
            if (value != null) {
                simpAttributes.removeAttribute(name);
                return value;
            }
            return null;
        }
    }

    public void registerDestructionCallback(String name, Runnable callback) {
        SimpAttributesContextHolder.currentAttributes().registerDestructionCallback(name, callback);
    }

    @Nullable
    public Object resolveContextualObject(String key) {
        return null;
    }

    public String getConversationId() {
        return SimpAttributesContextHolder.currentAttributes().getSessionId();
    }
}

