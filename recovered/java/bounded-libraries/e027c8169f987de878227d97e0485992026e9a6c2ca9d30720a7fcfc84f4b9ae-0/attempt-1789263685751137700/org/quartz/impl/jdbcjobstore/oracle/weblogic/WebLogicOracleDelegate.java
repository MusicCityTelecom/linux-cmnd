/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  weblogic.jdbc.vendor.oracle.OracleThinBlob
 */
package org.quartz.impl.jdbcjobstore.oracle.weblogic;

import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.oracle.OracleDelegate;
import weblogic.jdbc.vendor.oracle.OracleThinBlob;

public class WebLogicOracleDelegate
extends OracleDelegate {
    @Override
    protected Blob writeDataToBlob(ResultSet rs, int column, byte[] data) throws SQLException {
        Blob blob = rs.getBlob(column);
        if (blob == null) {
            throw new SQLException("Driver's Blob representation is null!");
        }
        if (blob instanceof OracleThinBlob) {
            ((OracleThinBlob)blob).putBytes(1L, data);
            return blob;
        }
        if (blob.getClass().getPackage().getName().startsWith("weblogic.")) {
            try {
                Method m = blob.getClass().getMethod("putBytes", Long.TYPE, byte[].class);
                m.invoke(blob, new Long(1L), data);
            }
            catch (Exception e) {
                try {
                    Method m = blob.getClass().getMethod("setBytes", Long.TYPE, byte[].class);
                    m.invoke(blob, new Long(1L), data);
                }
                catch (Exception e2) {
                    throw new SQLException("Unable to find putBytes(long,byte[]) or setBytes(long,byte[]) methods on blob: " + e2);
                }
            }
            return blob;
        }
        return super.writeDataToBlob(rs, column, data);
    }
}

