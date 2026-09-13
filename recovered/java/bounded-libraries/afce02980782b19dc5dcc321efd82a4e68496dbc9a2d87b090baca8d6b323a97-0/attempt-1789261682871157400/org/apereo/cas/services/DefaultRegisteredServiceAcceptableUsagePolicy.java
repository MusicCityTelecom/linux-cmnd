/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAcceptableUsagePolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAcceptableUsagePolicy;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceAcceptableUsagePolicy
implements RegisteredServiceAcceptableUsagePolicy {
    private static final long serialVersionUID = -1441506976879419151L;
    private boolean enabled = true;
    private String messageCode;
    private String text;

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceAcceptableUsagePolicy(enabled=" + this.enabled + ", messageCode=" + this.messageCode + ", text=" + this.text + ")";
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getMessageCode() {
        return this.messageCode;
    }

    @Generated
    public String getText() {
        return this.text;
    }

    @Generated
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Generated
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    @Generated
    public void setText(String text) {
        this.text = text;
    }

    @Generated
    public DefaultRegisteredServiceAcceptableUsagePolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceAcceptableUsagePolicy)) {
            return false;
        }
        DefaultRegisteredServiceAcceptableUsagePolicy other = (DefaultRegisteredServiceAcceptableUsagePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        String this$messageCode = this.messageCode;
        String other$messageCode = other.messageCode;
        if (this$messageCode == null ? other$messageCode != null : !this$messageCode.equals(other$messageCode)) {
            return false;
        }
        String this$text = this.text;
        String other$text = other.text;
        return !(this$text == null ? other$text != null : !this$text.equals(other$text));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceAcceptableUsagePolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.enabled ? 79 : 97);
        String $messageCode = this.messageCode;
        result = result * 59 + ($messageCode == null ? 43 : $messageCode.hashCode());
        String $text = this.text;
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        return result;
    }
}

