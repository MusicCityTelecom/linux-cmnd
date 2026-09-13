/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartcms.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@JsonAutoDetect
@Entity
@Table(name="publish")
@XmlRootElement(name="publish")
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Publish {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="publish_id", nullable=false, updatable=false)
    private int id;
    private int nid;
    private String title;
    private Date created;

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String toString() {
        return "Publish [id=" + this.id + ", nid=" + this.nid + ", title=" + this.title + ", created=" + this.created + "]";
    }
}

