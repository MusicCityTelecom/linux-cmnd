package com.tpvision.smartinstall.xml.device;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _Type_QNAME = new QName("", "Type");
   private static final QName _SW_QNAME = new QName("", "SW");
   private static final QName _IP_QNAME = new QName("", "IP");
   private static final QName _SN_QNAME = new QName("", "SN");
   private static final QName _CTN_QNAME = new QName("", "CTN");
   private static final QName _RID_QNAME = new QName("", "RID");
   private static final QName _MAC_QNAME = new QName("", "MAC");
   private static final QName _Name_QNAME = new QName("", "Name");
   private static final QName _Clone_QNAME = new QName("", "Clone");

   public DevicesInf createDevicesInf() {
      return new DevicesInf();
   }

   public Device createDevice() {
      return new Device();
   }

   @XmlElementDecl(namespace = "", name = "Type")
   public JAXBElement<String> createType(String value) {
      return new JAXBElement<>(_Type_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "SW")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createSW(String value) {
      return new JAXBElement<>(_SW_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "IP")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createIP(String value) {
      return new JAXBElement<>(_IP_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "SN")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createSN(String value) {
      return new JAXBElement<>(_SN_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "CTN")
   public JAXBElement<String> createCTN(String value) {
      return new JAXBElement<>(_CTN_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "RID")
   public JAXBElement<BigInteger> createRID(BigInteger value) {
      return new JAXBElement<>(_RID_QNAME, BigInteger.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "MAC")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createMAC(String value) {
      return new JAXBElement<>(_MAC_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "Name")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createName(String value) {
      return new JAXBElement<>(_Name_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "Clone")
   public JAXBElement<String> createClone(String value) {
      return new JAXBElement<>(_Clone_QNAME, String.class, null, value);
   }
}
