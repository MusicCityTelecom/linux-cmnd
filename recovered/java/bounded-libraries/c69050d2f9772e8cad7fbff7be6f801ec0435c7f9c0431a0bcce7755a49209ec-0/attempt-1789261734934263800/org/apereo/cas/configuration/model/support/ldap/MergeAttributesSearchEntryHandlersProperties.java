/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
public class MergeAttributesSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = -3988972992084584349L;
    private String mergeAttributeName;
    private List<String> attributeNames = new ArrayList<String>(0);

    @Generated
    public String getMergeAttributeName() {
        return this.mergeAttributeName;
    }

    @Generated
    public List<String> getAttributeNames() {
        return this.attributeNames;
    }

    @Generated
    public MergeAttributesSearchEntryHandlersProperties setMergeAttributeName(String mergeAttributeName) {
        this.mergeAttributeName = mergeAttributeName;
        return this;
    }

    @Generated
    public MergeAttributesSearchEntryHandlersProperties setAttributeNames(List<String> attributeNames) {
        this.attributeNames = attributeNames;
        return this;
    }
}

