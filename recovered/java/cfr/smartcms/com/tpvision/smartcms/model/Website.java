/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.tpvision.smartcms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpvision.smartcms.utils.Util;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="website")
@XmlAccessorType(value=XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement(name="websites")
public class Website
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    @NotNull
    private int id;
    private int nid;
    private int orientation;
    private String title;
    private String folderName;
    private String thumbnail;
    private String created;
    private String changed;
    private String publishDate;
    @JsonIgnore
    private String success = "0";

    protected Website() {
    }

    public String getSuccess() {
        return this.success == null ? "0" : this.success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public int getNid() {
        return this.nid;
    }

    @JsonProperty
    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getFolderName() {
        return this.folderName;
    }

    @JsonProperty
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getThumbnail() {
        return Util.getCurrentUrl() + "pages/" + this.getId() + "/" + this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getChanged() {
        return this.changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String toString() {
        return "Website [id=" + this.id + ", nid=" + this.nid + ", orientation=" + this.orientation + ", title=" + this.title + ", folderName=" + this.folderName + ", thumbnail=" + this.thumbnail + ", created=" + this.created + ", changed=" + this.changed + ", publishDate=" + this.publishDate + ", success=" + this.success + "]";
    }
}

