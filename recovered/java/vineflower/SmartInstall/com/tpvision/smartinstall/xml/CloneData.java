package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"folder", "crcFiles"})
@XmlRootElement(name = "cloneData")
public class CloneData {
   @XmlElement(required = true)
   protected Folder folder;
   @XmlElement(required = true)
   protected CrcFiles crcFiles;

   public Folder getFolder() {
      return this.folder;
   }

   public void setFolder(Folder value) {
      this.folder = value;
   }

   public CrcFiles getCrcFiles() {
      return this.crcFiles;
   }

   public void setCrcFiles(CrcFiles value) {
      this.crcFiles = value;
   }
}
