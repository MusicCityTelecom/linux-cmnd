/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.logout;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-logout", automated=true)
public class LogoutProperties
implements Serializable {
    private static final long serialVersionUID = 7466171260665661949L;
    private String redirectParameter = "service";
    private boolean followServiceRedirects;
    private boolean removeDescendantTickets;
    private boolean confirmLogout;
    private String redirectUrl;

    @Generated
    public String getRedirectParameter() {
        return this.redirectParameter;
    }

    @Generated
    public boolean isFollowServiceRedirects() {
        return this.followServiceRedirects;
    }

    @Generated
    public boolean isRemoveDescendantTickets() {
        return this.removeDescendantTickets;
    }

    @Generated
    public boolean isConfirmLogout() {
        return this.confirmLogout;
    }

    @Generated
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Generated
    public LogoutProperties setRedirectParameter(String redirectParameter) {
        this.redirectParameter = redirectParameter;
        return this;
    }

    @Generated
    public LogoutProperties setFollowServiceRedirects(boolean followServiceRedirects) {
        this.followServiceRedirects = followServiceRedirects;
        return this;
    }

    @Generated
    public LogoutProperties setRemoveDescendantTickets(boolean removeDescendantTickets) {
        this.removeDescendantTickets = removeDescendantTickets;
        return this;
    }

    @Generated
    public LogoutProperties setConfirmLogout(boolean confirmLogout) {
        this.confirmLogout = confirmLogout;
        return this;
    }

    @Generated
    public LogoutProperties setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }
}

