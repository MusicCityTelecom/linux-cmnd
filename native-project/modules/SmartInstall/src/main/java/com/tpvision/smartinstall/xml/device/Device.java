package com.tpvision.smartinstall.xml.device;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"name", "ctn", "sn", "rid", "type", "ip", "mac", "sw", "clone"})
@XmlRootElement(name = "device")
public class Device {
   @XmlElement(name = "name", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String name;
   @XmlElement(name = "ctn", required = true)
   protected String ctn;
   @XmlElement(name = "sn", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String sn;
   @XmlElement(name = "rid", required = true)
   protected BigInteger rid;
   @XmlElement(name = "type", required = true)
   protected String type;
   @XmlElement(name = "ip", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String ip;
   @XmlElement(name = "mac", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String mac;
   @XmlElement(name = "sw", required = true)
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String sw;
   @XmlTransient
   protected String cloneIdentifier;

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getCTN() {
      return this.ctn;
   }

   public void setCTN(String value) {
      this.ctn = value;
   }

   public String getSN() {
      return this.sn;
   }

   public void setSN(String value) {
      this.sn = value;
   }

   public BigInteger getRID() {
      return this.rid;
   }

   public void setRID(BigInteger value) {
      this.rid = value;
   }

   public void setRID(String str) {
      if (StringUtils.isNumeric(str)) {
         BigInteger value = new BigInteger(str);
         this.rid = value;
      } else {
         this.rid = BigInteger.valueOf(0L);
      }
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getIP() {
      return this.ip;
   }

   public void setIP(String value) {
      this.ip = value;
   }

   public String getMAC() {
      return this.mac;
   }

   public void setMAC(String value) {
      this.mac = value;
   }

   public String getSW() {
      return this.sw;
   }

   public void setSW(String value) {
      this.sw = value;
   }

   @XmlElement(name = "clone", required = true)
   public String getClone() {
      return this.cloneIdentifier;
   }

   public void setClone(String value) {
      this.cloneIdentifier = value;
   }
}
