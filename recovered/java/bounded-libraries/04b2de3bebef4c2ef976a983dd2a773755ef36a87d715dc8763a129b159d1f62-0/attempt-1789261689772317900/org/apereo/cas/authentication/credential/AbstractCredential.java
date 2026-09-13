/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.springframework.binding.message.MessageBuilder
 *  org.springframework.binding.message.MessageContext
 *  org.springframework.binding.validation.ValidationContext
 */
package org.apereo.cas.authentication.credential;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class AbstractCredential
implements Credential,
CredentialMetaData {
    private static final long serialVersionUID = 8196868021183513898L;

    @JsonIgnore
    public Class<? extends Credential> getCredentialClass() {
        return this.getClass();
    }

    @JsonIgnore
    public boolean isValid() {
        return StringUtils.isNotBlank((CharSequence)this.getId());
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Credential)) {
            return false;
        }
        if (other == this) {
            return true;
        }
        EqualsBuilder builder = new EqualsBuilder();
        builder.append((Object)this.getId(), (Object)((Credential)other).getId());
        return builder.isEquals();
    }

    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(11, 41);
        builder.append((Object)this.getClass().getName());
        builder.append((Object)this.getId());
        return builder.toHashCode();
    }

    public void validate(ValidationContext context) {
        if (!this.isValid()) {
            MessageContext messages = context.getMessageContext();
            messages.addMessage(new MessageBuilder().error().source((Object)"token").defaultText("Unable to accept credential with an empty or unspecified token").build());
        }
    }

    @Generated
    public String toString() {
        return "AbstractCredential()";
    }
}

