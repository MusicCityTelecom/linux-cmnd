/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.psg.catalog;

import com.tpvision.smartinstall.xml.psg.catalog.FileSet;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"fileSet"})
@XmlRootElement(name="FileSets")
public class FileSets {
    @XmlElement(name="FileSet", required=true)
    protected List<FileSet> fileSet;

    public List<FileSet> getFileSet() {
        if (null == this.fileSet) {
            this.fileSet = new ArrayList<FileSet>();
        }
        return this.fileSet;
    }
}

