/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package org.apereo.services.persondir.support.xml.om;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apereo.services.persondir.support.xml.om.Person;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"person"})
@XmlRootElement(name="person-data")
public class PersonData {
    protected List<Person> person;

    public List<Person> getPerson() {
        if (this.person == null) {
            this.person = new ArrayList<Person>();
        }
        return this.person;
    }
}

