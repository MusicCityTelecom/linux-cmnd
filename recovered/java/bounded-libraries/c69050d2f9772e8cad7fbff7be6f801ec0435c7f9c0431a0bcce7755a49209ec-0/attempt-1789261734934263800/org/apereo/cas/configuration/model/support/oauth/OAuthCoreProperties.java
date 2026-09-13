/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth")
@JsonFilter(value="OAuthCoreProperties")
public class OAuthCoreProperties
implements Serializable {
    private static final long serialVersionUID = -1687928082301669359L;
    private boolean bypassApprovalPrompt;
    private UserProfileViewTypes userProfileViewType = UserProfileViewTypes.NESTED;

    @Generated
    public boolean isBypassApprovalPrompt() {
        return this.bypassApprovalPrompt;
    }

    @Generated
    public UserProfileViewTypes getUserProfileViewType() {
        return this.userProfileViewType;
    }

    @Generated
    public OAuthCoreProperties setBypassApprovalPrompt(boolean bypassApprovalPrompt) {
        this.bypassApprovalPrompt = bypassApprovalPrompt;
        return this;
    }

    @Generated
    public OAuthCoreProperties setUserProfileViewType(UserProfileViewTypes userProfileViewType) {
        this.userProfileViewType = userProfileViewType;
        return this;
    }

    public static enum UserProfileViewTypes {
        NESTED,
        FLAT;

    }
}

