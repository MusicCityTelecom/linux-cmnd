package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.xml.psg.catalog.Catalog;
import com.tpvision.smartinstall.xml.psg.catalog.CatalogEntry;
import com.tpvision.smartinstall.xml.psg.catalog.Entries;
import com.tpvision.smartinstall.xml.psg.catalog.FileSet;
import com.tpvision.smartinstall.xml.psg.catalog.FileSets;
import com.tpvision.smartinstall.xml.psg.catalog.Files;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PSGCatalogGenerator {
   private static final Logger LOG = LoggerFactory.getLogger(PSGCatalogGenerator.class);

   public static void generateCatalog(String platformId, String versionFolder, HashMap<String, String> output) {
      PSGCatalogGeneratorService srv = PSGCatalogGenerator.CatalogFactory.get(platformId, versionFolder, output);
      if (null != srv) {
         srv.generate(platformId);
      }
   }

   public static void GenMediaEasySuite_2K15_MediaSuite(String productName, String upgPath, String versionFolder, String hasPlayOut) {
      if (hasPlayOut.equalsIgnoreCase("CF")) {
         Catalog catalog = new Catalog();
         Entries entries = new Entries();
         CatalogEntry catalogEntry = new CatalogEntry();
         catalogEntry.setAction("GenerateAndMerge");
         catalogEntry.setBaseFolder(productName);
         catalogEntry.setTargetTvModel("2k11 Easy Suite");
         catalogEntry.getOutputFileName().add(productName + ".ts");
         FileSets fileSets = new FileSets();
         FileSet fileSet1 = new FileSet();
         fileSet1.setRequirement("Optional");
         fileSet1.setPrefixFolder(upgPath + "FEEF");
         Files files = new Files();
         files.getString().add("oad.upg");
         fileSet1.setFiles(files);
         fileSet1.setID("Easy2K11Clone");
         fileSet1.setUsage("UseFirstFile");
         fileSets.getFileSet().add(fileSet1);
         catalogEntry.setFileSets(fileSets);
         entries.getCatalogEntry().add(catalogEntry);
         catalog.setEntries(entries);
         catalogFileGen(catalog);
      } else if (hasPlayOut.equalsIgnoreCase("F")) {
         Catalog catalog = new Catalog();
         Entries entries = new Entries();
         CatalogEntry catalogEntry = new CatalogEntry();
         catalogEntry.setAction("GenerateAndMerge");
         catalogEntry.setBaseFolder(productName);
         catalogEntry.setTargetTvModel("2k11 Easy Suite");
         catalogEntry.getOutputFileName().add(productName + "_Firmware.ts");
         FileSets fileSets = new FileSets();
         if (null != versionFolder && !versionFolder.equalsIgnoreCase("0")) {
            FileSet fileSet2 = new FileSet();
            fileSet2.setRequirement("Optional");
            fileSet2.setPrefixFolder(upgPath + versionFolder);
            Files files1 = new Files();
            files1.getString().add("oad.upg");
            fileSet2.setFiles(files1);
            fileSet2.setID("Easy2K11Firmware");
            fileSet2.setUsage("UseFirstFile");
            fileSets.getFileSet().add(fileSet2);
         }

         catalogEntry.setFileSets(fileSets);
         entries.getCatalogEntry().add(catalogEntry);
         catalog.setEntries(entries);
         catalogFileGen(catalog);
      } else if (hasPlayOut.equalsIgnoreCase("C")) {
         Catalog catalog = new Catalog();
         Entries entries = new Entries();
         CatalogEntry catalogEntry = new CatalogEntry();
         catalogEntry.setAction("GenerateAndMerge");
         catalogEntry.setBaseFolder(productName);
         catalogEntry.setTargetTvModel("2k11 Easy Suite");
         catalogEntry.getOutputFileName().add(productName + "_Clone.ts");
         FileSets fileSets = new FileSets();
         FileSet fileSet1 = new FileSet();
         fileSet1.setRequirement("Optional");
         fileSet1.setPrefixFolder(upgPath + "FEEF");
         Files files = new Files();
         files.getString().add("Autorun_RF_Cloning.upg");
         fileSet1.setFiles(files);
         fileSet1.setID("Easy2K11Clone");
         fileSet1.setUsage("UseFirstFile");
         fileSets.getFileSet().add(fileSet1);
         catalogEntry.setFileSets(fileSets);
         entries.getCatalogEntry().add(catalogEntry);
         catalog.setEntries(entries);
         catalogFileGen(catalog);
      }
   }

   public static void GenMediaEasySuite(String productName, String upgPath, String versionFolder, String hasPlayOut) {
      if (productName.contains("2K15_MediaSuite")) {
         GenMediaEasySuite_2K15_MediaSuite(productName, upgPath, versionFolder, hasPlayOut);
      } else if (hasPlayOut.equalsIgnoreCase("CF")) {
         Catalog catalog = new Catalog();
         Entries entries = new Entries();
         CatalogEntry catalogEntry = new CatalogEntry();
         catalogEntry.setAction("GenerateAndMerge");
         catalogEntry.setBaseFolder(productName);
         catalogEntry.setTargetTvModel("2k11 Easy Suite");
         catalogEntry.getOutputFileName().add(productName + ".ts");
         FileSets fileSets = new FileSets();
         if (null != versionFolder && !versionFolder.equalsIgnoreCase("0")) {
            FileSet fileSet2 = new FileSet();
            fileSet2.setRequirement("Optional");
            fileSet2.setPrefixFolder(upgPath + versionFolder);
            Files files1 = new Files();
            files1.getString().add("autorun.upg");
            fileSet2.setFiles(files1);
            fileSet2.setID("Easy2K11Firmware");
            fileSet2.setUsage("UseFirstFile");
            fileSets.getFileSet().add(fileSet2);
         }

         FileSet fileSet1 = new FileSet();
         fileSet1.setRequirement("Optional");
         fileSet1.setPrefixFolder(upgPath + "FEEF");
         Files files = new Files();
         if ("2K16_EasySuite".equalsIgnoreCase(productName)) {
            files.getString().add("MasterCloneData.zip");
         } else {
            files.getString().add("Autorun_RF_Cloning.upg");
         }

         fileSet1.setFiles(files);
         fileSet1.setID("Easy2K11Clone");
         fileSet1.setUsage("UseFirstFile");
         fileSets.getFileSet().add(fileSet1);
         catalogEntry.setFileSets(fileSets);
         entries.getCatalogEntry().add(catalogEntry);
         catalog.setEntries(entries);
         catalogFileGen(catalog);
      } else if (hasPlayOut.equalsIgnoreCase("F")) {
         Catalog catalog = new Catalog();
         Entries entries = new Entries();
         CatalogEntry catalogEntry = new CatalogEntry();
         catalogEntry.setAction("GenerateAndMerge");
         catalogEntry.setBaseFolder(productName);
         catalogEntry.setTargetTvModel("2k11 Easy Suite");
         catalogEntry.getOutputFileName().add(productName + "_Firmware.ts");
         FileSets fileSets = new FileSets();
         if (null != versionFolder && !versionFolder.equalsIgnoreCase("0")) {
            FileSet fileSet2 = new FileSet();
            fileSet2.setRequirement("Optional");
            fileSet2.setPrefixFolder(upgPath + versionFolder);
            Files files1 = new Files();
            files1.getString().add("autorun.upg");
            fileSet2.setFiles(files1);
            fileSet2.setID("Easy2K11Firmware");
            fileSet2.setUsage("UseFirstFile");
            fileSets.getFileSet().add(fileSet2);
         }

         catalogEntry.setFileSets(fileSets);
         entries.getCatalogEntry().add(catalogEntry);
         catalog.setEntries(entries);
         catalogFileGen(catalog);
      } else if (hasPlayOut.equalsIgnoreCase("C")) {
         Catalog catalog = new Catalog();
         Entries entries = new Entries();
         CatalogEntry catalogEntry = new CatalogEntry();
         catalogEntry.setAction("GenerateAndMerge");
         catalogEntry.setBaseFolder(productName);
         catalogEntry.setTargetTvModel("2k11 Easy Suite");
         catalogEntry.getOutputFileName().add(productName + "_Clone.ts");
         FileSets fileSets = new FileSets();
         FileSet fileSet1 = new FileSet();
         fileSet1.setRequirement("Optional");
         fileSet1.setPrefixFolder(upgPath + "FEEF");
         Files files = new Files();
         if ("2K16_EasySuite".equalsIgnoreCase(productName)) {
            files.getString().add("MasterCloneData.zip");
         } else {
            files.getString().add("Autorun_RF_Cloning.upg");
         }

         fileSet1.setFiles(files);
         fileSet1.setID("Easy2K11Clone");
         fileSet1.setUsage("UseFirstFile");
         fileSets.getFileSet().add(fileSet1);
         catalogEntry.setFileSets(fileSets);
         entries.getCatalogEntry().add(catalogEntry);
         catalog.setEntries(entries);
         catalogFileGen(catalog);
      }
   }

   public static void catalogFileGen(Catalog catalog) {
      try {
         JAXBContext context = JAXBContext.newInstance(Catalog.class);
         Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
         String catalogFileLocation = getCatalogFileLocation();
         File output = new File(catalogFileLocation);
         marshaller.marshal(catalog, output);
      } catch (JAXBException e) {
         LOG.error("" + e.getMessage(), e);
      }
   }

   public static String getCatalogFileLocation() {
      String ret = null;
      Map<String, String> mapEnv = new HashMap<>();

      try {
         mapEnv = EnvironmentUtils.getProcEnvironment();
      } catch (IOException e) {
         LOG.error("" + e.getMessage(), e);
      }

      for (String key : mapEnv.keySet()) {
         if (key.equalsIgnoreCase("APPDATA")) {
            ret = (String)mapEnv.get(key);
         }
      }

      return CommonConstants.SISERVER_PSG_CATALOG_FILE;
   }

   public static class CatalogFactory {
      public static PSGCatalogGeneratorService get(String platformId, String versionFolder, HashMap<String, String> output) {
         PSGCatalogGeneratorService ret = null;
         if (platformId.equals("TPN141HE_CloneData") || platformId.equals("TPN142HE_CloneData")) {
            ret = new PSGCatalogGenerator.MS2K14CatalogGenerator(versionFolder, output);
         } else if (platformId.equals("TPM1532HE_CloneData") || platformId.equals("TPN161HE_CloneData") || platformId.equals("TPM1531HE_CloneData")) {
            ret = new PSGCatalogGenerator.MS2K15CatalogGenerator(versionFolder, output);
         }

         return ret;
      }
   }

   public static class MS2K14CatalogGenerator implements PSGCatalogGeneratorService {
      private String versionFolder = null;
      private HashMap<String, String> outPut = null;

      public MS2K14CatalogGenerator(String vf, HashMap<String, String> output) {
         this.versionFolder = vf;
         this.outPut = output;
      }

      @Override
      public void generate(String platformId) {
         if (platformId.contains("TPN142HE")) {
            PSGCatalogGenerator.GenMediaEasySuite("2K14_EasySuite", "0148\\0000\\1408\\", this.versionFolder, this.outPut.get("hasPlayOut"));
         } else {
            PSGCatalogGenerator.GenMediaEasySuite("2K14_MediaSuite", "0144\\0000\\1404\\", this.versionFolder, this.outPut.get("hasPlayOut"));
         }
      }
   }

   public static class MS2K15CatalogGenerator implements PSGCatalogGeneratorService {
      private String versionFolder = null;
      private HashMap<String, String> outPut = null;

      public MS2K15CatalogGenerator(String vf, HashMap<String, String> output) {
         this.versionFolder = vf;
         this.outPut = output;
      }

      @Override
      public void generate(String platformId) {
         if (platformId.contains("TPM1532HE")) {
            PSGCatalogGenerator.GenMediaEasySuite("2K15_MediaSuite", "0218\\0122\\00F0\\", this.versionFolder, this.outPut.get("hasPlayOut"));
         }

         if (platformId.contains("TPM1531HE")) {
            PSGCatalogGenerator.GenMediaEasySuite("2K15_MediaSuite", "021b\\0122\\00F0\\", this.versionFolder, this.outPut.get("hasPlayOut"));
         } else if (platformId.contains("TPN161HE")) {
            PSGCatalogGenerator.GenMediaEasySuite("2K16_EasySuite", "1554\\0000\\1564\\", this.versionFolder, this.outPut.get("hasPlayOut"));
         }
      }
   }
}
