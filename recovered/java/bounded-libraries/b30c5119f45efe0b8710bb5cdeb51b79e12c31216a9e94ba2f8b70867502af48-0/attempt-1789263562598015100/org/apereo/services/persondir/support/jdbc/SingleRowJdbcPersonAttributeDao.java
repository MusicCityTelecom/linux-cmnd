/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.jdbc.core.RowMapper
 */
package org.apereo.services.persondir.support.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.BasePersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveAttributeNamedPersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.jdbc.AbstractJdbcPersonAttributeDao;
import org.apereo.services.persondir.support.jdbc.ColumnMapParameterizedRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class SingleRowJdbcPersonAttributeDao
extends AbstractJdbcPersonAttributeDao<Map<String, Object>> {
    private static final RowMapper<Map<String, Object>> MAPPER = new ColumnMapParameterizedRowMapper(true);

    public SingleRowJdbcPersonAttributeDao() {
    }

    public SingleRowJdbcPersonAttributeDao(DataSource ds, String sql) {
        super(ds, sql);
    }

    @Override
    protected RowMapper<Map<String, Object>> getRowMapper() {
        return MAPPER;
    }

    @Override
    protected List<IPersonAttributes> parseAttributeMapFromResults(List<Map<String, Object>> queryResults, String queryUserName) {
        ArrayList<IPersonAttributes> peopleAttributes = new ArrayList<IPersonAttributes>(queryResults.size());
        for (Map<String, Object> queryResult : queryResults) {
            Map<String, List<Object>> multivaluedQueryResult = MultivaluedPersonAttributeUtils.toMultivaluedMap(queryResult);
            String userNameAttribute = this.getConfiguredUserNameAttribute();
            BasePersonImpl person = this.isUserNameAttributeConfigured() && queryResult.containsKey(userNameAttribute) ? new CaseInsensitiveAttributeNamedPersonImpl(userNameAttribute, multivaluedQueryResult) : (queryUserName != null ? new CaseInsensitiveNamedPersonImpl(queryUserName, multivaluedQueryResult) : new CaseInsensitiveAttributeNamedPersonImpl(userNameAttribute, multivaluedQueryResult));
            peopleAttributes.add(person);
        }
        return peopleAttributes;
    }
}

