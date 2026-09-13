package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "URLsType", propOrder = "url")
public class URLsType {
   @XmlElement(name = "URL", required = true)
   protected List<URLsType.URL> url;

   public List<URLsType.URL> getURL() {
      if (this.url == null) {
         this.url = new ArrayList<>();
      }

      return this.url;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class URL extends URLType {
      @XmlAttribute(name = "ID")
      protected String id;

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }
   }
}
