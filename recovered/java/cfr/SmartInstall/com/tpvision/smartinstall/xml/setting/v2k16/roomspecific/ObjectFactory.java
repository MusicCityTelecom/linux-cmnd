/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.setting.v2k16.roomspecific;

import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.SchemaVersion;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.TV;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private static final QName _SerialNumber_QNAME = new QName("", "SerialNumber");
    private static final QName _Name_QNAME = new QName("", "Name");
    private static final QName _Value_QNAME = new QName("", "Value");

    public RoomSpecificSettings createRoomSpecificSettings() {
        return new RoomSpecificSettings();
    }

    public SchemaVersion createSchemaVersion() {
        return new SchemaVersion();
    }

    public TV createTV() {
        return new TV();
    }

    public Item createItem() {
        return new Item();
    }

    @XmlElementDecl(namespace="", name="SerialNumber")
    public JAXBElement<BigInteger> createSerialNumber(BigInteger value) {
        return new JAXBElement<BigInteger>(_SerialNumber_QNAME, BigInteger.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="", name="Value")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }
}

