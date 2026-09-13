/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package org.apereo.services.persondir.support.jdbc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractQueryPersonAttributeDao;
import org.apereo.services.persondir.support.QueryType;
import org.apereo.services.persondir.support.jdbc.PartialWhereClause;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractJdbcPersonAttributeDao<R>
extends AbstractQueryPersonAttributeDao<PartialWhereClause> {
    private static final Map<CaseCanonicalizationMode, MessageFormat> DEFAULT_DATA_ATTRIBUTE_CASE_CANONICALIZATION_FUNCTIONS = new HashMap<CaseCanonicalizationMode, MessageFormat>();
    private static final Pattern WHERE_PLACEHOLDER;
    private final JdbcTemplate simpleJdbcTemplate;
    private final String queryTemplate;
    private QueryType queryType = QueryType.AND;
    private Map<String, CaseCanonicalizationMode> caseInsensitiveDataAttributes;
    private Map<CaseCanonicalizationMode, MessageFormat> dataAttributeCaseCanonicalizationFunctions = DEFAULT_DATA_ATTRIBUTE_CASE_CANONICALIZATION_FUNCTIONS;

    public AbstractJdbcPersonAttributeDao() {
        this.simpleJdbcTemplate = null;
        this.queryTemplate = null;
    }

    public AbstractJdbcPersonAttributeDao(DataSource ds, String queryTemplate) {
        Validate.notNull((Object)ds, (String)"DataSource can not be null", (Object[])new Object[0]);
        Validate.notNull((Object)queryTemplate, (String)"queryTemplate can not be null", (Object[])new Object[0]);
        this.simpleJdbcTemplate = new JdbcTemplate(ds);
        this.queryTemplate = queryTemplate;
    }

    public String getQueryTemplate() {
        return this.queryTemplate;
    }

    public QueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    protected abstract List<IPersonAttributes> parseAttributeMapFromResults(List<R> var1, String var2);

    @JsonIgnore
    protected abstract RowMapper<R> getRowMapper();

    @Override
    protected PartialWhereClause appendAttributeToQuery(PartialWhereClause queryBuilder, String dataAttribute, List<Object> queryValues) {
        for (Object queryValue : queryValues) {
            String queryString = queryValue != null ? queryValue.toString() : null;
            if (!StringUtils.isNotBlank((CharSequence)queryString)) continue;
            if (queryBuilder == null) {
                queryBuilder = new PartialWhereClause();
            } else if (queryBuilder.sql.length() > 0) {
                queryBuilder.sql.append(" ").append(this.queryType.toString()).append(" ");
            }
            Matcher queryValueMatcher = IPersonAttributeDao.WILDCARD_PATTERN.matcher(queryString);
            String formattedQueryValue = queryValueMatcher.replaceAll("%");
            queryBuilder.arguments.add(formattedQueryValue);
            if (dataAttribute != null) {
                dataAttribute = this.canonicalizeDataAttributeForSql(dataAttribute);
                queryBuilder.sql.append(dataAttribute);
                if (formattedQueryValue.equals(queryString)) {
                    queryBuilder.sql.append(" = ");
                } else {
                    queryBuilder.sql.append(" LIKE ");
                }
            }
            queryBuilder.sql.append("?");
        }
        return queryBuilder;
    }

    protected String canonicalizeDataAttributeForSql(String dataAttribute) {
        MessageFormat mf;
        if (this.caseInsensitiveDataAttributes == null || this.caseInsensitiveDataAttributes.isEmpty() || !this.caseInsensitiveDataAttributes.containsKey(dataAttribute)) {
            return dataAttribute;
        }
        if (this.dataAttributeCaseCanonicalizationFunctions == null || this.dataAttributeCaseCanonicalizationFunctions.isEmpty()) {
            return dataAttribute;
        }
        CaseCanonicalizationMode canonicalizationMode = this.caseInsensitiveDataAttributes.get(dataAttribute);
        if (canonicalizationMode == null) {
            canonicalizationMode = this.getDefaultCaseCanonicalizationMode();
        }
        if ((mf = this.dataAttributeCaseCanonicalizationFunctions.get((Object)canonicalizationMode)) == null) {
            return dataAttribute;
        }
        return mf.format(new String[]{dataAttribute});
    }

    @Override
    protected List<IPersonAttributes> getPeopleForQuery(PartialWhereClause queryBuilder, String queryUserName) {
        List results;
        RowMapper<R> rowMapper = this.getRowMapper();
        if (queryBuilder != null) {
            StringBuilder partialSqlWhere = queryBuilder.sql;
            Matcher queryMatcher = WHERE_PLACEHOLDER.matcher(this.queryTemplate);
            String querySQL = queryMatcher.replaceAll(partialSqlWhere.toString());
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Executing '" + this.queryTemplate + "' with arguments " + queryBuilder.arguments);
            }
            results = this.simpleJdbcTemplate.query(querySQL, rowMapper, queryBuilder.arguments.toArray());
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Executed '" + this.queryTemplate + "' with arguments " + queryBuilder.arguments + " and got results " + results);
            }
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Executing '" + this.queryTemplate);
            }
            results = this.simpleJdbcTemplate.query(this.queryTemplate, rowMapper);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Executed '" + this.queryTemplate + "' and got results " + results);
            }
        }
        return this.parseAttributeMapFromResults(results, queryUserName);
    }

    public Map<String, CaseCanonicalizationMode> getCaseInsensitiveDataAttributes() {
        return this.caseInsensitiveDataAttributes;
    }

    public void setCaseInsensitiveDataAttributes(Map<String, CaseCanonicalizationMode> caseInsensitiveDataAttributes) {
        this.caseInsensitiveDataAttributes = caseInsensitiveDataAttributes;
    }

    public void setCaseInsensitiveDataAttributesAsCollection(Collection<String> caseInsensitiveDataAttributes) {
        if (caseInsensitiveDataAttributes == null || caseInsensitiveDataAttributes.isEmpty()) {
            this.setCaseInsensitiveDataAttributes(null);
        } else {
            HashMap<String, CaseCanonicalizationMode> asMap = new HashMap<String, CaseCanonicalizationMode>();
            for (String attrib : caseInsensitiveDataAttributes) {
                asMap.put(attrib, null);
            }
            this.setCaseInsensitiveDataAttributes(asMap);
        }
    }

    @JsonIgnore
    public void setDataAttributeCaseCanonicalizationFunctions(Map<CaseCanonicalizationMode, MessageFormat> dataAttributeCaseCanonicalizationFunctions) {
        this.dataAttributeCaseCanonicalizationFunctions = dataAttributeCaseCanonicalizationFunctions;
    }

    @JsonIgnore
    public Map<CaseCanonicalizationMode, MessageFormat> getDataAttributeCaseCanonicalizationFunctions() {
        return this.dataAttributeCaseCanonicalizationFunctions;
    }

    static {
        DEFAULT_DATA_ATTRIBUTE_CASE_CANONICALIZATION_FUNCTIONS.put(CaseCanonicalizationMode.LOWER, new MessageFormat("lower({0})"));
        DEFAULT_DATA_ATTRIBUTE_CASE_CANONICALIZATION_FUNCTIONS.put(CaseCanonicalizationMode.UPPER, new MessageFormat("upper({0})"));
        DEFAULT_DATA_ATTRIBUTE_CASE_CANONICALIZATION_FUNCTIONS.put(CaseCanonicalizationMode.NONE, new MessageFormat("{0}"));
        WHERE_PLACEHOLDER = Pattern.compile("\\{0\\}");
    }
}

