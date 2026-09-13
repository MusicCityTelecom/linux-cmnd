/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.psg.configuration;

import com.tpvision.smartinstall.xml.psg.configuration.Config;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private static final QName _DeviceStreamType_QNAME = new QName("", "DeviceStreamType");
    private static final QName _OriginalNetworkId_QNAME = new QName("", "OriginalNetworkId");
    private static final QName _PcrPid_QNAME = new QName("", "PcrPid");
    private static final QName _OutputChannel_QNAME = new QName("", "OutputChannel");
    private static final QName _TxMode_QNAME = new QName("", "TxMode");
    private static final QName _Constellation_QNAME = new QName("", "Constellation");
    private static final QName _RoomNumber_QNAME = new QName("", "RoomNumber");
    private static final QName _DeviceCapabilityFlags_QNAME = new QName("", "DeviceCapabilityFlags");
    private static final QName _NetworkId_QNAME = new QName("", "NetworkId");
    private static final QName _TransmissionMode_QNAME = new QName("", "TransmissionMode");
    private static final QName _ModulationType_QNAME = new QName("", "ModulationType");
    private static final QName _SsbIdentifier_QNAME = new QName("", "SsbIdentifier");
    private static final QName _TransportStreamId_QNAME = new QName("", "TransportStreamId");
    private static final QName _ChannelTableIdentifier_QNAME = new QName("", "ChannelTableIdentifier");
    private static final QName _Pid_QNAME = new QName("", "Pid");
    private static final QName _BuildRootFolder_QNAME = new QName("", "BuildRootFolder");
    private static final QName _ModulationFrequency_QNAME = new QName("", "ModulationFrequency");
    private static final QName _DwnldId_QNAME = new QName("", "DwnldId");
    private static final QName _Bandwidth_QNAME = new QName("", "Bandwidth");
    private static final QName _OutputLevel_QNAME = new QName("", "OutputLevel");
    private static final QName _WorkingRootFolder_QNAME = new QName("", "WorkingRootFolder");
    private static final QName _CodeRate_QNAME = new QName("", "CodeRate");
    private static final QName _StuffMode_QNAME = new QName("", "StuffMode");
    private static final QName _Oui_QNAME = new QName("", "Oui");
    private static final QName _ProgNum_QNAME = new QName("", "ProgNum");
    private static final QName _GuardInterval_QNAME = new QName("", "GuardInterval");
    private static final QName _TxnId_QNAME = new QName("", "TxnId");

    public Config createConfig() {
        return new Config();
    }

    @XmlElementDecl(namespace="", name="DeviceStreamType")
    public JAXBElement<String> createDeviceStreamType(String value) {
        return new JAXBElement<String>(_DeviceStreamType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="OriginalNetworkId")
    public JAXBElement<String> createOriginalNetworkId(String value) {
        return new JAXBElement<String>(_OriginalNetworkId_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="PcrPid")
    public JAXBElement<String> createPcrPid(String value) {
        return new JAXBElement<String>(_PcrPid_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="OutputChannel")
    public JAXBElement<String> createOutputChannel(String value) {
        return new JAXBElement<String>(_OutputChannel_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="TxMode")
    public JAXBElement<String> createTxMode(String value) {
        return new JAXBElement<String>(_TxMode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Constellation")
    public JAXBElement<String> createConstellation(String value) {
        return new JAXBElement<String>(_Constellation_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="RoomNumber")
    public JAXBElement<String> createRoomNumber(String value) {
        return new JAXBElement<String>(_RoomNumber_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="DeviceCapabilityFlags")
    public JAXBElement<String> createDeviceCapabilityFlags(String value) {
        return new JAXBElement<String>(_DeviceCapabilityFlags_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="NetworkId")
    public JAXBElement<String> createNetworkId(String value) {
        return new JAXBElement<String>(_NetworkId_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="TransmissionMode")
    public JAXBElement<String> createTransmissionMode(String value) {
        return new JAXBElement<String>(_TransmissionMode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="ModulationType")
    public JAXBElement<String> createModulationType(String value) {
        return new JAXBElement<String>(_ModulationType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="SsbIdentifier")
    public JAXBElement<String> createSsbIdentifier(String value) {
        return new JAXBElement<String>(_SsbIdentifier_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="TransportStreamId")
    public JAXBElement<String> createTransportStreamId(String value) {
        return new JAXBElement<String>(_TransportStreamId_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="ChannelTableIdentifier")
    public JAXBElement<String> createChannelTableIdentifier(String value) {
        return new JAXBElement<String>(_ChannelTableIdentifier_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Pid")
    public JAXBElement<String> createPid(String value) {
        return new JAXBElement<String>(_Pid_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="BuildRootFolder")
    public JAXBElement<String> createBuildRootFolder(String value) {
        return new JAXBElement<String>(_BuildRootFolder_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="ModulationFrequency")
    public JAXBElement<String> createModulationFrequency(String value) {
        return new JAXBElement<String>(_ModulationFrequency_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="DwnldId")
    public JAXBElement<String> createDwnldId(String value) {
        return new JAXBElement<String>(_DwnldId_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Bandwidth")
    public JAXBElement<String> createBandwidth(String value) {
        return new JAXBElement<String>(_Bandwidth_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="OutputLevel")
    public JAXBElement<String> createOutputLevel(String value) {
        return new JAXBElement<String>(_OutputLevel_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="WorkingRootFolder")
    public JAXBElement<String> createWorkingRootFolder(String value) {
        return new JAXBElement<String>(_WorkingRootFolder_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="CodeRate")
    public JAXBElement<String> createCodeRate(String value) {
        return new JAXBElement<String>(_CodeRate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="StuffMode")
    public JAXBElement<String> createStuffMode(String value) {
        return new JAXBElement<String>(_StuffMode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Oui")
    public JAXBElement<String> createOui(String value) {
        return new JAXBElement<String>(_Oui_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="ProgNum")
    public JAXBElement<String> createProgNum(String value) {
        return new JAXBElement<String>(_ProgNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="GuardInterval")
    public JAXBElement<String> createGuardInterval(String value) {
        return new JAXBElement<String>(_GuardInterval_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="TxnId")
    public JAXBElement<String> createTxnId(String value) {
        return new JAXBElement<String>(_TxnId_QNAME, String.class, null, value);
    }
}

