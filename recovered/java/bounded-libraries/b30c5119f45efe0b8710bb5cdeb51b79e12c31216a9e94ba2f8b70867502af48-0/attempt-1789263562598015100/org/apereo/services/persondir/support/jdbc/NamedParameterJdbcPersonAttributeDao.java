/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package org.apereo.services.persondir.support.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.util.CollectionsUtil;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class NamedParameterJdbcPersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao
implements InitializingBean {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private String sql;
    private IUsernameAttributeProvider usernameAttributeProvider;
    private Set<String> availableQueryAttributes = null;
    private Set<String> userAttributeNames = null;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public void setUsernameAttributeProvider(IUsernameAttributeProvider usernameAttributeProvider) {
        this.usernameAttributeProvider = usernameAttributeProvider;
    }

    public void setAvailableQueryAttributes(Set<String> availableQueryAttributes) {
        this.availableQueryAttributes = CollectionsUtil.safelyWrapAsUnmodifiableSet(availableQueryAttributes);
    }

    public void setUserAttributeNames(Set<String> userAttributeNames) {
        this.userAttributeNames = CollectionsUtil.safelyWrapAsUnmodifiableSet(userAttributeNames);
    }

    public void afterPropertiesSet() throws Exception {
        if (this.dataSource == null) {
            throw new BeanInitializationException("dataSource property is required");
        }
        if (StringUtils.isEmpty((CharSequence)this.sql)) {
            throw new BeanInitializationException("sql property is required");
        }
        if (this.userAttributeNames == null) {
            throw new BeanInitializationException("userAttributeNames property is required");
        }
        if (this.usernameAttributeProvider == null) {
            throw new BeanInitializationException("usernameAttributeProvider is required");
        }
        this.jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return this.availableQueryAttributes;
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> queryParameters, IPersonAttributeDaoFilter filter) {
        String username = this.usernameAttributeProvider.getUsernameFromQuery(queryParameters);
        RowCallbackHandlerImpl rslt = new RowCallbackHandlerImpl(username);
        this.jdbcTemplate.query(this.sql, (SqlParameterSource)new SqlParameterSourceImpl(queryParameters), (RowCallbackHandler)rslt);
        return rslt.getResults();
    }

    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.userAttributeNames;
    }

    private class RowCallbackHandlerImpl
    implements RowCallbackHandler {
        final String username;
        final Map<String, Set<Object>> attributes = new HashMap<String, Set<Object>>();

        public RowCallbackHandlerImpl(String username) {
            this.username = username;
        }

        public void processRow(ResultSet rs) throws SQLException {
            for (String attrName : NamedParameterJdbcPersonAttributeDao.this.userAttributeNames) {
                Object val;
                Set<Object> values = this.attributes.get(attrName);
                if (values == null) {
                    values = new HashSet<Object>();
                    this.attributes.put(attrName, values);
                }
                if ((val = rs.getObject(attrName)) == null) continue;
                values.add(val);
            }
        }

        public Set<IPersonAttributes> getResults() {
            HashMap<String, List<Object>> mapOfLists = new HashMap<String, List<Object>>();
            for (Map.Entry<String, Set<Object>> y : this.attributes.entrySet()) {
                mapOfLists.put(y.getKey(), new ArrayList(y.getValue()));
            }
            CaseInsensitiveNamedPersonImpl person = new CaseInsensitiveNamedPersonImpl(this.username, mapOfLists);
            return Collections.singleton(person);
        }
    }

    private static final class SqlParameterSourceImpl
    extends AbstractSqlParameterSource {
        private final Map<String, List<Object>> queryParameters;

        public SqlParameterSourceImpl(Map<String, List<Object>> queryParameters) {
            this.queryParameters = queryParameters;
        }

        public Object getValue(String paramName) throws IllegalArgumentException {
            List<Object> val = this.queryParameters.get(paramName);
            return val != null && val.size() != 0 ? val.get(0) : null;
        }

        public boolean hasValue(String paramName) {
            List<Object> val = this.queryParameters.get(paramName);
            return val != null && val.size() != 0;
        }
    }
}

