/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.JAXBElement
 *  javax.xml.bind.annotation.XmlElementDecl
 *  javax.xml.bind.annotation.XmlRegistry
 */
package org.apereo.services.persondir.support.xml.om;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apereo.services.persondir.support.xml.om.Attribute;
import org.apereo.services.persondir.support.xml.om.Person;
import org.apereo.services.persondir.support.xml.om.PersonData;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Value_QNAME = new QName("", "value");

    public Attribute createAttribute() {
        return new Attribute();
    }

    public Person createPerson() {
        return new Person();
    }

    public PersonData createPersonData() {
        return new PersonData();
    }

    @XmlElementDecl(namespace="", name="value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement(_Value_QNAME, String.class, null, (Object)value);
    }
}

