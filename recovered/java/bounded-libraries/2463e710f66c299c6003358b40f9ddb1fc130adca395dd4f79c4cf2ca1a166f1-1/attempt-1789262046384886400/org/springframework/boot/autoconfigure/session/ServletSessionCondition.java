/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.WebApplicationType
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.session.AbstractSessionCondition;

class ServletSessionCondition
extends AbstractSessionCondition {
    ServletSessionCondition() {
        super(WebApplicationType.SERVLET);
    }
}

