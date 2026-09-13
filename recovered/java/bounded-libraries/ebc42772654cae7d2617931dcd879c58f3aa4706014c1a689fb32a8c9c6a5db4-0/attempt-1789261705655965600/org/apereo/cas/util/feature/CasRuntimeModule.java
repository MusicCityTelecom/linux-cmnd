/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util.feature;

import java.io.Serializable;
import lombok.Generated;

public class CasRuntimeModule
implements Serializable {
    private static final long serialVersionUID = -1581604787854700568L;
    private final String name;
    private final String version;
    private final String description;

    @Generated
    protected CasRuntimeModule(CasRuntimeModuleBuilder<?, ?> b) {
        this.name = b.name;
        this.version = b.version;
        this.description = b.description;
    }

    @Generated
    public static CasRuntimeModuleBuilder<?, ?> builder() {
        return new CasRuntimeModuleBuilderImpl();
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CasRuntimeModule)) {
            return false;
        }
        CasRuntimeModule other = (CasRuntimeModule)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$version = this.version;
        String other$version = other.version;
        return !(this$version == null ? other$version != null : !this$version.equals(other$version));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof CasRuntimeModule;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $version = this.version;
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "CasRuntimeModule(name=" + this.name + ", version=" + this.version + ", description=" + this.description + ")";
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getVersion() {
        return this.version;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    private static final class CasRuntimeModuleBuilderImpl
    extends CasRuntimeModuleBuilder<CasRuntimeModule, CasRuntimeModuleBuilderImpl> {
        @Generated
        private CasRuntimeModuleBuilderImpl() {
        }

        @Override
        @Generated
        protected CasRuntimeModuleBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public CasRuntimeModule build() {
            return new CasRuntimeModule(this);
        }
    }

    @Generated
    public static abstract class CasRuntimeModuleBuilder<C extends CasRuntimeModule, B extends CasRuntimeModuleBuilder<C, B>> {
        @Generated
        private String name;
        @Generated
        private String version;
        @Generated
        private String description;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B name(String name) {
            this.name = name;
            return this.self();
        }

        @Generated
        public B version(String version) {
            this.version = version;
            return this.self();
        }

        @Generated
        public B description(String description) {
            this.description = description;
            return this.self();
        }

        @Generated
        public String toString() {
            return "CasRuntimeModule.CasRuntimeModuleBuilder(name=" + this.name + ", version=" + this.version + ", description=" + this.description + ")";
        }
    }
}

