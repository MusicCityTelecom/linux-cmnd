/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.apereo.services.persondir.support.QueryType
 *  org.apereo.services.persondir.util.CaseCanonicalizationMode
 */
package org.apereo.cas.configuration.model.support.jdbc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.apereo.services.persondir.support.QueryType;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
@JsonFilter(value="JdbcPrincipalAttributesProperties")
public class JdbcPrincipalAttributesProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 6915428382578138387L;
    private String sql;
    private boolean singleRow = true;
    private boolean requireAllAttributes = true;
    private String caseCanonicalization = CaseCanonicalizationMode.NONE.name();
    private String queryType = QueryType.AND.name();
    private Map<String, String> columnMappings = new HashMap<String, String>(0);
    private List<String> username = new ArrayList<String>(0);
    private int order;
    private String id;
    private Map<String, String> attributes = new HashMap<String, String>(0);
    private List<String> caseInsensitiveQueryAttributes = new ArrayList<String>(0);
    private Map<String, String> queryAttributes = new HashMap<String, String>(0);
    private AttributeRepositoryStates state = AttributeRepositoryStates.ACTIVE;

    @Generated
    public String getSql() {
        return this.sql;
    }

    @Generated
    public boolean isSingleRow() {
        return this.singleRow;
    }

    @Generated
    public boolean isRequireAllAttributes() {
        return this.requireAllAttributes;
    }

    @Generated
    public String getCaseCanonicalization() {
        return this.caseCanonicalization;
    }

    @Generated
    public String getQueryType() {
        return this.queryType;
    }

    @Generated
    public Map<String, String> getColumnMappings() {
        return this.columnMappings;
    }

    @Generated
    public List<String> getUsername() {
        return this.username;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    @Generated
    public List<String> getCaseInsensitiveQueryAttributes() {
        return this.caseInsensitiveQueryAttributes;
    }

    @Generated
    public Map<String, String> getQueryAttributes() {
        return this.queryAttributes;
    }

    @Generated
    public AttributeRepositoryStates getState() {
        return this.state;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setSql(String sql) {
        this.sql = sql;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setSingleRow(boolean singleRow) {
        this.singleRow = singleRow;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setRequireAllAttributes(boolean requireAllAttributes) {
        this.requireAllAttributes = requireAllAttributes;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setCaseCanonicalization(String caseCanonicalization) {
        this.caseCanonicalization = caseCanonicalization;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setQueryType(String queryType) {
        this.queryType = queryType;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setColumnMappings(Map<String, String> columnMappings) {
        this.columnMappings = columnMappings;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setUsername(List<String> username) {
        this.username = username;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setCaseInsensitiveQueryAttributes(List<String> caseInsensitiveQueryAttributes) {
        this.caseInsensitiveQueryAttributes = caseInsensitiveQueryAttributes;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setQueryAttributes(Map<String, String> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return this;
    }

    @Generated
    public JdbcPrincipalAttributesProperties setState(AttributeRepositoryStates state) {
        this.state = state;
        return this;
    }
}

