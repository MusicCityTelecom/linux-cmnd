/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="GrouperMultifactorAuthenticationProperties")
public class GrouperMultifactorAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 6426522468929733907L;
    private String grouperGroupField;

    @Generated
    public String getGrouperGroupField() {
        return this.grouperGroupField;
    }

    @Generated
    public GrouperMultifactorAuthenticationProperties setGrouperGroupField(String grouperGroupField) {
        this.grouperGroupField = grouperGroupField;
        return this;
    }
}

