/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="Setup")
public class Setup {
    @XmlAttribute(name="presetnumber")
    protected String presetnumber;
    @XmlAttribute(name="name", required=true)
    @XmlSchemaType(name="anySimpleType")
    protected String name;
    @XmlAttribute(name="blank")
    protected String blank;
    @XmlAttribute(name="skip")
    protected String skip;
    @XmlAttribute(name="FreePKG", required=true)
    protected String freePKG;
    @XmlAttribute(name="PayPKG1", required=true)
    protected String payPKG1;
    @XmlAttribute(name="PayPKG2", required=true)
    protected String payPKG2;
    @XmlAttribute(name="logo", required=false)
    protected String logo;

    public String getPresetnumber() {
        return this.presetnumber;
    }

    public void setPresetnumber(String value) {
        this.presetnumber = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getBlank() {
        return this.blank;
    }

    public void setBlank(String value) {
        this.blank = value;
    }

    public String getSkip() {
        return this.skip;
    }

    public void setSkip(String value) {
        this.skip = value;
    }

    public String getFreePKG() {
        return this.freePKG;
    }

    public void setFreePKG(String value) {
        this.freePKG = value;
    }

    public String getPayPKG1() {
        return this.payPKG1;
    }

    public void setPayPKG1(String value) {
        this.payPKG1 = value;
    }

    public String getPayPKG2() {
        return this.payPKG2;
    }

    public void setPayPKG2(String value) {
        this.payPKG2 = value;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}

