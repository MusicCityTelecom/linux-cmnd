/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  javax.persistence.Column
 *  javax.persistence.Embeddable
 *  javax.persistence.Table
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceContact
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceContact;

@Embeddable
@Table(name="RegisteredServiceImplContact")
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceContact
implements RegisteredServiceContact {
    private static final long serialVersionUID = 1324660891900737066L;
    @Column(nullable=false)
    private String name;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String department;
    @Column
    private String type;

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceContact(name=" + this.name + ", email=" + this.email + ", phone=" + this.phone + ", department=" + this.department + ", type=" + this.type + ")";
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getEmail() {
        return this.email;
    }

    @Generated
    public String getPhone() {
        return this.phone;
    }

    @Generated
    public String getDepartment() {
        return this.department;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public DefaultRegisteredServiceContact setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceContact setEmail(String email) {
        this.email = email;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceContact setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceContact setDepartment(String department) {
        this.department = department;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceContact setType(String type) {
        this.type = type;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceContact() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceContact)) {
            return false;
        }
        DefaultRegisteredServiceContact other = (DefaultRegisteredServiceContact)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$email = this.email;
        String other$email = other.email;
        return !(this$email == null ? other$email != null : !this$email.equals(other$email));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceContact;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $email = this.email;
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        return result;
    }
}

