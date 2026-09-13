/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v5;

import com.tpvision.smartinstall.xml.channel.v5.TTV1;
import com.tpvision.smartinstall.xml.channel.v5.TTV10;
import com.tpvision.smartinstall.xml.channel.v5.TTV2;
import com.tpvision.smartinstall.xml.channel.v5.TTV3;
import com.tpvision.smartinstall.xml.channel.v5.TTV4;
import com.tpvision.smartinstall.xml.channel.v5.TTV5;
import com.tpvision.smartinstall.xml.channel.v5.TTV6;
import com.tpvision.smartinstall.xml.channel.v5.TTV7;
import com.tpvision.smartinstall.xml.channel.v5.TTV8;
import com.tpvision.smartinstall.xml.channel.v5.TTV9;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"ttv1", "ttv2", "ttv3", "ttv4", "ttv5", "ttv6", "ttv7", "ttv8", "ttv9", "ttv10"})
@XmlRootElement(name="ThemeTV")
public class ThemeTV {
    @XmlElement(name="TTV1", required=true)
    protected TTV1 ttv1;
    @XmlElement(name="TTV2", required=true)
    protected TTV2 ttv2;
    @XmlElement(name="TTV3", required=true)
    protected TTV3 ttv3;
    @XmlElement(name="TTV4", required=true)
    protected TTV4 ttv4;
    @XmlElement(name="TTV5", required=true)
    protected TTV5 ttv5;
    @XmlElement(name="TTV6", required=true)
    protected TTV6 ttv6;
    @XmlElement(name="TTV7", required=true)
    protected TTV7 ttv7;
    @XmlElement(name="TTV8", required=true)
    protected TTV8 ttv8;
    @XmlElement(name="TTV9", required=true)
    protected TTV9 ttv9;
    @XmlElement(name="TTV10", required=true)
    protected TTV10 ttv10;

    public TTV1 getTTV1() {
        return this.ttv1;
    }

    public void setTTV1(TTV1 value) {
        this.ttv1 = value;
    }

    public TTV2 getTTV2() {
        return this.ttv2;
    }

    public void setTTV2(TTV2 value) {
        this.ttv2 = value;
    }

    public TTV3 getTTV3() {
        return this.ttv3;
    }

    public void setTTV3(TTV3 value) {
        this.ttv3 = value;
    }

    public TTV4 getTTV4() {
        return this.ttv4;
    }

    public void setTTV4(TTV4 value) {
        this.ttv4 = value;
    }

    public TTV5 getTTV5() {
        return this.ttv5;
    }

    public void setTTV5(TTV5 value) {
        this.ttv5 = value;
    }

    public TTV6 getTTV6() {
        return this.ttv6;
    }

    public void setTTV6(TTV6 value) {
        this.ttv6 = value;
    }

    public TTV7 getTTV7() {
        return this.ttv7;
    }

    public void setTTV7(TTV7 value) {
        this.ttv7 = value;
    }

    public TTV8 getTTV8() {
        return this.ttv8;
    }

    public void setTTV8(TTV8 value) {
        this.ttv8 = value;
    }

    public TTV9 getTTV9() {
        return this.ttv9;
    }

    public void setTTV9(TTV9 value) {
        this.ttv9 = value;
    }

    public TTV10 getTTV10() {
        return this.ttv10;
    }

    public void setTTV10(TTV10 value) {
        this.ttv10 = value;
    }
}

