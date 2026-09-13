/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.audit;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-audit", automated=true)
@JsonFilter(value="AuditEngineProperties")
public class AuditEngineProperties
implements Serializable {
    private static final long serialVersionUID = 3946106584608417663L;
    private boolean enabled = true;
    private int numberOfDaysInHistory = 30;
    private boolean includeValidationAssertion;
    private String appCode = "CAS";
    private String alternateServerAddrHeaderName;
    private String alternateClientAddrHeaderName = "X-Forwarded-For";
    private boolean useServerHostAddress;
    private boolean ignoreAuditFailures;
    private List<String> supportedActions = Stream.of("*").collect(Collectors.toList());
    private List<String> excludedActions = new ArrayList<String>();
    private AuditFormatTypes auditFormat = AuditFormatTypes.DEFAULT;
    private int abbreviationLength = 125;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public int getNumberOfDaysInHistory() {
        return this.numberOfDaysInHistory;
    }

    @Generated
    public boolean isIncludeValidationAssertion() {
        return this.includeValidationAssertion;
    }

    @Generated
    public String getAppCode() {
        return this.appCode;
    }

    @Generated
    public String getAlternateServerAddrHeaderName() {
        return this.alternateServerAddrHeaderName;
    }

    @Generated
    public String getAlternateClientAddrHeaderName() {
        return this.alternateClientAddrHeaderName;
    }

    @Generated
    public boolean isUseServerHostAddress() {
        return this.useServerHostAddress;
    }

    @Generated
    public boolean isIgnoreAuditFailures() {
        return this.ignoreAuditFailures;
    }

    @Generated
    public List<String> getSupportedActions() {
        return this.supportedActions;
    }

    @Generated
    public List<String> getExcludedActions() {
        return this.excludedActions;
    }

    @Generated
    public AuditFormatTypes getAuditFormat() {
        return this.auditFormat;
    }

    @Generated
    public int getAbbreviationLength() {
        return this.abbreviationLength;
    }

    @Generated
    public AuditEngineProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public AuditEngineProperties setNumberOfDaysInHistory(int numberOfDaysInHistory) {
        this.numberOfDaysInHistory = numberOfDaysInHistory;
        return this;
    }

    @Generated
    public AuditEngineProperties setIncludeValidationAssertion(boolean includeValidationAssertion) {
        this.includeValidationAssertion = includeValidationAssertion;
        return this;
    }

    @Generated
    public AuditEngineProperties setAppCode(String appCode) {
        this.appCode = appCode;
        return this;
    }

    @Generated
    public AuditEngineProperties setAlternateServerAddrHeaderName(String alternateServerAddrHeaderName) {
        this.alternateServerAddrHeaderName = alternateServerAddrHeaderName;
        return this;
    }

    @Generated
    public AuditEngineProperties setAlternateClientAddrHeaderName(String alternateClientAddrHeaderName) {
        this.alternateClientAddrHeaderName = alternateClientAddrHeaderName;
        return this;
    }

    @Generated
    public AuditEngineProperties setUseServerHostAddress(boolean useServerHostAddress) {
        this.useServerHostAddress = useServerHostAddress;
        return this;
    }

    @Generated
    public AuditEngineProperties setIgnoreAuditFailures(boolean ignoreAuditFailures) {
        this.ignoreAuditFailures = ignoreAuditFailures;
        return this;
    }

    @Generated
    public AuditEngineProperties setSupportedActions(List<String> supportedActions) {
        this.supportedActions = supportedActions;
        return this;
    }

    @Generated
    public AuditEngineProperties setExcludedActions(List<String> excludedActions) {
        this.excludedActions = excludedActions;
        return this;
    }

    @Generated
    public AuditEngineProperties setAuditFormat(AuditFormatTypes auditFormat) {
        this.auditFormat = auditFormat;
        return this;
    }

    @Generated
    public AuditEngineProperties setAbbreviationLength(int abbreviationLength) {
        this.abbreviationLength = abbreviationLength;
        return this;
    }

    public static enum AuditFormatTypes {
        DEFAULT,
        JSON;

    }
}

