/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pm")
@JsonFilter(value="JsonPasswordManagementProperties")
public class JsonPasswordManagementProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 1129426669588789974L;
}

