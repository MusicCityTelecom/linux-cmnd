/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v4;

import com.tpvision.smartinstall.xml.channel.v4.Extension;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"extension"})
@XmlRootElement(name="ExtensionMap")
public class ExtensionMap {
    @XmlElement(name="Extension", required=true)
    protected List<Extension> extension;

    public List<Extension> getExtension() {
        if (null == this.extension) {
            this.extension = new ArrayList<Extension>();
        }
        return this.extension;
    }
}

