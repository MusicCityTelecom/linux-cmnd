/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
@JsonFilter(value="RecursiveSearchEntryHandlersProperties")
public class RecursiveSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = 7138108925310792763L;
    private String searchAttribute;
    private List<String> mergeAttributes = new ArrayList<String>(0);

    @Generated
    public String getSearchAttribute() {
        return this.searchAttribute;
    }

    @Generated
    public List<String> getMergeAttributes() {
        return this.mergeAttributes;
    }

    @Generated
    public RecursiveSearchEntryHandlersProperties setSearchAttribute(String searchAttribute) {
        this.searchAttribute = searchAttribute;
        return this;
    }

    @Generated
    public RecursiveSearchEntryHandlersProperties setMergeAttributes(List<String> mergeAttributes) {
        this.mergeAttributes = mergeAttributes;
        return this;
    }
}

