package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"childrenFiles", "childrenFolders"})
@XmlRootElement(name = "folder")
public class Folder {
   protected ChildrenFiles childrenFiles;
   protected List<ChildrenFolders> childrenFolders;
   @XmlAttribute(name = "name", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   @XmlSchemaType(name = "NCName")
   protected String name;

   public ChildrenFiles getChildrenFiles() {
      return this.childrenFiles;
   }

   public void setChildrenFiles(ChildrenFiles value) {
      this.childrenFiles = value;
   }

   public List<ChildrenFolders> getChildrenFolders() {
      if (null == this.childrenFolders) {
         this.childrenFolders = new ArrayList<>();
      }

      return this.childrenFolders;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }
}
