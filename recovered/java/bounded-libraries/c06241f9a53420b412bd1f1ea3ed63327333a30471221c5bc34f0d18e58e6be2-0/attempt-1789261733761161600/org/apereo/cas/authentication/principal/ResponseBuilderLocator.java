/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.authentication.principal.ResponseBuilder;
import org.apereo.cas.authentication.principal.WebApplicationService;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface ResponseBuilderLocator<T extends WebApplicationService>
extends Serializable {
    public ResponseBuilder<T> locate(T var1);
}

