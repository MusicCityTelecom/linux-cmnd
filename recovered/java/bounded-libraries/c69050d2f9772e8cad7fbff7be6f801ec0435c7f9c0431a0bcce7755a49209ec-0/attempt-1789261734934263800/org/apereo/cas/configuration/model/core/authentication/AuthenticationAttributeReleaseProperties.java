/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-validation", automated=true)
public class AuthenticationAttributeReleaseProperties
implements Serializable {
    private static final long serialVersionUID = 6123748197108749858L;
    private boolean enabled = true;
    private List<String> neverRelease = new ArrayList<String>(0);
    private List<String> onlyRelease = new ArrayList<String>(0);

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public List<String> getNeverRelease() {
        return this.neverRelease;
    }

    @Generated
    public List<String> getOnlyRelease() {
        return this.onlyRelease;
    }

    @Generated
    public AuthenticationAttributeReleaseProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public AuthenticationAttributeReleaseProperties setNeverRelease(List<String> neverRelease) {
        this.neverRelease = neverRelease;
        return this;
    }

    @Generated
    public AuthenticationAttributeReleaseProperties setOnlyRelease(List<String> onlyRelease) {
        this.onlyRelease = onlyRelease;
        return this;
    }
}

