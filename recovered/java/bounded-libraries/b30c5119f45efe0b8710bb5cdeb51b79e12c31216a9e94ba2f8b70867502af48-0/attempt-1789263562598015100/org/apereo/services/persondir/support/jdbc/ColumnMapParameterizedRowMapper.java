/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.support.JdbcUtils
 */
package org.apereo.services.persondir.support.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class ColumnMapParameterizedRowMapper
implements RowMapper<Map<String, Object>> {
    private final boolean ignoreNull;

    public ColumnMapParameterizedRowMapper() {
        this(false);
    }

    public ColumnMapParameterizedRowMapper(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }

    public final Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> mapOfColValues = this.createColumnMap(columnCount);
        for (int i = 1; i <= columnCount; ++i) {
            String columnName = JdbcUtils.lookupColumnName((ResultSetMetaData)rsmd, (int)i);
            Object obj = this.getColumnValue(rs, i);
            if (this.ignoreNull && obj == null) continue;
            String key = this.getColumnKey(columnName);
            mapOfColValues.put(key, obj);
        }
        return mapOfColValues;
    }

    protected Map<String, Object> createColumnMap(int columnCount) {
        return new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
    }

    protected String getColumnKey(String columnName) {
        return columnName;
    }

    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtils.getResultSetValue((ResultSet)rs, (int)index);
    }
}

