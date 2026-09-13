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
@JsonFilter(value="CaseChangeSearchEntryHandlersProperties")
public class CaseChangeSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = 2420895955116725666L;
    private String dnCaseChange;
    private String attributeNameCaseChange;
    private String attributeValueCaseChange;
    private List<String> attributeNames = new ArrayList<String>(0);

    @Generated
    public String getDnCaseChange() {
        return this.dnCaseChange;
    }

    @Generated
    public String getAttributeNameCaseChange() {
        return this.attributeNameCaseChange;
    }

    @Generated
    public String getAttributeValueCaseChange() {
        return this.attributeValueCaseChange;
    }

    @Generated
    public List<String> getAttributeNames() {
        return this.attributeNames;
    }

    @Generated
    public CaseChangeSearchEntryHandlersProperties setDnCaseChange(String dnCaseChange) {
        this.dnCaseChange = dnCaseChange;
        return this;
    }

    @Generated
    public CaseChangeSearchEntryHandlersProperties setAttributeNameCaseChange(String attributeNameCaseChange) {
        this.attributeNameCaseChange = attributeNameCaseChange;
        return this;
    }

    @Generated
    public CaseChangeSearchEntryHandlersProperties setAttributeValueCaseChange(String attributeValueCaseChange) {
        this.attributeValueCaseChange = attributeValueCaseChange;
        return this;
    }

    @Generated
    public CaseChangeSearchEntryHandlersProperties setAttributeNames(List<String> attributeNames) {
        this.attributeNames = attributeNames;
        return this;
    }
}

