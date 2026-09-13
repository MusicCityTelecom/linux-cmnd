package com.tpvision.smartinstall.xml.psg.catalog;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _TargetTvModel_QNAME = new QName("", "TargetTvModel");
   private static final QName _Action_QNAME = new QName("", "Action");
   private static final QName _BaseFolder_QNAME = new QName("", "BaseFolder");
   private static final QName _Requirement_QNAME = new QName("", "Requirement");
   private static final QName _String_QNAME = new QName("", "string");
   private static final QName _ID_QNAME = new QName("", "ID");
   private static final QName _PrefixFolder_QNAME = new QName("", "PrefixFolder");
   private static final QName _OutputFileName_QNAME = new QName("", "OutputFileName");
   private static final QName _Usage_QNAME = new QName("", "Usage");

   public FileSets createFileSets() {
      return new FileSets();
   }

   public FileSet createFileSet() {
      return new FileSet();
   }

   public Files createFiles() {
      return new Files();
   }

   public InterimFolder createInterimFolder() {
      return new InterimFolder();
   }

   public Entries createEntries() {
      return new Entries();
   }

   public CatalogEntry createCatalogEntry() {
      return new CatalogEntry();
   }

   public Catalog createCatalog() {
      return new Catalog();
   }

   @XmlElementDecl(namespace = "", name = "TargetTvModel")
   public JAXBElement<String> createTargetTvModel(String value) {
      return new JAXBElement<>(_TargetTvModel_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "Action")
   public JAXBElement<String> createAction(String value) {
      return new JAXBElement<>(_Action_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "BaseFolder")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public JAXBElement<String> createBaseFolder(String value) {
      return new JAXBElement<>(_BaseFolder_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "Requirement")
   public JAXBElement<String> createRequirement(String value) {
      return new JAXBElement<>(_Requirement_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "string")
   public JAXBElement<String> createString(String value) {
      return new JAXBElement<>(_String_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "ID")
   public JAXBElement<String> createID(String value) {
      return new JAXBElement<>(_ID_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "PrefixFolder")
   public JAXBElement<String> createPrefixFolder(String value) {
      return new JAXBElement<>(_PrefixFolder_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "OutputFileName")
   public JAXBElement<String> createOutputFileName(String value) {
      return new JAXBElement<>(_OutputFileName_QNAME, String.class, null, value);
   }

   @XmlElementDecl(namespace = "", name = "Usage")
   public JAXBElement<String> createUsage(String value) {
      return new JAXBElement<>(_Usage_QNAME, String.class, null, value);
   }
}
