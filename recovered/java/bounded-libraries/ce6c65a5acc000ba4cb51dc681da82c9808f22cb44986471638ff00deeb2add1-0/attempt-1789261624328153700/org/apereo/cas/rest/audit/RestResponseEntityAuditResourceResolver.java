/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apereo.inspektr.audit.spi.support.ReturnValueAsStringResourceResolver
 *  org.aspectj.lang.JoinPoint
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.audit;

import java.net.URI;
import java.util.HashMap;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.inspektr.audit.spi.support.ReturnValueAsStringResourceResolver;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class RestResponseEntityAuditResourceResolver
extends ReturnValueAsStringResourceResolver {
    private final boolean includeEntityBody;

    public String[] resolveFrom(JoinPoint auditableTarget, Object returnValue) {
        if (returnValue instanceof ResponseEntity) {
            return this.getAuditResourceFromResponseEntity((ResponseEntity)returnValue);
        }
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    private String[] getAuditResourceFromResponseEntity(ResponseEntity entity) {
        HttpHeaders headers = entity.getHeaders();
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("status", entity.getStatusCodeValue() + "-" + entity.getStatusCode().name());
        URI location = headers.getLocation();
        if (location != null) {
            values.put("location", location);
        }
        if (this.includeEntityBody && entity.getBody() != null) {
            values.put("body", entity.getBody().toString());
        }
        return new String[]{this.auditFormat.serialize(values)};
    }

    @Generated
    public RestResponseEntityAuditResourceResolver(boolean includeEntityBody) {
        this.includeEntityBody = includeEntityBody;
    }
}

