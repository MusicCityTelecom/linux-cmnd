package com.tpvision.smartinstall.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipCommonUtils {
   private static final Pattern WINDOWS_ILLEGAL_CHARACTERS_EXCEPT_SLASH = Pattern.compile("[\\\\:*?\"<>|]");
   private static final Logger LOG = LoggerFactory.getLogger(ZipCommonUtils.class);

   private ZipCommonUtils() {
   }

   public static void zipFileRead(String zipfile, String unzipFileName, String saveRootDirectory) throws Exception {
      try (ZipFile zipFile = new ZipFile(zipfile)) {
         Enumeration<? extends ZipEntry> enu = zipFile.entries();

         while (enu.hasMoreElements()) {
            ZipEntry zipElement = enu.nextElement();
            InputStream read = zipFile.getInputStream(zipElement);
            String fileName = zipElement.getName();
            if (null != fileName && fileName.indexOf(46) != -1) {
               unZipFile(zipElement, read, unzipFileName, saveRootDirectory);
            }
         }
      } catch (Exception e) {
         LOG.error("" + e.getMessage(), e);
         throw e;
      }
   }

   public static void deleteZipFile(String zipFileDirectory, String zipFileName1, String zipFileName2) {
      TpvFileUtils.deleteFile(zipFileDirectory + zipFileName1);
      TpvFileUtils.deleteFile(zipFileDirectory + zipFileName2);
   }

   public static String readJsonFromZip(String zipFilePath, String jsonFilePath) throws IOException {
      ZipEntry entry;
      try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
         while ((entry = zipIn.getNextEntry()) != null) {
            if (entry.getName().equals(jsonFilePath)) {
               return readEntry(zipIn);
            }

            zipIn.closeEntry();
         }
      }

      throw new FileNotFoundException("File " + jsonFilePath + " not found in zip.");
   }

   private static String readEntry(InputStream inputStream) throws IOException {
      StringBuilder content = new StringBuilder();

      String line;
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
         while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
         }
      }

      return content.toString();
   }

   public static void unzip(File zipInputFile, String processingDirName) throws IOException {
      org.apache.commons.compress.archivers.zip.ZipFile zipFile = new org.apache.commons.compress.archivers.zip.ZipFile(zipInputFile);

      try {
         Enumeration<ZipArchiveEntry> archEntryEnum = zipFile.getEntries();

         while (archEntryEnum.hasMoreElements()) {
            ZipArchiveEntry entry1 = archEntryEnum.nextElement();
            InputStream content = zipFile.getInputStream(entry1);
            String zipDirPath = processingDirName;
            String zipPath = zipDirPath + "/" + entry1.getName();
            File file = new File(zipPath);
            if (!file.exists()) {
               String dirPath = null;
               if (!entry1.isDirectory()) {
                  dirPath = file.getAbsolutePath();
                  dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\"));
               } else {
                  dirPath = file.getAbsolutePath();
               }

               File dir = new File(dirPath);
               LOG.debug(file.getAbsolutePath());
               dir.mkdirs();
               if (!entry1.isDirectory()) {
                  file.createNewFile();
               }
            }

            if (!entry1.isDirectory()) {
               FileOutputStream fos = new FileOutputStream(file);
               IOUtils.copy(content, fos);
               fos.close();
            }
         }
      } finally {
         zipFile.close();
      }
   }

   private static void unZipFile(ZipEntry ze, InputStream read, String unzipFileName, String saveRootDirectory) throws IOException {
      String fileName = ze.getName();
      if (fileName.equals(unzipFileName)) {
         File file = new File(saveRootDirectory + fileName);
         if (!file.exists()) {
            File rootDirectoryFile = new File(file.getParent());
            if (!rootDirectoryFile.exists()) {
               rootDirectoryFile.mkdirs();
            }

            try {
               file.createNewFile();
            } catch (IOException e) {
               LOG.error("" + e.getMessage(), e);
            }
         }

         try (BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(file))) {
            int cha = 0;

            while ((cha = read.read()) != -1) {
               write.write(cha);
            }

            write.flush();
         } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
         }

         read.close();
      }
   }

   public static void zipFiles(List<File> fileList, String destName) {
      File destFile = new File(destName);
      File oldFile = new File(destName + ".bak");
      if (destFile.exists()) {
         destFile.renameTo(oldFile);
      }

      destFile.getParentFile().mkdirs();

      try (
         FileOutputStream fos = new FileOutputStream(destName);
         ZipOutputStream zipOut = new ZipOutputStream(fos);
      ) {
         if (oldFile.exists()) {
            try (ZipFile backZip = new ZipFile(oldFile)) {
               Enumeration<? extends ZipEntry> entries = backZip.entries();
               byte[] buffer = new byte[1024];

               while (entries.hasMoreElements()) {
                  ZipEntry ze = entries.nextElement();
                  zipOut.putNextEntry(ze);
                  InputStream zin = backZip.getInputStream(ze);

                  for (int read = zin.read(buffer); read > -1; read = zin.read(buffer)) {
                     zipOut.write(buffer, 0, read);
                  }

                  zipOut.flush();
               }
            }

            FileUtils.deleteQuietly(oldFile);
         }

         for (File fileToZip : fileList) {
            zipFile(fileToZip, fileToZip.getName(), zipOut, destFile);
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static void zipFiles(String sourceFile, String destFile) {
      File file = new File(destFile);
      if (file.isDirectory()) {
         if (!file.exists()) {
            file.mkdirs();
         }
      } else {
         String strParentDirectory = file.getParent();
         File tmpFile = new File(strParentDirectory);
         if (!tmpFile.exists()) {
            tmpFile.mkdirs();
         }
      }

      int fileLen = destFile.length();
      if (fileLen > 4) {
         String tmp = destFile.substring(fileLen - 4, fileLen);
         if (!tmp.contains(".zip") && !tmp.contains(".ZIP") && !tmp.contains(".upg") && !tmp.contains(".UPG")) {
            destFile = destFile + ".zip";
         }
      }

      try (
         FileOutputStream fos = new FileOutputStream(destFile);
         ZipOutputStream zipOut = new ZipOutputStream(fos);
      ) {
         File fileToZip = new File(sourceFile);
         zipFile(fileToZip, fileToZip.getName(), zipOut, file);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static void unZipFiles(File zipFile, String descDir) {
      if (zipFile.exists()) {
         File pathFile = new File(descDir);
         if (!pathFile.exists()) {
            pathFile.mkdirs();
         }

         int fileLen = descDir.length();
         if (fileLen > 2) {
            String tmp = descDir.substring(fileLen - 2, fileLen);
            if (!tmp.contains(File.separator)) {
               descDir = descDir + File.separator;
            }
         }

         ArchiveEntry entry;
         try (
            InputStream is = new BufferedInputStream(new FileInputStream(zipFile));
            ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("zip", is);
         ) {
            while ((entry = ais.getNextEntry()) != null) {
               if (ais.canReadEntryData(entry)) {
                  String sanitizedFileName = WINDOWS_ILLEGAL_CHARACTERS_EXCEPT_SLASH.matcher(entry.getName()).replaceAll("_");
                  File entryFile = new File(descDir, sanitizedFileName);
                  if (entry.isDirectory()) {
                     if (!entryFile.isDirectory() && !entryFile.mkdirs()) {
                        throw new IOException("Failed to create directory: " + entryFile);
                     }
                  } else {
                     File parent = entryFile.getParentFile();
                     if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory: " + parent);
                     }

                     try (OutputStream os = new FileOutputStream(entryFile)) {
                        IOUtils.copy(ais, os);
                     } catch (IOException e) {
                        LOG.error(e.getMessage(), e);
                     }
                  }
               }
            }
         } catch (IOException | ArchiveException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut, File file) throws IOException {
      if (!fileToZip.isHidden()) {
         if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
               zipOut.putNextEntry(new ZipEntry(fileName));
               zipOut.closeEntry();
            } else {
               zipOut.putNextEntry(new ZipEntry(fileName + "/"));
               zipOut.closeEntry();
            }

            File[] children = fileToZip.listFiles();

            for (File childFile : children) {
               zipFile(childFile, fileName + "/" + childFile.getName(), zipOut, file);
            }
         } else if (!fileToZip.getAbsolutePath().equalsIgnoreCase(file.getAbsolutePath())) {
            try (FileInputStream fis = new FileInputStream(fileToZip)) {
               ZipEntry zipEntry = new ZipEntry(fileName);
               zipOut.putNextEntry(zipEntry);
               byte[] bytes = new byte[1024];

               int length;
               while ((length = fis.read(bytes)) >= 0) {
                  zipOut.write(bytes, 0, length);
               }

               zipOut.flush();
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   public static void gen7ZipForOAD(String targetZipPath, String targetZipName, String sourceZipPath, String sourceZipName) {
      String ext = sourceZipName.replace("*.", "");
      List<File> zips = TpvFileUtils.findFiles(new File(sourceZipPath), ext);
      zipFiles(zips, targetZipPath + targetZipName);
   }

   public static void createZip(String directoryPath, String zipPath) throws IOException {
      try (
         FileOutputStream fOut = new FileOutputStream(new File(zipPath));
         BufferedOutputStream bOut = new BufferedOutputStream(fOut);
         ZipArchiveOutputStream tOut = new ZipArchiveOutputStream(bOut);
      ) {
         addFileToZip(tOut, directoryPath, "", true);
      } catch (Exception e1) {
         LOG.error(e1.getMessage(), e1);
      }
   }

   private static void addFileToZip(ZipArchiveOutputStream zOut, String path, String base, boolean isTopCall) throws IOException {
      File assemblyRoot = new File(path);
      if (assemblyRoot.isFile()) {
         String entryName = base + assemblyRoot.getName();
         ZipArchiveEntry zipEntry = new ZipArchiveEntry(assemblyRoot, entryName);
         zOut.putArchiveEntry(zipEntry);

         try (FileInputStream fis = new FileInputStream(assemblyRoot)) {
            IOUtils.copy(fis, zOut);
         }

         zOut.closeArchiveEntry();
      } else {
         for (File f : assemblyRoot.listFiles()) {
            String entryName = base + f.getName();
            if (!isTopCall) {
               entryName = base + assemblyRoot.getName() + "/" + f.getName();
            }

            ZipArchiveEntry zipEntry = new ZipArchiveEntry(f, entryName);
            zOut.putArchiveEntry(zipEntry);
            if (f.isFile()) {
               try (FileInputStream fis = new FileInputStream(f)) {
                  IOUtils.copy(fis, zOut);
               }

               zOut.closeArchiveEntry();
            } else {
               zOut.closeArchiveEntry();
               File[] children = f.listFiles();
               if (null != children) {
                  for (File child : children) {
                     addFileToZip(zOut, child.getAbsolutePath(), entryName + "/", false);
                  }
               }
            }
         }
      }
   }

   public static void gen7Zip(String zipPath, String zipName) {
      String line = "cmd /c 7z.exe a " + zipName + "  " + zipPath + "/*";
      CommandLine cmdLine = CommandLine.parse(line);
      DefaultExecutor executor = new DefaultExecutor();
      executor.setWorkingDirectory(new File(CommonConstants.ZIP_7));
      int[] values = new int[]{0, 1};
      executor.setExitValues(values);

      try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
         PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
         executor.setStreamHandler(streamHandler);
         executor.execute(cmdLine);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }
}
