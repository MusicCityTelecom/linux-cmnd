/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.configuration.model.support.email;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="EmailProperties")
public class EmailProperties
implements Serializable {
    private static final long serialVersionUID = 7367120636536230761L;
    @RequiredProperty
    private String attributeName = "mail";
    private String text;
    @RequiredProperty
    private String from;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String subject;
    private List<String> cc = new ArrayList<String>();
    private List<String> bcc = new ArrayList<String>();
    private String replyTo;
    private boolean html;
    private boolean validateAddresses;
    private int priority = 1;

    @JsonIgnore
    public boolean isUndefined() {
        return StringUtils.isBlank((CharSequence)this.text) || StringUtils.isBlank((CharSequence)this.from) || StringUtils.isBlank((CharSequence)this.subject);
    }

    @JsonIgnore
    public boolean isDefined() {
        return !this.isUndefined();
    }

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public String getText() {
        return this.text;
    }

    @Generated
    public String getFrom() {
        return this.from;
    }

    @Generated
    public String getSubject() {
        return this.subject;
    }

    @Generated
    public List<String> getCc() {
        return this.cc;
    }

    @Generated
    public List<String> getBcc() {
        return this.bcc;
    }

    @Generated
    public String getReplyTo() {
        return this.replyTo;
    }

    @Generated
    public boolean isHtml() {
        return this.html;
    }

    @Generated
    public boolean isValidateAddresses() {
        return this.validateAddresses;
    }

    @Generated
    public int getPriority() {
        return this.priority;
    }

    @Generated
    public EmailProperties setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Generated
    public EmailProperties setText(String text) {
        this.text = text;
        return this;
    }

    @Generated
    public EmailProperties setFrom(String from) {
        this.from = from;
        return this;
    }

    @Generated
    public EmailProperties setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @Generated
    public EmailProperties setCc(List<String> cc) {
        this.cc = cc;
        return this;
    }

    @Generated
    public EmailProperties setBcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    @Generated
    public EmailProperties setReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    @Generated
    public EmailProperties setHtml(boolean html) {
        this.html = html;
        return this;
    }

    @Generated
    public EmailProperties setValidateAddresses(boolean validateAddresses) {
        this.validateAddresses = validateAddresses;
        return this;
    }

    @Generated
    public EmailProperties setPriority(int priority) {
        this.priority = priority;
        return this;
    }
}

