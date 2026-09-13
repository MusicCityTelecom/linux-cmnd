/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.services.stream;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-service-registry-stream")
@JsonFilter(value="BaseStreamServicesProperties")
public class BaseStreamServicesProperties
implements Serializable {
    private static final long serialVersionUID = 7025417314334269017L;
}

