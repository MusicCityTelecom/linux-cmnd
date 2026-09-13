/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.remotediagnose;

import com.tpvision.smartinstall.xml.remotediagnose.Value;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"logEnableAll", "logDestination", "uploadFrequency", "logEnableCSMDump", "logEnableDiagnosticItems", "logEnableAnalyticItems", "logLevel", "crashDumpRequest", "logTypeDiagnostic"})
@XmlRootElement(name="DIAGNOSTIC_ANALYTIC")
public class DIAGNOSTICANALYTIC {
    @XmlElement(name="LogEnableAll", required=true)
    protected Value logEnableAll;
    @XmlElement(name="LogDestination", required=true)
    protected Value logDestination;
    @XmlElement(name="UploadFrequency", required=true)
    protected Value uploadFrequency;
    @XmlElement(name="LogEnableCSMDump", required=true)
    protected Value logEnableCSMDump;
    @XmlElement(name="LogEnableDiagnosticItems", required=true)
    protected Value logEnableDiagnosticItems;
    @XmlElement(name="LogEnableAnalyticItems", required=true)
    protected Value logEnableAnalyticItems;
    @XmlElement(name="LogLevel", required=true)
    protected Value logLevel;
    @XmlElement(name="CrashDumpRequest", required=true)
    protected Value crashDumpRequest;
    @XmlElement(name="LogTypeDiagnostic", required=true)
    protected Value logTypeDiagnostic;

    public Value getLogEnableAll() {
        return this.logEnableAll;
    }

    public void setLogEnableAll(Value value) {
        this.logEnableAll = value;
    }

    public Value getLogDestination() {
        return this.logDestination;
    }

    public void setLogDestination(Value value) {
        this.logDestination = value;
    }

    public Value getUploadFrequency() {
        return this.uploadFrequency;
    }

    public void setUploadFrequency(Value value) {
        this.uploadFrequency = value;
    }

    public Value getLogEnableCSMDump() {
        return this.logEnableCSMDump;
    }

    public void setLogEnableCSMDump(Value value) {
        this.logEnableCSMDump = value;
    }

    public Value getLogEnableDiagnosticItems() {
        return this.logEnableDiagnosticItems;
    }

    public void setLogEnableDiagnosticItems(Value value) {
        this.logEnableDiagnosticItems = value;
    }

    public Value getLogEnableAnalyticItems() {
        return this.logEnableAnalyticItems;
    }

    public void setLogEnableAnalyticItems(Value value) {
        this.logEnableAnalyticItems = value;
    }

    public Value getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(Value value) {
        this.logLevel = value;
    }

    public Value getCrashDumpRequest() {
        return this.crashDumpRequest;
    }

    public void setCrashDumpRequest(Value value) {
        this.crashDumpRequest = value;
    }

    public Value getLogTypeDiagnostic() {
        return this.logTypeDiagnostic;
    }

    public void setLogTypeDiagnostic(Value value) {
        this.logTypeDiagnostic = value;
    }
}

