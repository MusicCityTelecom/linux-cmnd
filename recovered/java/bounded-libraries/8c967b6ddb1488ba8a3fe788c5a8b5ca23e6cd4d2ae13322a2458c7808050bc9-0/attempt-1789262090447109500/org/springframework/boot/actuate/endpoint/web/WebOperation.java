/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;

public interface WebOperation
extends Operation {
    public String getId();

    public boolean isBlocking();

    public WebOperationRequestPredicate getRequestPredicate();
}

