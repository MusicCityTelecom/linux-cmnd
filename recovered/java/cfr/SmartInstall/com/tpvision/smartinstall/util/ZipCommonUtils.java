/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvFileUtils;
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
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
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
        try (java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(zipfile);){
            Enumeration<? extends ZipEntry> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipElement = enu.nextElement();
                InputStream read = zipFile.getInputStream(zipElement);
                String fileName = zipElement.getName();
                if (null == fileName || fileName.indexOf(46) == -1) continue;
                ZipCommonUtils.unZipFile(zipElement, read, unzipFileName, saveRootDirectory);
            }
        }
        catch (Exception e) {
            LOG.error("" + e.getMessage(), e);
            throw e;
        }
    }

    public static void deleteZipFile(String zipFileDirectory, String zipFileName1, String zipFileName2) {
        TpvFileUtils.deleteFile(zipFileDirectory + zipFileName1);
        TpvFileUtils.deleteFile(zipFileDirectory + zipFileName2);
    }

    public static String readJsonFromZip(String zipFilePath, String jsonFilePath) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));){
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (entry.getName().equals(jsonFilePath)) {
                    String string = ZipCommonUtils.readEntry(zipIn);
                    return string;
                }
                zipIn.closeEntry();
            }
        }
        throw new FileNotFoundException("File " + jsonFilePath + " not found in zip.");
    }

    private static String readEntry(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));){
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void unzip(File zipInputFile, String processingDirName) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipInputFile);){
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
                if (entry1.isDirectory()) continue;
                FileOutputStream fos = new FileOutputStream(file);
                IOUtils.copy(content, (OutputStream)fos);
                fos.close();
            }
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
                }
                catch (IOException e) {
                    LOG.error("" + e.getMessage(), e);
                }
            }
            try (BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(file));){
                int cha = 0;
                while ((cha = read.read()) != -1) {
                    write.write(cha);
                }
                write.flush();
            }
            catch (IOException ex) {
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
        try (FileOutputStream fos = new FileOutputStream(destName);
             ZipOutputStream zipOut = new ZipOutputStream(fos);){
            if (oldFile.exists()) {
                try (java.util.zip.ZipFile backZip = new java.util.zip.ZipFile(oldFile);){
                    Enumeration<? extends ZipEntry> entries = backZip.entries();
                    byte[] buffer = new byte[1024];
                    while (entries.hasMoreElements()) {
                        ZipEntry ze = entries.nextElement();
                        zipOut.putNextEntry(ze);
                        InputStream zin = backZip.getInputStream(ze);
                        int read = zin.read(buffer);
                        while (read > -1) {
                            zipOut.write(buffer, 0, read);
                            read = zin.read(buffer);
                        }
                        zipOut.flush();
                    }
                }
                FileUtils.deleteQuietly(oldFile);
            }
            for (File fileToZip : fileList) {
                ZipCommonUtils.zipFile(fileToZip, fileToZip.getName(), zipOut, destFile);
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void zipFiles(String sourceFile, String destFile) {
        String tmp;
        int fileLen;
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
        if (!((fileLen = destFile.length()) <= 4 || (tmp = destFile.substring(fileLen - 4, fileLen)).contains(".zip") || tmp.contains(".ZIP") || tmp.contains(".upg") || tmp.contains(".UPG"))) {
            destFile = destFile + ".zip";
        }
        try (FileOutputStream fos = new FileOutputStream(destFile);
             ZipOutputStream zipOut = new ZipOutputStream(fos);){
            File fileToZip = new File(sourceFile);
            ZipCommonUtils.zipFile(fileToZip, fileToZip.getName(), zipOut, file);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void unZipFiles(File zipFile, String descDir) {
        block45: {
            String tmp;
            int fileLen;
            if (!zipFile.exists()) {
                return;
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            if ((fileLen = descDir.length()) > 2 && !(tmp = descDir.substring(fileLen - 2, fileLen)).contains(File.separator)) {
                descDir = descDir + File.separator;
            }
            try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(zipFile));){
                ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("zip", is);
                Throwable throwable = null;
                block31: while (true) {
                    try {
                        ArchiveEntry entry;
                        while ((entry = ais.getNextEntry()) != null) {
                            if (!ais.canReadEntryData(entry)) continue;
                            String sanitizedFileName = WINDOWS_ILLEGAL_CHARACTERS_EXCEPT_SLASH.matcher(entry.getName()).replaceAll("_");
                            File entryFile = new File(descDir, sanitizedFileName);
                            if (entry.isDirectory()) {
                                if (entryFile.isDirectory() || entryFile.mkdirs()) continue;
                                throw new IOException("Failed to create directory: " + entryFile);
                            }
                            File parent = entryFile.getParentFile();
                            if (!parent.isDirectory() && !parent.mkdirs()) {
                                throw new IOException("Failed to create directory: " + parent);
                            }
                            try {
                                FileOutputStream os = new FileOutputStream(entryFile);
                                Throwable throwable2 = null;
                                try {
                                    IOUtils.copy((InputStream)ais, (OutputStream)os);
                                    continue block31;
                                }
                                catch (Throwable throwable3) {
                                    throwable2 = throwable3;
                                    throw throwable3;
                                }
                                finally {
                                    if (os == null) continue block31;
                                    if (throwable2 != null) {
                                        try {
                                            ((OutputStream)os).close();
                                        }
                                        catch (Throwable throwable4) {
                                            throwable2.addSuppressed(throwable4);
                                        }
                                        continue block31;
                                    }
                                    ((OutputStream)os).close();
                                    continue block31;
                                }
                            }
                            catch (IOException e) {
                                LOG.error(e.getMessage(), e);
                            }
                        }
                        break block45;
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                }
                finally {
                    if (ais != null) {
                        if (throwable != null) {
                            try {
                                ais.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                        } else {
                            ais.close();
                        }
                    }
                }
            }
            catch (IOException | ArchiveException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut, File file) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children;
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            for (File childFile : children = fileToZip.listFiles()) {
                ZipCommonUtils.zipFile(childFile, fileName + "/" + childFile.getName(), zipOut, file);
            }
            return;
        }
        if (fileToZip.getAbsolutePath().equalsIgnoreCase(file.getAbsolutePath())) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip);){
            int length;
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.flush();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void gen7ZipForOAD(String targetZipPath, String targetZipName, String sourceZipPath, String sourceZipName) {
        String ext = sourceZipName.replace("*.", "");
        List<File> zips = TpvFileUtils.findFiles(new File(sourceZipPath), ext);
        ZipCommonUtils.zipFiles(zips, targetZipPath + targetZipName);
    }

    public static void createZip(String directoryPath, String zipPath) throws IOException {
        try (FileOutputStream fOut = new FileOutputStream(new File(zipPath));
             BufferedOutputStream bOut = new BufferedOutputStream(fOut);
             ZipArchiveOutputStream tOut = new ZipArchiveOutputStream(bOut);){
            ZipCommonUtils.addFileToZip(tOut, directoryPath, "", true);
        }
        catch (Exception e1) {
            LOG.error(e1.getMessage(), e1);
        }
    }

    private static void addFileToZip(ZipArchiveOutputStream zOut, String path, String base, boolean isTopCall) throws IOException {
        File assemblyRoot = new File(path);
        if (assemblyRoot.isFile()) {
            String entryName = base + assemblyRoot.getName();
            ZipArchiveEntry zipEntry = new ZipArchiveEntry(assemblyRoot, entryName);
            zOut.putArchiveEntry(zipEntry);
            try (FileInputStream fis = new FileInputStream(assemblyRoot);){
                IOUtils.copy((InputStream)fis, (OutputStream)zOut);
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
                    FileInputStream fis = new FileInputStream(f);
                    Object object = null;
                    try {
                        IOUtils.copy((InputStream)fis, (OutputStream)zOut);
                    }
                    catch (Throwable throwable) {
                        object = throwable;
                        throw throwable;
                    }
                    finally {
                        if (fis != null) {
                            if (object != null) {
                                try {
                                    fis.close();
                                }
                                catch (Throwable throwable) {
                                    ((Throwable)object).addSuppressed(throwable);
                                }
                            } else {
                                fis.close();
                            }
                        }
                    }
                    zOut.closeArchiveEntry();
                    continue;
                }
                zOut.closeArchiveEntry();
                File[] children = f.listFiles();
                if (null == children) continue;
                for (File child : children) {
                    ZipCommonUtils.addFileToZip(zOut, child.getAbsolutePath(), entryName + "/", false);
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
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            executor.setStreamHandler(streamHandler);
            executor.execute(cmdLine);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

