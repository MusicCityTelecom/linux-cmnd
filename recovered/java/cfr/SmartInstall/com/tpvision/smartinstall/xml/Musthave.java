/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"file"})
@XmlRootElement(name="musthave")
public class Musthave {
    @XmlElement(required=true)
    protected List<File> file;

    public List<File> getFile() {
        if (null == this.file) {
            this.file = new ArrayList<File>();
        }
        return this.file;
    }
}

