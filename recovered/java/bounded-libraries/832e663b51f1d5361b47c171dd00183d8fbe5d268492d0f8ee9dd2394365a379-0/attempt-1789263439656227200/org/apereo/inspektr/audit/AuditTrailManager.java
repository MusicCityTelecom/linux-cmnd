/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  org.apereo.inspektr.common.Cleanable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.inspektr.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Map;
import java.util.Set;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.common.Cleanable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface AuditTrailManager
extends Cleanable {
    public static final Logger LOG = LoggerFactory.getLogger(AuditTrailManager.class);
    public static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    public static String toJson(Object arg) {
        try {
            return MAPPER.writeValueAsString(arg);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public void record(AuditActionContext var1);

    public Set<? extends AuditActionContext> getAuditRecords(Map<WhereClauseFields, Object> var1);

    default public void removeAll() {
    }

    default public void clean() {
    }

    default public void setAuditFormat(AuditFormats auditFormat) {
    }

    public static enum AuditFormats {
        DEFAULT{

            @Override
            public String serialize(Object object) {
                return object.toString();
            }
        }
        ,
        JSON{

            @Override
            public String serialize(Object object) {
                return AuditTrailManager.toJson(object);
            }
        };


        public abstract String serialize(Object var1);
    }

    public static enum WhereClauseFields {
        DATE,
        PRINCIPAL;

    }
}

