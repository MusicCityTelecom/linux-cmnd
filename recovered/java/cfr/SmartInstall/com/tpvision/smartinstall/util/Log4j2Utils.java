/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ProcessUtils;
import com.tpvision.smartinstall.util.SnowFlake;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.json.JSONObject;

public class Log4j2Utils {
    private static final SnowFlake SNOW_FLIACK = new SnowFlake(1L, 1L);
    private static final String FILE_BEAT_SERVICE_NAME = "filebeat";
    private static final String FILE_BEAT_SERVER_CONFIG_FILE = "server.json";
    private static final String FILE_BEAT_EXE_FILE = "filebeat.exe";
    private static final String FILE_BEAT_INDEX_PLACE_HOLDER = "{server-log-id}";
    public static final String FILE_BEAT_SERVER_ID = Log4j2Utils.getFileBeatServerId();

    private Log4j2Utils() {
    }

    public static void putSeverletRequestIdToThreadContext() {
        ThreadContext.put("RequestId", String.valueOf(SNOW_FLIACK.nextId()));
    }

    public static void initFileBeartService(boolean isStart) {
        boolean isFilebeatRunning = ProcessUtils.isProcessRunning(FILE_BEAT_EXE_FILE);
        if (isStart && !isFilebeatRunning) {
            File fileBeatServerConfigFile = new File(CommonConstants.FILE_BEAT_INSTALL_DIR + FILE_BEAT_SERVER_CONFIG_FILE);
            File fileBeatExeFile = new File(CommonConstants.FILE_BEAT_INSTALL_DIR + FILE_BEAT_EXE_FILE);
            if (!fileBeatServerConfigFile.exists() || !fileBeatExeFile.exists()) {
                System.err.println("filebeat not installed correctly, please confirm before start...");
                return;
            }
            Log4j2Utils.startFileBeatService(fileBeatServerConfigFile);
        } else if (!isStart && isFilebeatRunning) {
            ProcessUtils.stopService(FILE_BEAT_SERVICE_NAME);
        }
    }

    private static void startFileBeatService(File fileBeatServerConfigFile) {
        try {
            String serverConfig = FileUtils.readFileToString(fileBeatServerConfigFile, StandardCharsets.UTF_8);
            boolean notConfigBefore = serverConfig.contains(FILE_BEAT_INDEX_PLACE_HOLDER);
            if (notConfigBefore) {
                serverConfig = serverConfig.replace(FILE_BEAT_INDEX_PLACE_HOLDER, FILE_BEAT_SERVER_ID);
                FileUtils.writeStringToFile(fileBeatServerConfigFile, serverConfig, StandardCharsets.UTF_8);
            }
            ProcessUtils.startService(FILE_BEAT_SERVICE_NAME);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFileBeatServerId() {
        try {
            File fileBeatServerConfigFile = new File(CommonConstants.FILE_BEAT_INSTALL_DIR + FILE_BEAT_SERVER_CONFIG_FILE);
            JSONObject serverJson = new JSONObject(FileUtils.readFileToString(fileBeatServerConfigFile, StandardCharsets.UTF_8));
            String currentServerId = serverJson.getString("server_id");
            if (!StringUtils.equalsIgnoreCase(currentServerId, FILE_BEAT_INDEX_PLACE_HOLDER)) {
                return currentServerId;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return Log4j2Utils.getWindowsCpuSerialNumber();
    }

    private static String getWindowsCpuSerialNumber() {
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

    public static void main(String[] args) {
        System.out.println(Log4j2Utils.getWindowsCpuSerialNumber());
    }
}

