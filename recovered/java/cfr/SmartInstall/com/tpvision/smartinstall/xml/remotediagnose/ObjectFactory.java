/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.remotediagnose;

import com.tpvision.smartinstall.xml.remotediagnose.DIAGNOSTICANALYTIC;
import com.tpvision.smartinstall.xml.remotediagnose.Value;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private static final QName _CrashDumpRequest_QNAME = new QName("", "CrashDumpRequest");
    private static final QName _Value_QNAME = new QName("", "Value");
    private static final QName _LogLevel_QNAME = new QName("", "LogLevel");
    private static final QName _UploadFrequency_QNAME = new QName("", "UploadFrequency");
    private static final QName _LogEnableDiagnosticItems_QNAME = new QName("", "LogEnableDiagnosticItems");
    private static final QName _LogEnableCSMDump_QNAME = new QName("", "LogEnableCSMDump");
    private static final QName _LogTypeDiagnostic_QNAME = new QName("", "LogTypeDiagnostic");
    private static final QName _LogDestination_QNAME = new QName("", "LogDestination");
    private static final QName _LogEnableAll_QNAME = new QName("", "LogEnableAll");
    private static final QName _LogEnableAnalyticItems_QNAME = new QName("", "LogEnableAnalyticItems");

    public Value createValue() {
        return new Value();
    }

    public DIAGNOSTICANALYTIC createDIAGNOSTICANALYTIC() {
        return new DIAGNOSTICANALYTIC();
    }

    @XmlElementDecl(namespace="", name="CrashDumpRequest")
    public JAXBElement<Value> createCrashDumpRequest(Value value) {
        return new JAXBElement<Value>(_CrashDumpRequest_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogLevel")
    public JAXBElement<Value> createLogLevel(Value value) {
        return new JAXBElement<Value>(_LogLevel_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="UploadFrequency")
    public JAXBElement<Value> createUploadFrequency(Value value) {
        return new JAXBElement<Value>(_UploadFrequency_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogEnableDiagnosticItems")
    public JAXBElement<Value> createLogEnableDiagnosticItems(Value value) {
        return new JAXBElement<Value>(_LogEnableDiagnosticItems_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogEnableCSMDump")
    public JAXBElement<Value> createLogEnableCSMDump(Value value) {
        return new JAXBElement<Value>(_LogEnableCSMDump_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogTypeDiagnostic")
    public JAXBElement<Value> createLogTypeDiagnostic(Value value) {
        return new JAXBElement<Value>(_LogTypeDiagnostic_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogDestination")
    public JAXBElement<Value> createLogDestination(Value value) {
        return new JAXBElement<Value>(_LogDestination_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogEnableAll")
    public JAXBElement<Value> createLogEnableAll(Value value) {
        return new JAXBElement<Value>(_LogEnableAll_QNAME, Value.class, null, value);
    }

    @XmlElementDecl(namespace="", name="LogEnableAnalyticItems")
    public JAXBElement<Value> createLogEnableAnalyticItems(Value value) {
        return new JAXBElement<Value>(_LogEnableAnalyticItems_QNAME, Value.class, null, value);
    }
}

