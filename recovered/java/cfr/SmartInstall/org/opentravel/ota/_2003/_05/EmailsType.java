/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.EmailType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EmailsType", propOrder={"email"})
public class EmailsType {
    @XmlElement(name="Email", required=true)
    protected List<Email> email;

    public List<Email> getEmail() {
        if (this.email == null) {
            this.email = new ArrayList<Email>();
        }
        return this.email;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Email
    extends EmailType {
        @XmlAttribute(name="Removal")
        protected Boolean removal;
        @XmlAttribute(name="ID")
        protected String id;

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }
    }
}

