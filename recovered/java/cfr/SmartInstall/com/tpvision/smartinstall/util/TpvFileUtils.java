/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.Utils;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TpvFileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TpvFileUtils.class);
    public static final int MAX_FILENAME_LENGTH = 45;
    public static final String RESOLUTION_OTHERS = "Others";
    public static final String RESOLUTION_3840X2160 = "3840X2160";
    public static final String RESOLUTION_1920X1080 = "1920X1080";
    public static final String RESOLUTION_1280X720 = "1280X720";
    public static final String RESOLUTION_960X540 = "960X540";

    private TpvFileUtils() {
    }

    public static void copyDirectoryIngoreExistsFile(File srcDir, File destDir) {
        if (null == srcDir || null == destDir) {
            return;
        }
        if (!srcDir.exists()) {
            return;
        }
        File[] srcFiles = srcDir.listFiles();
        if (!destDir.exists() && srcFiles.length > 0) {
            destDir.mkdirs();
        }
        for (File file : srcFiles) {
            String fileName = file.getName();
            LOG.info("copy files:{}", (Object)fileName);
            String destFileName = destDir.getAbsolutePath() + File.separatorChar + fileName;
            File destFile = new File(destFileName);
            if (file.isFile()) {
                if (destFile.exists()) continue;
                try {
                    FileUtils.copyFileToDirectory(file, destDir);
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
                continue;
            }
            if (!file.isDirectory()) continue;
            try {
                FileUtils.copyDirectory(file, destFile);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public static boolean checkDir(String strDir) {
        File dir = new File(strDir);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                LOG.error("Folder exist: {}", (Object)strDir);
            } else {
                LOG.error("A file with the same name exists, can not create folder:{} ", (Object)strDir);
            }
            return false;
        }
        return dir.mkdir();
    }

    public static File getFolderByName(File parentFolder, String fileName) {
        File[] subFolders = parentFolder.listFiles();
        if (null != subFolders) {
            for (File folder : subFolders) {
                if (!folder.isDirectory()) continue;
                if (folder.getName().indexOf(fileName) > -1) {
                    return folder;
                }
                File subfolder = TpvFileUtils.getFolderByName(folder, fileName);
                if (null == subfolder) continue;
                return subfolder;
            }
        }
        return null;
    }

    public static File getFileByName(File root, String fileName, String[] extFilters) {
        File[] cloneItemFileLists = root.listFiles();
        if (null != cloneItemFileLists && cloneItemFileLists.length > 0) {
            for (File file : cloneItemFileLists) {
                File founded;
                if (!file.isDirectory() && file.getName().indexOf(fileName) > -1) {
                    String ext = FilenameUtils.getExtension(file.getName());
                    ext = ext.toLowerCase();
                    if (!Arrays.asList(extFilters).contains(ext)) continue;
                    return file;
                }
                if (!file.isDirectory() || null == (founded = TpvFileUtils.getFileByName(file, fileName, extFilters))) continue;
                return founded;
            }
        }
        return null;
    }

    public static List<File> findFiles(File file, String extStr) {
        ArrayList<File> files = new ArrayList<File>();
        File[] childrenFiles = file.listFiles();
        if (childrenFiles == null) {
            LOG.error("listfiles null,file={}", (Object)file.getAbsolutePath());
            return files;
        }
        for (File f : childrenFiles) {
            if (f.isDirectory()) {
                files.addAll(TpvFileUtils.findFiles(f, extStr));
                continue;
            }
            String ext = FilenameUtils.getExtension(f.getName());
            if (null != extStr && !extStr.trim().equals("")) {
                if (!ext.equalsIgnoreCase(extStr)) continue;
                files.add(f);
                continue;
            }
            files.add(f);
        }
        return files;
    }

    public static void checkDiskSpaceFull(long fileSize) throws IOException {
        File root = new File(Utils.getRootDir());
        if (root.getFreeSpace() <= fileSize) {
            throw new IOException("error: Disk Full, please clean files in " + Utils.getRootDir() + " first");
        }
    }

    public static void checkDiskSpaceFull(File fileToCopy) throws IOException {
        TpvFileUtils.checkDiskSpaceFull(fileToCopy.length());
    }

    public static String getImageResolution(File f) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(f);
            if (null != image) {
                int height = image.getHeight();
                int width = image.getWidth();
                if (width == 960 && height == 540) {
                    return RESOLUTION_960X540;
                }
                if (width == 1280 && height == 720) {
                    return RESOLUTION_1280X720;
                }
                if (width == 1920 && height == 1080) {
                    return RESOLUTION_1920X1080;
                }
                if (width == 3840 && height == 2160) {
                    return RESOLUTION_3840X2160;
                }
                return width + "X" + height;
            }
            LOG.info("image is null!");
            return "";
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return "";
        }
    }

    public static long getDirSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (null != children && children.length > 0) {
                    long size = 0L;
                    for (File child : children) {
                        size += TpvFileUtils.getDirSize(child);
                    }
                    return size;
                }
            } else {
                return file.length();
            }
        }
        return 0L;
    }

    public static File getFileByNamePrefix(File parentDir, String fileNamePrefix) {
        File[] listFiles = parentDir.listFiles();
        if (listFiles != null) {
            for (File f1 : listFiles) {
                if (!f1.isFile() || !f1.getName().startsWith(fileNamePrefix)) continue;
                return f1;
            }
        }
        return null;
    }

    public static File getDirectoryByName(File parentDir, String dirName) {
        File[] listFiles = parentDir.listFiles();
        if (listFiles != null) {
            for (File f1 : listFiles) {
                if (!f1.isDirectory() || !f1.getName().contains(dirName)) continue;
                return f1;
            }
        }
        return null;
    }

    public static void clearFiles(String rootPath) {
        if (rootPath == null) {
            return;
        }
        File parentFile = new File(rootPath);
        if (parentFile.exists()) {
            try {
                FileUtils.cleanDirectory(parentFile);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getVersion(String filePath) {
        String version = null;
        File file = new File(filePath);
        if (!file.exists()) {
            return "Unknown";
        }
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");){
            byte[] buffer = new byte[64];
            raf.read(buffer);
            String str = "" + (char)buffer[0] + (char)buffer[1];
            if (!"MZ".equals(str)) {
                String string = version;
                return string;
            }
            int peOffset = TpvFileUtils.unpack(new byte[]{buffer[60], buffer[61], buffer[62], buffer[63]});
            if (peOffset < 64) {
                String string = version;
                return string;
            }
            raf.seek(peOffset);
            buffer = new byte[24];
            raf.read(buffer);
            str = "" + (char)buffer[0] + (char)buffer[1];
            if (!"PE".equals(str)) {
                String string = version;
                return string;
            }
            int machine = TpvFileUtils.unpack(new byte[]{buffer[4], buffer[5]});
            if (machine != 332) {
                String string = version;
                return string;
            }
            int noSections = TpvFileUtils.unpack(new byte[]{buffer[6], buffer[7]});
            int optHdrSize = TpvFileUtils.unpack(new byte[]{buffer[20], buffer[21]});
            raf.seek(raf.getFilePointer() + (long)optHdrSize);
            boolean resFound = false;
            for (int i = 0; i < noSections; ++i) {
                buffer = new byte[40];
                raf.read(buffer);
                str = "" + (char)buffer[0] + (char)buffer[1] + (char)buffer[2] + (char)buffer[3] + (char)buffer[4];
                if (!".rsrc".equals(str)) continue;
                resFound = true;
                break;
            }
            if (!resFound) {
                String i = version;
                return i;
            }
            int infoVirt = TpvFileUtils.unpack(new byte[]{buffer[12], buffer[13], buffer[14], buffer[15]});
            int infoSize = TpvFileUtils.unpack(new byte[]{buffer[16], buffer[17], buffer[18], buffer[19]});
            int infoOff = TpvFileUtils.unpack(new byte[]{buffer[20], buffer[21], buffer[22], buffer[23]});
            raf.seek(infoOff);
            buffer = new byte[infoSize];
            raf.read(buffer);
            int nameEntries = TpvFileUtils.unpack(new byte[]{buffer[12], buffer[13]});
            int idEntries = TpvFileUtils.unpack(new byte[]{buffer[14], buffer[15]});
            boolean infoFound = false;
            int subOff = 0;
            for (int i = 0; i < nameEntries + idEntries; ++i) {
                int type = TpvFileUtils.unpack(new byte[]{buffer[i * 8 + 16], buffer[i * 8 + 17], buffer[i * 8 + 18], buffer[i * 8 + 19]});
                if (type != 16) continue;
                infoFound = true;
                subOff = TpvFileUtils.unpack(new byte[]{buffer[i * 8 + 20], buffer[i * 8 + 21], buffer[i * 8 + 22], buffer[i * 8 + 23]});
                break;
            }
            if (!infoFound) {
                String i = version;
                return i;
            }
            infoOff = TpvFileUtils.unpack(new byte[]{buffer[(subOff &= Integer.MAX_VALUE) + 20], buffer[subOff + 21], buffer[subOff + 22], buffer[subOff + 23]});
            infoOff = TpvFileUtils.unpack(new byte[]{buffer[(infoOff &= Integer.MAX_VALUE) + 20], buffer[infoOff + 21], buffer[infoOff + 22], buffer[infoOff + 23]});
            int dataOff = TpvFileUtils.unpack(new byte[]{buffer[infoOff], buffer[infoOff + 1], buffer[infoOff + 2], buffer[infoOff + 3]});
            int version1 = TpvFileUtils.unpack(new byte[]{buffer[(dataOff -= infoVirt) + 48], buffer[dataOff + 48 + 1]});
            int version2 = TpvFileUtils.unpack(new byte[]{buffer[dataOff + 48 + 2], buffer[dataOff + 48 + 3]});
            int version3 = TpvFileUtils.unpack(new byte[]{buffer[dataOff + 48 + 4], buffer[dataOff + 48 + 5]});
            int version4 = TpvFileUtils.unpack(new byte[]{buffer[dataOff + 48 + 6], buffer[dataOff + 48 + 7]});
            version = version2 + "." + version1 + "." + version4 + "." + version3;
            LOG.info("{} version: {}", (Object)filePath, (Object)version);
            String string = version;
            return string;
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private static int unpack(byte[] b) {
        int num = 0;
        for (int i = 0; i < b.length; ++i) {
            num = 256 * num + (b[b.length - 1 - i] & 0xFF);
        }
        return num;
    }

    public static String filterFileName(String fileName) {
        String ext = FilenameUtils.getExtension(fileName);
        String baseName = FilenameUtils.getBaseName(fileName);
        String newName = baseName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        int maxlength = 45 - ext.length();
        return newName.substring(0, newName.length() > maxlength ? maxlength : newName.length()) + "." + ext;
    }

    public static void exportThumbnailFile(File srcFile, File targetfile) {
        try {
            BufferedImage image = ImageIO.read(srcFile);
            if (null != image) {
                BufferedImage thumbnail = Scalr.resize(image, 160, 90, new BufferedImageOp[0]);
                String ext = FilenameUtils.getExtension(targetfile.getName());
                FileUtils.createParentDirectories(targetfile);
                ImageIO.write((RenderedImage)thumbnail, ext, targetfile);
                image.flush();
                thumbnail.flush();
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static String getImageMd5(File f) {
        String md5 = "";
        try (FileInputStream fis = new FileInputStream(f.getAbsolutePath());){
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
            IOUtils.closeQuietly(fis);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return md5;
    }

    public static boolean isValidFileName(String fileName) {
        File f = new File(fileName);
        try {
            return f.getCanonicalFile().getName().equals(fileName);
        }
        catch (IOException e) {
            return false;
        }
    }

    public static boolean compareFolderContent(String folderPath1, String folderPath2) {
        File folder1 = new File(folderPath1);
        File folder2 = new File(folderPath2);
        if (folder1.isDirectory() && folder2.isDirectory()) {
            if (folder1.toString().equals(folder2.toString())) {
                return true;
            }
            List<String> fileDataList1 = TpvFileUtils.findSubFoldersAndFile(folder1);
            List<String> fileDataList2 = TpvFileUtils.findSubFoldersAndFile(folder2);
            if (fileDataList1.size() != fileDataList2.size()) {
                return false;
            }
            Collections.sort(fileDataList1);
            Collections.sort(fileDataList2);
            if (!fileDataList1.toString().equals(fileDataList2.toString())) {
                return false;
            }
            String formattedFolderPath1 = folder1.toString();
            String formattedFolderPath2 = folder2.toString();
            for (String path : fileDataList1) {
                File checkFile1 = new File(formattedFolderPath1 + path);
                File checkFile2 = new File(formattedFolderPath2 + path);
                if (checkFile1.isDirectory() && checkFile2.isDirectory()) continue;
                if (checkFile1.isDirectory() && checkFile2.isFile()) {
                    return false;
                }
                if (checkFile1.isFile() && checkFile2.isDirectory()) {
                    return false;
                }
                boolean isContentEqual = TpvFileUtils.compareFile(checkFile1, checkFile2);
                if (isContentEqual) continue;
                return false;
            }
        }
        return true;
    }

    private static boolean compareFile(File file1, File file2) {
        try {
            String file1Hash = DigestUtils.md5Hex(new FileInputStream(file1));
            String file2Hash = DigestUtils.md5Hex(new FileInputStream(file2));
            return StringUtils.equalsIgnoreCase(file1Hash, file2Hash);
        }
        catch (Exception exception) {
            return false;
        }
    }

    private static List<String> findSubFoldersAndFile(File fileFolder) {
        ArrayList<String> findResult = new ArrayList<String>();
        TpvFileUtils.getFilename(fileFolder.toString(), fileFolder, findResult);
        return findResult;
    }

    public static List<String> findSubFolders(File fileFolder) {
        ArrayList<String> findResult = new ArrayList<String>();
        File[] files = fileFolder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.isDirectory()) continue;
                findResult.add(f.getPath());
            }
        }
        return findResult;
    }

    private static String trimFileName(String fullPath, String trimPath) {
        return fullPath.substring(trimPath.length());
    }

    private static void getFilename(String trimPath, File folderFile, List<String> findResult) {
        File[] files = folderFile.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (FileUtils.isSymlink(file)) {
                    }
                }
                catch (Exception ex) {}
                continue;
                findResult.add(TpvFileUtils.trimFileName(file.toString(), trimPath));
                if (!file.isDirectory()) continue;
                TpvFileUtils.getFilename(trimPath, file, findResult);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getLineMatchKeyword(File file, String keyword) {
        if (!file.exists()) return null;
        if (!file.isFile()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));){
            String s = null;
            do {
                if ((s = br.readLine()) == null) return null;
            } while (!s.contains(keyword));
            String string = s;
            return string;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public static String convertFileSizeToString(long size) {
        long kb = 1024L;
        long mb = kb * 1024L;
        long gb = mb * 1024L;
        String ret = "";
        DecimalFormat df = new DecimalFormat("0.00");
        if (size >= gb) {
            ret = df.format((double)size / ((double)gb * 1.0)) + " GB";
        } else if (size >= mb) {
            ret = df.format((double)size / ((double)mb * 1.0)) + " MB";
        } else if (size >= kb) {
            ret = df.format((double)size / ((double)kb * 1.0)) + " KB";
        } else if (size > 0L) {
            ret = df.format((double)size / 1.0) + " Byte";
        }
        return ret;
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

    public static void deleteDirecotry(String directory) {
        File fileDir = new File(directory);
        if (fileDir.exists()) {
            try {
                FileUtils.forceDelete(fileDir);
                LOG.info("delete:{}", (Object)fileDir.getPath());
            }
            catch (Exception e) {
                LOG.error("Failed to delete {}", (Object)fileDir.getPath());
            }
        }
    }

    public static class FilenameFilterFinder
    implements FilenameFilter {
        private String name;

        public FilenameFilterFinder(String fileName) {
            this.name = fileName;
        }

        @Override
        public boolean accept(File dir, String filename) {
            return filename.equalsIgnoreCase(this.name);
        }
    }
}

