/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v5;

import com.tpvision.smartinstall.xml.channel.v5.App;
import com.tpvision.smartinstall.xml.channel.v5.Setup;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"app", "setup"})
@XmlRootElement(name="Application")
public class Application {
    @XmlElement(name="App", required=true)
    protected App app;
    @XmlElement(name="Setup", required=true)
    protected Setup setup;

    public App getApp() {
        return this.app;
    }

    public void setApp(App value) {
        this.app = value;
    }

    public Setup getSetup() {
        return this.setup;
    }

    public void setSetup(Setup value) {
        this.setup = value;
    }
}

