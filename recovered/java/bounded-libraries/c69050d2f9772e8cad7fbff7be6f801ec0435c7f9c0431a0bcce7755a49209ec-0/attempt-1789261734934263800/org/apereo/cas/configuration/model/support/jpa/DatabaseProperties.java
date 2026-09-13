/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.util.LinkedCaseInsensitiveMap
 */
package org.apereo.cas.configuration.model.support.jpa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.util.LinkedCaseInsensitiveMap;

@RequiresModule(name="cas-server-support-jdbc-drivers")
@JsonFilter(value="DatabaseProperties")
public class DatabaseProperties
implements Serializable {
    private static final long serialVersionUID = 7740236971148591965L;
    private boolean showSql;
    private boolean genDdl = true;
    private boolean caseInsensitive;
    private Map<String, String> physicalTableNames = new LinkedCaseInsensitiveMap();

    @Generated
    public boolean isShowSql() {
        return this.showSql;
    }

    @Generated
    public boolean isGenDdl() {
        return this.genDdl;
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Generated
    public Map<String, String> getPhysicalTableNames() {
        return this.physicalTableNames;
    }

    @Generated
    public DatabaseProperties setShowSql(boolean showSql) {
        this.showSql = showSql;
        return this;
    }

    @Generated
    public DatabaseProperties setGenDdl(boolean genDdl) {
        this.genDdl = genDdl;
        return this;
    }

    @Generated
    public DatabaseProperties setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    @Generated
    public DatabaseProperties setPhysicalTableNames(Map<String, String> physicalTableNames) {
        this.physicalTableNames = physicalTableNames;
        return this;
    }
}

