/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v5;

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
    @XmlAttribute(name="FreePKG", required=true)
    protected String freePKG;
    @XmlAttribute(name="PayPKG1", required=true)
    protected String payPKG1;
    @XmlAttribute(name="PayPKG2", required=true)
    protected String payPKG2;
    @XmlAttribute(name="TTV1")
    protected String ttv1;
    @XmlAttribute(name="TTV10")
    protected String ttv10;
    @XmlAttribute(name="TTV2")
    protected String ttv2;
    @XmlAttribute(name="TTV3")
    protected String ttv3;
    @XmlAttribute(name="TTV4")
    protected String ttv4;
    @XmlAttribute(name="TTV5")
    protected String ttv5;
    @XmlAttribute(name="TTV6")
    protected String ttv6;
    @XmlAttribute(name="TTV7")
    protected String ttv7;
    @XmlAttribute(name="TTV8")
    protected String ttv8;
    @XmlAttribute(name="TTV9")
    protected String ttv9;
    @XmlAttribute(name="blank")
    protected String blank;
    @XmlAttribute(name="name", required=true)
    @XmlSchemaType(name="anySimpleType")
    protected String name;
    @XmlAttribute(name="presetnumber")
    protected String presetnumber;
    @XmlAttribute(name="skip")
    protected String skip;
    @XmlAttribute(name="logo", required=false)
    protected String logo;
    @XmlAttribute(name="SvlRecId", required=false)
    protected String svlRecId;

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

    public String getTTV1() {
        return this.ttv1;
    }

    public void setTTV1(String value) {
        this.ttv1 = value;
    }

    public String getTTV10() {
        return this.ttv10;
    }

    public void setTTV10(String value) {
        this.ttv10 = value;
    }

    public String getTTV2() {
        return this.ttv2;
    }

    public void setTTV2(String value) {
        this.ttv2 = value;
    }

    public String getTTV3() {
        return this.ttv3;
    }

    public void setTTV3(String value) {
        this.ttv3 = value;
    }

    public String getTTV4() {
        return this.ttv4;
    }

    public void setTTV4(String value) {
        this.ttv4 = value;
    }

    public String getTTV5() {
        return this.ttv5;
    }

    public void setTTV5(String value) {
        this.ttv5 = value;
    }

    public String getTTV6() {
        return this.ttv6;
    }

    public void setTTV6(String value) {
        this.ttv6 = value;
    }

    public String getTTV7() {
        return this.ttv7;
    }

    public void setTTV7(String value) {
        this.ttv7 = value;
    }

    public String getTTV8() {
        return this.ttv8;
    }

    public void setTTV8(String value) {
        this.ttv8 = value;
    }

    public String getTTV9() {
        return this.ttv9;
    }

    public void setTTV9(String value) {
        this.ttv9 = value;
    }

    public String getBlank() {
        return this.blank;
    }

    public void setBlank(String value) {
        this.blank = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getPresetnumber() {
        return this.presetnumber;
    }

    public void setPresetnumber(String value) {
        this.presetnumber = value;
    }

    public String getSkip() {
        return this.skip;
    }

    public void setSkip(String value) {
        this.skip = value;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSvlRecId() {
        return this.svlRecId;
    }

    public void setSvlRecId(String svlRecId) {
        this.svlRecId = svlRecId;
    }
}

