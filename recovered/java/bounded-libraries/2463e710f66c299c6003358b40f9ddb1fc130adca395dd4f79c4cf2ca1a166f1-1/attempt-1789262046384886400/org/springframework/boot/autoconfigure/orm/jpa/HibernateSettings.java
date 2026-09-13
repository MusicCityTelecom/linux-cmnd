/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

public class HibernateSettings {
    private Supplier<String> ddlAuto;
    private Collection<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

    public HibernateSettings ddlAuto(Supplier<String> ddlAuto) {
        this.ddlAuto = ddlAuto;
        return this;
    }

    public String getDdlAuto() {
        return this.ddlAuto != null ? this.ddlAuto.get() : null;
    }

    public HibernateSettings hibernatePropertiesCustomizers(Collection<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        this.hibernatePropertiesCustomizers = new ArrayList<HibernatePropertiesCustomizer>(hibernatePropertiesCustomizers);
        return this;
    }

    public Collection<HibernatePropertiesCustomizer> getHibernatePropertiesCustomizers() {
        return this.hibernatePropertiesCustomizers;
    }
}

