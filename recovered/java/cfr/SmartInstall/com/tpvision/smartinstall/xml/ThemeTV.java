/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.ImageSpec;
import com.tpvision.smartinstall.xml.ThemeTVImageSpec;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"imageSpec", "themeTVImageSpec"})
@XmlRootElement(name="themeTV")
public class ThemeTV {
    protected ImageSpec imageSpec;
    protected ThemeTVImageSpec themeTVImageSpec;
    @XmlAttribute(name="folderName", required=true)
    @XmlSchemaType(name="anySimpleType")
    protected String folderName;

    public ImageSpec getImageSpec() {
        return this.imageSpec;
    }

    public void setImageSpec(ImageSpec value) {
        this.imageSpec = value;
    }

    public ThemeTVImageSpec getThemeTVImageSpec() {
        return this.themeTVImageSpec;
    }

    public void setThemeTVImageSpec(ThemeTVImageSpec value) {
        this.themeTVImageSpec = value;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String value) {
        this.folderName = value;
    }
}

