/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_ProfileMessageSummaryType")
public class HTNGProfileMessageSummaryType {
    @XmlAttribute(name="Image")
    protected Integer image;
    @XmlAttribute(name="Text")
    protected Integer text;
    @XmlAttribute(name="Video")
    protected Integer video;
    @XmlAttribute(name="Other")
    protected Integer other;

    public int getImage() {
        if (this.image == null) {
            return 0;
        }
        return this.image;
    }

    public void setImage(Integer value) {
        this.image = value;
    }

    public int getText() {
        if (this.text == null) {
            return 0;
        }
        return this.text;
    }

    public void setText(Integer value) {
        this.text = value;
    }

    public int getVideo() {
        if (this.video == null) {
            return 0;
        }
        return this.video;
    }

    public void setVideo(Integer value) {
        this.video = value;
    }

    public int getOther() {
        if (this.other == null) {
            return 0;
        }
        return this.other;
    }

    public void setOther(Integer value) {
        this.other = value;
    }
}

