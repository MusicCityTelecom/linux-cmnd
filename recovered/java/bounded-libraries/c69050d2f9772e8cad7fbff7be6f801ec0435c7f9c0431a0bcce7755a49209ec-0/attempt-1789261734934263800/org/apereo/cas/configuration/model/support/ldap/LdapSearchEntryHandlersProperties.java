/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.CaseChangeSearchEntryHandlersProperties;
import org.apereo.cas.configuration.model.support.ldap.DnAttributeSearchEntryHandlersProperties;
import org.apereo.cas.configuration.model.support.ldap.FollowReferralSearchEntryHandlersProperties;
import org.apereo.cas.configuration.model.support.ldap.FollowResultSearchEntryHandlersProperties;
import org.apereo.cas.configuration.model.support.ldap.MergeAttributesSearchEntryHandlersProperties;
import org.apereo.cas.configuration.model.support.ldap.PrimaryGroupIdSearchEntryHandlersProperties;
import org.apereo.cas.configuration.model.support.ldap.RecursiveSearchEntryHandlersProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ldap")
@JsonFilter(value="LdapSearchEntryHandlersProperties")
public class LdapSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = -5198990160347131821L;
    private SearchEntryHandlerTypes type;
    @NestedConfigurationProperty
    private CaseChangeSearchEntryHandlersProperties caseChange = new CaseChangeSearchEntryHandlersProperties();
    @NestedConfigurationProperty
    private DnAttributeSearchEntryHandlersProperties dnAttribute = new DnAttributeSearchEntryHandlersProperties();
    @NestedConfigurationProperty
    private MergeAttributesSearchEntryHandlersProperties mergeAttribute = new MergeAttributesSearchEntryHandlersProperties();
    @NestedConfigurationProperty
    private PrimaryGroupIdSearchEntryHandlersProperties primaryGroupId = new PrimaryGroupIdSearchEntryHandlersProperties();
    @NestedConfigurationProperty
    private RecursiveSearchEntryHandlersProperties recursive = new RecursiveSearchEntryHandlersProperties();
    @NestedConfigurationProperty
    private FollowReferralSearchEntryHandlersProperties searchReferral = new FollowReferralSearchEntryHandlersProperties();
    @NestedConfigurationProperty
    private FollowResultSearchEntryHandlersProperties searchResult = new FollowResultSearchEntryHandlersProperties();

    @Generated
    public SearchEntryHandlerTypes getType() {
        return this.type;
    }

    @Generated
    public CaseChangeSearchEntryHandlersProperties getCaseChange() {
        return this.caseChange;
    }

    @Generated
    public DnAttributeSearchEntryHandlersProperties getDnAttribute() {
        return this.dnAttribute;
    }

    @Generated
    public MergeAttributesSearchEntryHandlersProperties getMergeAttribute() {
        return this.mergeAttribute;
    }

    @Generated
    public PrimaryGroupIdSearchEntryHandlersProperties getPrimaryGroupId() {
        return this.primaryGroupId;
    }

    @Generated
    public RecursiveSearchEntryHandlersProperties getRecursive() {
        return this.recursive;
    }

    @Generated
    public FollowReferralSearchEntryHandlersProperties getSearchReferral() {
        return this.searchReferral;
    }

    @Generated
    public FollowResultSearchEntryHandlersProperties getSearchResult() {
        return this.searchResult;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setType(SearchEntryHandlerTypes type) {
        this.type = type;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setCaseChange(CaseChangeSearchEntryHandlersProperties caseChange) {
        this.caseChange = caseChange;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setDnAttribute(DnAttributeSearchEntryHandlersProperties dnAttribute) {
        this.dnAttribute = dnAttribute;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setMergeAttribute(MergeAttributesSearchEntryHandlersProperties mergeAttribute) {
        this.mergeAttribute = mergeAttribute;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setPrimaryGroupId(PrimaryGroupIdSearchEntryHandlersProperties primaryGroupId) {
        this.primaryGroupId = primaryGroupId;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setRecursive(RecursiveSearchEntryHandlersProperties recursive) {
        this.recursive = recursive;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setSearchReferral(FollowReferralSearchEntryHandlersProperties searchReferral) {
        this.searchReferral = searchReferral;
        return this;
    }

    @Generated
    public LdapSearchEntryHandlersProperties setSearchResult(FollowResultSearchEntryHandlersProperties searchResult) {
        this.searchResult = searchResult;
        return this;
    }

    public static enum SearchEntryHandlerTypes {
        FOLLOW_SEARCH_REFERRAL,
        FOLLOW_SEARCH_RESULT_REFERENCE,
        ACTIVE_DIRECTORY,
        OBJECT_GUID,
        OBJECT_SID,
        CASE_CHANGE,
        DN_ATTRIBUTE_ENTRY,
        MERGE,
        PRIMARY_GROUP,
        RANGE_ENTRY,
        RECURSIVE_ENTRY,
        MERGE_ENTRIES;

    }
}

