/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.service.MetricsService;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value={"production"})
public class MetricsServiceImpl
implements MetricsService {
    private static final String FILE_BEAT_CONFIG_FILE = "C:\\Philips\\filebeat\\server.json";
    private static final String FILE_BEAT_INDEX_PLACE_HOLDER = "{server-log-id}";
    private static final String METRICS_LOG_PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\logs\\metrics\\";
    private boolean iSInitServerId = false;
    private String fileBeatServerId = null;

    @Override
    public void writeMetricsLog(JSONObject metricDetails) {
        if (!this.iSInitServerId) {
            this.initFileBeatServerId();
        }
        if (StringUtils.isBlank(this.fileBeatServerId)) {
            return;
        }
        String logFileName = "metrics.pd." + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
        File logFile = new File(METRICS_LOG_PATH + logFileName);
        metricDetails.put("server_id", this.fileBeatServerId);
        try {
            metricDetails.put("server_id", this.fileBeatServerId);
            FileUtils.writeStringToFile(logFile, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date()) + " " + metricDetails.toString() + "\r\n", StandardCharsets.UTF_8, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void initFileBeatServerId() {
        if (this.iSInitServerId) {
            return;
        }
        this.iSInitServerId = true;
        try {
            File fileBeatServerConfigFile = new File(FILE_BEAT_CONFIG_FILE);
            JSONObject serverJson = new JSONObject(FileUtils.readFileToString(fileBeatServerConfigFile, StandardCharsets.UTF_8));
            String currentServerId = serverJson.getString("server_id");
            if (!StringUtils.equalsIgnoreCase(currentServerId, FILE_BEAT_INDEX_PLACE_HOLDER)) {
                this.fileBeatServerId = currentServerId;
                return;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        this.fileBeatServerId = this.getWindowsCpuSerialNumber();
    }

    private String getWindowsCpuSerialNumber() {
        StringBuilder result = new StringBuilder();
        try {
            String line;
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            String vbs1 = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";
            FileUtils.writeStringToFile(file, vbs1, StandardCharsets.UTF_8);
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
        }
        catch (Exception E) {
            E.printStackTrace();
            System.err.println("Windows CPU Exp : " + E.getMessage());
        }
        return result.toString().trim();
    }
}

