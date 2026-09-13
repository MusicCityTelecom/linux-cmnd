/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.psg.catalog;

import com.tpvision.smartinstall.xml.psg.catalog.FileSets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"targetTvModel", "baseFolder", "outputFileName", "fileSets", "action"})
@XmlRootElement(name="CatalogEntry")
public class CatalogEntry {
    @XmlElement(name="TargetTvModel", required=true)
    protected String targetTvModel;
    @XmlElement(name="BaseFolder", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NMTOKEN")
    protected String baseFolder;
    @XmlElement(name="OutputFileName", required=true)
    protected List<String> outputFileName;
    @XmlElement(name="FileSets", required=true)
    protected FileSets fileSets;
    @XmlElement(name="Action", required=true)
    protected String action;

    public String getTargetTvModel() {
        return this.targetTvModel;
    }

    public void setTargetTvModel(String value) {
        this.targetTvModel = value;
    }

    public String getBaseFolder() {
        return this.baseFolder;
    }

    public void setBaseFolder(String value) {
        this.baseFolder = value;
    }

    public List<String> getOutputFileName() {
        if (null == this.outputFileName) {
            this.outputFileName = new ArrayList<String>();
        }
        return this.outputFileName;
    }

    public FileSets getFileSets() {
        return this.fileSets;
    }

    public void setFileSets(FileSets value) {
        this.fileSets = value;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String value) {
        this.action = value;
    }
}

