/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.validation.constraints.Size
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.springframework.binding.message.MessageBuilder
 *  org.springframework.binding.message.MessageContext
 *  org.springframework.binding.validation.ValidationContext
 */
package org.apereo.cas.authentication.credential;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.constraints.Size;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.credential.AbstractCredential;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

public class UsernamePasswordCredential
extends AbstractCredential {
    public static final String AUTHENTICATION_ATTRIBUTE_PASSWORD = "credential";
    private static final long serialVersionUID = -700605081472810939L;
    @Size(min=1, message="username.required")
    private @Size(min=1, message="username.required") String username;
    @JsonIgnore
    @Size(min=1, message="password.required")
    private @Size(min=1, message="password.required") char[] password;
    private String source;
    private Map<String, Object> customFields = new LinkedHashMap<String, Object>();

    public UsernamePasswordCredential(String username, String password) {
        this.username = username;
        this.assignPassword(StringUtils.defaultString((String)password));
    }

    public String getId() {
        return this.username;
    }

    @Override
    public void validate(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (!context.getUserEvent().equalsIgnoreCase("submit") || messages.hasErrorMessages()) {
            return;
        }
        ApplicationContextProvider.getCasConfigurationProperties().ifPresent(props -> {
            if (StringUtils.isBlank((CharSequence)this.source) && props.getAuthn().getPolicy().isSourceSelectionEnabled()) {
                messages.addMessage(new MessageBuilder().error().source((Object)"source").defaultText("Required Source").code("source.required").build());
            }
        });
        super.validate(context);
    }

    public String toPassword() {
        return (String)FunctionUtils.doIfNull((Object)this.password, () -> null, () -> new String(this.password)).get();
    }

    public void assignPassword(String password) {
        FunctionUtils.doIfNotNull((Object)password, p -> {
            this.password = new char[p.length()];
            System.arraycopy(password.toCharArray(), 0, this.password, 0, password.length());
        }, p -> {
            this.password = ArrayUtils.EMPTY_CHAR_ARRAY;
        });
    }

    @Override
    @Generated
    public String toString() {
        return "UsernamePasswordCredential(username=" + this.username + ", source=" + this.source + ", customFields=" + this.customFields + ")";
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public char[] getPassword() {
        return this.password;
    }

    @Generated
    public String getSource() {
        return this.source;
    }

    @Generated
    public Map<String, Object> getCustomFields() {
        return this.customFields;
    }

    @Generated
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Generated
    public void setPassword(char[] password) {
        this.password = password;
    }

    @Generated
    public void setSource(String source) {
        this.source = source;
    }

    @Generated
    public void setCustomFields(Map<String, Object> customFields) {
        this.customFields = customFields;
    }

    @Generated
    public UsernamePasswordCredential() {
    }

    @Generated
    public UsernamePasswordCredential(String username, char[] password, String source, Map<String, Object> customFields) {
        this.username = username;
        this.password = password;
        this.source = source;
        this.customFields = customFields;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UsernamePasswordCredential)) {
            return false;
        }
        UsernamePasswordCredential other = (UsernamePasswordCredential)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$username = this.username;
        String other$username = other.username;
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        if (!Arrays.equals(this.password, other.password)) {
            return false;
        }
        String this$source = this.source;
        String other$source = other.source;
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) {
            return false;
        }
        Map<String, Object> this$customFields = this.customFields;
        Map<String, Object> other$customFields = other.customFields;
        return !(this$customFields == null ? other$customFields != null : !((Object)this$customFields).equals(other$customFields));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof UsernamePasswordCredential;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $username = this.username;
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        result = result * 59 + Arrays.hashCode(this.password);
        String $source = this.source;
        result = result * 59 + ($source == null ? 43 : $source.hashCode());
        Map<String, Object> $customFields = this.customFields;
        result = result * 59 + ($customFields == null ? 43 : ((Object)$customFields).hashCode());
        return result;
    }
}

