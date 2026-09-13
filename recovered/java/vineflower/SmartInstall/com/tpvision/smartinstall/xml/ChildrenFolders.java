package com.tpvision.smartinstall.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = "folder")
@XmlRootElement(name = "childrenFolders")
public class ChildrenFolders {
   protected List<Folder> folder;

   public List<Folder> getFolder() {
      if (null == this.folder) {
         this.folder = new ArrayList<>();
      }

      return this.folder;
   }
}
