/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.MapMaker
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.jdbc.BadSqlGrammarException
 *  org.springframework.jdbc.core.RowMapper
 */
package org.apereo.services.persondir.support.jdbc;

import com.google.common.collect.MapMaker;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import javax.sql.DataSource;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.jdbc.AbstractJdbcPersonAttributeDao;
import org.apereo.services.persondir.support.jdbc.ColumnMapParameterizedRowMapper;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;

public class MultiRowJdbcPersonAttributeDao
extends AbstractJdbcPersonAttributeDao<Map<String, Object>> {
    private static final RowMapper<Map<String, Object>> MAPPER = new ColumnMapParameterizedRowMapper();
    private Map<String, Set<String>> nameValueColumnMappings = null;

    public MultiRowJdbcPersonAttributeDao() {
    }

    public MultiRowJdbcPersonAttributeDao(DataSource ds, String sql) {
        super(ds, sql);
    }

    public Map<String, Set<String>> getNameValueColumnMappings() {
        return this.nameValueColumnMappings;
    }

    public void setNameValueColumnMappings(Map<String, ?> nameValueColumnMap) {
        if (nameValueColumnMap == null) {
            this.nameValueColumnMappings = null;
        } else {
            Map<String, Set<String>> mappings = MultivaluedPersonAttributeUtils.parseAttributeToAttributeMapping(nameValueColumnMap);
            if (mappings.containsValue(null)) {
                throw new IllegalArgumentException("nameValueColumnMap may not have null values");
            }
            this.nameValueColumnMappings = mappings;
        }
    }

    @Override
    protected RowMapper<Map<String, Object>> getRowMapper() {
        return MAPPER;
    }

    @Override
    protected List<IPersonAttributes> parseAttributeMapFromResults(List<Map<String, Object>> queryResults, String queryUserName) {
        ConcurrentMap peopleAttributesBuilder = new MapMaker().makeMap();
        String userNameAttribute = this.getConfiguredUserNameAttribute();
        for (Map<String, Object> queryResult : queryResults) {
            String userName;
            Object userNameValue;
            if (this.isUserNameAttributeConfigured() && queryResult.containsKey(userNameAttribute)) {
                userNameValue = queryResult.get(userNameAttribute);
                userName = userNameValue.toString();
            } else if (queryUserName != null) {
                userName = queryUserName;
            } else if (queryResult.containsKey(userNameAttribute)) {
                userNameValue = queryResult.get(userNameAttribute);
                userName = userNameValue.toString();
            } else {
                throw new BadSqlGrammarException("No userName column named '" + userNameAttribute + "' exists in result set and no userName provided in query Map", this.getQueryTemplate(), null);
            }
            Map attributes = peopleAttributesBuilder.computeIfAbsent(userName, key -> new LinkedHashMap());
            for (Map.Entry<String, Set<String>> columnMapping : this.nameValueColumnMappings.entrySet()) {
                String keyColumn = columnMapping.getKey();
                Object attrNameObj = queryResult.get(keyColumn);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Collecting attribute name=" + attrNameObj + " from column=" + keyColumn);
                }
                if (attrNameObj == null && !queryResult.containsKey(keyColumn)) {
                    throw new BadSqlGrammarException("No attribute key column named '" + keyColumn + "' exists in result set", this.getQueryTemplate(), null);
                }
                String attrName = String.valueOf(attrNameObj);
                Set<String> valueColumns = columnMapping.getValue();
                ArrayList<Object> attrValues = new ArrayList<Object>(valueColumns.size());
                for (String valueColumn : valueColumns) {
                    Object attrValue = queryResult.get(valueColumn);
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Collecting attribute value=" + attrValue + " from column=" + valueColumn);
                    }
                    if (attrValue == null && !queryResult.containsKey(valueColumn)) {
                        throw new BadSqlGrammarException("No attribute value column named '" + valueColumn + "' exists in result set", this.getQueryTemplate(), null);
                    }
                    attrValues.add(attrValue);
                }
                MultivaluedPersonAttributeUtils.addResult(attributes, attrName, attrValues);
            }
        }
        ArrayList<IPersonAttributes> people = new ArrayList<IPersonAttributes>(peopleAttributesBuilder.size());
        for (Map.Entry mappedAttributesEntry : peopleAttributesBuilder.entrySet()) {
            String userName = (String)mappedAttributesEntry.getKey();
            Map attributes = (Map)mappedAttributesEntry.getValue();
            CaseInsensitiveNamedPersonImpl person = new CaseInsensitiveNamedPersonImpl(userName, attributes);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Collecting person=" + person);
            }
            people.add(person);
        }
        return people;
    }
}

