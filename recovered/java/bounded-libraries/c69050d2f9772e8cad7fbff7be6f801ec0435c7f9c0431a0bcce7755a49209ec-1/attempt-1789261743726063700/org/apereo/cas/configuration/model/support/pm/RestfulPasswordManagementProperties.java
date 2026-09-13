/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pm-rest")
@JsonFilter(value="RestfulPasswordManagementProperties")
public class RestfulPasswordManagementProperties
implements Serializable {
    private static final long serialVersionUID = 5262948164099973872L;
    @RequiredProperty
    private String endpointUrlEmail;
    @RequiredProperty
    private String endpointUrlPhone;
    @RequiredProperty
    private String endpointUrlUser;
    @RequiredProperty
    private String endpointUrlSecurityQuestions;
    @RequiredProperty
    private String endpointUrlAccountUnlock;
    @RequiredProperty
    private String endpointUrlChange;
    @RequiredProperty
    private String endpointUsername;
    @RequiredProperty
    private String endpointPassword;
    @RequiredProperty
    private String fieldNameUser = "username";
    @RequiredProperty
    private String fieldNamePassword = "password";
    @RequiredProperty
    private String fieldNamePasswordOld = "oldPassword";

    @Generated
    public String getEndpointUrlEmail() {
        return this.endpointUrlEmail;
    }

    @Generated
    public String getEndpointUrlPhone() {
        return this.endpointUrlPhone;
    }

    @Generated
    public String getEndpointUrlUser() {
        return this.endpointUrlUser;
    }

    @Generated
    public String getEndpointUrlSecurityQuestions() {
        return this.endpointUrlSecurityQuestions;
    }

    @Generated
    public String getEndpointUrlAccountUnlock() {
        return this.endpointUrlAccountUnlock;
    }

    @Generated
    public String getEndpointUrlChange() {
        return this.endpointUrlChange;
    }

    @Generated
    public String getEndpointUsername() {
        return this.endpointUsername;
    }

    @Generated
    public String getEndpointPassword() {
        return this.endpointPassword;
    }

    @Generated
    public String getFieldNameUser() {
        return this.fieldNameUser;
    }

    @Generated
    public String getFieldNamePassword() {
        return this.fieldNamePassword;
    }

    @Generated
    public String getFieldNamePasswordOld() {
        return this.fieldNamePasswordOld;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUrlEmail(String endpointUrlEmail) {
        this.endpointUrlEmail = endpointUrlEmail;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUrlPhone(String endpointUrlPhone) {
        this.endpointUrlPhone = endpointUrlPhone;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUrlUser(String endpointUrlUser) {
        this.endpointUrlUser = endpointUrlUser;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUrlSecurityQuestions(String endpointUrlSecurityQuestions) {
        this.endpointUrlSecurityQuestions = endpointUrlSecurityQuestions;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUrlAccountUnlock(String endpointUrlAccountUnlock) {
        this.endpointUrlAccountUnlock = endpointUrlAccountUnlock;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUrlChange(String endpointUrlChange) {
        this.endpointUrlChange = endpointUrlChange;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointUsername(String endpointUsername) {
        this.endpointUsername = endpointUsername;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setEndpointPassword(String endpointPassword) {
        this.endpointPassword = endpointPassword;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setFieldNameUser(String fieldNameUser) {
        this.fieldNameUser = fieldNameUser;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setFieldNamePassword(String fieldNamePassword) {
        this.fieldNamePassword = fieldNamePassword;
        return this;
    }

    @Generated
    public RestfulPasswordManagementProperties setFieldNamePasswordOld(String fieldNamePasswordOld) {
        this.fieldNamePasswordOld = fieldNamePasswordOld;
        return this;
    }
}

