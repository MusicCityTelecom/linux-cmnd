/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.Setting;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"setting"})
@XmlRootElement(name="settings")
public class Settings {
    protected List<Setting> setting;

    public List<Setting> getSetting() {
        if (null == this.setting) {
            this.setting = new ArrayList<Setting>();
        }
        return this.setting;
    }
}

