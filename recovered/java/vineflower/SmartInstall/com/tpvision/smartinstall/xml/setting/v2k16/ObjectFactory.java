package com.tpvision.smartinstall.xml.setting.v2k16;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _Name_QNAME = new QName("", "Name");
   private static final QName _Value_QNAME = new QName("", "Value");
   private static final QName _ClonIn_QNAME = new QName("", "ClonIn");

   public SchemaVersion createSchemaVersion() {
      return new SchemaVersion();
   }

   public Item createItem() {
      return new Item();
   }

   public TVSettings createTVSettings() {
      return new TVSettings();
   }

   @XmlElementDecl(namespace = "", name = "Name")
   public JAXBElement<String> createName(String value) {
      return new JAXBElement<>(_Name_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "Value")
   public JAXBElement<BigInteger> createValue(BigInteger value) {
      return new JAXBElement<>(_Value_QNAME, BigInteger.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "ClonIn")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createClonIn(String value) {
      return new JAXBElement<>(_ClonIn_QNAME, String.class, null, value);
   }
}
