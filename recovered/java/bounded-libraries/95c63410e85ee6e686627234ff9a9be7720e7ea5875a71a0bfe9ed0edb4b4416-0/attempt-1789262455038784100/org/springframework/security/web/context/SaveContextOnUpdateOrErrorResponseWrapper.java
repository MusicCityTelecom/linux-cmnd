/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 */
package org.springframework.security.web.context;

import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.OnCommittedResponseWrapper;

@Deprecated
public abstract class SaveContextOnUpdateOrErrorResponseWrapper
extends OnCommittedResponseWrapper {
    private boolean contextSaved = false;
    private final boolean disableUrlRewriting;

    public SaveContextOnUpdateOrErrorResponseWrapper(HttpServletResponse response, boolean disableUrlRewriting) {
        super(response);
        this.disableUrlRewriting = disableUrlRewriting;
    }

    public void disableSaveOnResponseCommitted() {
        this.disableOnResponseCommitted();
    }

    protected abstract void saveContext(SecurityContext var1);

    @Override
    protected void onResponseCommitted() {
        this.saveContext(SecurityContextHolder.getContext());
        this.contextSaved = true;
    }

    public final String encodeRedirectUrl(String url) {
        if (this.disableUrlRewriting) {
            return url;
        }
        return super.encodeRedirectUrl(url);
    }

    public final String encodeRedirectURL(String url) {
        if (this.disableUrlRewriting) {
            return url;
        }
        return super.encodeRedirectURL(url);
    }

    public final String encodeUrl(String url) {
        if (this.disableUrlRewriting) {
            return url;
        }
        return super.encodeUrl(url);
    }

    public final String encodeURL(String url) {
        if (this.disableUrlRewriting) {
            return url;
        }
        return super.encodeURL(url);
    }

    public final boolean isContextSaved() {
        return this.contextSaved;
    }
}

