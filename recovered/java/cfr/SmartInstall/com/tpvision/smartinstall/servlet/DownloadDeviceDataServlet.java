/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import com.tpvision.smartinstall.xml.device.Device;
import com.tpvision.smartinstall.xml.device.DevicesInf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/downloadDeviceData"})
public class DownloadDeviceDataServlet
extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadDeviceDataServlet.class);
    private static final long serialVersionUID = 1L;
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.downloadDeviceData(request, response);
        this.downloadVsecureKey(request, response);
    }

    private void createOverviewZip(String sourceFilePath, String zipFilePath) {
        File f = null;
        try {
            ZipCommonUtils.createZip(sourceFilePath, zipFilePath);
            f = new File(zipFilePath);
            if (!f.exists()) {
                f.createNewFile();
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void downloadVsecureKey(HttpServletRequest request, HttpServletResponse response) {
        block45: {
            String vseccertFile = CommonConstants.DOWNLOAD_LOCATION + "/Overview/vseccert.txt";
            try {
                File directoryPath = new File(vseccertFile);
                if (directoryPath.exists()) {
                    directoryPath.delete();
                }
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
            File f = new File(vseccertFile);
            try (FileWriter fileWriter = new FileWriter(f);){
                DevicesManager dm = JpaManager.getDevicesManager();
                List<Devices> tvs = dm.loadAll();
                if (tvs.isEmpty()) break block45;
                for (int i = 0; i < tvs.size(); ++i) {
                    String vsecureKey = tvs.get(i).getVsecureKey();
                    String vsecureID = tvs.get(i).getVsecuretvid();
                    if (null == vsecureKey || null == vsecureID) continue;
                    fileWriter.write(" " + vsecureID + " " + vsecureKey + "\r\n");
                }
                fileWriter.flush();
                String overviewPath = CommonConstants.DOWNLOAD_LOCATION + "/Overview";
                String zipFilePath = CommonConstants.DOWNLOAD_LOCATION + "/Overview.zip";
                this.createOverviewZip(overviewPath, zipFilePath);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"Overview.zip\"");
                File overviewFile = new File(zipFilePath);
                try (FileInputStream fis = new FileInputStream(overviewFile);
                     ServletOutputStream outStream = response.getOutputStream();){
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void downloadDeviceData(HttpServletRequest request, HttpServletResponse response) {
        try {
            File directoryPath;
            File overviewPath = new File(CommonConstants.DOWNLOAD_LOCATION + "/Overview");
            if (!overviewPath.exists()) {
                overviewPath.mkdir();
            }
            if ((directoryPath = new File(CommonConstants.DOWNLOAD_LOCATION + "/Overview/DeviceData.xml")).exists()) {
                directoryPath.delete();
            }
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        try {
            DevicesManager dm = JpaManager.getDevicesManager();
            List<Devices> ret = dm.loadAll();
            if (!ret.isEmpty()) {
                DevicesInf di = new DevicesInf();
                List<Device> lst = di.getDevice();
                for (Devices devides : ret) {
                    Device temp = new Device();
                    temp.setName(Optional.ofNullable(devides.getTvname()).orElse(""));
                    temp.setCTN(Optional.ofNullable(devides.getTvmodelnumber()).orElse(""));
                    temp.setSN(Optional.ofNullable(devides.getTvserialnumber()).orElse(""));
                    temp.setRID(Optional.ofNullable(devides.getTvroomid()).orElse(""));
                    temp.setType(Optional.ofNullable(devides.getType()).orElse(""));
                    temp.setIP(Optional.ofNullable(devides.getTvipaddress()).orElse(""));
                    temp.setMAC(Optional.ofNullable(devides.getTvmacaddress()).orElse(""));
                    temp.setSW(Optional.ofNullable(devides.getTvFirmwareIdentifier()).orElse(""));
                    temp.setClone(Optional.ofNullable(devides.getTvCloneIdentifiers()).orElse(""));
                    lst.add(temp);
                }
                this.proccessDeviceDataXml(di);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    protected void proccessDeviceDataXml(DevicesInf listOfDevice) {
        if (null == listOfDevice) {
            return;
        }
        String fileName = CommonConstants.DOWNLOAD_LOCATION + "Overview/DeviceData.xml";
        try {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.device");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal((Object)listOfDevice, sw);
            this.writeXML(fileName, sw);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    protected void writeXML(String fileName, StringWriter sw) {
        if (null == sw) {
            return;
        }
        String str = sw.toString().replace("&amp;apos;", "&apos;");
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(f);
                 OutputStreamWriter out = new OutputStreamWriter((OutputStream)fos, StandardCharsets.UTF_8);){
                out.write(str);
                ((Writer)out).flush();
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

