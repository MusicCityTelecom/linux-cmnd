/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder
 *  lombok.Generated
 */
package org.apereo.cas.authentication.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(builder=MultifactorAuthenticationRegisteredDeviceBuilderImpl.class)
public class MultifactorAuthenticationRegisteredDevice
implements Serializable {
    private static final long serialVersionUID = 1040948239519297651L;
    private final String name;
    private final String type;
    private final String id;
    private final String number;
    private final String model;
    private final String lastUsedDateTime;
    private Map<String, Object> details;
    private final String payload;
    private final String source;

    @Generated
    private static Map<String, Object> $default$details() {
        return new LinkedHashMap<String, Object>();
    }

    @Generated
    protected MultifactorAuthenticationRegisteredDevice(MultifactorAuthenticationRegisteredDeviceBuilder<?, ?> b) {
        this.name = b.name;
        this.type = b.type;
        this.id = b.id;
        this.number = b.number;
        this.model = b.model;
        this.lastUsedDateTime = b.lastUsedDateTime;
        this.details = b.details$set ? b.details$value : MultifactorAuthenticationRegisteredDevice.$default$details();
        this.payload = b.payload;
        this.source = b.source;
    }

    @Generated
    public static MultifactorAuthenticationRegisteredDeviceBuilder<?, ?> builder() {
        return new MultifactorAuthenticationRegisteredDeviceBuilderImpl();
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getNumber() {
        return this.number;
    }

    @Generated
    public String getModel() {
        return this.model;
    }

    @Generated
    public String getLastUsedDateTime() {
        return this.lastUsedDateTime;
    }

    @Generated
    public Map<String, Object> getDetails() {
        return this.details;
    }

    @Generated
    public String getPayload() {
        return this.payload;
    }

    @Generated
    public String getSource() {
        return this.source;
    }

    @Generated
    public String toString() {
        return "MultifactorAuthenticationRegisteredDevice(name=" + this.name + ", type=" + this.type + ", id=" + this.id + ", model=" + this.model + ")";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MultifactorAuthenticationRegisteredDevice)) {
            return false;
        }
        MultifactorAuthenticationRegisteredDevice other = (MultifactorAuthenticationRegisteredDevice)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$type = this.type;
        String other$type = other.type;
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$model = this.model;
        String other$model = other.model;
        return !(this$model == null ? other$model != null : !this$model.equals(other$model));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof MultifactorAuthenticationRegisteredDevice;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.type;
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $model = this.model;
        result = result * 59 + ($model == null ? 43 : $model.hashCode());
        return result;
    }

    @Generated
    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    @Generated
    public MultifactorAuthenticationRegisteredDevice() {
        this.name = null;
        this.type = null;
        this.id = null;
        this.number = null;
        this.model = null;
        this.lastUsedDateTime = null;
        this.payload = null;
        this.source = null;
        this.details = MultifactorAuthenticationRegisteredDevice.$default$details();
    }

    @JsonPOJOBuilder(withPrefix="", buildMethodName="build")
    @Generated
    static final class MultifactorAuthenticationRegisteredDeviceBuilderImpl
    extends MultifactorAuthenticationRegisteredDeviceBuilder<MultifactorAuthenticationRegisteredDevice, MultifactorAuthenticationRegisteredDeviceBuilderImpl> {
        @Generated
        private MultifactorAuthenticationRegisteredDeviceBuilderImpl() {
        }

        @Override
        @Generated
        protected MultifactorAuthenticationRegisteredDeviceBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public MultifactorAuthenticationRegisteredDevice build() {
            return new MultifactorAuthenticationRegisteredDevice(this);
        }
    }

    @Generated
    public static abstract class MultifactorAuthenticationRegisteredDeviceBuilder<C extends MultifactorAuthenticationRegisteredDevice, B extends MultifactorAuthenticationRegisteredDeviceBuilder<C, B>> {
        @Generated
        private String name;
        @Generated
        private String type;
        @Generated
        private String id;
        @Generated
        private String number;
        @Generated
        private String model;
        @Generated
        private String lastUsedDateTime;
        @Generated
        private boolean details$set;
        @Generated
        private Map<String, Object> details$value;
        @Generated
        private String payload;
        @Generated
        private String source;

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
        public B type(String type) {
            this.type = type;
            return this.self();
        }

        @Generated
        public B id(String id) {
            this.id = id;
            return this.self();
        }

        @Generated
        public B number(String number) {
            this.number = number;
            return this.self();
        }

        @Generated
        public B model(String model) {
            this.model = model;
            return this.self();
        }

        @Generated
        public B lastUsedDateTime(String lastUsedDateTime) {
            this.lastUsedDateTime = lastUsedDateTime;
            return this.self();
        }

        @Generated
        public B details(Map<String, Object> details) {
            this.details$value = details;
            this.details$set = true;
            return this.self();
        }

        @Generated
        public B payload(String payload) {
            this.payload = payload;
            return this.self();
        }

        @Generated
        public B source(String source) {
            this.source = source;
            return this.self();
        }

        @Generated
        public String toString() {
            return "MultifactorAuthenticationRegisteredDevice.MultifactorAuthenticationRegisteredDeviceBuilder(name=" + this.name + ", type=" + this.type + ", id=" + this.id + ", number=" + this.number + ", model=" + this.model + ", lastUsedDateTime=" + this.lastUsedDateTime + ", details$value=" + this.details$value + ", payload=" + this.payload + ", source=" + this.source + ")";
        }
    }
}

